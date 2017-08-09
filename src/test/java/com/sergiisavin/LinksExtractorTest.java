package com.sergiisavin;

import com.sergiisavin.linksextractor.LinksExtractor;
import com.sergiisavin.linksextractor.LinksExtractorJsoup;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LinksExtractorTest {

    String page = "<html><head></head><body><a href=\"site1.com\">link</a></body></html>";
    @Test
    public void givenPageWithOneLinkExtractTheLink() throws Exception {
        LinksExtractor extractor = new LinksExtractorJsoup();
        Set<String> links = extractor.extractLinks(page);
        assertEquals(1, links.size());
        assertTrue(links.contains("site1.com"));
    }
}
