import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;
import io.github.sauranbone.plang.PlangUtils;
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
        Language language = new Language("english", "en", lexicon,
                NormalLexer.DEFAULT_LEXER, new NormalParser());
        Message message = new Message("test", language);
        System.out.println(message.getTokens());

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
