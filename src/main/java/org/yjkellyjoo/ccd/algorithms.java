package org.yjkellyjoo.ccd;


import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.yjkellyjoo.parser.MySolidityv070Visitor;
import org.yjkellyjoo.v070.Solidityv070Lexer;
import org.yjkellyjoo.v070.Solidityv070Parser;

import java.util.*;

public class algorithms {
    public static String runAlgo(String algoName, String code) {
        String abstCode = null;
        switch (algoName) {
            case "nicad":
                abstCode = algorithms.runNicad(code);
                break;
            case "ccfinder":
                abstCode = algorithms.runCCFinder(code);
                break;
            case "vuddy":
                abstCode = algorithms.runVuddy(code);
                break;
            case "sourcerercc":
                abstCode = algorithms.runSourcererCC(code);
                break;
            default:
                System.err.println("wrong algorithm name.");
        }
        return abstCode;
    }

    private static String runSourcererCC(String code) {
        String abstCode = code;

        return abstCode;
    }

    private static String runVuddy(String code) {
        String abstCode = code;

        return abstCode;
    }

    private static String runCCFinder(String code) {
        String abstCode = code;

        return abstCode;
    }

    private static String runNicad(String code) {
        String abstCode = code;

        Solidityv070Lexer lexer = new Solidityv070Lexer(CharStreams.fromString(code));
        Solidityv070Parser parser = new Solidityv070Parser(new CommonTokenStream(lexer));
        MySolidityv070Visitor visitor = new MySolidityv070Visitor();

        // use visitor and find out all identifiers from the given code.
        ParseTree functionDefinitionTree = parser.functionDefinition();
        List<String> identifiers = visitor.visit(functionDefinitionTree);

        // abstract identifiers
        Iterator<String> idIterator = identifiers.iterator();
        Map<String, String> abstIds = new HashMap<String, String>();
        int count = 0;

        while(idIterator.hasNext()) {
            String id = idIterator.next();
            if (abstIds.get(id) == null) {
                abstIds.put(id, "id_" + count);
                count++;
            }
            abstCode = abstCode.replaceFirst(id, abstIds.get(id));
        }

        return abstCode;
    }
}
