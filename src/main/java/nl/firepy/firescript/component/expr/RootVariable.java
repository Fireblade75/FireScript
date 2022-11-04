package nl.firepy.firescript.component.expr;

import nl.firepy.firescript.type.FSType;

public class RootVariable extends Variable {

    public RootVariable(String variableName, FSType type, boolean isConstant) {
        super(variableName, type, isConstant);
    }

    @Override
    public String generateCode() {
        return getVariableName();
    }
    
}
