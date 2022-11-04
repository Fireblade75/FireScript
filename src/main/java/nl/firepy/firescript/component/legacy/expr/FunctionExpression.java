package nl.firepy.firescript.component.legacy.expr;

import java.util.ArrayList;

import nl.firepy.firescript.compiler.scope.Scope;
import nl.firepy.firescript.component.BlockComponent;
import nl.firepy.firescript.component.internal.FireScriptComponent;
import nl.firepy.firescript.component.legacy.ParamList;
import nl.firepy.firescript.type.Value;

public class FunctionExpression implements FireScriptComponent {

    private ParamList paramList;
    private BlockComponent block;
    private String returnType;
    private Scope scope;

    public FunctionExpression(ParamList paramList, String returnType, Scope scope) {
        this.paramList = paramList;
        this.returnType = returnType;
        this.scope = scope;

        for(ParamList.ParamListItem param : paramList.items()) {
            // scope.addValue(param.getName(), new Value(param.getType(), false));
        }
    }

    @Override
    public ArrayList<String> generateCode() {
        return null;
    }

    public Scope getScope() {
        return scope;
    }

    public BlockComponent getBlock() {
        return block;
    }

    public ParamList getParamList() {
        return paramList;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setBlock(BlockComponent block) {
        this.block = block;
    }
}
