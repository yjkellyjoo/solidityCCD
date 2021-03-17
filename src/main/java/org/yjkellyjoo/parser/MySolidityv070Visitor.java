package org.yjkellyjoo.parser;

import org.yjkellyjoo.v070.Solidityv070BaseVisitor;
import org.yjkellyjoo.v070.Solidityv070Parser;

import java.util.HashMap;
import java.util.Map;

public class MySolidityv070Visitor extends Solidityv070BaseVisitor<Token> {

    // store variables (there's only one global scope!)
//    private Map<String, Token> memory = new HashMap<String, Token>();

    @Override public Token visitSourceUnit(Solidityv070Parser.SourceUnitContext ctx) {
        return visitChildren(ctx);
    }

//    @Override public Token visitFunctionDefinition(Solidityv070Parser.FunctionDefinitionContext ctx) {
//
//        String functionIdentifier = ctx.functionDescriptor().identifier().getText();
//
//        return memory.put(functionIdentifier, value);
//    }

    @Override public Token visitFunctionDescriptor(Solidityv070Parser.FunctionDescriptorContext ctx) {
        String functionIdentifier = ctx.identifier().getText();

        return new Token(functionIdentifier);
    }
}
