package nl.saxion.viglo;

import nl.saxion.viglo.type.ClassHeader;

import java.util.ArrayList;
import java.util.List;

public class VigloMethodVisitor extends VigloBaseVisitor<Object> {

    private ClassHeader classHeader;

    @Override
    public ClassHeader visitProgram(VigloParser.ProgramContext ctx) {
        if(ctx.classBlock() != null) {
            return visitClassBlock(ctx.classBlock());
        } else {
            return visitStructBlock(ctx.structBlock());
        }
    }

    @Override
    public ClassHeader visitStructBlock(VigloParser.StructBlockContext ctx) {
        return new ClassHeader(ctx.NAME().getText());
    }

    @Override
    public ClassHeader visitClassBlock(VigloParser.ClassBlockContext ctx) {
        classHeader = new ClassHeader(ctx.NAME().getText());
        visit(ctx.rootBlock());
        return classHeader;
    }

    @Override
    public String visitRootBlock(VigloParser.RootBlockContext ctx) {
        List<VigloParser.RootStatementContext> statments = ctx.rootStatement();
        for (VigloParser.RootStatementContext statment : statments) {
            visit(statment);
        }
        return "block";
    }

    @Override
    public Object visitDeclareFunction(VigloParser.DeclareFunctionContext ctx) {
        String functionName = ctx.NAME().getText();
        String returnType = ctx.functionStatement().type().getText();
        ArrayList<String> params = visitParamList(ctx.functionStatement().paramList());
        FunctionDescriptor function =
                new FunctionDescriptor(functionName, classHeader.getClassName(), params, returnType);

        classHeader.addFunction(function);

        return visit(ctx.functionStatement());
    }

    @Override
    public ArrayList<String> visitParamList(VigloParser.ParamListContext ctx) {
        ArrayList<String> types = new ArrayList<>();
        for(VigloParser.ParamItemContext item : ctx.paramItem()) {
            types.add(item.type().getText());
        }
        return types;
    }

    public ClassHeader getClassHeader() {
        return classHeader;
    }
}

