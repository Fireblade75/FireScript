package nl.firepy.firescript.component.expr;

import java.util.ArrayList;

import nl.firepy.firescript.type.StdType;
import nl.firepy.firescript.type.Value;

public class NullLiteral extends ExprComponent {
    public NullLiteral() {
        super(new Value(StdType.OBJECT, false));
    }

    @Override
    boolean isStatic() {
        return true;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\taconst_null");
        return asm;
    }
}
