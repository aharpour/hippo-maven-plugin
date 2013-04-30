package com.aharpour.ebrahim.parser;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.maven.plugin.logging.Log;

import com.aharpour.ebrahim.antlr.*;
import com.aharpour.ebrahim.antlr.Java7Parser.AnnotationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.AnnotationMethodContext;
import com.aharpour.ebrahim.antlr.Java7Parser.AnnotationNameContext;
import com.aharpour.ebrahim.antlr.Java7Parser.AnnotationTypeDeclarationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.AnnotationTypeElementContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ArgumentsContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ArrayCreatorRestContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ArrayInitializerContext;
import com.aharpour.ebrahim.antlr.Java7Parser.BlockContext;
import com.aharpour.ebrahim.antlr.Java7Parser.BlockStatementContext;
import com.aharpour.ebrahim.antlr.Java7Parser.BooleanLiteralContext;
import com.aharpour.ebrahim.antlr.Java7Parser.BoundContext;
import com.aharpour.ebrahim.antlr.Java7Parser.CatchClauseContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ClassBodyContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ClassBodyDeclarationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ClassCreatorRestContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ClassDeclarationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ClassOrInterfaceDeclarationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ClassOrInterfaceTypeContext;
import com.aharpour.ebrahim.antlr.Java7Parser.CompilationUnitContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ConstantDeclaratorContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ConstantDeclaratorRestContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ConstantDeclaratorsRestContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ConstantExpressionContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ConstructorDeclarationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.CreatedNameContext;
import com.aharpour.ebrahim.antlr.Java7Parser.CreatorContext;
import com.aharpour.ebrahim.antlr.Java7Parser.DefaultValueContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ElementValueArrayInitializerContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ElementValueContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ElementValuePairContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ElementValuePairsContext;
import com.aharpour.ebrahim.antlr.Java7Parser.EnhancedForControlContext;
import com.aharpour.ebrahim.antlr.Java7Parser.EnumConstantContext;
import com.aharpour.ebrahim.antlr.Java7Parser.EnumConstantNameContext;
import com.aharpour.ebrahim.antlr.Java7Parser.EnumConstantsContext;
import com.aharpour.ebrahim.antlr.Java7Parser.EnumDeclarationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ExplicitConstructorInvocationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ExplicitGenericInvocationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ExpressionContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ExpressionListContext;
import com.aharpour.ebrahim.antlr.Java7Parser.FieldDeclarationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ForControlContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ForInitContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ForUpdateContext;
import com.aharpour.ebrahim.antlr.Java7Parser.FormalParameterDeclarationsContext;
import com.aharpour.ebrahim.antlr.Java7Parser.FormalParameterVariablesContext;
import com.aharpour.ebrahim.antlr.Java7Parser.FormalParametersContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ImportDeclarationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.InnerCreatorContext;
import com.aharpour.ebrahim.antlr.Java7Parser.IntegerLiteralContext;
import com.aharpour.ebrahim.antlr.Java7Parser.InterfaceBodyContext;
import com.aharpour.ebrahim.antlr.Java7Parser.InterfaceDeclarationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.InterfaceGenericMethodDeclContext;
import com.aharpour.ebrahim.antlr.Java7Parser.InterfaceMemberDeclContext;
import com.aharpour.ebrahim.antlr.Java7Parser.InterfaceMethodDeclaratorRestContext;
import com.aharpour.ebrahim.antlr.Java7Parser.InterfaceMethodOrFieldDeclContext;
import com.aharpour.ebrahim.antlr.Java7Parser.InterfaceMethodOrFieldRestContext;
import com.aharpour.ebrahim.antlr.Java7Parser.LiteralContext;
import com.aharpour.ebrahim.antlr.Java7Parser.LocalVariableDeclarationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.MemberDeclContext;
import com.aharpour.ebrahim.antlr.Java7Parser.MethodBodyContext;
import com.aharpour.ebrahim.antlr.Java7Parser.MethodDeclarationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ModifierContext;
import com.aharpour.ebrahim.antlr.Java7Parser.NonWildcardTypeArgumentsContext;
import com.aharpour.ebrahim.antlr.Java7Parser.NormalClassDeclarationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.NormalInterfaceDeclarationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.PackageDeclarationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.PackageOrTypeNameContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ParExpressionContext;
import com.aharpour.ebrahim.antlr.Java7Parser.PrimaryContext;
import com.aharpour.ebrahim.antlr.Java7Parser.PrimitiveTypeContext;
import com.aharpour.ebrahim.antlr.Java7Parser.QualifiedIdentifierContext;
import com.aharpour.ebrahim.antlr.Java7Parser.QualifiedIdentifierListContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ResourceContext;
import com.aharpour.ebrahim.antlr.Java7Parser.ResourcesContext;
import com.aharpour.ebrahim.antlr.Java7Parser.StatementContext;
import com.aharpour.ebrahim.antlr.Java7Parser.StatementExpressionContext;
import com.aharpour.ebrahim.antlr.Java7Parser.SwitchBlockContext;
import com.aharpour.ebrahim.antlr.Java7Parser.SwitchBlockStatementGroupContext;
import com.aharpour.ebrahim.antlr.Java7Parser.SwitchLabelContext;
import com.aharpour.ebrahim.antlr.Java7Parser.TryStatementContext;
import com.aharpour.ebrahim.antlr.Java7Parser.TypeArgumentContext;
import com.aharpour.ebrahim.antlr.Java7Parser.TypeArgumentsContext;
import com.aharpour.ebrahim.antlr.Java7Parser.TypeDeclarationContext;
import com.aharpour.ebrahim.antlr.Java7Parser.TypeListContext;
import com.aharpour.ebrahim.antlr.Java7Parser.TypeNameContext;
import com.aharpour.ebrahim.antlr.Java7Parser.TypeParameterContext;
import com.aharpour.ebrahim.antlr.Java7Parser.TypeParametersContext;
import com.aharpour.ebrahim.antlr.Java7Parser.TypeRefContext;
import com.aharpour.ebrahim.antlr.Java7Parser.VariableDeclaratorContext;
import com.aharpour.ebrahim.antlr.Java7Parser.VariableDeclaratorIdContext;
import com.aharpour.ebrahim.antlr.Java7Parser.VariableDeclaratorsContext;
import com.aharpour.ebrahim.antlr.Java7Parser.VariableInitializerContext;
import com.aharpour.ebrahim.antlr.Java7Parser.VariableModifierContext;
import com.aharpour.ebrahim.antlr.Java7Parser.VoidInterfaceMethodDeclaratorRestContext;
import com.aharpour.ebrahim.model.HippoBeanClass;

public class JavaAntlrParser implements JavaParser, Java7Listener {

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
			// TODO write an appropriate message
			log.warn(e);
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
		if (ctx.getChildCount() == 5) {
			if (annotations.contains(ctx.getChild(1).getText())) {
				ParseTree child = ctx.getChild(3);
				String type = child.getChild(0).getChild(2).getText();
				if (type.startsWith(QUOTATION_MARK) && type.endsWith(QUOTATION_MARK) && type.length() > 2) {
					jcrType = type.substring(1, type.length() - 1);
				}
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

	@Override
	public void enterEveryRule(ParserRuleContext arg0) {

	}

	@Override
	public void exitEveryRule(ParserRuleContext arg0) {

	}

	@Override
	public void visitErrorNode(ErrorNode arg0) {

	}

	@Override
	public void visitTerminal(TerminalNode arg0) {

	}

	@Override
	public void enterInnerCreator(InnerCreatorContext ctx) {

	}

	@Override
	public void exitInnerCreator(InnerCreatorContext ctx) {

	}

	@Override
	public void enterExpression(ExpressionContext ctx) {

	}

	@Override
	public void exitExpression(ExpressionContext ctx) {

	}

	@Override
	public void enterAnnotationTypeDeclaration(AnnotationTypeDeclarationContext ctx) {

	}

	@Override
	public void exitAnnotationTypeDeclaration(AnnotationTypeDeclarationContext ctx) {

	}

	@Override
	public void enterVariableDeclarator(VariableDeclaratorContext ctx) {

	}

	@Override
	public void exitVariableDeclarator(VariableDeclaratorContext ctx) {

	}

	@Override
	public void enterResources(ResourcesContext ctx) {

	}

	@Override
	public void exitResources(ResourcesContext ctx) {

	}

	@Override
	public void enterExpressionList(ExpressionListContext ctx) {

	}

	@Override
	public void exitExpressionList(ExpressionListContext ctx) {

	}

	@Override
	public void enterQualifiedIdentifier(QualifiedIdentifierContext ctx) {

	}

	@Override
	public void exitQualifiedIdentifier(QualifiedIdentifierContext ctx) {

	}

	@Override
	public void enterTypeDeclaration(TypeDeclarationContext ctx) {

	}

	@Override
	public void exitTypeDeclaration(TypeDeclarationContext ctx) {

	}

	@Override
	public void enterForUpdate(ForUpdateContext ctx) {

	}

	@Override
	public void exitForUpdate(ForUpdateContext ctx) {

	}

	@Override
	public void enterFormalParameterVariables(FormalParameterVariablesContext ctx) {

	}

	@Override
	public void exitFormalParameterVariables(FormalParameterVariablesContext ctx) {

	}

	@Override
	public void enterElementValueArrayInitializer(ElementValueArrayInitializerContext ctx) {

	}

	@Override
	public void exitElementValueArrayInitializer(ElementValueArrayInitializerContext ctx) {

	}

	@Override
	public void enterAnnotation(AnnotationContext ctx) {

	}

	@Override
	public void enterMemberDecl(MemberDeclContext ctx) {

	}

	@Override
	public void exitMemberDecl(MemberDeclContext ctx) {

	}

	@Override
	public void enterEnumConstant(EnumConstantContext ctx) {

	}

	@Override
	public void exitEnumConstant(EnumConstantContext ctx) {

	}

	@Override
	public void enterAnnotationName(AnnotationNameContext ctx) {

	}

	@Override
	public void exitAnnotationName(AnnotationNameContext ctx) {

	}

	@Override
	public void enterEnhancedForControl(EnhancedForControlContext ctx) {

	}

	@Override
	public void exitEnhancedForControl(EnhancedForControlContext ctx) {

	}

	@Override
	public void enterPrimary(PrimaryContext ctx) {

	}

	@Override
	public void exitPrimary(PrimaryContext ctx) {

	}

	@Override
	public void enterNormalClassDeclaration(NormalClassDeclarationContext ctx) {

	}

	@Override
	public void exitNormalClassDeclaration(NormalClassDeclarationContext ctx) {

	}

	@Override
	public void enterClassBody(ClassBodyContext ctx) {

	}

	@Override
	public void exitClassBody(ClassBodyContext ctx) {

	}

	@Override
	public void enterDefaultValue(DefaultValueContext ctx) {

	}

	@Override
	public void exitDefaultValue(DefaultValueContext ctx) {

	}

	@Override
	public void enterImportDeclaration(ImportDeclarationContext ctx) {

	}

	@Override
	public void enterVariableModifier(VariableModifierContext ctx) {

	}

	@Override
	public void exitVariableModifier(VariableModifierContext ctx) {

	}

	@Override
	public void enterEnumConstantName(EnumConstantNameContext ctx) {

	}

	@Override
	public void exitEnumConstantName(EnumConstantNameContext ctx) {

	}

	@Override
	public void enterCreatedName(CreatedNameContext ctx) {

	}

	@Override
	public void exitCreatedName(CreatedNameContext ctx) {

	}

	@Override
	public void enterInterfaceDeclaration(InterfaceDeclarationContext ctx) {

	}

	@Override
	public void exitInterfaceDeclaration(InterfaceDeclarationContext ctx) {

	}

	@Override
	public void enterQualifiedIdentifierList(QualifiedIdentifierListContext ctx) {

	}

	@Override
	public void exitQualifiedIdentifierList(QualifiedIdentifierListContext ctx) {

	}

	@Override
	public void enterPackageDeclaration(PackageDeclarationContext ctx) {

	}

	@Override
	public void enterTypeRef(TypeRefContext ctx) {

	}

	@Override
	public void exitTypeRef(TypeRefContext ctx) {

	}

	@Override
	public void enterConstantDeclarator(ConstantDeclaratorContext ctx) {

	}

	@Override
	public void exitConstantDeclarator(ConstantDeclaratorContext ctx) {

	}

	@Override
	public void enterConstantDeclaratorRest(ConstantDeclaratorRestContext ctx) {

	}

	@Override
	public void exitConstantDeclaratorRest(ConstantDeclaratorRestContext ctx) {

	}

	@Override
	public void enterElementValuePairs(ElementValuePairsContext ctx) {

	}

	@Override
	public void exitElementValuePairs(ElementValuePairsContext ctx) {

	}

	@Override
	public void enterVariableDeclarators(VariableDeclaratorsContext ctx) {

	}

	@Override
	public void exitVariableDeclarators(VariableDeclaratorsContext ctx) {

	}

	@Override
	public void enterTypeArguments(TypeArgumentsContext ctx) {

	}

	@Override
	public void exitTypeArguments(TypeArgumentsContext ctx) {

	}

	@Override
	public void enterClassCreatorRest(ClassCreatorRestContext ctx) {

	}

	@Override
	public void exitClassCreatorRest(ClassCreatorRestContext ctx) {

	}

	@Override
	public void enterSwitchBlock(SwitchBlockContext ctx) {

	}

	@Override
	public void exitSwitchBlock(SwitchBlockContext ctx) {

	}

	@Override
	public void enterStatement(StatementContext ctx) {

	}

	@Override
	public void exitStatement(StatementContext ctx) {

	}

	@Override
	public void enterAnnotationMethod(AnnotationMethodContext ctx) {

	}

	@Override
	public void exitAnnotationMethod(AnnotationMethodContext ctx) {

	}

	@Override
	public void enterModifier(ModifierContext ctx) {

	}

	@Override
	public void exitModifier(ModifierContext ctx) {

	}

	@Override
	public void enterCatchClause(CatchClauseContext ctx) {

	}

	@Override
	public void exitCatchClause(CatchClauseContext ctx) {

	}

	@Override
	public void enterEnumConstants(EnumConstantsContext ctx) {

	}

	@Override
	public void exitEnumConstants(EnumConstantsContext ctx) {

	}

	@Override
	public void enterInterfaceBody(InterfaceBodyContext ctx) {

	}

	@Override
	public void exitInterfaceBody(InterfaceBodyContext ctx) {

	}

	@Override
	public void enterConstantExpression(ConstantExpressionContext ctx) {

	}

	@Override
	public void exitConstantExpression(ConstantExpressionContext ctx) {

	}

	@Override
	public void enterClassBodyDeclaration(ClassBodyDeclarationContext ctx) {

	}

	@Override
	public void exitClassBodyDeclaration(ClassBodyDeclarationContext ctx) {

	}

	@Override
	public void enterPackageOrTypeName(PackageOrTypeNameContext ctx) {

	}

	@Override
	public void exitPackageOrTypeName(PackageOrTypeNameContext ctx) {

	}

	@Override
	public void enterForControl(ForControlContext ctx) {

	}

	@Override
	public void exitForControl(ForControlContext ctx) {

	}

	@Override
	public void enterEnumDeclaration(EnumDeclarationContext ctx) {

	}

	@Override
	public void exitEnumDeclaration(EnumDeclarationContext ctx) {

	}

	@Override
	public void enterLocalVariableDeclaration(LocalVariableDeclarationContext ctx) {

	}

	@Override
	public void exitLocalVariableDeclaration(LocalVariableDeclarationContext ctx) {

	}

	@Override
	public void enterTypeList(TypeListContext ctx) {

	}

	@Override
	public void exitTypeList(TypeListContext ctx) {

	}

	@Override
	public void enterTypeParameter(TypeParameterContext ctx) {

	}

	@Override
	public void exitTypeParameter(TypeParameterContext ctx) {

	}

	@Override
	public void enterVariableDeclaratorId(VariableDeclaratorIdContext ctx) {

	}

	@Override
	public void exitVariableDeclaratorId(VariableDeclaratorIdContext ctx) {

	}

	@Override
	public void enterExplicitConstructorInvocation(ExplicitConstructorInvocationContext ctx) {

	}

	@Override
	public void exitExplicitConstructorInvocation(ExplicitConstructorInvocationContext ctx) {

	}

	@Override
	public void enterInterfaceMethodDeclaratorRest(InterfaceMethodDeclaratorRestContext ctx) {

	}

	@Override
	public void exitInterfaceMethodDeclaratorRest(InterfaceMethodDeclaratorRestContext ctx) {

	}

	@Override
	public void enterElementValue(ElementValueContext ctx) {

	}

	@Override
	public void exitElementValue(ElementValueContext ctx) {

	}

	@Override
	public void enterCompilationUnit(CompilationUnitContext ctx) {

	}

	@Override
	public void exitCompilationUnit(CompilationUnitContext ctx) {

	}

	@Override
	public void enterStatementExpression(StatementExpressionContext ctx) {

	}

	@Override
	public void exitStatementExpression(StatementExpressionContext ctx) {

	}

	@Override
	public void enterClassOrInterfaceType(ClassOrInterfaceTypeContext ctx) {

	}

	@Override
	public void exitClassOrInterfaceType(ClassOrInterfaceTypeContext ctx) {

	}

	@Override
	public void enterFormalParameterDeclarations(FormalParameterDeclarationsContext ctx) {

	}

	@Override
	public void exitFormalParameterDeclarations(FormalParameterDeclarationsContext ctx) {

	}

	@Override
	public void enterBlock(BlockContext ctx) {

	}

	@Override
	public void exitBlock(BlockContext ctx) {

	}

	@Override
	public void enterVariableInitializer(VariableInitializerContext ctx) {

	}

	@Override
	public void exitVariableInitializer(VariableInitializerContext ctx) {

	}

	@Override
	public void enterBlockStatement(BlockStatementContext ctx) {

	}

	@Override
	public void exitBlockStatement(BlockStatementContext ctx) {

	}

	@Override
	public void enterIntegerLiteral(IntegerLiteralContext ctx) {

	}

	@Override
	public void exitIntegerLiteral(IntegerLiteralContext ctx) {

	}

	@Override
	public void enterInterfaceMemberDecl(InterfaceMemberDeclContext ctx) {

	}

	@Override
	public void exitInterfaceMemberDecl(InterfaceMemberDeclContext ctx) {

	}

	@Override
	public void enterCreator(CreatorContext ctx) {

	}

	@Override
	public void exitCreator(CreatorContext ctx) {

	}

	@Override
	public void enterConstantDeclaratorsRest(ConstantDeclaratorsRestContext ctx) {

	}

	@Override
	public void exitConstantDeclaratorsRest(ConstantDeclaratorsRestContext ctx) {

	}

	@Override
	public void enterInterfaceGenericMethodDecl(InterfaceGenericMethodDeclContext ctx) {

	}

	@Override
	public void exitInterfaceGenericMethodDecl(InterfaceGenericMethodDeclContext ctx) {

	}

	@Override
	public void enterInterfaceMethodOrFieldDecl(InterfaceMethodOrFieldDeclContext ctx) {

	}

	@Override
	public void exitInterfaceMethodOrFieldDecl(InterfaceMethodOrFieldDeclContext ctx) {

	}

	@Override
	public void enterTryStatement(TryStatementContext ctx) {

	}

	@Override
	public void exitTryStatement(TryStatementContext ctx) {

	}

	@Override
	public void enterFieldDeclaration(FieldDeclarationContext ctx) {

	}

	@Override
	public void exitFieldDeclaration(FieldDeclarationContext ctx) {

	}

	@Override
	public void enterNormalInterfaceDeclaration(NormalInterfaceDeclarationContext ctx) {

	}

	@Override
	public void exitNormalInterfaceDeclaration(NormalInterfaceDeclarationContext ctx) {

	}

	@Override
	public void enterExplicitGenericInvocation(ExplicitGenericInvocationContext ctx) {

	}

	@Override
	public void exitExplicitGenericInvocation(ExplicitGenericInvocationContext ctx) {

	}

	@Override
	public void enterMethodDeclaration(MethodDeclarationContext ctx) {

	}

	@Override
	public void exitMethodDeclaration(MethodDeclarationContext ctx) {

	}

	@Override
	public void enterParExpression(ParExpressionContext ctx) {

	}

	@Override
	public void exitParExpression(ParExpressionContext ctx) {

	}

	@Override
	public void enterSwitchLabel(SwitchLabelContext ctx) {

	}

	@Override
	public void exitSwitchLabel(SwitchLabelContext ctx) {

	}

	@Override
	public void enterConstructorDeclaration(ConstructorDeclarationContext ctx) {

	}

	@Override
	public void exitConstructorDeclaration(ConstructorDeclarationContext ctx) {

	}

	@Override
	public void enterTypeParameters(TypeParametersContext ctx) {

	}

	@Override
	public void exitTypeParameters(TypeParametersContext ctx) {

	}

	@Override
	public void enterAnnotationTypeElement(AnnotationTypeElementContext ctx) {

	}

	@Override
	public void exitAnnotationTypeElement(AnnotationTypeElementContext ctx) {

	}

	@Override
	public void enterResource(ResourceContext ctx) {

	}

	@Override
	public void exitResource(ResourceContext ctx) {

	}

	@Override
	public void exitClassDeclaration(ClassDeclarationContext ctx) {

	}

	@Override
	public void enterElementValuePair(ElementValuePairContext ctx) {

	}

	@Override
	public void exitElementValuePair(ElementValuePairContext ctx) {

	}

	@Override
	public void enterBooleanLiteral(BooleanLiteralContext ctx) {

	}

	@Override
	public void exitBooleanLiteral(BooleanLiteralContext ctx) {

	}

	@Override
	public void enterVoidInterfaceMethodDeclaratorRest(VoidInterfaceMethodDeclaratorRestContext ctx) {

	}

	@Override
	public void exitVoidInterfaceMethodDeclaratorRest(VoidInterfaceMethodDeclaratorRestContext ctx) {

	}

	@Override
	public void enterInterfaceMethodOrFieldRest(InterfaceMethodOrFieldRestContext ctx) {

	}

	@Override
	public void exitInterfaceMethodOrFieldRest(InterfaceMethodOrFieldRestContext ctx) {

	}

	@Override
	public void enterTypeName(TypeNameContext ctx) {

	}

	@Override
	public void exitTypeName(TypeNameContext ctx) {

	}

	@Override
	public void enterArguments(ArgumentsContext ctx) {

	}

	@Override
	public void exitArguments(ArgumentsContext ctx) {

	}

	@Override
	public void enterMethodBody(MethodBodyContext ctx) {

	}

	@Override
	public void exitMethodBody(MethodBodyContext ctx) {

	}

	@Override
	public void enterArrayInitializer(ArrayInitializerContext ctx) {

	}

	@Override
	public void exitArrayInitializer(ArrayInitializerContext ctx) {

	}

	@Override
	public void enterFormalParameters(FormalParametersContext ctx) {

	}

	@Override
	public void exitFormalParameters(FormalParametersContext ctx) {

	}

	@Override
	public void enterPrimitiveType(PrimitiveTypeContext ctx) {

	}

	@Override
	public void exitPrimitiveType(PrimitiveTypeContext ctx) {

	}

	@Override
	public void enterNonWildcardTypeArguments(NonWildcardTypeArgumentsContext ctx) {

	}

	@Override
	public void exitNonWildcardTypeArguments(NonWildcardTypeArgumentsContext ctx) {

	}

	@Override
	public void enterTypeArgument(TypeArgumentContext ctx) {

	}

	@Override
	public void exitTypeArgument(TypeArgumentContext ctx) {

	}

	@Override
	public void enterClassOrInterfaceDeclaration(ClassOrInterfaceDeclarationContext ctx) {

	}

	@Override
	public void exitClassOrInterfaceDeclaration(ClassOrInterfaceDeclarationContext ctx) {

	}

	@Override
	public void enterForInit(ForInitContext ctx) {

	}

	@Override
	public void exitForInit(ForInitContext ctx) {

	}

	@Override
	public void enterArrayCreatorRest(ArrayCreatorRestContext ctx) {

	}

	@Override
	public void exitArrayCreatorRest(ArrayCreatorRestContext ctx) {

	}

	@Override
	public void enterBound(BoundContext ctx) {

	}

	@Override
	public void exitBound(BoundContext ctx) {

	}

	@Override
	public void enterSwitchBlockStatementGroup(SwitchBlockStatementGroupContext ctx) {

	}

	@Override
	public void exitSwitchBlockStatementGroup(SwitchBlockStatementGroupContext ctx) {

	}

	@Override
	public void enterLiteral(LiteralContext ctx) {

	}

	@Override
	public void exitLiteral(LiteralContext ctx) {

	}

}
