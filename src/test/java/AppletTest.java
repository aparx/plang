import io.github.sauranbone.plang.core.Plang;
import io.github.sauranbone.plang.core.factory.LanguageFactory;
import io.github.sauranbone.plang.core.map.DataBindMap;
import io.github.sauranbone.plang.core.specific.MessageRegistry;
import io.github.sauranbone.plang.core.placeholder.Placeholder;
import io.github.sauranbone.plang.core.specific.*;

/**
 * @author Vinzent Zeband
 * @version 23:54 CET, 12.02.2022
 * @since 1.0
 */
public class AppletTest {

    public static void main(String[] args) {
        LanguageFactory factory = Plang.getLanguageFactory();
        Lexicon lexicon = new Lexicon();
        lexicon.set(Placeholder.of("userToString", User.class));
        lexicon.set(Placeholder.of("userName", User.class, u -> u.name));
        lexicon.set(Placeholder.of("userAge", User.class, u -> u.age));
        lexicon.set(Placeholder.of("prefix", "[Prefix123]"));
        Language language = factory.getOrBake("English", "en", lexicon);
        language.setErrorHandler(error -> System.out.println(error.getErrorType() + ": " + error.getMessage()));
        MessageRegistry registry = language.getRegister();
        registry.set("welcome", "{prefix} Hello {userToString} or " +
                "{userName}, " +
                "{userAge} to the server!");
        System.out.println(registry.get("welcome").transform(DataBindMap.index("some bastard")));

        Language en = factory.getByAbbreviation("en");
        DataBindMap map = new DataBindMap();
        map.bindType(new User("vincent", 18));
        System.out.println(en.getRegister().get("welcome").transform(map));

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

        @Override
        public String toString() {
            return "User{" + "name='" + name + '\'' + ", age=" + age + '}';
        }
    }

}
