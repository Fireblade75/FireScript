package nl.firepy.firescript.component.internal;

import nl.firepy.firescript.type.FSType;

public interface FireScriptExpression extends FireScriptComponent<String> {
    
    /**
     * Can this value be changed on runtime?
     * @return true if the value is determined on compile time
     */
    boolean isConstant();

    /**
     * Get the return type of the expression
     * @return the type of the expression
     */
    FSType getType();
}
