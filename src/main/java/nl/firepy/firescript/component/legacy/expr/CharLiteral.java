package nl.firepy.firescript.component.legacy.expr;

import java.util.ArrayList;

import nl.firepy.firescript.component.expr.ExprComponent;
import nl.firepy.firescript.type.StdType;
import nl.firepy.firescript.type.Value;

public class CharLiteral extends ExprComponent {

    private char charValue;

    public CharLiteral(char charValue) {
        super(new Value(StdType.CHAR, false));
        this.charValue = charValue;
    }

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\tbipush " + (int)(charValue));
        return asm;
    }
}
