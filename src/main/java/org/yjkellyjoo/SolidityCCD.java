package org.yjkellyjoo;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.FileUtils;

import org.yjkellyjoo.ccd.algorithms;
import org.yjkellyjoo.utils.Constants;
import org.yjkellyjoo.utils.FileUtil;
import org.yjkellyjoo.parser.*;
import org.yjkellyjoo.v070.*;


public class SolidityCCD {

    public static void main(String[] args){
        // TODO: choose which CCD to run



        // read files from the original folder
        Map<String , List<Integer>> abstGroup = new HashMap<String, List<Integer>>();

        File files = new File(Constants.ORIGINAL_1_FOLDER);
        for (File file : files.listFiles()) {
            try {
                String code = FileUtils.readFileToString(file, "UTF-8");

                // TODO: run the selected CCD on the read files
                String abstCode = algorithms.runNicad(code);
                if(abstGroup.get(abstCode) != null) {
                    Integer tmp = new Integer(Integer.parseInt(file.getName());

                    abstGroup.put(code, tmp);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }





        // TODO: triage into sub-folders in the corresponding CCD folder





//        File file = new File("src/main/resources/070.sol");
//        try {
//            String code = FileUtils.readFileToString(file, "UTF-8");
//            Solidityv070Lexer lexer = new Solidityv070Lexer(CharStreams.fromString(code));
//            Solidityv070Parser parser = new Solidityv070Parser(new CommonTokenStream(lexer));
//            ParseTree sourceUnitTree = parser.sourceUnit();
//
//            MySolidityv070Visitor visitor = new MySolidityv070Visitor();
//            visitor.visit(sourceUnitTree);



//            MySolidityv070Listener listener = new MySolidityv070Listener();

//            ParseTreeWalker walker = new ParseTreeWalker();
//            walker.walk(listener, sourceUnit);

//            Token t = visitor.visitSourceUnit(parser.sourceUnit());
//            visitor.visitFunctionDefinition(parser.functionDefinition());

//            for (Token t : lexer.getAllTokens()){
//                System.out.println(t.getLine() + " " + t.getType() + " " + t.getText());
//            }
//
//            String ruleName = Solidityv070Parser.ruleNames[parser.getRuleIndex()];
//
//
//            System.out.println();
//            for (int i = 0; i < tree.getChildCount(); i++) {
//                System.out.println(tree.getChild(i));
//            }
//
//            Solidityv070Parser.FunctionDefinitionContext functionDef = parser.functionDefinition();
//            System.out.println(functionDef.functionDescriptor().identifier());


//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
