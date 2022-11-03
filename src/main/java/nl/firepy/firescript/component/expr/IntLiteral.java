package nl.firepy.firescript.component.expr;

import java.util.ArrayList;

import nl.firepy.firescript.type.StdType;
import nl.firepy.firescript.type.Value;

public class IntLiteral extends ExprComponent {

    private long intValue;

    public IntLiteral(long intValue) {
        super(new Value(StdType.INT, false));
        this.intValue = intValue;
    }

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add(Long.toString(intValue));
        return asm;
    }
}
