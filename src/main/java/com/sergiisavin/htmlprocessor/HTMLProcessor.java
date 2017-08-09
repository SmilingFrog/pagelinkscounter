package com.sergiisavin.htmlprocessor;

import com.sergiisavin.domainlinks.DomainLinks;

import java.util.Map;
import java.util.Set;

public interface HTMLProcessor {
    Map<String, DomainLinks> process(Set<String> aTagHrefs);
}