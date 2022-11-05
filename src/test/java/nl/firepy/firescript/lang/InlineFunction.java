package nl.firepy.firescript.lang;

import org.junit.jupiter.api.Test;

import nl.firepy.firescript.compiler.FireScriptCompiler;

public class InlineFunction {

    FireScriptCompiler target = new FireScriptCompiler();

    @Test
    void createOptionalVariable() {
        String code = """
            const double = (val: int) -> {
                
            };
        """;
        String result = target.compileFireScript(code);
        System.out.println(result);
    }

}
