package com.example.group19project;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class updatedTest {
    Validity day = new Validity();
    @Test
    void validDayCheck(){

        assertTrue(day.validDay("Monday"));

    }

}