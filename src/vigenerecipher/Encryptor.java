/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vigenerecipher;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 *
 * @author crapson
 */
public class Encryptor {
    
    Encryptor(String sourceFile, String key, String destinationFile)
    {
        System.out.println(sourceFile);
        System.out.println(key);
        System.out.println(destinationFile);
        
        //System.out.println(key.toUpperCase().charAt(0));
        
        System.out.println();
        
        try{
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream(sourceFile);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            FileWriter fileOutput = new FileWriter(destinationFile);
            
            //Current character
            int character;
            //Current index for encryption
            int keyIndex = 0;
            //Read File character By character
            while ((character = br.read()) != -1)   {
                // Print the content on the console
                if(97 <= character && character <= 122)
                {
                    character = character - 32;
                }
                if(65 <= character && character <= 90)
                {
                    character = (((character - 65) + key.toUpperCase().charAt(keyIndex%key.length()) - 65) % 26 + 65);
                    keyIndex++;
                    //System.out.print ((char)character);
                    fileOutput.write((char)character);
                }
                //Not sure what to do with numbers... I am going to keep the count of the key, but keep the numbers unencrypted for now
                //Numbers are removed
                /*if(48 <= character && character <= 57)
                {
                    keyIndex++;
                    //System.out.print ((char)character);
                    fileOutput.write((char)character);
                }*/
            }
            fileOutput.write(-1);
            //Close the input stream
            in.close();
            fileOutput.close();
        }
        catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
    
}
