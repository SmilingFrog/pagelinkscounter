package com.sergiisavin.linkscounter;

import com.sergiisavin.domainlinks.DomainLinks;

import java.util.Map;

public interface LinksCounter {
    Map<String,Integer> countLinks(Map<String, DomainLinks> links);
}
