package com.example.group19project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import junit.framework.TestCase;

import org.junit.Test;

public class ValidityTest2 extends TestCase {
    @Test
    public void testCases() {
        Validity valid = new Validity();
        assertTrue(valid.validDay("Monday"));
        assertTrue(valid.validDay("Tuesday"));
        assertEquals(true, valid.validTime("5:43 PM"));
        assertEquals(true, valid.validTime("12:43 PM"));
        assertFalse(valid.validTime("22:43 PM"));
    }
}