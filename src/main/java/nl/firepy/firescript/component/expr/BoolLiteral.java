package nl.firepy.firescript.component.expr;

import java.util.ArrayList;

import nl.firepy.firescript.type.StdType;
import nl.firepy.firescript.type.Value;

public class BoolLiteral extends ExprComponent {

    private boolean boolValue;

    public BoolLiteral(boolean boolValue) {
        super(new Value(StdType.BOOL, false));
        this.boolValue = boolValue;
    }

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add(Boolean.toString(boolValue));
        return asm;
    }
}
