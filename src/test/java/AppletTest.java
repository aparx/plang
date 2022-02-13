import io.github.sauranbone.plang.parsing.MessageLexer;
import io.github.sauranbone.plang.parsing.impl.NormalLexer;
import io.github.sauranbone.plang.parsing.impl.NormalParser;
import io.github.sauranbone.plang.specific.Language;

/**
 * @author Vinzent Zeband
 * @version 23:54 CET, 12.02.2022
 * @since 1.0
 */
public class AppletTest {

    public static void main(String[] args) {
        Language language = new Language("english", "en",
                NormalLexer.DEFAULT_LEXER, new NormalParser());
        MessageLexer lexer = language.getLexer();
        System.out.println(lexer.tokenize(language, "Hello {world} how are you"));

//        Lexicon lexicon = new Lexicon();
//        lexicon.set(Placeholder.of("test", "asd"));
//        lexicon.get("test");
//        lexicon.clear();
//        ParsedTokens.Builder builder = new ParsedTokens.Builder();
//        builder.add(new MessageToken("hi", null, MessageTokenType.LITERAL))
//                .build().get(2);
//
//        MessageToken token = new MessageToken("test", "test", MessageTokenType.LITERAL);
//        Placeholder<?> ph = Placeholder.of("test", s -> "test", String.class);
    }

}
