package com.sergiisavin.linkscounter;

import com.sergiisavin.domainlinks.DomainLinks;

import java.util.HashMap;
import java.util.Map;

public class LinksCounterJsoup implements LinksCounter {
    public Map<String,Integer> countLinks(Map<String, DomainLinks> links) {
        Map<String, Integer> linksCount = new HashMap<>();
        for(String linkHref : links.keySet()){
            DomainLinks domainLink = links.get(linkHref);
            int numberOfLinks = domainLink.getSize();
            linksCount.put(linkHref, numberOfLinks);
        }
        return linksCount;
    }
}
