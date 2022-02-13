import io.github.sauranbone.plang.lang.Placeholder;
import io.github.sauranbone.plang.parsing.MessageToken;
import io.github.sauranbone.plang.parsing.TokenType;

/**
 * @author Vinzent Zeband
 * @version 23:54 CET, 12.02.2022
 * @since 1.0
 */
public class AppletTest {

    public static void main(String[] args) {
        MessageToken token = new MessageToken("test", "test", TokenType.LITERAL);
        Placeholder<?> ph = Placeholder.of("test", s -> "test", String.class);
    }

}
