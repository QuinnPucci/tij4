/*
Exercise 7: (2) Open a text file so that you can read the file one line at a time.
Read each line as a String and place that String object into a LinkedList.
Print all of the lines in the LinkedList in reverse order.
 */

import java.io.*;
import java.util.*;

/*
this demonstrates a common and important pattern with input streams.

declare the stream variable outside
try → open and use it
catch → handle problems
finally → close it no matter what

 */

public class InputLinkedList {
    public static void main(String args[]) {

        BufferedReader in = null;
        // declare input
        try {
            in = new BufferedReader(new FileReader("sometext.txt"));
            // open the input and use it within the try
            LinkedList<String> list = new LinkedList<String>();
            String s;
            while((s = in.readLine()) != null)
                list.addFirst(s);
                // this is how the reverse order happens. placing it at the top of the linked list (First)
                // instead of inputting normally and printing reverse later, builds the list in reverse as it goes
            for(int i = 0; i < list.size(); i++)
                System.out.println(list.get(i));
            // since the list is already in reverse order, just loop and print
        }
        catch(FileNotFoundException e) {
            System.err.println("File not found");
        }
        catch(IOException e) {
            System.err.println("IO error");
        }
        finally {
            // finally block to close input stream no matter what.
            try {
                if (in != null) in.close();
            }
            catch (Exception e) { }
        }
    }
}
