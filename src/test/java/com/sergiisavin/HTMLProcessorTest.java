package com.sergiisavin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class HTMLProcessorTest {

    PageLoader loader = new PageLoaderJsoup();

    @Test
    public void canProcessHTML() throws Exception {
        String html = loader.loadPage("http://www.pravda.com.ua");
        Map<String, DomainLinks> links = process(html);
        Map<String, Integer> linksCount = countLinks(links);
        printLinks(links);
        printLinksCount(linksCount);

    }

    private void printLinksCount(Map<String, Integer> linksCount) {
        for(String href : linksCount.keySet()){
            System.out.println(href + " " + linksCount.get(href));
        }
    }

    private Map<String,Integer> countLinks(Map<String, DomainLinks> links) {
        Map<String, Integer> linksCount = new HashMap<>();
        for(String linkHref : links.keySet()){
            DomainLinks domainLink = links.get(linkHref);
            int numberOfLinks = domainLink.getSize();
            linksCount.put(linkHref, numberOfLinks);
        }
        return linksCount;
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

    private Map<String, DomainLinks> process(String html) {

        Map<String, DomainLinks> links = new HashMap<>();

        Set<String> aTagHrefs = extractLinks(html);

        fillDomainLinks(links, aTagHrefs);

        return links;
    }

    private void fillDomainLinks(Map<String, DomainLinks> links, Set<String> aTagHrefs) {
        for(String aTagHref : aTagHrefs){
            try {
                URL url = new URL(aTagHref);
                String linkHostStr = url.getHost();
                DomainLinks link = links.get(linkHostStr);
                if(link == null){
                    link = new DomainLinks();
                    link.add(aTagHref);
                    links.put(linkHostStr, link);
                }else{
                    link.add(aTagHref);
                    links.put(linkHostStr, link);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private Set<String> extractLinks(String html) {
        Set<String> aTagHrefs = new HashSet<>();
        Document document = Jsoup.parse(html);
        Elements parsedLinks = document.select("a[href]");
        for(Element parsedLink : parsedLinks){
            String aTagHref = parsedLink.attr("abs:href");
            if(aTagHref != ""){
                String protocol = null;
                try {
                    protocol = new URL(aTagHref).getProtocol();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                if(protocol.equals("http")){
                    if(aTagHref.charAt(aTagHref.length()-1) == '/'){
                          String aTagHrefWithoutEndingSlash = aTagHref.substring(0, aTagHref.length()-1);
                          if(aTagHrefs.contains(aTagHrefWithoutEndingSlash)){
                             continue;
                          }
                    }else{
                        if(aTagHrefs.contains(aTagHref + "/")){
                            continue;
                        }
                    }
                    aTagHrefs.add(aTagHref);
                }
            }
        }
        return aTagHrefs;
    }

}
