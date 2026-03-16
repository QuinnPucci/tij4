/*
Exercise 12: (3) Modify Exercise 8 to also open a text file so you can write text into it.
Write the lines in the LinkedList, along with line numbers
(do not attempt to use the “LineNumber” classes), out to the file.
 */

import java.io.*;
import java.util.*;

public class Ch19ex12 {
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

            // setup output for ex 12
            PrintStream out = new PrintStream("output.txt");
            int lineCount = 1;
            for(int i = 0; i < list.size(); i++){
                out.println(lineCount++ + ": " + list.get(i).toUpperCase()); //ex 9 toUpper
            }
            out.close();
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

/* the cleaner solution

import java.io.*;
import java.util.*;

public class Ch19ex12 {
    public static void main(String args[]) {

        if(args.length != 1) {
            System.out.println("Usage: java Ch19ex12 filename");
            return;
        }

        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in  = new BufferedReader(new FileReader(args[0]));
            out = new PrintWriter("Ch19ex12.out");

            LinkedList<String> list = new LinkedList<String>();
            String s;
            while((s = in.readLine()) != null)
                list.addFirst(s);

            for(int i = 0; i < list.size(); i++)
                out.println(list.get(i));

            out.println("Total (including this line): " + (list.size() + 1));
            out.flush();
        }
        catch(FileNotFoundException e) {
            System.err.println("File not found");
        }
        catch(IOException e) {
            System.err.println("IO error");
        }
        finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            }
            catch (Exception e) { }
        }
    }
}

 */
