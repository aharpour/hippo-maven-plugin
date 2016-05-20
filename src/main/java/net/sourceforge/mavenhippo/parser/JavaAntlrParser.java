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

import net.sourceforge.mavenhippo.antlr.Java8BaseListener;
import net.sourceforge.mavenhippo.antlr.Java8Lexer;
import net.sourceforge.mavenhippo.antlr.Java8Listener;
import net.sourceforge.mavenhippo.antlr.Java8Parser;
import net.sourceforge.mavenhippo.antlr.Java8Parser.*;
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
public class JavaAntlrParser extends Java8BaseListener implements JavaParser, Java8Listener {

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
                Java8Lexer lexer = new Java8Lexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                Java8Parser parser = new Java8Parser(tokens);
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
        StringBuilder sb = new StringBuilder();
        for(int i = 1 ; i < ctx.getChildCount() - 1; i++)
        {
            sb.append(ctx.getChild(i));
        }
        packageName = sb.toString();
    }

    @Override
    public void exitImportDeclaration(ImportDeclarationContext ctx) {
        if (ctx.getChildCount() > 0 && ctx.getChild(0).getChildCount() > 0 && NODE_INTERFACE_FULLY_QUALLIFIED_NAME.equals(ctx.getChild(0).getChild(1).getText())) {
            annotations.add("Node");
        }
    }

    @Override
    public void exitAnnotation(AnnotationContext ctx) {
        if (ctx.getChildCount() > 0 && ctx.getChild(0).getChildCount() == 5 && annotations.contains(ctx.getChild(0).getChild(1).getText())) {
            ParseTree child = ctx.getChild(0).getChild(3);
            String type = child.getChild(0).getChild(2).getText();
            if (type.startsWith(QUOTATION_MARK) && type.endsWith(QUOTATION_MARK) && type.length() > 2) {
                jcrType = type.substring(1, type.length() - 1);
            }

        }
    }


    @Override
    public void exitClassDeclaration(ClassDeclarationContext ctx) {
        if (jcrType != null) {
            ParseTree item = ctx.getChild(0);
            String name = "";
            for(int i = 0; i < item.getChildCount(); i++){
                if(item.getChild(i).getText().equals("class")){
                    name = item.getChild(i + 1).getText();
                }
            }
            HippoBeanClass beanClass = new HippoBeanClass(packageName, name, jcrType);
            log.debug("Class declaration: package: "+ packageName + ", Class: " + name + ", jcrType: " + jcrType);
            map.put(jcrType, beanClass);
            jcrType = null;
        }
    }
}
