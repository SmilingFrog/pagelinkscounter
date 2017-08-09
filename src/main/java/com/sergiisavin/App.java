package com.sergiisavin;

import com.sergiisavin.domainlinks.DomainLinks;
import com.sergiisavin.htmlprocessor.HTMLProcessor;
import com.sergiisavin.htmlprocessor.HTMLProcessorJsoup;
import com.sergiisavin.linkscounter.LinksCounter;
import com.sergiisavin.linkscounter.LinksCounterJsoup;
import com.sergiisavin.linksextractor.LinksExtractor;
import com.sergiisavin.linksextractor.LinksExtractorJsoup;
import com.sergiisavin.pageloader.PageLoader;
import com.sergiisavin.pageloader.PageLoaderJsoup;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class App
{
    public static void main( String[] args ) {
        PageLoader loader = new PageLoaderJsoup();
        LinksExtractor extractor = new LinksExtractorJsoup();
        HTMLProcessor processor = new HTMLProcessorJsoup();
        LinksCounter counter = new LinksCounterJsoup();

        System.out.println("enter valid URL including protocol: ");
        Scanner scanner = new Scanner(System.in);
        String url = scanner.nextLine();

        String html = loader.loadPage(url);
        Set<String> links = extractor.extractLinks(html);
        Map<String, DomainLinks> domainLinks = processor.process(links);
        Map<String, Integer> countedLinks = counter.countLinks(domainLinks);

        for(String link : countedLinks.keySet()){
            System.out.println(link + " " + countedLinks.get(link));
        }

    }
}
