import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class solidityCCD {

    public static void main(String[] args){
        File file = new File("src/main/resources/test.sol");
        try {
            String code = FileUtils.readFileToString(file, "UTF-8");
            SolidityLexer lexer = new SolidityLexer(CharStreams.fromString(code));
            Solidity parser = new Solidity(new CommonTokenStream(lexer));

            // use parser~~!

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
