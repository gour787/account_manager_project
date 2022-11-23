package com.example.group19project;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;


class ValidityTests {


    @Test
    public void testCases() {
        Validity valid = new Validity();
        assertTrue(valid.validDay("Monday"));
        assertTrue(valid.validDay("Tuesday"));
        assertEquals(true, valid.validTime("5:43 PM"));
        assertEquals(true, valid.validTime("12:43 PM"));
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("ValidityTests");

    }
}

