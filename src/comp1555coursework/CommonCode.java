package comp1555coursework;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 *
 * @author na8363c This common code is reused from the COMP 1752 coursework,
 * primarily when relating to the the read/write functionality
 */
public class CommonCode {

    public static final String ORDERED_DATE_TIME_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    public static final String UK_DATE_TIME_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
    public static final String US_DATE_TIME_FORMAT_NOW = "MM-dd-yyyy HH:mm:ss";
    public static final String ORDERED_DATE_FORMAT_NOW = "yyyy-MM-dd";
    public static final String UK_DATE_FORMAT_NOW = "dd-MM-yyyy";
    public static final String US_DATE_FORMAT_NOW = "MM-dd-yyyy";
    public String orderedDateAndTime;
    public String ukDateAndTime;
    public String usDateAndTime;
    public String orderedDate;
    public String ukDate;
    public String usDate;

    public final String userName = System.getProperty("user.name");
    public final String appDir = System.getProperty("user.dir");
    public final String os = System.getProperty("os.name");
    public final String fileseperator = System.getProperty("file.separator");

    private ActionListener calledBy;

    CommonCode(ActionListener call) {
        calledBy = call;
        initialiseVariables();
    }

    CommonCode() {
        initialiseVariables();
    }

    private void initialiseVariables() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat o1sdf = new SimpleDateFormat(ORDERED_DATE_TIME_FORMAT_NOW);
        orderedDateAndTime = o1sdf.format(cal.getTime());
        SimpleDateFormat uk1sdf = new SimpleDateFormat(UK_DATE_TIME_FORMAT_NOW);
        ukDateAndTime = uk1sdf.format(cal.getTime());
        SimpleDateFormat us1sdf = new SimpleDateFormat(US_DATE_TIME_FORMAT_NOW);
        usDateAndTime = us1sdf.format(cal.getTime());
        SimpleDateFormat o2sdf = new SimpleDateFormat(ORDERED_DATE_TIME_FORMAT_NOW);
        orderedDate = o2sdf.format(cal.getTime());
        SimpleDateFormat uk2sdf = new SimpleDateFormat(UK_DATE_TIME_FORMAT_NOW);
        ukDate = uk2sdf.format(cal.getTime());
        SimpleDateFormat us2sdf = new SimpleDateFormat(US_DATE_TIME_FORMAT_NOW);
        usDate = us2sdf.format(cal.getTime());
    }

    /*public ArrayList<String> readTextFile(String fileName) {
        ArrayList file = new ArrayList();
        String line;
        if ((fileName == null) || (fileName.equals(""))) {
            System.out.println("no file name specified");
        } else {
            try {
                BufferedReader in = new BufferedReader(new FileReader(fileName));
                if (!in.ready()) {
                    throw new IOException();
                }
                while ((line = in.readLine()) != null) {
                    file.add(line);
                }
                in.close();
            } catch (IOException e) {
                System.out.println(e);
                file.add("file not found");
            }

        }
        return file;
    }*/

 /*public void writeTextFile(String fn, ArrayList<String> outputText)
            throws FileNotFoundException, IOException {
        File fileName = new File(fn);
        Writer output = new BufferedWriter(new FileWriter(fileName));
        try {
            for (int i = 0; i < outputText.size(); i++) {
                output.write(outputText.get(i).toString() + "\n");
            }
        } catch (Exception e) {            System.out.println(e.getMessage());
        } finally {
            output.close();
        }
    }*/
    
    // read the file to be used for the chart
    public static ArrayList<double[]> readTextFile(String fileName) {
        //initialisation of integer value to be used to set the width of the array
        int width = 8;
        //initialisation of arraylist which stores double arrays
        ArrayList<double[]> trainingTable = new ArrayList<>();
        String line;
        //if there's no file, print out this message
        if ((fileName == null) || (fileName.equals(""))) {
            System.out.println("no file name specified");
        } else {
            try {
//create a buffered reader looking for a specified filename
                BufferedReader in = new BufferedReader(new FileReader(fileName));
                //initialising a scanner to collect data from the system
                Scanner scan = new Scanner(in);
                //while loop for when the scanner finds a line of text
                while (scan.hasNextLine()) {
                    //creates an array the size of the created width
                    double[] row = new double[width];
                    //while the array still has space
                    for (int i = 0; i < width; i++) {
                        //add the next double value in the file to the array
                        row[i] = scan.nextDouble();
                    }
                    //add the array to the arraylist
                    trainingTable.add(row);
                    //if line has already been searched through
                    if (scan.hasNextLine()) {

                        // Go to the next line:
                        scan.nextLine();

                    }
                }
                //close scanner
                scan.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        //return the table
        return trainingTable;
    }
   //code to time method length
//final long startTime = System.currentTimeMillis();
    //put method here
//final long endTime = System.currentTimeMillis();
//System.out.println("Total execution time: " + (endTime - startTime));
}
