package com.example.notes;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MainActivityTest extends TestCase {

    @Test
    public void fromMenuTitleToSectionName() {//OK
        String str = "Delete Sergio";
        String[] separatedTitle = str.split(" ", 2);
        String result = separatedTitle[1];
        assertEquals("Sergio", result);
    }

    @Test
    public void fromMenuTitleToComposedSectionName() {//OK
        String str = "Delete Sergio Ribera";
        String[] separatedTitle = str.split(" ", 2);
        String result = separatedTitle[1];
        assertEquals("Sergio Ribera", result);
    }
}