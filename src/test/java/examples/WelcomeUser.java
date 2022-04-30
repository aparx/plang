package examples;

import io.github.sauranbone.plang.core.Plang;
import io.github.sauranbone.plang.core.factory.LanguageFactory;
import io.github.sauranbone.plang.core.map.DataBindMap;
import io.github.sauranbone.plang.core.map.DataBinder;
import io.github.sauranbone.plang.core.placeholder.Placeholder;
import io.github.sauranbone.plang.core.specific.Language;
import io.github.sauranbone.plang.core.specific.Lexicon;
import io.github.sauranbone.plang.core.specific.Message;
import io.github.sauranbone.plang.core.specific.MessageRegistry;

import javax.jws.soap.SOAPBinding;

/**
 * @author Vinzent Zeband
 * @version 15:00 CET, 23.02.2022
 * @since 1.0
 */
public class WelcomeUser {

    public static void func1() {
        LanguageFactory factory = Plang.getLanguageFactory();
        Language english = factory.getOrCreate("English", "en");
        MessageRegistry content = english.getRegistry();
        content.set("public-announce", "Welcome {Name} to our chat!");

        //Display the message
        Message message = content.get("public-announce");
        String formatted = message.transform(DataBindMap.index("Vinzent"));
        System.out.println(formatted);
    }

    public static void main(String[] args) {
        Lexicon lexicon = new Lexicon();
        LanguageFactory factory = Plang.getLanguageFactory();
        Language english = factory.getOrCreate("English", "en", lexicon);
        //Bind dynamic placeholders into the global lexicon
        lexicon.set(Placeholder.of("user.name", User.class, user -> user.name));
        lexicon.set(Placeholder.of("user.age", User.class, user -> user.age));

        //Bind a static placeholder into the global lexicon
        lexicon.set(Placeholder.of("worldLanguage", "English"));

        //Bind a type to the global lexicon which #toString() is used
        lexicon.set(Placeholder.of("userToString", User.class));

        MessageRegistry content = english.getRegistry();
        content.set("age", "{user.name} is {user.age} years old!");
        content.set("lang", "The language of the world is: {worldLanguage}");
        content.set("user", "User instance #toString() is: {userToString}");

        //Display the message
        Message message = content.get("age");
        User exampleUser = new User("oceanHuntr3", 43);
        DataBinder data = DataBindMap.types(exampleUser);
        String formatted = message.transform(data);
        System.out.println(formatted);
        System.out.println(content.get("lang").transform());
        System.out.println(content.get("user").transform(data));
    }

    public static class User {
        public String name;
        public int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

}
