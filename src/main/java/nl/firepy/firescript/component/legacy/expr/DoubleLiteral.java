package nl.firepy.firescript.component.legacy.expr;

import java.util.ArrayList;

import nl.firepy.firescript.component.expr.ExprComponent;
import nl.firepy.firescript.type.StdType;
import nl.firepy.firescript.type.Value;

public class DoubleLiteral extends ExprComponent {

    private double doubleValue;

    public DoubleLiteral(double doubleValue) {
        super(new Value(StdType.DOUBLE, false));
        this.doubleValue = doubleValue;
    }

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\tldc2_w " + doubleValue);
        return asm;
    }
}
