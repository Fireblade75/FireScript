package nl.firepy.firescript.component.legacy.expr;

import java.util.ArrayList;

import nl.firepy.firescript.compiler.scope.Scope;
import nl.firepy.firescript.component.expr.ExprComponent;
import nl.firepy.firescript.type.FSType;
import nl.firepy.firescript.type.TypeConverter;
import nl.firepy.firescript.type.Value;

public class EqualsExpression extends ExprComponent {

    private ExprComponent leftExpr, rightExpr;
    private Scope scope;
    private boolean inverted;

    public EqualsExpression(ExprComponent leftExpr, ExprComponent rightExpr, boolean inverted, Scope scope) {
        super(new Value("BOOL", false));
        this.leftExpr = leftExpr;
        this.rightExpr = rightExpr;
        this.scope = scope;
        this.inverted = inverted;
    }


    @Override
    public boolean isStatic() {
        return leftExpr.isStatic() && rightExpr.isStatic();
    }

    @Override
    public ArrayList<String> generateCode() {
        ArrayList<String> asm = new ArrayList<>();

        // String leftType = leftExpr.getValue().getRawType(scope);
        // String rightType = rightExpr.getValue().getRawType(scope);

        // //String trueLabel = scope.getLabel();
        // String falseLabel = scope.getLabel();
        // String endLabel = scope.getLabel();

        // if (TypeConverter.isNumber(leftType) && TypeConverter.isNumber(rightType)) {

        //     String resultType = TypeConverter.combineNumbers(leftType, rightType);
        //     NumberConverter converter = new NumberConverter(scope);
        //     asm.addAll(converter.loadNumber(leftExpr, rightExpr, resultType));

        //     if(resultType.equals("int")) {
        //         asm.add("\tif_icmpne " + falseLabel);
        //     } else if(resultType.equals("long")) {
        //         asm.add("\tlcmp");
        //         asm.add("\tifne " + falseLabel);
        //     } else if(resultType.equals("float")) {
        //         asm.add("\tfcmpl");
        //         asm.add("\tifne " + falseLabel);
        //     } else {
        //         asm.add("\tdcmpl");
        //         asm.add("\tifne " + falseLabel);
        //     }

        //     asm.add(inverted ? "\ticonst_0" : "\ticonst_1");
        //     asm.add("\tgoto " + endLabel);
        //     asm.add(falseLabel + ":");
        //     asm.add(inverted ? "\ticonst_1" : "\ticonst_0");
        //     asm.add(endLabel + ":");
        // } else {
        //     asm.add("\tpop");
        //     asm.add("\tpop");
        //     asm.add("\ticonst_0");
        // }
        return asm;
    }
}
