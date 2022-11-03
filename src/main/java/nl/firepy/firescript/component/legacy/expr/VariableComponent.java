package nl.firepy.firescript.component.legacy.expr;

import java.util.ArrayList;

import nl.firepy.firescript.compiler.scope.Scope;
import nl.firepy.firescript.component.expr.ExprComponent;
import nl.firepy.firescript.type.FieldValue;
import nl.firepy.firescript.type.Value;

public class VariableComponent extends ExprComponent {

    private Value value;
    private Scope scope;
    private int localId;

    public VariableComponent(Value value, Scope scope, int localId) {
        super(value);
        this.value = value;
        this.scope = scope;
        this.localId = localId;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        if(value instanceof FieldValue) {
            FieldValue fieldValue = (FieldValue) value;
            asm.add("\taload_0");
            asm.add("\tgetfield " + fieldValue.getPath());
        } else {
            switch (value.getRawType(scope)) {
                case "int":
                    asm.add("\tiload " + localId);
                    break;
                case "long":
                    asm.add("\tlload " + localId);
                    break;
                case "float":
                    asm.add("\tfload " + localId);
                    break;
                case "double":
                    asm.add("\tdload " + localId);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        return asm;
    }

    @Override
    public boolean isStatic() {
        return value.isConstant();
    }
}
