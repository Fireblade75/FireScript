package nl.firepy.firescript.component.expr;

import java.util.ArrayList;

import nl.firepy.firescript.type.StdType;
import nl.firepy.firescript.type.Value;

public class FloatLiteral extends ExprComponent {

    double floatValue;

    public FloatLiteral(double floatValue) {
        super(new Value(StdType.FLOAT, false));
        this.floatValue = floatValue;
    }

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add(Double.toString(floatValue));
        return asm;
    }
}
