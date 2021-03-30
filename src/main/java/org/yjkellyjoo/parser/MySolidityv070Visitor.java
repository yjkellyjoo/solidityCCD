package org.yjkellyjoo.parser;

import org.antlr.runtime.Token;
import org.yjkellyjoo.v070.Solidityv070BaseVisitor;
import org.yjkellyjoo.v070.Solidityv070Parser;

import java.util.ArrayList;
import java.util.List;

public class MySolidityv070Visitor extends Solidityv070BaseVisitor<List<String>> {

    // store all identifiers
    private List<String> identifiers = new ArrayList<String>();

//    @Override public Token visitSourceUnit(Solidityv070Parser.SourceUnitContext ctx) {
//        return visitChildren(ctx);
//    }

    @Override public List<String> visitFunctionDefinition(Solidityv070Parser.FunctionDefinitionContext ctx) {
        visitChildren(ctx);

        return identifiers;
    }

//    @Override public Token visitFunctionDescriptor(Solidityv070Parser.FunctionDescriptorContext ctx) {
//        String functionIdentifier = ctx.identifier().getText();
//
//        return new Token(functionIdentifier);
//    }

//    @Override public Token visitBlock(Solidityv070Parser.BlockContext ctx) {
//
//        return visitChildren(ctx);
//    }


    @Override public List<String> visitIdentifier(Solidityv070Parser.IdentifierContext ctx) {
        try {
//            System.out.println(ctx.Identifier().getText());
            String identifier = ctx.Identifier().getText();
            identifiers.add(identifier);

            return visitChildren(ctx);
        }
        // TODO: handle <address type> casting; ignoring them for now
        catch (NullPointerException ignored) {

        }
        return null;
    }
}
