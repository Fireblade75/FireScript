package nl.firepy.firescript.component.expr;

import nl.firepy.firescript.component.internal.FireScriptExpression;
import nl.firepy.firescript.type.FSType;

public abstract class Variable implements FireScriptExpression {
    private String variableName;
    private FSType type;
    private boolean isConstant;

    public Variable(String variableName, FSType type, boolean isConstant) {
        this.variableName = variableName;
        this.type = type;
        this.isConstant = isConstant;
    }

    @Override
    public FSType getType() {
        return type;
    }

    @Override
    public boolean isConstant() {
        return isConstant;
    }

    public String getVariableName() {
        return variableName;
    }
}
