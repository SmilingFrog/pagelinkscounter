package com.sergiisavin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DomainLinks implements Iterable<String>{
    List<String> hrefs;

    public DomainLinks(){
        hrefs = new ArrayList<>();
    }

    public void add(String aTagHref) {
        hrefs.add(aTagHref);
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < hrefs.size();
            }

            @Override
            public String next() {
                return hrefs.get(index++);
            }
        };
    }

    public int getSize() {
        return hrefs.size();
    }
}
