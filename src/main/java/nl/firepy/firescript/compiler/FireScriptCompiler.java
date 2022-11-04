package nl.firepy.firescript.compiler;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import nl.firepy.firescript.compiler.FireScriptParser.ProgramContext;
import nl.firepy.firescript.component.internal.FireScriptBlock;
import nl.firepy.firescript.component.internal.FireScriptComponent;
import nl.firepy.firescript.error.SyntaxErrorException;
import nl.firepy.firescript.error.SyntaxErrorListener;
import nl.firepy.firescript.type.CodeFileDescriptor;

public class FireScriptCompiler {
    
    public String compileFireScript(String sourceCode) throws CompilerException {
        // Create lexer and run scanner to create stream of tokens
        CharStream charStream = CharStreams.fromString(sourceCode);
        FireScriptLexer lexer = new FireScriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Create parser and feed it the tokens
        FireScriptParser parser = new FireScriptParser(tokens);
        SyntaxErrorListener errorListener = new SyntaxErrorListener();
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        ProgramContext program = parser.program();

        if (errorListener.hasSyntaxErrors()) {
            throw new SyntaxErrorException(errorListener.getSyntaxErrors());
        } else {
            // FireScriptMethodVisitor headerBuilder = new FireScriptMethodVisitor();
            FireScriptTypeVisitor typeChecker = new FireScriptTypeVisitor();
            FireScriptCodeVisitor visitor = new FireScriptCodeVisitor();

                // headerBuilder.visit(program);
                // ClassHeader classHeader = headerBuilder.getClassHeader();

                // typeChecker.setClassHeader(classHeader);
                // visitor.setClassHeader(classHeader);
                CodeFileDescriptor codeFileDescriptor = new CodeFileDescriptor("main");
                typeChecker.visitCodeFile(codeFileDescriptor, program);
                FireScriptBlock fireScriptComponent = visitor.visitCodeFile(codeFileDescriptor, program);
                List<String> prog = fireScriptComponent.generateCode();

                String code = prog.stream().collect(Collectors.joining("\n"));

                return code;
        }
    }
}
