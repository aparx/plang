package io.github.sauranbone.plang.parsing.impl;

import io.github.sauranbone.plang.parsing.MessageParser;
import io.github.sauranbone.plang.parsing.MessageToken;
import io.github.sauranbone.plang.parsing.MessageTokenType;
import io.github.sauranbone.plang.parsing.ParsedTokens;
import io.github.sauranbone.plang.specific.Language;
import io.github.sauranbone.plang.specific.Lexicon;

import java.util.ArrayList;
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
public class NormalParser implements MessageParser {

    @Override
    public ParsedTokens parse(Language language, List<MessageToken> tokens) {
        Objects.requireNonNull(language);
        Lexicon lexicon = language.getLexicon();
        Objects.requireNonNull(lexicon, "Lexicon");
        if (tokens == null || tokens.isEmpty())
            return new ParsedTokens();
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
                //Send warning that target is not contained TODO
                token = new MessageToken(token.getRaw(),
                        null, MessageTokenType.LITERAL);
            }
            output.add(token);
        }
        //Trim the actual list reference to its actual size
        output.getReference().trimToSize();
        return output.build();
    }

}
