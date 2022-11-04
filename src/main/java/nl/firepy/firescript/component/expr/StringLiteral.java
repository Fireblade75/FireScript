package nl.firepy.firescript.component.expr;

import nl.firepy.firescript.component.internal.FireScriptExpression;
import nl.firepy.firescript.type.FSType;

public class StringLiteral implements FireScriptExpression {

    private String stringValue;

    public StringLiteral(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public FSType getType() {
        return FSType.STRING;
    }

    @Override
    public String generateCode() {
        return "\"" + stringValue + "\"";
    }
}

