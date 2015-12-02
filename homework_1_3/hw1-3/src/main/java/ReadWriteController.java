
import com.google.gdata.util.ServiceException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author carlos
 */
public class ReadWriteController {
    
    public static void main (String[] args) throws FileNotFoundException, IOException, ServiceException {
        
        ReadWrite readWrite = new ReadWrite();
        
        ArrayList<String []> fileRead = readWrite.readFile("feedbacks.csv");
        
        
        
        readWrite.writeFile("juejuejue.csv", fileRead);
        
    }
    
}
