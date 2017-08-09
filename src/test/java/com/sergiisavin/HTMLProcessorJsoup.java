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
    
    private void fillDomainLinks(Map<String, DomainLinks> links, Set<String> aTagHrefs) {
        for(String aTagHref : aTagHrefs){
            try {
                String hostName = getHostName(aTagHref);
                DomainLinks link = links.get(hostName);
                if(link == null){
                    link = new DomainLinks();
                    link.add(aTagHref);
                    links.put(hostName, link);
                }else{
                    link.add(aTagHref);
                    links.put(hostName, link);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private String getHostName(String aTagHref) throws MalformedURLException {
        URL url = new URL(aTagHref);
        return url.getHost();
    }


    @Override
    public Map<String, DomainLinks> process(Set<String> aTagHrefs) {
        Map<String, DomainLinks> links = new HashMap<>();
        fillDomainLinks(links, aTagHrefs);
        return links;
    }
}
