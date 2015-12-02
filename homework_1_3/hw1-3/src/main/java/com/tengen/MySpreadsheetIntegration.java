package com.tengen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author carlos
 */
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.Person;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;

public class MySpreadsheetIntegration {

    private static String CLIENT_ID = "413875268711-s7jeppdtqbej2h4dm74k5q5cinv86gk0.apps.googleusercontent.com";
    private static String CLIENT_SECRET = "QRr3HRYTxUVxbeC9idObHJYn";

    private static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

    private static String SCOPE = "https://docs.google.com/spreadsheets/d/1tEVQlfiwfDAE_5lfwxRSM3gWs8kuAqj3-di1CjgKFCM/edit#gid=0";

    public static void main(String[] args)
            throws AuthenticationException, MalformedURLException, IOException, ServiceException {
        
        
        

        
        
        SpreadsheetService spreadsheetService = new SpreadsheetService("MySpreadSheetServicejuelandiado");
        
        spreadsheetService.setUserCredentials("usuario", "contraseña");
        
        URL spreadUrl;
        

        
        FeedURLFactory factory;
        
        factory = FeedURLFactory.getDefault().getDefault();
        
        SpreadsheetQuery spreadsheetQuery = new SpreadsheetQuery(factory.getSpreadsheetsFeedUrl());
        spreadsheetQuery.setTitleQuery("Clientes");
        SpreadsheetFeed spreadsheetFeed = spreadsheetService.query(spreadsheetQuery, SpreadsheetFeed.class);
        

        List<SpreadsheetEntry> spreadSheets = spreadsheetFeed.getEntries();
        
        SpreadsheetEntry spreadSheet = spreadSheets.get(0);
        
        System.out.println(spreadSheet.getTitle().getPlainText());
        
        WorksheetFeed worksheetFeed = spreadsheetService.getFeed(spreadSheet.getWorksheetFeedUrl(),
                WorksheetFeed.class);
        List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
        WorksheetEntry worksheet = worksheets.get(2);
        
        URL listFeedUrl = worksheet.getListFeedUrl();
        ListFeed listFeed  = spreadsheetService.getFeed(listFeedUrl, ListFeed.class);
        
        ListEntry row = new ListEntry();
        
       
        
        row.getCustomElements().setValueLocal("Nombre", "Juejuejue");
        row.getCustomElements().setValueLocal("Apellido", "Juajua");
        row.getCustomElements().setValueLocal("Sexo", "Masculino");
        row.getCustomElements().setValueLocal("Peso", "90kg");
        
        spreadsheetService.insert(listFeedUrl, row);
        
        row.getCustomElements().setValueLocal("Nombre", "Juejuejue");
        row.getCustomElements().setValueLocal("Apellido", "Juajua");
        row.getCustomElements().setValueLocal("Sexo", "Masculino");
        row.getCustomElements().setValueLocal("Peso", "90kg");
        row.getCustomElements().setValueLocal("Nombre", "Juejuejue");
        row.getCustomElements().setValueLocal("Apellido", "Juajua");
        row.getCustomElements().setValueLocal("Sexo", "Masculino");
        row.getCustomElements().setValueLocal("Peso", "90kg");
        
        
        spreadsheetService.insert(listFeedUrl, row);
        
        
        
        
       
    }
}
