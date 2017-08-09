package com.sergiisavin.linksextractor;

import java.util.Set;

public interface LinksExtractor {
    Set<String> extractLinks(String html);
}
