package org.example;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            List<List<String>> data = WebCrawler.crawlData();
            GoogleSheetWriter.writeDataToSheet(data);
            System.out.println("Successfully!");
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
