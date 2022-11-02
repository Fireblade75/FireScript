package nl.firepy.firescript.component.expr;

import java.util.ArrayList;

import nl.firepy.firescript.compiler.scope.Scope;
import nl.firepy.firescript.type.StdType;
import nl.firepy.firescript.type.Value;

public class NotExprComponent extends ExprComponent {

    private final Scope scope;
    private ExprComponent childExpr;

    public NotExprComponent(ExprComponent childExpr, Scope scope) {
        super(new Value(StdType.BOOL, childExpr.isStatic()));
        this.childExpr = childExpr;
        this.scope = scope;
    }

    @Override
    boolean isStatic() {
        return childExpr.isStatic();
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>(childExpr.generateCode());
        String endLabel = scope.getLabel();
        String falseLabel = scope.getLabel();
        asm.add("\tifne " + falseLabel);
        asm.add("\ticonst_1");
        asm.add("\tgoto " + endLabel);
        asm.add(falseLabel + ":");
        asm.add("\ticonst_0");
        asm.add(endLabel + ":");
        return asm;
    }
}

/*
    ifne #_false
    iconst_1
    goto #_end
    #_false
    iconst_0
    #_end
 */