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
    private Set<String> lvarIds = new LinkedHashSet<>();           // TODO: 이건 어떻게 찾아야 할지 고민해봐야함
    private Set<String> elementaryTypes = new LinkedHashSet<>();
    private Set<String> functionCalls = new LinkedHashSet<>();
    private Set<String> identifiers = new LinkedHashSet<>();


    //    @Override public Token visitFunctionDescriptor(Solidityv070Parser.FunctionDescriptorContext ctx) {
//        String functionIdentifier = ctx.identifier().getText();
//
//        return new Token(functionIdentifier);
//    }


    @Override public Map<String, Set<String>> visitFunctionDefinition(Solidityv070Parser.FunctionDefinitionContext ctx) {
        visitChildren(ctx);

        // link every Sets into the identifier Map
        resultMap.put(Constants.dtype, elementaryTypes);
        resultMap.put(Constants.fparam, fparamIds);
        resultMap.put(Constants.ident, identifiers);

        return resultMap;
    }


    @Override public Map<String, Set<String>> visitParameterList(Solidityv070Parser.ParameterListContext ctx) {
        // TODO: get parameter identifier names to abstract them as FPARAM
        List<Solidityv070Parser.ParameterContext> params = ctx.parameter();
        for (Solidityv070Parser.ParameterContext param : params) {
            fparamIds.add(param.identifier().getText());
        }

        return visitChildren(ctx);
    }

    @Override public Map<String, Set<String>> visitFunctionCall(Solidityv070Parser.FunctionCallContext ctx) {
        // TODO: get function calls to abstract them as FUNCCALL

        return visitChildren(ctx);
    }


    @Override public Map<String, Set<String>> visitElementaryTypeName(Solidityv070Parser.ElementaryTypeNameContext ctx) {
        // TODO: get list of all elementary types used in the investigating code to abstract them as DTYPE
//        System.out.println(ctx.getText());
        elementaryTypes.add(ctx.getText());

        return visitChildren(ctx);
    }


    @Override public Map<String, Set<String>> visitIdentifier(Solidityv070Parser.IdentifierContext ctx) {
        try {
            identifiers.add(ctx.Identifier().getText());
        }
        // TODO: handle <address type> casting; ignoring them for now
        catch (NullPointerException ignored) {

        }

        return visitChildren(ctx);
    }
}
