package nl.firepy.firescript.lang;

import org.junit.jupiter.api.Test;

import nl.firepy.firescript.compiler.FireScriptCompiler;

public class VCounter {
    
    FireScriptCompiler target = new FireScriptCompiler();

    private final String exampleProgram = """
        class Counter {
            func Counter(): void => {
                twice(5)
            }

            static func twice(val: double): void => {
                echo val * 2
            }
        }
    """;

    @Test
    void compileBasicStruct() {
        String result = target.compileFireScript(exampleProgram);
        System.out.println(result);
        
    }

}