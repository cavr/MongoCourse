import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.io.*;
import java.net.URL;
import java.util.List;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author carlos
 */
public class ReadWrite {

    public ArrayList<String[]> readFile(String fileToRead) throws FileNotFoundException, IOException {

        ArrayList<String[]> data = new ArrayList<String[]>();

        FileInputStream fileInputStream = new FileInputStream(fileToRead);

        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = bufferedReader.readLine();

        while (line != null) {

            data.add(line.split(","));
            line = bufferedReader.readLine();
        }

        bufferedReader.close();
        inputStreamReader.close();
        fileInputStream.close();

        return data;

    }

    public void writeFile(String fileToWrite, ArrayList<String[]> data) throws FileNotFoundException, IOException, AuthenticationException, ServiceException {

        OutputStream outputStream = new FileOutputStream(fileToWrite);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF8");

        SpreadsheetService spreadsheetService = new SpreadsheetService("MySpreadSheetServicejuelandiado");

        spreadsheetService.setUserCredentials("usario", "contraseña");

        URL spreadUrl;

        FeedURLFactory factory;

        factory = FeedURLFactory.getDefault().getDefault();

        SpreadsheetQuery spreadsheetQuery = new SpreadsheetQuery(factory.getSpreadsheetsFeedUrl());
        spreadsheetQuery.setTitleQuery("Clientes");
        SpreadsheetFeed spreadsheetFeed = spreadsheetService.query(spreadsheetQuery, SpreadsheetFeed.class);

        List<SpreadsheetEntry> spreadSheets = spreadsheetFeed.getEntries();

        SpreadsheetEntry spreadSheet = spreadSheets.get(0);

        WorksheetFeed worksheetFeed = spreadsheetService.getFeed(spreadSheet.getWorksheetFeedUrl(),
                WorksheetFeed.class);
        List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
        WorksheetEntry worksheet = worksheets.get(2);

        URL listFeedUrl = worksheet.getListFeedUrl();
        ListFeed listFeed = spreadsheetService.getFeed(listFeedUrl, ListFeed.class);

        ListEntry row = new ListEntry();
        
        row.getCustomElements().setValueLocal("hola", "1");
        row.getCustomElements().setValueLocal("hole", "2");
        row.getCustomElements().setValueLocal("holi", "3");
        row.getCustomElements().setValueLocal("holo", "4");
        row.getCustomElements().setValueLocal("holu", "5");
        
        spreadsheetService.insert(listFeedUrl, row);
        
        

//        int rowLength = data.get(0).length;
//
//        ArrayList<String> index = new ArrayList<String>();
//        
//        for(String indexPosition : data.get(0)){
//            
//            index.add(indexPosition);
//            
//            System.out.println(indexPosition);
//        }
//
//        for (int i = 1; i < data.size(); i++) {
//
//            String[] rowFile = data.get(i);
//
//            for (int j = 0; j < rowLength; j++) {
//                
//                System.out.println("Estoy insertando");
//
//                row.getCustomElements().setValueLocal(index.get(j).toLowerCase(), rowFile[j]);
//                   // System.out.println(index.get(j)+ " "+ data.get(i)[j]);
//
//            }
//            //System.out.println(index.size());
//
//            //System.out.println(i);
//            
//             spreadsheetService.insert(listFeedUrl, row);
//        }
//               

        //System.out.println(data.get(0)[0]);

        //System.out.println(index);             

    }

    public void cleanUp(OutputStreamWriter outputStreamWriter) throws IOException {

        outputStreamWriter.close();

    }

}
