package nl.firepy.firescript.component.legacy.expr;

import java.util.ArrayList;

import nl.firepy.firescript.component.expr.ExprComponent;
import nl.firepy.firescript.type.StdType;
import nl.firepy.firescript.type.Value;

public class LongLiteral extends ExprComponent {

    long longValue;

    public LongLiteral(long longValue) {
        super(new Value(StdType.LONG, false));
        this.longValue = longValue;
    }

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\tldc2_w " + longValue);
        return asm;
    }
}