package org.yjkellyjoo.parser;

import org.yjkellyjoo.utils.Constants;
import org.yjkellyjoo.universal.SolidityBaseVisitor;
import org.yjkellyjoo.universal.SolidityParser;

import java.util.*;

public class MySolidityVisitor extends SolidityBaseVisitor<Map<String, Set<String>>> {

    // store all identifiers
    private Map<String, Set<String>> resultMap = new HashMap<>();
    private Set<String> fparamIds = new LinkedHashSet<>();
    private Set<String> elementaryTypes = new LinkedHashSet<>();
    private Set<String> functionCalls = new LinkedHashSet<>();
    private Set<String> identifiers = new LinkedHashSet<>();


    //    @Override public Token visitFunctionDescriptor(Solidityv070Parser.FunctionDescriptorContext ctx) {
//        String functionIdentifier = ctx.identifier().getText();
//
//        return new Token(functionIdentifier);
//    }


    @Override public Map<String, Set<String>> visitFunctionDefinition(SolidityParser.FunctionDefinitionContext ctx) {
        visitChildren(ctx);

        // link every Sets into the identifier Map
        resultMap.put(Constants.fparam, fparamIds);
        resultMap.put(Constants.dtype, elementaryTypes);
        resultMap.put(Constants.funccall, functionCalls);
        resultMap.put(Constants.ident, identifiers);

        return resultMap;
    }


    @Override public Map<String, Set<String>> visitParameterList(SolidityParser.ParameterListContext ctx) {
        // get parameter identifier names to abstract them as FPARAM
        List<SolidityParser.ParameterContext> params = ctx.parameter();
        for (SolidityParser.ParameterContext param : params) {
            try {
                fparamIds.add(param.identifier().getText());
            }
            catch (NullPointerException ignored) {
            }
        }

        return visitChildren(ctx);
    }

    @Override public Map<String, Set<String>> visitFunctionCall(SolidityParser.FunctionCallContext ctx) {
        // get function call expressions to abstract them as FUNCCALL
        try {
//            System.out.println(ctx.expression().getText());
            functionCalls.add(ctx.expression().getText());
        }
        catch (NullPointerException ignored) {
        }
        return visitChildren(ctx);
    }


    @Override public Map<String, Set<String>> visitElementaryTypeName(SolidityParser.ElementaryTypeNameContext ctx) {
        // get all elementary types used in the investigating code to abstract them as DTYPE
        try {
            elementaryTypes.add(ctx.getText());
        }
        catch (NullPointerException ignored) {
        }
        return visitChildren(ctx);
    }


    @Override public Map<String, Set<String>> visitIdentifier(SolidityParser.IdentifierContext ctx) {
        try {
            identifiers.add(ctx.Identifier().getText());
        }
        catch (NullPointerException ignored) {
        }

        return visitChildren(ctx);
    }
}
