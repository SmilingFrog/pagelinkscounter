package com.sergiisavin;

import java.util.Map;
import java.util.Set;

public interface HTMLProcessor {
    Map<String, DomainLinks> process(Set<String> aTagHrefs);
}