package org.yjkellyjoo.ccd;


import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.FileUtils;
import org.yjkellyjoo.parser.MySolidityv070Visitor;
import org.yjkellyjoo.v070.Solidityv070Lexer;
import org.yjkellyjoo.v070.Solidityv070Parser;

import java.io.File;
import java.io.IOException;

public class algorithms {
    public static String runNicad(String code) {
        // TODO
        String abstCode = code;

        Solidityv070Lexer lexer = new Solidityv070Lexer(CharStreams.fromString(code));
        Solidityv070Parser parser = new Solidityv070Parser(new CommonTokenStream(lexer));
        ParseTree functionDefinitionTree = parser.functionDefinition();

        // TODO: visitor 쓰지 말고 parser로 그냥 할 수 있는 방법이 없으려나? identifier일 때 abstraction만 하면 되는데...
        MySolidityv070Visitor visitor = new MySolidityv070Visitor();
        visitor.visit(functionDefinitionTree);


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

        return abstCode;
    }
}
