package org.example;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

public class GoogleSheetWriter {

    private static final String APPLICATION_NAME = "Interview Project";
    private static final String SPREADSHEET_ID = "1P9t7XSP6V5jg8xUMDmKV0-EwKoFn231DP4XWW4VchrE";
    private static final String RANGE = "Data!A1";

    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("src/main/resources/interviewproject-442214-68cb191652a7.json"))
                .createScoped(List.of("https://www.googleapis.com/auth/spreadsheets"));
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(), new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void writeDataToSheet(List<List<String>> data) throws IOException, GeneralSecurityException {
        Sheets sheetsService = getSheetsService();
        List<List<Object>> objectData = data.stream()
                .map(row -> row.stream().map(value -> (Object) value).collect(Collectors.toList()))
                .collect(Collectors.toList());
        ValueRange body = new ValueRange().setValues(objectData);
        sheetsService.spreadsheets().values()
                .update(SPREADSHEET_ID, RANGE, body)
                .setValueInputOption("RAW")
                .execute();
    }
}