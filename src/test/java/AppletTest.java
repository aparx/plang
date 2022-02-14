import io.github.sauranbone.plang.PlangUtils;
import io.github.sauranbone.plang.map.DataBindMap;
import io.github.sauranbone.plang.map.DataBinder;
import io.github.sauranbone.plang.parsing.impl.NormalLexer;
import io.github.sauranbone.plang.parsing.impl.NormalParser;
import io.github.sauranbone.plang.parsing.impl.NormalTransformer;
import io.github.sauranbone.plang.placeholder.Placeholder;
import io.github.sauranbone.plang.specific.Language;
import io.github.sauranbone.plang.specific.Lexicon;
import io.github.sauranbone.plang.specific.Message;

import java.util.Locale;

/**
 * @author Vinzent Zeband
 * @version 23:54 CET, 12.02.2022
 * @since 1.0
 */
public class AppletTest {

    public static void main(String[] args) {

        System.out.println(PlangUtils.getTopSuperclass(Lexicon.class));

        Lexicon lexicon = new Lexicon();
        lexicon.set(Placeholder.of("test", 2));
        lexicon.set(Placeholder.of("user.name", User::getName, User.class));
        lexicon.set(Placeholder.of("user.age", User::getAge, User.class));

        Language language = new Language("english", "en", lexicon, NormalLexer.DEFAULT_LEXER, NormalParser.SINGLETON, NormalTransformer.SINGLETON);
        Message message = new Message("Hello {user.name}, are you really" +
                " {user.age} years old?", language);
        System.out.println(message.getTokens());

        DataBinder map = new DataBindMap();
        map.bindType(new User("undestroy", 33));
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

    public static class User {
        private String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }
    }

}
