package org.yjkellyjoo;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.IOException;

import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.FileUtils;

import org.yjkellyjoo.ccd.nicad;
import org.yjkellyjoo.parser.*;
import org.yjkellyjoo.v070.*;

public class SolidityCCD {

    public static void main(String[] args){
        File file = new File("src/main/resources/070.sol");
        try {
            String code = FileUtils.readFileToString(file, "UTF-8");
            Solidityv070Lexer lexer = new Solidityv070Lexer(CharStreams.fromString(code));
            Solidityv070Parser parser = new Solidityv070Parser(new CommonTokenStream(lexer));
            ParseTree sourceUnitTree = parser.sourceUnit();

//            MySolidityv070Listener listener = new MySolidityv070Listener();
            MySolidityv070Visitor visitor = new MySolidityv070Visitor();

//            ParseTreeWalker walker = new ParseTreeWalker();
//            walker.walk(listener, sourceUnit);
            visitor.visit(sourceUnitTree);
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


            // run CCDs
            nicad.runNicad();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
