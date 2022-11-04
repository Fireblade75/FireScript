package nl.firepy.firescript.type;

import nl.firepy.firescript.compiler.scope.Scope;

public class Value {
    private String type;
    private boolean constant;

    public Value(String type, boolean constant) {
        this.type = type;
        this.constant = constant;
    }

    /**
     * Get the type of the variable in the code itself
     * @return the name of the type
     * @param scope
     */
    public String getType(Scope scope) {
        return type;
    }

    public boolean isConstant(){
        return constant;
    }
}
