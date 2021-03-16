package org.yjkellyjoo.parser;

import org.yjkellyjoo.v070.Solidityv070BaseVisitor;
import org.yjkellyjoo.v070.Solidityv070Parser;

public class MySolidityv070Visitor extends Solidityv070BaseVisitor<Token> {
    @Override public Token visitSourceUnit(Solidityv070Parser.SourceUnitContext ctx) {
        System.out.println("=== START ===");

        return visitChildren(ctx);
    }

    @Override public Token visitFunctionDefinition(Solidityv070Parser.FunctionDefinitionContext ctx) {
//        System.out.println(ctx.getChildCount());
//        System.out.println(ctx.getParent());
//        System.out.println(ctx.getRuleIndex());
        System.out.println(ctx.functionDescriptor().identifier().getText());
//        System.out.println(ctx.getStart());
//        System.out.println(ctx.getText());

        return visitChildren(ctx);
    }

}
