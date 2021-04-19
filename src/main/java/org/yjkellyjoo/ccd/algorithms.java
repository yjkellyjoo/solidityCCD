package org.yjkellyjoo.ccd;


import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.yjkellyjoo.parser.MySolidityv070Visitor;
import org.yjkellyjoo.utils.Constants;
import org.yjkellyjoo.v070.Solidityv070Lexer;
import org.yjkellyjoo.v070.Solidityv070Parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class algorithms {
    public static String runSourcererCC(String code) {
        // TODO
        String abstCode = code;

        return abstCode;
    }

    public static String runVuddy(String code) {
        String abstCode = code;

        Solidityv070Lexer lexer = new Solidityv070Lexer(CharStreams.fromString(code));
        Solidityv070Parser parser = new Solidityv070Parser(new CommonTokenStream(lexer));
        MySolidityv070Visitor visitor = new MySolidityv070Visitor();

        ParseTree functionDefTree = parser.functionDefinition();
        Map<String, Set<String>> identifiers = visitor.visit(functionDefTree);

        // abstract fparams
        Set<String> fparamIds = identifiers.get(Constants.fparam);
        for (String id : fparamIds) {
            abstCode = abstCode.replaceAll(id, Constants.fparam);
        }

        // abstract data types
        Set<String> dtypes = identifiers.get(Constants.dtype);
        for (String dtype : dtypes) {
            abstCode = abstCode.replaceAll(dtype, Constants.dtype);
        }

        // abstract function calls
        Set<String> funccalls = identifiers.get(Constants.funccall);
        for (String funccall : funccalls) {
//            System.out.println(funccall);
            abstCode = abstCode.replaceAll(funccall, Constants.funccall);
        }

        // abstract lvar
        Set<String> localVariables = identifiers.get(Constants.ident);
        for (String var : localVariables) {
            abstCode = abstCode.replaceAll(var, Constants.lvar);
        }

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
        Map<String, Set<String>> identifiers = visitor.visit(functionDefinitionTree);

        // abstract identifiers
        Iterator<String> idIterator = identifiers.get(Constants.ident).iterator();
        Map<String, String> abstIds = new HashMap<>();
        int count = 0;

        while(idIterator.hasNext()) {
            String id = idIterator.next();
            if (abstIds.get(id) == null) {
                abstIds.put(id, "id_" + count);
                count++;
            }
            abstCode = abstCode.replaceAll(id, abstIds.get(id));
        }

        // unify all whitespaces
        abstCode = abstCode.replaceAll("\\s+", " ");

        return abstCode;
    }
}
