package org.yjkellyjoo.ccd;


import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import org.yjkellyjoo.utils.Constants;

//import org.yjkellyjoo.parser.MySolidityv070Visitor;
//import org.yjkellyjoo.v070.Solidityv070Lexer;
//import org.yjkellyjoo.v070.Solidityv070Parser;

import org.yjkellyjoo.parser.MySolidityVisitor;
import org.yjkellyjoo.universal.SolidityLexer;
import org.yjkellyjoo.universal.SolidityParser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class algorithms {
    public static Map<String , List<Integer>> compareAbstCodes(String algoName, Map<Integer, Object> abstCodes) {
        // TODO: must use different comparison for each algo.
        Map<String , List<Integer>> abstGroup = new HashMap<>();

        switch (algoName) {
            case "nicad":
            case "vuddy":
                for (Integer fileName : abstCodes.keySet()) {
                    String abstCode = abstCodes.get(fileName).toString();

                    // if the abstracted code is already in the group,
                    // add the file number to the group's list
                    if (abstGroup.get(abstCode) != null) {
                        List<Integer> filesList = abstGroup.get(abstCode);

                        filesList.add(fileName);
                        abstGroup.put(abstCode, filesList);
                    }
                    // else input a new group
                    else {
                        List<Integer> filesList = new ArrayList<>();

                        filesList.add(fileName);
                        abstGroup.put(abstCode, filesList);
                    }
                }
                break;
            case "sourcerercc":
                // TODO


                break;
        }


        return abstGroup;
    }

    public static Map<String, Integer> runSourcererCC(String code) {
        String abstCode = code;

        /* 1. tokenize code */
        // remove comments
        // TODO: Is there a way to remove comments using the parser?
        String commentsPattern = "(//.*?$)|(/\\*.*?\\*/)";
        String stringPattern = "(\".*?(?<!\\\\)\")";
        Matcher commentMatcher = Pattern.compile(commentsPattern).matcher(code);
        Matcher stringMatcher = Pattern.compile(stringPattern).matcher(code);
        boolean stringFlag = stringMatcher.find();

        while (commentMatcher.find()) {
            while (commentMatcher.start() < stringMatcher.start()) {
                stringFlag = stringMatcher.find();
            }
            // do not remove comment patterns inside a string pattern
            if (stringFlag && commentMatcher.start() < stringMatcher.end()) {
                stringFlag = stringMatcher.find();
            }
            else {
                abstCode = abstCode.replaceFirst(commentMatcher.group(), "");
            }
        }

        // remove separators
        String separators = ":|;|\\^|\\||~|<|>|=|\\{|\\}|\\[|\\]|\\(|\\)|,|-|\\+|!|\\*|\\/|%|&|\\?|\\.|\"|'|\\s+";
        abstCode = abstCode.replaceAll(separators, " ");
//        System.out.println(abstCode);

        /* 2. count the tokens and save the frequency of each code into bag of tokens. */
        String[] splitToken = abstCode.split("\\s+");
        Map<String, Integer> tokens = new HashMap<>();

        for (String token : splitToken) {
            // if the token is already in the group, increase the frequency
            if (tokens.get(token) != null) {
                Integer frequency = tokens.get(token);

                frequency++;
                tokens.put(token, frequency);
            } else {
                tokens.put(token, 1);
            }
        }

        /* 3. return the bag of tokens. */
        return tokens;
    }

    public static String runVuddy(String code) {
        String abstCode = code;

//        Solidityv070Lexer lexer = new Solidityv070Lexer(CharStreams.fromString(code));
//        Solidityv070Parser parser = new Solidityv070Parser(new CommonTokenStream(lexer));
//        MySolidityv070Visitor visitor = new MySolidityv070Visitor();
        SolidityLexer lexer = new SolidityLexer(CharStreams.fromString(code));
        SolidityParser parser = new SolidityParser(new CommonTokenStream(lexer));
        MySolidityVisitor visitor = new MySolidityVisitor();

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

        abstCode = abstCode.replaceAll("\\s+", " ");
        abstCode = abstCode.toLowerCase(Locale.ROOT);

        return abstCode;
    }


    public static String runCCFinder(String code) {
        // TODO
        String abstCode = code;

        return abstCode;
    }

    public static String runNicad(String code) {
        String abstCode = code;

//        Solidityv070Lexer lexer = new Solidityv070Lexer(CharStreams.fromString(code));
//        Solidityv070Parser parser = new Solidityv070Parser(new CommonTokenStream(lexer));
//        MySolidityv070Visitor visitor = new MySolidityv070Visitor();
        SolidityLexer lexer = new SolidityLexer(CharStreams.fromString(code));
        SolidityParser parser = new SolidityParser(new CommonTokenStream(lexer));
        MySolidityVisitor visitor = new MySolidityVisitor();

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
