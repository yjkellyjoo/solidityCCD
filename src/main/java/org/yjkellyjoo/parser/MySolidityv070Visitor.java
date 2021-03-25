package org.yjkellyjoo.parser;

import org.yjkellyjoo.v070.Solidityv070BaseVisitor;
import org.yjkellyjoo.v070.Solidityv070Parser;

import java.util.HashMap;
import java.util.Map;

public class MySolidityv070Visitor extends Solidityv070BaseVisitor<Token> {

    // store variables
    private Map<String, String> identifiers = new HashMap<String, String>();
    private int count = 0;

//    @Override public Token visitSourceUnit(Solidityv070Parser.SourceUnitContext ctx) {
//        return visitChildren(ctx);
//    }

//    @Override public Token visitFunctionDefinition(Solidityv070Parser.FunctionDefinitionContext ctx) {
//        Solidityv070Parser.BlockContext block = ctx.block();
//
//        return visitChildren(ctx);
//    }

//    @Override public Token visitFunctionDescriptor(Solidityv070Parser.FunctionDescriptorContext ctx) {
//        String functionIdentifier = ctx.identifier().getText();
//
//        return new Token(functionIdentifier);
//    }

//    @Override public Token visitBlock(Solidityv070Parser.BlockContext ctx) {
//
//        return visitChildren(ctx);
//    }


    @Override public Token visitIdentifier(Solidityv070Parser.IdentifierContext ctx) {
        try {
            // TODO: edit below to handle identifiers
//            System.out.println(ctx.Identifier().getText());

            String identifier = ctx.Identifier().getText();

//            if (identifiers.get(identifier) == null) {
//                identifiers.put(identifier, "id_" + Integer.toString(count));
//                count++;
//            }

            return new Token(identifier);
        }
        // TODO: ignore address type casting for now
        catch (NullPointerException ignored){

        }
        return null;
    }
}
