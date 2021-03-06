package io.github.sauranbone.plang.core.parsing.impl;

import io.github.sauranbone.plang.core.error.LanguageErrorHandler;
import io.github.sauranbone.plang.core.error.ParseError;
import io.github.sauranbone.plang.core.error.ParseErrorType;
import io.github.sauranbone.plang.core.parsing.MessageParser;
import io.github.sauranbone.plang.core.parsing.MessageToken;
import io.github.sauranbone.plang.core.parsing.ParsedTokens;
import io.github.sauranbone.plang.core.specific.Language;
import io.github.sauranbone.plang.core.specific.Lexicon;

import java.util.List;
import java.util.Objects;

/**
 * Normal and global parser implementation that parses an input of tokens
 * and validates them and their appearance.
 *
 * @author Vinzent Zeband
 * @version 02:59 CET, 13.02.2022
 * @see MessageParser
 * @since 1.0
 */
public class DefaultParser implements MessageParser {

    /**
     * A default normal parsing instance used to parse default generated
     * tokens.
     *
     * @see NormalLexer
     */
    public static final DefaultParser SINGLETON = new DefaultParser();

    /**
     * Allocates a new normal parser instance.
     *
     * @see #SINGLETON
     */
    protected DefaultParser() {
        //Hide constructor for singleton but to still allow in inheritance
    }

    @Override
    public ParsedTokens parse(Language language, List<MessageToken> tokens) {
        Objects.requireNonNull(language);
        Lexicon lexicon = language.getLexicon();
        Objects.requireNonNull(lexicon, "Lexicon");
        if (tokens == null || tokens.isEmpty()) return new ParsedTokens();
        //Create buffer and start validating each token
        final int n = tokens.size();
        ParsedTokens.Builder output = new ParsedTokens.Builder(n);
        for (int i = 0; i < n; i++) {
            MessageToken token = tokens.get(i);
            if (!token.isPlaceholder()) {
                output.add(token);
                continue;
            }
            //add setting that allows "dynamic placeholder" TODO
            String target = token.getValue();
            if (!lexicon.has(target)) {
                //Send warning that target is not contained
                LanguageErrorHandler errors = language.getErrorHandler();
                errors.handle(new ParseError(ParseErrorType.NOTIFY,
                        "placeholder " + target + " is dynamic"));
            }
            output.add(token);
        }
        //Trim the actual list reference to its actual size
        output.getReference().trimToSize();
        return output.build();
    }

}
