/*
 *    Copyright 2013 Ebrahim Aharpour
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   
 *   Partially sponsored by Smile B.V
 */
package net.sourceforge.mavenhippo.parser;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sourceforge.mavenhippo.antlr.Java7BaseListener;
import net.sourceforge.mavenhippo.antlr.Java7Lexer;
import net.sourceforge.mavenhippo.antlr.Java7Listener;
import net.sourceforge.mavenhippo.antlr.Java7Parser;
import net.sourceforge.mavenhippo.antlr.Java7Parser.AnnotationContext;
import net.sourceforge.mavenhippo.antlr.Java7Parser.ClassDeclarationContext;
import net.sourceforge.mavenhippo.antlr.Java7Parser.ImportDeclarationContext;
import net.sourceforge.mavenhippo.antlr.Java7Parser.PackageDeclarationContext;
import net.sourceforge.mavenhippo.model.HippoBeanClass;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.maven.plugin.logging.Log;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class JavaAntlrParser extends Java7BaseListener implements JavaParser, Java7Listener {

    private static final String QUOTATION_MARK = "\"";
    private static final String NODE_INTERFACE_FULLY_QUALLIFIED_NAME = "org.hippoecm.hst.content.beans.Node";
    private final Log log;
    private String jcrType;
    private String packageName = "";
    private Map<String, HippoBeanClass> map = new HashMap<String, HippoBeanClass>();
    private final Set<String> annotations = new HashSet<String>() {

        private static final long serialVersionUID = 1L;

        {
            add(NODE_INTERFACE_FULLY_QUALLIFIED_NAME);
        }
    };

    public JavaAntlrParser(Log log) {
        this.log = log;
    }

    @Override
    public Map<String, HippoBeanClass> parse(File javaFile) {
        try {
            if (javaFile.exists()) {
                ANTLRInputStream input = new ANTLRFileStream(javaFile.getAbsolutePath());
                Java7Lexer lexer = new Java7Lexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                Java7Parser parser = new Java7Parser(tokens);
                ParserRuleContext tree = parser.compilationUnit();
                ParseTreeWalker walker = new ParseTreeWalker();
                walker.walk(this, tree);
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        return map;
    }

    @Override
    public void exitPackageDeclaration(PackageDeclarationContext ctx) {
        packageName = ctx.getChild(1).getText();
    }

    @Override
    public void exitImportDeclaration(ImportDeclarationContext ctx) {
        if (ctx.getChildCount() > 0 && "org.hippoecm.hst.content.beans.Node".equals(ctx.getChild(1).getText())) {
            annotations.add("Node");
        }
    }

    @Override
    public void exitAnnotation(AnnotationContext ctx) {
        if (ctx.getChildCount() == 5 && annotations.contains(ctx.getChild(1).getText())) {
            ParseTree child = ctx.getChild(3);
            String type = child.getChild(0).getChild(2).getText();
            if (type.startsWith(QUOTATION_MARK) && type.endsWith(QUOTATION_MARK) && type.length() > 2) {
                jcrType = type.substring(1, type.length() - 1);
            }

        }
    }

    @Override
    public void enterClassDeclaration(ClassDeclarationContext ctx) {
        if (jcrType != null) {
            HippoBeanClass beanClass = new HippoBeanClass(packageName, ctx.getChild(0).getChild(1).getText(), jcrType);
            map.put(jcrType, beanClass);
            jcrType = null;
        }

    }

}
