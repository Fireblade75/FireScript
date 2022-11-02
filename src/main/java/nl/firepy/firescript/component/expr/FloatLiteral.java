package nl.firepy.firescript.component.expr;

import java.util.ArrayList;

import nl.firepy.firescript.type.StdType;
import nl.firepy.firescript.type.Value;

public class FloatLiteral extends ExprComponent {

    float floatValue;

    public FloatLiteral(float floatValue) {
        super(new Value(StdType.FLOAT, false));
        this.floatValue = floatValue;
    }

    @Override
    boolean isStatic() {
        return true;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\tldc " + floatValue);
        return asm;
    }
}
