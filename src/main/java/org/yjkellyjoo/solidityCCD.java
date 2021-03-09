package org.yjkellyjoo;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import org.yjkellyjoo.v070.*;
import org.yjkellyjoo.ccd.nicad;

public class solidityCCD {

    public static void main(String[] args){
        File file = new File("src/main/resources/test.sol");
        try {
            String code = FileUtils.readFileToString(file, "UTF-8");
            Solidityv070Lexer lexer = new Solidityv070Lexer(CharStreams.fromString(code));
            Solidityv070Parser parser = new Solidityv070Parser(new CommonTokenStream(lexer));

//             use parser~~!
            nicad.runNicad(parser);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
