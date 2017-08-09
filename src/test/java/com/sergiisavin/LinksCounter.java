package com.sergiisavin;

import java.util.Map;

public interface LinksCounter {
    Map<String,Integer> countLinks(Map<String, DomainLinks> links);
}
