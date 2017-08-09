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
        Map<String, LinkHost> links = process(html);

        for(String linkHref : links.keySet()){
            System.out.println();
            System.out.println(linkHref);
            LinkHost linkHost = links.get(linkHref);
            Iterator iterator = linkHost.iterator();
            while(iterator.hasNext()){
            System.out.printf("\t %s\n", iterator.next());
            }
        }
    }

    private Map<String, LinkHost> process(String html) {

        Map<String, LinkHost> links = new HashMap<>();

        Set<String> aTagHrefs = extractLinks(html);

        for(String aTagHref : aTagHrefs){
            try {
                URL url = new URL(aTagHref);
                String linkHostStr = url.getHost();
                LinkHost link = links.get(linkHostStr);
                if(link == null){
                    link = new LinkHost();
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

        return links;
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
