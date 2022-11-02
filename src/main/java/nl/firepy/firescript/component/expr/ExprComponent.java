package nl.firepy.firescript.component.expr;

import nl.firepy.firescript.component.FireScriptComponent;
import nl.firepy.firescript.type.Value;

public abstract class ExprComponent implements FireScriptComponent {

    private Value value;

    public ExprComponent(Value value) {
        this.value = value;
    }

    abstract boolean isStatic();

    public Value getValue() {
        return value;
    }
}
