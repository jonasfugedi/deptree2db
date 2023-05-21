package com.tajjm.maven.deptree2db;

import com.tajjm.maven.MavenDotLexer;
import com.tajjm.maven.MavenDotParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        InputStream is;
        String inputFile = null;
        String runId = null;
        if (args.length == 2) {
            inputFile = args[1];
            is = new FileInputStream(inputFile);
            runId = args[0];
        } else {
            throw new RuntimeException("Bad arguments; Provide rundId filePath");
        }
        CharStream input = CharStreams.fromStream(is);

        var lexer = new MavenDotLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new MavenDotParser(tokens);
        MavenDotParser.GraphContext graphContext = parser.graph();

        ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
        var converter = new Converter(runId);
        walker.walk(converter, graphContext); // initiate walk of tree with listener

        // debugPrintMap(converter);
    }

    private static void debugPrintMap(Converter converter) {
        for (Map.Entry<String, List<Dependency>> entry : converter.getMap().entrySet()) {
            System.out.println(entry.getKey());
            for (Dependency dependency : entry.getValue()) {
                System.out.println("\t" + dependency.toGAV());
            }
        }
    }
}
