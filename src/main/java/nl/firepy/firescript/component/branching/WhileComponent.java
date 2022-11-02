package nl.firepy.firescript.component.branching;

import java.util.ArrayList;

import nl.firepy.firescript.compiler.scope.Scope;
import nl.firepy.firescript.component.BlockComponent;
import nl.firepy.firescript.component.FireScriptComponent;
import nl.firepy.firescript.component.expr.ExprComponent;

public class WhileComponent implements FireScriptComponent {

    private ExprComponent exrp;
    private BlockComponent block;
    private Scope scope;

    public WhileComponent(ExprComponent exrp, BlockComponent block, Scope scope) {
        this.exrp = exrp;
        this.block = block;
        this.scope = scope;
    }

    @Override
    public ArrayList<String> generateCode() {
        String startLabel = scope.getLabel("start_while");
        String endLabel = scope.getLabel("end_while");
        ArrayList<String> asm = new ArrayList<>();
        asm.add("\tnop");
        asm.add(startLabel + ":");
        asm.addAll(exrp.generateCode());
        asm.add("\tifeq " + endLabel);
        asm.addAll(block.generateCode());
        asm.add("\tgoto " + startLabel);
        asm.add(endLabel + ":");
        return asm;
    }
}
