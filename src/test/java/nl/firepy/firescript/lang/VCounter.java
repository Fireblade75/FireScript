package nl.firepy.firescript.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nl.firepy.firescript.compiler.FireScriptCompiler;

public class VCounter {
    
    FireScriptCompiler target = new FireScriptCompiler();

    @Test
    void defineStatement() {
        String defineStatement = "let loc : int;";
        String result = target.compileFireScript(defineStatement);
        assertEquals("local loc", result);
    }

    @Test
    void assignInferStatement() {
        String assigmentStatement = "let loc = 8;";
        String result = target.compileFireScript(assigmentStatement);
        assertEquals("local loc = 8", result);
    }

    @Test
    void assignConstStatement() {
        String assigmentStatement = "const loc = 8;";
        String result = target.compileFireScript(assigmentStatement);
        assertEquals("local loc = 8", result);
    }

    @Test
    void reAssignStatement() {
        String assigmentStatement = "let loc = 8; loc = 5;";
        String result = target.compileFireScript(assigmentStatement);
        assertEquals("local loc = 8\nloc = 5", result);
    }

    @Test
    void assignDefinedStatement() {
        String assigmentStatement = "let loc : string; loc = \"test\";";
        String result = target.compileFireScript(assigmentStatement);
        System.out.println(result);
        assertEquals("local loc\nloc = \"test\"", result);
    }
}