package examples;

import io.github.sauranbone.plang.map.DataBindMap;
import io.github.sauranbone.plang.map.DataBinder;
import io.github.sauranbone.plang.placeholder.Placeholder;
import io.github.sauranbone.plang.specific.Language;
import io.github.sauranbone.plang.specific.Lexicon;
import io.github.sauranbone.plang.specific.Message;
import io.github.sauranbone.plang.specific.MessageRegistry;

/**
 * @author Vinzent Zeband
 * @version 04:49 CET, 15.02.2022
 * @since 1.0
 */
public class UserExample {

    public static void main(String[] args) {
        //Allocate lexicon and then the language for it
        Lexicon lexicon = new Lexicon();
        Language language = new Language("English", "en", lexicon);

        //Now we load default global placeholders
        //The following placeholders are static
        lexicon.set(Placeholder.of("1+1", 2));

        //The following placeholders are dynamic
        lexicon.set(Placeholder.of("user.name", user -> user.username, User.class));
        lexicon.set(Placeholder.of("user.address", user -> user.address.getFullAddress(), User.class));
        lexicon.set(Placeholder.of("address.country", addr -> addr.country, Address.class));
        lexicon.set(Placeholder.of("address.city", addr -> addr.city, Address.class));
        lexicon.set(Placeholder.of("address.postcode", addr -> addr.postcode, Address.class));
        lexicon.set(Placeholder.of("address.address", addr -> addr.address, Address.class));
        lexicon.set(Placeholder.of("address.number", addr -> addr.number, Address.class));

        //Now we load some example messages
        MessageRegistry messages = language.getContent();
        messages.set("user-welcome", "Welcome {user.name} to this example!");
        messages.set("user-full", "{user.name} full address: {user.address}");
        messages.set("user-near", "{user.name} lives in {address.country} near {address.postcode}");

        //Create our example
        Address address = new Address("Germany", "Viersen", "41748", "Greenland Str.", 33);
        User user = new User("oceanHuntr3", address);

        //Now we create a binding data map using types
        DataBinder data = DataBindMap.types(user, address);

        //We get our messages and sysout them
        Message welcome = messages.get("user-welcome");
        System.out.println(welcome.transform(data));
        // = "Welcome oceanHuntr3 to this example!"

        Message fullAddr = messages.get("user-full");
        System.out.println(fullAddr.transform(data));
        // = "oceanHuntr3 full address: Viersen 41748, Greenland Str. 33"

        Message nearAddr = messages.get("user-near");
        System.out.println(nearAddr.transform(data));
        // = "oceanHuntr3 lives in Germany near 41748"
    }

    public static class Address {
        final String country;
        final String city;
        final String postcode;
        final String address;
        final int number;

        public Address(String country, String city, String postcode, String address, int number) {
            this.country = country;
            this.city = city;
            this.postcode = postcode;
            this.address = address;
            this.number = number;
        }

        public String getFullAddress() {
            return city + ' ' + postcode + ", " + address + " " + number;
        }

    }

    public static class User {
        final String username;
        final Address address;

        public User(String username, Address address) {
            this.username = username;
            this.address = address;
        }
    }

}
