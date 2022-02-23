package io.github.sauranbone.plang.core.parsing.impl;

import io.github.sauranbone.plang.core.PlangUtils;
import io.github.sauranbone.plang.core.error.LanguageErrorHandler;
import io.github.sauranbone.plang.core.error.ParseError;
import io.github.sauranbone.plang.core.error.ParseErrorType;
import io.github.sauranbone.plang.core.map.DataBinder;
import io.github.sauranbone.plang.core.parsing.MessageToken;
import io.github.sauranbone.plang.core.parsing.MessageTransformer;
import io.github.sauranbone.plang.core.parsing.ParsedTokens;
import io.github.sauranbone.plang.core.placeholder.Placeholder;
import io.github.sauranbone.plang.core.specific.Language;
import io.github.sauranbone.plang.core.specific.Lexicon;

import java.util.Objects;

/**
 * Default messaging transformer transforming tokens into a literal
 * sequence to be understood as the final result of message transformation
 * or translation.
 * <p>This transformer has the following specifications:
 * <p>Only bound string-, integer- and class-type keys are used off of
 * the provided {@code data} binding map.
 * <p>A literal placeholder is only transformed once.
 * <p>The following table explains the precedences of boundaries and
 * their object key types:
 * <table>
 *     <tr>
 *         <th>Type</th>
 *         <th>Precedence</th>
 *         <th>Description</th>
 *     </tr>
 *     <tr>
 *         <th>Integer</th>
 *         <th>3 - highest</th>
 *         <th>An integer is used as an index and thus has the
 *         most precedence as it is directly addressing an exact and
 *         certain placeholder in literal context.</th>
 *     </tr>
 *     <tr>
 *         <th>String</th>
 *         <th>2 - middle</th>
 *         <th>A string is mainly used to bind literal
 *         placeholders to a certain value and thus have second
 *         highest precedence.</th>
 *     </tr>
 *     <tr>
 *         <th>Class</th>
 *         <th>1 - lowest</th>
 *         <th>A class is mainly used whenever a literal
 *         placeholder in literal context is neither bound to its
 *         corresponding position (index) nor to its representing
 *         targeted literal placeholder name.  The class is used
 *         to only pass a certain object that should then be
 *         transformed using {@link Placeholder#transform(Object)}.
 *         </th>
 *     </tr>
 * </table>
 */
public class DefaultTransformer implements MessageTransformer {

    /**
     * Default message transformer instance having standard
     * specifications.
     *
     * @see DefaultTransformer
     */
    transient public static final DefaultTransformer SINGLETON = new DefaultTransformer();

    /**
     * Allocates a new normal transformer having standard specifications.
     *
     * @see #SINGLETON
     */
    protected DefaultTransformer() {
        //Hide constructor for singleton but to still allow in inheritance
    }

    /**
     * {@inheritDoc}
     *
     * @param tokens   the target already parsed tokens of a message
     * @param language the target language that should be converted into
     * @param message  the target message that is transformed
     * @param data     the target binding information
     * @return the translated {@code tokens} sequence
     * @see DefaultTransformer Normal Transformer Specifications
     */
    @Override
    @SuppressWarnings("unchecked")
    public String transform(ParsedTokens tokens, Language language, String message, DataBinder data) {
        Objects.requireNonNull(tokens, "Tokens");
        Objects.requireNonNull(language, "Language");
        Objects.requireNonNull(message, "Message");
        Objects.requireNonNull(data, "Data");
        if (tokens.isEmpty()) return message;
        final StringBuilder builder = new StringBuilder();
        final Lexicon lexicon = language.getLexicon();
        //Iterate through every token and check the bindings
        for (int n = tokens.size(), i = 0, p = 0; i < n; i++) {
            MessageToken token = tokens.get(i);
            if (token.isPlaceholder()) {
                //Process the token and its content compared to this attribs
                final String val = token.getValue();
                final int idx = p++;
                if (data.isBound(idx)) {
                    builder.append(data.get(idx));
                    continue;
                } else if (data.isBound(val)) {
                    builder.append(data.get(val));
                    continue;
                } else if (lexicon.has(val)) {
                    //Access the placeholder directly and ask for its type
                    Placeholder<Object> ph = (Placeholder<Object>) lexicon.get(val);
                    if (ph == null) continue;   //Throw warning
                    Class<?> type = PlangUtils.getTopSuperclass(ph.getAcceptingType());
                    if (ph.isTransformative() && data.isBound(type)) {
                        //TODO also add class boundary if index or string is
                        // set with a target value that is not null
                        builder.append(ph.transform(data.get(type)));
                        continue;
                    } else if (ph.isStatic() && ph.isNullable()) {
                        //Get static access or nullable access
                        builder.append(ph.transform(null));
                        continue;
                    }
                }
                //TODO Warn that the placeholder is not bound
                //Send warning that target is not contained
                LanguageErrorHandler errors = language.getErrorHandler();
                errors.handle(new ParseError(ParseErrorType.WARNING,
                        "placeholder " + val + " not bound"));
            }
            //Just append the token as literal text
            builder.append(token.getRaw());
        }
        return builder.toString();
    }

}
