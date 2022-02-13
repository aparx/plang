import com.sun.xml.internal.ws.assembler.MetroTubelineAssembler;
import io.github.sauranbone.plang.parsing.ParsedTokens;
import io.github.sauranbone.plang.specific.Lexicon;
import io.github.sauranbone.plang.placeholder.Placeholder;
import io.github.sauranbone.plang.parsing.MessageToken;
import io.github.sauranbone.plang.parsing.MessageTokenType;

/**
 * @author Vinzent Zeband
 * @version 23:54 CET, 12.02.2022
 * @since 1.0
 */
public class AppletTest {

    public static void main(String[] args) {
        Lexicon lexicon = new Lexicon();

        lexicon.set(Placeholder.of("test", "asd"));
        lexicon.get("test");
        lexicon.clear();
        ParsedTokens.Builder builder = new ParsedTokens.Builder();
        builder.add(new MessageToken("hi", null, MessageTokenType.LITERAL))
                .build().get(2);

        MessageToken token = new MessageToken("test", "test", MessageTokenType.LITERAL);
        Placeholder<?> ph = Placeholder.of("test", s -> "test", String.class);
    }

}
