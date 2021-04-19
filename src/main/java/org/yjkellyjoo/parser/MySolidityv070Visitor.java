package org.yjkellyjoo.parser;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParameterList;
import org.yjkellyjoo.utils.Constants;
import org.yjkellyjoo.v070.Solidityv070BaseVisitor;
import org.yjkellyjoo.v070.Solidityv070Parser;

import java.util.*;

public class MySolidityv070Visitor extends Solidityv070BaseVisitor<Map<String, Set<String>>> {

    // store all identifiers
    private Map<String, Set<String>> resultMap = new HashMap<>();
    private Set<String> fparamIds = new LinkedHashSet<>();
    private Set<String> elementaryTypes = new LinkedHashSet<>();
    private Set<String> functionCalls = new LinkedHashSet<>();
    private Set<String> lvars = new LinkedHashSet<>();
    private Set<String> identifiers = new LinkedHashSet<>();


    //    @Override public Token visitFunctionDescriptor(Solidityv070Parser.FunctionDescriptorContext ctx) {
//        String functionIdentifier = ctx.identifier().getText();
//
//        return new Token(functionIdentifier);
//    }


    @Override public Map<String, Set<String>> visitFunctionDefinition(Solidityv070Parser.FunctionDefinitionContext ctx) {
        visitChildren(ctx);

        // link every Sets into the identifier Map
        resultMap.put(Constants.fparam, fparamIds);
        resultMap.put(Constants.dtype, elementaryTypes);
        resultMap.put(Constants.funccall, functionCalls);
        resultMap.put(Constants.ident, identifiers);

        return resultMap;
    }


    @Override public Map<String, Set<String>> visitParameterList(Solidityv070Parser.ParameterListContext ctx) {
        // get parameter identifier names to abstract them as FPARAM
        List<Solidityv070Parser.ParameterContext> params = ctx.parameter();
        for (Solidityv070Parser.ParameterContext param : params) {
            try {
                fparamIds.add(param.identifier().getText());
            }
            catch (NullPointerException ignored) {
            }
        }

        return visitChildren(ctx);
    }

    @Override public Map<String, Set<String>> visitFunctionCall(Solidityv070Parser.FunctionCallContext ctx) {
        // get function call expressions to abstract them as FUNCCALL
        try {
//            System.out.println(ctx.expression().getText());
            functionCalls.add(ctx.expression().getText());
        }
        catch (NullPointerException ignored) {
        }
        return visitChildren(ctx);
    }


    @Override public Map<String, Set<String>> visitElementaryTypeName(Solidityv070Parser.ElementaryTypeNameContext ctx) {
        // get all elementary types used in the investigating code to abstract them as DTYPE
        try {
            elementaryTypes.add(ctx.getText());
        }
        catch (NullPointerException ignored) {
        }
        return visitChildren(ctx);
    }


    @Override public Map<String, Set<String>> visitIdentifier(Solidityv070Parser.IdentifierContext ctx) {
        try {
            identifiers.add(ctx.Identifier().getText());
        }
        catch (NullPointerException ignored) {
        }

        return visitChildren(ctx);
    }
}
