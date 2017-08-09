package com.sergiisavin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HTMLProcessor {
    protected Map<String,Integer> countLinks(Map<String, DomainLinks> links) {
        Map<String, Integer> linksCount = new HashMap<>();
        for(String linkHref : links.keySet()){
            DomainLinks domainLink = links.get(linkHref);
            int numberOfLinks = domainLink.getSize();
            linksCount.put(linkHref, numberOfLinks);
        }
        return linksCount;
    }

    protected Map<String, DomainLinks> process(String html) {

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
