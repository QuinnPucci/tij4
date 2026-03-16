/*

Exercise 8: (1) Modify Exercise 7 so that the name of the file you read is provided as a
command-line argument.

Exercise 9: (1) Modify Exercise 8 to force all the lines in the LinkedList to
uppercase and send the results to System.out.
 */

import java.io.*;
import java.util.*;

public class Ch19ex8and9 {
    public static void main(String args[]) {
        String fileName = args[0]; // exercise 8
        // unlike scanner, this takes the file name as an argument
        // when the program is using java Ch19ex8and9 sometext.txt
        BufferedReader in = null;
        // declare input
        try {
            in = new BufferedReader(new FileReader(fileName));
            // open the input and use it within the try
            LinkedList<String> list = new LinkedList<String>();
            String s;
            while((s = in.readLine()) != null)
                list.addFirst(s);
            // this is how the reverse order happens. placing it at the top of the linked list (First)
            // instead of inputting normally and printing reverse later, builds the list in reverse as it goes
            for(int i = 0; i < list.size(); i++){
                System.out.println(list.get(i).toUpperCase()); // print as uppercase (exercise 9)
            }

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
