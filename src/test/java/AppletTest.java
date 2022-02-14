import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;
import io.github.sauranbone.plang.PlangUtils;
import io.github.sauranbone.plang.map.DataBindMap;
import io.github.sauranbone.plang.map.DataBinder;
import io.github.sauranbone.plang.parsing.impl.NormalLexer;
import io.github.sauranbone.plang.parsing.impl.NormalParser;
import io.github.sauranbone.plang.placeholder.Placeholder;
import io.github.sauranbone.plang.specific.Language;
import io.github.sauranbone.plang.specific.Lexicon;
import io.github.sauranbone.plang.specific.Message;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Vinzent Zeband
 * @version 23:54 CET, 12.02.2022
 * @since 1.0
 */
public class AppletTest {

    public static void main(String[] args) {
        Lexicon lexicon = new Lexicon();
        lexicon.set(Placeholder.of("player", "danishGunner"));
        lexicon.set(Placeholder.of("world", "world!"));
        Language language = new Language("english", "en",
                lexicon, NormalLexer.DEFAULT_LEXER, NormalParser.DEFAULT_PARSER);
        Message message = new Message("Hello {player} {xx}", language);
        System.out.println(message.getTokens());

        DataBinder map = new DataBindMap();
        map.bind("XX", "undestroy");
        System.out.println(message.transform(map));

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

    public static class PlayerTestObject {
        private String name;

        public PlayerTestObject(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
