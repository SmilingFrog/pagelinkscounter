package com.sergiisavin;

import org.jsoup.Jsoup;

import java.io.IOException;

public class PageLoaderJsoup implements PageLoader {

    @Override
    public String loadPage(String pageURL) {
        String page = "";
        try {
            page = Jsoup.connect(pageURL).get().html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }
}
