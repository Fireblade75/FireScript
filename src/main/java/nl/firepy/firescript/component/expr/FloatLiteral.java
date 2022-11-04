package nl.firepy.firescript.component.expr;

import nl.firepy.firescript.component.internal.FireScriptExpression;
import nl.firepy.firescript.type.FSType;

public class FloatLiteral implements FireScriptExpression {

    private double floatValue;

    public FloatLiteral(double floatValue) {
        this.floatValue = floatValue;
    }

    @Override
    public boolean isConstant() {
        return true;
    }
    
    @Override
    public FSType getType() {
        return FSType.FLOAT;
    }

    @Override
    public String generateCode() {
        return Double.toString(floatValue);
    }
}
