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

public class HTMLProcessorJsoup implements HTMLProcessor {

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
        Elements parsedLinks = getElements(html);
        for(Element parsedLink : parsedLinks){
            String aTagHref = parsedLink.attr("abs:href");
            if(isNotEmptyString(aTagHref)){
                String protocol = getProtocol(aTagHref);
                if(protocol.equals("http") && isNotDuplikateHref(aTagHref, aTagHrefs)){
                    aTagHrefs.add(aTagHref);
                }
            }
        }
        return aTagHrefs;
    }

    private boolean isNotDuplikateHref(String aTagHref, Set<String> aTagHrefs) {
        return !(endsInSlash(aTagHref) &&
                aTagHrefs.contains(getATagHrefWithoutEndingSlash(aTagHref))) ||
                (aTagHrefs.contains(getATagWithEndingSlash(aTagHref)));
    }

    private boolean isNotEmptyString(String aTagHref) {
        return aTagHref != "";
    }

    private Elements getElements(String html) {
        Document document = Jsoup.parse(html);
        return document.select("a[href]");
    }

    private String getATagWithEndingSlash(String aTagHref) {
        return aTagHref + "/";
    }

    private String getATagHrefWithoutEndingSlash(String aTagHref) {
        return aTagHref.substring(0, aTagHref.length()-1);
    }

    private boolean endsInSlash(String aTagHref) {
        return aTagHref.charAt(aTagHref.length()-1) == '/';
    }

    private String getProtocol(String aTagHref) {
        String protocol = null;
        try {
            protocol = new URL(aTagHref).getProtocol();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return protocol;
    }
}
