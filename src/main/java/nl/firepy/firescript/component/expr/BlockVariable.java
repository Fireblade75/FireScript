package nl.firepy.firescript.component.expr;

import nl.firepy.firescript.type.FSType;

public class BlockVariable extends Variable {

    public BlockVariable(String variableName, FSType type, boolean isConstant) {
        super(variableName, type, isConstant);
    }

    @Override
    public String generateCode() {
        return "self." + getVariableName();
    }
    
}
