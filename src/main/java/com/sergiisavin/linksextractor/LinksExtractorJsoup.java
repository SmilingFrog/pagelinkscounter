package com.sergiisavin.linksextractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class LinksExtractorJsoup implements LinksExtractor {

    @Override
    public Set<String> extractLinks(String html) {
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

    private boolean isNotDuplikateHref(String aTagHref, Set<String> aTagHrefs) {
        return !(endsInSlash(aTagHref) &&
                aTagHrefs.contains(getATagHrefWithoutEndingSlash(aTagHref))) ||
                (aTagHrefs.contains(getATagWithEndingSlash(aTagHref)));
    }

    private boolean isNotEmptyString(String aTagHref) {
        return aTagHref != "";
    }

}
