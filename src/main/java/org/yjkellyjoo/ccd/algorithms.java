package org.yjkellyjoo.ccd;


import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.yjkellyjoo.parser.MySolidityv070Visitor;
import org.yjkellyjoo.v070.Solidityv070Lexer;
import org.yjkellyjoo.v070.Solidityv070Parser;

import java.util.*;

public class algorithms {
    public static String runSourcererCC(String code) {
        // TODO
        String abstCode = code;

        return abstCode;
    }

    public static String runVuddy(String code) {
        // TODO
        String abstCode = code;

        return abstCode;
    }

    public static String runCCFinder(String code) {
        // TODO
        String abstCode = code;

        return abstCode;
    }

    public static String runNicad(String code) {
        String abstCode = code;

        Solidityv070Lexer lexer = new Solidityv070Lexer(CharStreams.fromString(code));
        Solidityv070Parser parser = new Solidityv070Parser(new CommonTokenStream(lexer));
        MySolidityv070Visitor visitor = new MySolidityv070Visitor();

        // use visitor and find out all identifiers from the given code.
        ParseTree functionDefinitionTree = parser.functionDefinition();
        List<String> identifiers = visitor.visit(functionDefinitionTree);

        // abstract identifiers
        Iterator<String> idIterator = identifiers.iterator();
        Map<String, String> abstIds = new HashMap<>();
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
