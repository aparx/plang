package io.github.sauranbone.plang.parsing.impl;

import io.github.sauranbone.plang.PlangUtils;
import io.github.sauranbone.plang.specific.Language;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * A normal and default lexer using regex to separate placeholders and
 * literal text from each other.
 *
 * @author Vinzent Zeband
 * @version 03:28 CET, 13.02.2022
 * @since 1.0
 */
public class NormalLexer extends RegexLexer {

    /**
     * Default regex lexer used for messages.
     * <p>The placeholder syntax requires a placeholder to be surrounded,
     * or rather be wrapped around, with curly brackets and the target
     * placeholder name in-between:
     * <pre><code>
     *     Example: "this is literal text and {and this is a placeholder}"
     *     Example: "Welcome {userName}!"
     * </code></pre>
     * <p>Thus, {@link #getValue(Language, String)} will always just
     * return the actual intend name of the placeholder, which is equal to
     * <code>"and this is a placeholder"</code> in the first example and
     * <code>"userName"</code> in the second example.
     */
    transient public static final NormalLexer DEFAULT_LEXER
            = new NormalLexer("{", "}");

    final String opening, closing;

    /**
     * Allocates a normal lexer having an {@code opening} and
     * {@code closing} as content around a target placeholder in order to
     * indicate that the sequence is meant to be a placeholder.
     * <p>The given {@code opening} and {@code closing} strings are
     * automatically escaped if required and thus do not need to be escaped
     * already.  These strings are not allowed to represent regular
     * expression, it is exactly the characters that are wrapped around a
     * literal placeholder to indicate being a placeholder.
     *
     * @see NormalLexer
     */
    public NormalLexer(String opening, String closing) {
        super(Pattern.compile(PlangUtils.escapeRegex(opening)
                + "[.[^" + nullcheck(opening) + nullcheck(closing) + "]]+"
                + PlangUtils.escapeRegex(closing)));
        this.opening = opening;
        this.closing = closing;
    }

    /**
     * Returns true if this opening character sequence is neither null or
     * empty.
     *
     * @return false if this lexer instance has required no opening
     * characters
     */
    public boolean hasOpening() {
        return !StringUtils.isEmpty(opening);
    }

    /**
     * Returns true if this closing character sequence is neither null or
     * empty.
     *
     * @return false if this lexer instance has required no closing
     * characters
     */
    public boolean hasClosing() {
        return !StringUtils.isEmpty(closing);
    }

    /**
     * Returns the leading character before a placeholder name.
     *
     * @return the leading character before entering a placeholder,
     * {@code nullable}
     */
    public String getOpening() {
        return opening;
    }

    /**
     * Returns the closing characters after a placeholder name.
     *
     * @return the tailing characters after entering a placeholder,
     * {@code nullable}
     */
    public String getClosing() {
        return closing;
    }

    @Override
    protected String getValue(Language language, String raw) {
        final int n = StringUtils.length(raw);
        int opn = StringUtils.length(opening);
        int cls = StringUtils.length(closing);
        if (n < opn + cls) {
            //TODO warn or issue that the given value cannot exist?
            return raw;
        }
        return raw.substring(opn, n - cls);
    }

    private static String nullcheck(String str) {
        return str == null ? StringUtils.EMPTY : str;
    }
}
