package nl.firepy.firescript.lang;

import org.junit.jupiter.api.Test;

import nl.firepy.firescript.compiler.FireScriptCompiler;

public class OptionalVariables {
    
    FireScriptCompiler target = new FireScriptCompiler();

    @Test
    void createOptionalVariable() {
        String defineStatement = "let loc : int?;";
        String result = target.compileFireScript(defineStatement);
        System.out.println(result);
    }

    @Test
    void createOptionalChain() {
        String defineStatement = "let arr : Array<Vector2>; let x : int? = arr?.x";
        String result = target.compileFireScript(defineStatement);
        System.out.println(result);
    }
}
