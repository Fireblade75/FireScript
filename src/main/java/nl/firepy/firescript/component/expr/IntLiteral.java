package nl.firepy.firescript.component.expr;

import nl.firepy.firescript.component.internal.FireScriptExpression;
import nl.firepy.firescript.type.FSType;

public class IntLiteral implements FireScriptExpression {

    private long intValue;

    public IntLiteral(long intValue) {
        this.intValue = intValue;
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public FSType getType() {
        return FSType.INT;
    }

    @Override
    public String generateCode() {
        return Long.toString(intValue);
    }
}
