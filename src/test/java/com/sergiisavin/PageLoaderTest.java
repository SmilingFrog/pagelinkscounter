package com.sergiisavin;

import com.sergiisavin.pageloader.PageLoaderJsoup;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PageLoaderTest extends PageLoaderJsoup {

    @Test(expected = IllegalArgumentException.class)
    public void givenNullThrowsException() throws Exception {
        String page = loadPage(null);
        assertEquals("", page);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenEmptyStringReturnEmptyPage() throws Exception {
        String page = loadPage("");
        assertEquals("", page);
    }

    @Test
    public void givenPageURLIsCorrectLoadPage() throws Exception {
        String page = loadPage("http://127.0.0.1:3000/index.html");
        System.out.println(page);
    }

}
