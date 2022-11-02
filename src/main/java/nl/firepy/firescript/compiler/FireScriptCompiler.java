package nl.firepy.firescript.compiler;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import nl.firepy.firescript.error.SyntaxErrorException;
import nl.firepy.firescript.error.SyntaxErrorListener;
import nl.firepy.firescript.type.ClassHeader;

public class FireScriptCompiler {
    
    public String compileFireScript(String sourceCode) throws CompilerException {
        // Create lexer and run scanner to create stream of tokens
        CharStream charStream = CharStreams.fromString(sourceCode);
        VigloLexer lexer = new VigloLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Create parser and feed it the tokens
        FireScriptParser parser = new FireScriptParser(tokens);
        SyntaxErrorListener errorListener = new SyntaxErrorListener();
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        ParseTree program = parser.program();

        if (errorListener.hasSyntaxErrors()) {
            throw new SyntaxErrorException(errorListener.getSyntaxErrors());
        } else {
            FireScriptMethodVisitor headerBuilder = new FireScriptMethodVisitor();
            FireScriptTypeVisitor typeChecker = new FireScriptTypeVisitor();
            FireScriptCodeVisitor visitor = new FireScriptCodeVisitor();

                headerBuilder.visit(program);
                ClassHeader classHeader = headerBuilder.getClassHeader();

                typeChecker.setClassHeader(classHeader);
                visitor.setClassHeader(classHeader);
                typeChecker.visit(program);
                ArrayList<String> prog = visitor.visit(program).generateCode();

                String code = prog.stream().collect(Collectors.joining("\n"));

                return code;
        }
    }
}
