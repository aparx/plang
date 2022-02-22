import io.github.sauranbone.plang.error.DefaultErrorHandler;
import io.github.sauranbone.plang.map.DataBindMap;
import io.github.sauranbone.plang.parsing.impl.NormalLexer;
import io.github.sauranbone.plang.specific.MessageRegistry;
import io.github.sauranbone.plang.placeholder.Placeholder;
import io.github.sauranbone.plang.specific.*;

/**
 * @author Vinzent Zeband
 * @version 23:54 CET, 12.02.2022
 * @since 1.0
 */
public class AppletTest {

    public static void main(String[] args) {
        Lexicon lexicon = new Lexicon();
        lexicon.set(Placeholder.of("user.name", "oceanHuntr3"));
        Language language = new Language("English", "en", lexicon);
        language.setErrorHandler(error -> System.out.println(error.getErrorType() + ": " + error.getMessage()));
        MessageRegistry registry = language.getRegister();
        registry.set("welcome", "Hello {user.x} to the server!");
        System.out.println(registry.get("welcome")
                .transform(DataBindMap.index("some bastard")));

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
