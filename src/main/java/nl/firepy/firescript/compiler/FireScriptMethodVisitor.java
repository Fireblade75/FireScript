package nl.firepy.firescript.compiler;

import java.util.ArrayList;
import java.util.List;

import nl.firepy.firescript.type.ClassHeader;

public class FireScriptMethodVisitor extends FireScriptBaseVisitor<Object> {

    private ClassHeader classHeader;

    @Override
    public ClassHeader visitProgram(FireScriptParser.ProgramContext ctx) {
        if(ctx.classBlock() != null) {
            return visitClassBlock(ctx.classBlock());
        } else {
            return visitStructBlock(ctx.structBlock());
        }
    }

    @Override
    public ClassHeader visitStructBlock(FireScriptParser.StructBlockContext ctx) {
        return new ClassHeader(ctx.NAME().getText());
    }

    @Override
    public ClassHeader visitClassBlock(FireScriptParser.ClassBlockContext ctx) {
        classHeader = new ClassHeader(ctx.NAME().getText());
        visit(ctx.rootBlock());
        return classHeader;
    }

    @Override
    public String visitRootBlock(FireScriptParser.RootBlockContext ctx) {
        List<FireScriptParser.RootStatementContext> statments = ctx.rootStatement();
        for (FireScriptParser.RootStatementContext statment : statments) {
            visit(statment);
        }
        return "block";
    }

    @Override
    public Object visitDeclareFunction(FireScriptParser.DeclareFunctionContext ctx) {
        String functionName = ctx.NAME().getText();
        String returnType = ctx.functionStatement().type().getText();
        boolean isStatic = ctx.STATIC() != null;
        ArrayList<String> params = visitParamList(ctx.functionStatement().paramList());
        FunctionDescriptor function =
                new FunctionDescriptor(functionName, classHeader.getClassName(), params, returnType, isStatic);

        classHeader.addFunction(function);

        return visit(ctx.functionStatement());
    }

    @Override
    public Object visitDeclareOnlyStatement(FireScriptParser.DeclareOnlyStatementContext ctx) {
        String name = ctx.NAME().getText();
        String type = ctx.type().getText();
        classHeader.addField(name, type);
        return null;
    }

    @Override
    public ArrayList<String> visitParamList(FireScriptParser.ParamListContext ctx) {
        ArrayList<String> types = new ArrayList<>();
        for(FireScriptParser.ParamItemContext item : ctx.paramItem()) {
            types.add(item.type().getText());
        }
        return types;
    }

    public ClassHeader getClassHeader() {
        return classHeader;
    }
}

