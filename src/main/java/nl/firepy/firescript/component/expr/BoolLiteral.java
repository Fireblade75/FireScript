package nl.firepy.firescript.component.expr;

import nl.firepy.firescript.component.internal.FireScriptExpression;
import nl.firepy.firescript.type.FSType;

public class BoolLiteral implements FireScriptExpression {

    private boolean boolValue;

    public BoolLiteral(boolean boolValue) {
        this.boolValue = boolValue;
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public FSType getType() {
        return FSType.BOOL;
    }

    @Override
    public String generateCode() {
        return Boolean.toString(boolValue);
    }
}
