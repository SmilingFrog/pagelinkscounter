package com.sergiisavin;

import java.util.Set;

public interface LinksExtractor {
    Set<String> extractLinks(String html);
}
