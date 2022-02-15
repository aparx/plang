import io.github.sauranbone.plang.map.DataBindMap;
import io.github.sauranbone.plang.map.DataBinder;
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
        Language language = new Language("English", "en", lexicon);

        //Fill the lexicon with values
        lexicon.set(Placeholder.of("test", 2));
        lexicon.set(Placeholder.of("user.name", User::getName, User.class));
        lexicon.set(Placeholder.of("user.age", User::getAge, User.class));
        lexicon.set(Placeholder.of("lang.name", Language::getName, Language.class));
        lexicon.set(Placeholder.of("lang.abb", Language::getAbbreviation, Language.class));
        lexicon.set(Placeholder.of("lang.id", l -> l.getIdentifier(), Language.class));

        //Create language based registry
        MessageRegistry registry = language.getContent();
        registry.set("error", "<Cannot localize message>");
        registry.set("hi-user", "Welcome {user.name}, you are reading " +
                "{lang.name} ({lang.abb}, {lang.id}) language!");
        registry.set("bye-user", "The user {user.name} has left our " + "server!");
        Message message = registry.get("hi-user");
        System.out.println(message.getTokens());

        User user = new User("vincent", 33);

        DataBinder payload = DataBindMap.types(user);
        System.out.println(message.transform(payload));

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
