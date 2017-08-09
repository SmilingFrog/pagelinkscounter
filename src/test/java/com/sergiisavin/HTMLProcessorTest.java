package com.sergiisavin;

import org.junit.Test;

import java.util.*;

public class HTMLProcessorTest extends HTMLProcessorJsoup {

    PageLoader loader = new PageLoaderJsoup();
    LinksExtractor extractor = new LinksExtractorJsoup();
    LinksCounter counter = new LinksCounterJsoup();

    @Test
    public void canProcessHTML() throws Exception {
        String html = loader.loadPage("http://www.pravda.com.ua");
        Set<String> extractedLinks = extractor.extractLinks(html);
        Map<String, DomainLinks> links = process(extractedLinks);
        Map<String, Integer> linksCount = counter.countLinks(links);
        printLinks(links);
        printLinksCount(linksCount);

    }

    private void printLinksCount(Map<String, Integer> linksCount) {
        for(String href : linksCount.keySet()){
            System.out.println(href + " " + linksCount.get(href));
        }
    }

    private void printLinks(Map<String, DomainLinks> links) {
        for (String linkHref : links.keySet()) {
            System.out.println();
            System.out.println(linkHref);
            DomainLinks domainLink = links.get(linkHref);
            Iterator iterator = domainLink.iterator();
            while (iterator.hasNext()) {
                System.out.printf("\t %s\n", iterator.next());
            }
        }
    }

}
