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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Algorithms {
    private final int MUL_FACTOR = 1;
    private final int THRESHOLD = 8;
    private Constants constants = new Constants();


    private Map<String, Integer> runSourcererCC(String code) {
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

    private String runVuddy(String code) {
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
        Set<String> fparamIds = identifiers.get(constants.fparam);
        for (String id : fparamIds) {
            abstCode = abstCode.replaceAll(id, constants.fparam);
        }

        // abstract data types
        Set<String> dtypes = identifiers.get(constants.dtype);
        for (String dtype : dtypes) {
            abstCode = abstCode.replaceAll(dtype, constants.dtype);
        }

        // abstract function calls
        Set<String> funccalls = identifiers.get(constants.funccall);
        for (String funccall : funccalls) {
//            System.out.println(funccall);
            abstCode = abstCode.replaceAll(funccall, constants.funccall);
        }

        // abstract lvar
        Set<String> localVariables = identifiers.get(constants.ident);
        for (String var : localVariables) {
            abstCode = abstCode.replaceAll(var, constants.lvar);
        }

        abstCode = abstCode.replaceAll("\\s+", " ");
        abstCode = abstCode.toLowerCase(Locale.ROOT);

        return abstCode;
    }


    private String runNicad(String code) {
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
        Iterator<String> idIterator = identifiers.get(constants.ident).iterator();
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

    public String getAlgoFolder(String algoName, String vulnLevelFolder) {
        String currFolder = null;
        switch (algoName) {
            case "nicad":
                currFolder = constants.NICAD_FOLDER + vulnLevelFolder;
                break;
            case "vuddy":
                currFolder = constants.VUDDY_FOLDER + vulnLevelFolder;
                break;
            case "sourcerercc":
                currFolder = constants.SOURCERERCC_FOLDER + vulnLevelFolder;
                break;
            default:
                System.err.println("wrong algorithm name.");
        }
        return currFolder;
    }

    public Object runAlgo(String algoName, String code) {
        Object abstCode = null;
        switch (algoName) {
            case "nicad":
                abstCode = this.runNicad(code);
                break;
            case "vuddy":
                abstCode = this.runVuddy(code);
                break;
            case "sourcerercc":
                abstCode = this.runSourcererCC(code);
                break;
            default:
                System.err.println("wrong algorithm name.");
        }
        return abstCode;
    }

    public Map<String , List<Integer>> compareAbstCodes(String algoName, Map<Integer, Object> abstCodes, String vulnFolder) {
        // must use different comparison for each algo.
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
                // do comparison
                StringBuffer cloneGroup = new StringBuffer();
                for (Integer fileNameA: abstCodes.keySet()){
                    for (Integer fileNameB: abstCodes.keySet()) {
                        if (fileNameA < fileNameB) {
                            int computedThreshold;
                            Map<String, Integer> tokensA = (Map<String, Integer>) abstCodes.get(fileNameA);
                            Map<String, Integer> tokensB = (Map<String, Integer>) abstCodes.get(fileNameB);

                            int maxLength = Math.max(tokensA.size(), tokensB.size());
                            computedThreshold = (int) Math.ceil(this.THRESHOLD * maxLength / (10 * this.MUL_FACTOR));
                            System.out.println(computedThreshold);

                            int count = 0;
                            for (String token : tokensA.keySet()) {
                                Integer freqB = tokensB.get(token);
                                if (null != freqB) {
                                    count += Math.min(tokensA.get(token), freqB);
                                    if (count >= computedThreshold) {
                                        cloneGroup.append("(").append(fileNameA).append(",").append(fileNameB).append(")\n");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                // write result to file
                File vulnFolderfile = new File(vulnFolder);
                vulnFolderfile.mkdirs();

                File resultFile = new File(vulnFolder + "results.txt");
                try {
                    resultFile.createNewFile();
                    FileWriter fw = new FileWriter(resultFile);
                    fw.write(cloneGroup.toString());
                    fw.flush();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // nullify abstGroup to skip the rest steps
                abstGroup = null;
                break;
        }

        return abstGroup;
    }

}
