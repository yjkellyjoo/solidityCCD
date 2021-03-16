package org.yjkellyjoo.parser;

import org.yjkellyjoo.v070.Solidityv070BaseListener;
import org.yjkellyjoo.v070.Solidityv070Parser;

public class MySolidityv070Listener extends Solidityv070BaseListener {
    @Override public void enterSourceUnit(Solidityv070Parser.SourceUnitContext ctx) {
        System.out.println("=== START ===");
    }

    @Override public void enterFunctionDefinition(Solidityv070Parser.FunctionDefinitionContext ctx) {
//        System.out.println(ctx.getChildCount());
//        System.out.println(ctx.getParent());
//        System.out.println(ctx.getRuleIndex());
        System.out.println(ctx.functionDescriptor().identifier().getText());
//        System.out.println(ctx.getStart());
//        System.out.println(ctx.getText());


    }

    @Override public void enterFunctionDescriptor(Solidityv070Parser.FunctionDescriptorContext ctx) {

    }

}
