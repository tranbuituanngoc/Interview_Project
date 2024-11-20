package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebCrawler {

    public static List<List<String>> crawlData() throws IOException {
        String url = "https://ratings.fide.com/topfed.phtml?ina=1&country=AUS";
        Document doc = Jsoup.connect(url).get();
        Elements rows = doc.select("table[width=450] tr");

        List<List<String>> data = new ArrayList<>();
        for (Element row : rows) {
            Elements cols = row.select("td");
            List<String> rowData = new ArrayList<>();
            for (Element col : cols) {
                rowData.add(col.text());
            }
            if (!rowData.isEmpty()) {
                data.add(rowData);
            }
        }
        return data;
    }
}