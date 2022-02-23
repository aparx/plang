package io.github.sauranbone.plang.core.parsing.impl;

import io.github.sauranbone.plang.core.parsing.MessageLexer;
import io.github.sauranbone.plang.core.parsing.MessageToken;
import io.github.sauranbone.plang.core.parsing.MessageTokenType;
import io.github.sauranbone.plang.core.placeholder.Placeholder;
import io.github.sauranbone.plang.core.specific.Language;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Vinzent Zeband
 * @version 02:53 CET, 13.02.2022
 * @since 1.0
 */
public abstract class RegexLexer implements MessageLexer {

    //Placeholder lexeme
    private final Pattern primaryLexeme;

    /**
     * Allocates a new regex lexer having given {@code lexeme} as
     * placeholder lexeme, which determines whenever a placeholder is found
     * in literal context.
     *
     * @param lexeme the target placeholder lexeme
     * @throws NullPointerException if {@code lexemes} is null
     */
    public RegexLexer(Pattern lexeme) {
        Objects.requireNonNull(lexeme);
        this.primaryLexeme = lexeme;
    }

    /**
     * Method called whenever a sequence within content has been identified
     * as token and must now filter out the actual placeholder pointing
     * name.
     *
     * @param language the language that is used
     * @param raw      the raw sub-content that contains the syntax of the
     *                 placeholder
     * @return the targeting plain placeholder name the context is trying
     * to point to
     * @see Placeholder
     */
    protected abstract String getValue(Language language, String raw);

    @Override
    public List<MessageToken> tokenize(Language language, String content) {
        Objects.requireNonNull(language);
        List<MessageToken> stack = new ArrayList<>();
        if (StringUtils.isEmpty(content))
            return stack;

        // Now we just match the content to this lexeme and try to filter
        // out every token possible
        final int n = content.length();
        Matcher matcher = primaryLexeme.matcher(content);
        for (int end = 0; true; ) {
            boolean found = matcher.find();
            int beg = found ? matcher.start() : n;
            if (beg - end > 0) {
                //Add anything before (in content scope) as literal
                String lta = content.substring(end, beg);
                stack.add(new MessageToken(lta, null,
                        MessageTokenType.LITERAL));
            }
            if (!found) break;
            end = matcher.end();
            String ctn = matcher.group();
            String val = getValue(language, ctn);
            if (!StringUtils.isEmpty(val)) {
                //Add val as placeholder to the stack
                stack.add(new MessageToken(ctn, val,
                        MessageTokenType.PLACEHOLDER));
            }
        }
        return stack;
    }

    /**
     * Returns the placeholder lexeme that determines whenever a
     * placeholder is matching in literal context.
     *
     * @return the placeholder lexeme
     */
    public Pattern getLexeme() {
        return primaryLexeme;
    }
}
