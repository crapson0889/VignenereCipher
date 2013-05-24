/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vigenerecipher;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 *
 * @author crapson
 */
public class Examiner {
    private static int lowerKeyLimit = 6;
    private static int upperKeyLimit = 10;
    
    String results;
    String key;
    
    Examiner(String sourceFile)
    {
        System.out.println(sourceFile);
        System.out.println();
        
        int keyLength = getKeyLength(sourceFile);
        System.out.println("KeyLength: "+keyLength);
        System.out.println();
        String key = getKey(sourceFile, keyLength);
        results = key;
    }
    
    private int getKeyLength(String sourceFile)
    {
        float averageIndexOfCoincidences[] = new float[upperKeyLimit - lowerKeyLimit + 1];
        
        //For key lengths lowerKeyLimit to upperKeyLimit we find the frequency of occurance of each letter in the encrypted text
        for(int keyLength = lowerKeyLimit; keyLength <= upperKeyLimit; keyLength++)
        {
            int characterFrequency[][] = new int[keyLength][26];
            int characterCount[] = new int[keyLength];
            
            for(int i = 0; i < keyLength; i++)
            {
                for(int j = 0; j < 26; j++)
                {
                    characterFrequency[i][j] = 0;
                }
                characterCount[i]=0;
            }

            try{
                // Open the file that is the first 
                // command line parameter
                FileInputStream fstream = new FileInputStream(sourceFile);
                // Get the object of DataInputStream
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

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
                        characterFrequency[keyIndex%keyLength][character-65]++;
                        characterCount[keyIndex%keyLength]++;
                        keyIndex++;
                    }
                }
                //Close the input stream
                in.close();
                
                //Using the frequency of each letter that we found above we can find the Index of Coincidence
                float indexOfCoincidence[] = new float[keyLength];
                float averageIndexOfCoincidence = 0;
                for(int i = 0; i < keyLength; i++)
                {
                    float currentIndexOfCoincidence = 0;

                    for(int j = 0; j < 26; j++)
                    {
                        int currentCharacterFrequency = characterFrequency[i][j];
                        currentIndexOfCoincidence += (float)currentCharacterFrequency*(currentCharacterFrequency - 1)/(characterCount[i]*(characterCount[i]-1));
                    }
                    indexOfCoincidence[i] = currentIndexOfCoincidence;
                    averageIndexOfCoincidence += indexOfCoincidence[i];
                }
                averageIndexOfCoincidences[keyLength - lowerKeyLimit] = averageIndexOfCoincidence / keyLength;
            }
            catch (Exception e){//Catch exception if any
                System.err.println("Error: " + e.getMessage());
            }
        }
        
        int keyLength = 0;
        float highestIndexOfCoincidence = 0;
        
        for(int i = 0; i < averageIndexOfCoincidences.length; i++)
        {
            //System.out.println((i+lowerKeyLimit)+" "+averageIndexOfCoincidences[i]);
            
            if(highestIndexOfCoincidence < averageIndexOfCoincidences[i])
            {
                highestIndexOfCoincidence = averageIndexOfCoincidences[i];
                keyLength = i;
            }
        }
        
        return keyLength + lowerKeyLimit;
    }
    
    private String getKey(String sourceFile, int keyLength)
    {   
        int characterFrequency[][] = new int[keyLength][26];
        int characterCount[] = new int[keyLength];
        double expectedCharacterFrequency[] = new double[26];
        
        expectedCharacterFrequency[0] = 0.08167;  //A
        expectedCharacterFrequency[1] = 0.01492;  //B
        expectedCharacterFrequency[2] = 0.02782;  //C
        expectedCharacterFrequency[3] = 0.04253;  //D
        expectedCharacterFrequency[4] = 0.12702;  //E
        expectedCharacterFrequency[5] = 0.02228;  //F
        expectedCharacterFrequency[6] = 0.02015;  //G
        expectedCharacterFrequency[7] = 0.06094;  //H
        expectedCharacterFrequency[8] = 0.06966;  //I
        expectedCharacterFrequency[9] = 0.00153;  //J
        expectedCharacterFrequency[10] = 0.00772; //K
        expectedCharacterFrequency[11] = 0.04025; //L
        expectedCharacterFrequency[12] = 0.02406; //M
        expectedCharacterFrequency[13] = 0.06749; //N
        expectedCharacterFrequency[14] = 0.07507; //O
        expectedCharacterFrequency[15] = 0.01929; //P
        expectedCharacterFrequency[16] = 0.00095; //Q
        expectedCharacterFrequency[17] = 0.05987; //R
        expectedCharacterFrequency[18] = 0.06327; //S
        expectedCharacterFrequency[19] = 0.09056; //T
        expectedCharacterFrequency[20] = 0.02758; //U
        expectedCharacterFrequency[21] = 0.00978; //V
        expectedCharacterFrequency[22] = 0.02360; //W
        expectedCharacterFrequency[23] = 0.00150; //X
        expectedCharacterFrequency[24] = 0.01974; //Y
        expectedCharacterFrequency[25] = 0.00074; //Z

        for(int i = 0; i < keyLength; i++)
        {
            for(int j = 0; j < 26; j++)
            {
                characterFrequency[i][j] = 0;
            }
            characterCount[i] = 0;
        }

        try{
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream(sourceFile);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

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
                    characterFrequency[keyIndex%keyLength][character-65]++;
                    characterCount[keyIndex%keyLength]++;
                    keyIndex++;
                }
            }
            //Close the input stream
            in.close();
        }
        catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        
        int[] key = new int[keyLength];
        
        for(int i = 0; i < keyLength; i++)
        {
            double[] chiSquared = new double[26];
            for(int j = 0; j < 26; j++)
            {
                for(int k = 0; k < 26; k++)
                {
                    chiSquared[j] += Math.pow((characterFrequency[i][k] - characterCount[i]*expectedCharacterFrequency[(k-j+26)%26]),2)/(characterCount[i]*expectedCharacterFrequency[(k-j+26)%26]);
                    //System.out.println((char)(k+65)+" "+characterFrequency[i][k]+" "+characterCount[i]*expectedCharacterFrequency[(k+j)%26]+" "+chiSquared[j]);
                }
            }
            
            double lowestChiSquared = Float.POSITIVE_INFINITY;
            int keyCharacter = -1;
            
            for(int j = 0; j < 26; j++)
            {
                System.out.println((char)(j+65)+" "+chiSquared[j]);
                if(lowestChiSquared > chiSquared[j])
                {
                    keyCharacter = j;
                    lowestChiSquared = chiSquared[j];
                }
            }
            System.out.println();
            key[i] = keyCharacter;
        }
        
        System.out.println();
        String keyString = "";
        for(int i = 0; i < keyLength; i++)
        {
            System.out.print((char)(key[i]+65));
            keyString = keyString+(char)(key[i]+65);
        }
        System.out.println();
        
        return keyString;
    }
    
    /**
     *
     * @return
     */
    public String getResults()
    {
        return results;
    }
    
}
