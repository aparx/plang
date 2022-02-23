package io.github.sauranbone.plang.core.specific;

import io.github.sauranbone.plang.core.placeholder.Placeholder;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Vinzent Zeband
 * @version 02:46 CET, 13.02.2022
 * @since 1.0
 */
public class TestLexicon {

    @Test
    public void get() {
        Lexicon lexicon = new Lexicon();
        lexicon.set(Placeholder.of("foo", "bar"));
        assertNotNull(lexicon.get("foo"));
        assertTrue(lexicon.get("foo").isStatic());
        assertTrue(lexicon.get("foo").isNullable());
        assertFalse(lexicon.get("foo").isTransformative());
        assertEquals("bar", lexicon.get("foo").transform(null));
    }

}
