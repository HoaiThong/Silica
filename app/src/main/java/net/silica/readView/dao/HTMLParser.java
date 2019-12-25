package net.silica.readView.dao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HTMLParser {
    ArrayList<String> listCSS;

    public HTMLParser() {
    }

    public String getLinkTagCSS(String filePath) {
        StringBuilder builder=new StringBuilder();
        File input = new File(filePath);
        try {
            Document doc = Jsoup.parse(input, "ISO-8859-1");
            Elements elements = doc.getElementsByTag("link");
            for (Element e : elements) {
                builder.append(e.html());
                System.out.println(e.html());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}