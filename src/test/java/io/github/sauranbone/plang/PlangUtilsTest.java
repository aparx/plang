package io.github.sauranbone.plang;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Vinzent Zeband
 * @version 06:13 CET, 13.02.2022
 * @since 1.0
 */
public class PlangUtilsTest {

    @Test
    public void testEscaping() {
        assertEquals("java \\{bar\\|coco\\} is \\=\\= import \\<c\\+\\+\\> with \\*\\! \\(Or is it o\\.O\\?\\^\\^\\)",
                PlangUtils.escapeRegex("java {bar|coco} is == import <c++> with *! (Or is it o.O?^^)"));
        for (char i : PlangUtils.REGEX_SPECIAL_CHARACTERS.toCharArray()) {
            assertEquals("\\" + i, PlangUtils.escapeRegex(i));
        }
        for (int i = 'A'; i < 'Z'; i++) {
            assertEquals(String.valueOf((char) i),
                    PlangUtils.escapeRegex((char) i));
        }
    }

}
