package nl.firepy.firescript.component.expr;

import nl.firepy.firescript.component.internal.FireScriptComponent;
import nl.firepy.firescript.type.Value;

public abstract class ExprComponent implements FireScriptComponent {

    private Value value;

    public ExprComponent(Value value) {
        this.value = value;
    }

    /**
     * Can this value be changed on runtime?
     * @return true if the value is determined on compile time
     */
    public abstract boolean isStatic();

    public Value getValue() {
        return value;
    }
}
