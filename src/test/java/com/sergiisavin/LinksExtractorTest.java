package com.sergiisavin;

import com.sergiisavin.linksextractor.LinksExtractor;
import com.sergiisavin.linksextractor.LinksExtractorJsoup;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LinksExtractorTest {

    String pageWith1Link = "<html><head></head><body>" +
            "<a href='http://www.site1.com'>link</a></body></html>";
    String pageWith2Links = "<html><head></head><body>" +
            "<a href='http://www.site1.com'>link1</a>" +
            "<a href='http://www.site2.org'>link2</a></body></html>";
    LinksExtractor extractor;

    @Before
    public void setUp() throws Exception {
        extractor = new LinksExtractorJsoup();
    }

    @Test
    public void givenPageWithOneLinkExtractTheLink() throws Exception {
        Set<String> links = extractor.extractLinks(pageWith1Link);
        assertEquals(1, links.size());
        assertTrue(links.contains("http://www.site1.com"));
    }

    @Test
    public void givenTwoLinksExtract2Links() throws Exception {
        Set<String> links = extractor.extractLinks(pageWith2Links);
        assertEquals(2, links.size());
        assertTrue(links.contains("http://www.site1.com"));
        assertTrue(links.contains("http://www.site2.org"));
    }
}
