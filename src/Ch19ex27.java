/*
Exercise 27: (1) Create a Serializable class containing a reference to an object of a second Serializable class.
Create an instance of your class, serialize it to disk, then restore it and verify that the process worked correctly
 */

// serialization = save an object for even while the program is not running

import java.io.*;
import java.util.*;

public class Ch19ex27 {
    static void main(String[] args){

        // Create the objects, use the spice to create the steak
        Seasoning steakspice = new Seasoning("stealspice");
        Steak tbone = new Steak("tbone", steakspice);

        // Ive gone for a try with resources instead of a try catch finally

        try {
            // first try with resource block:
            // Open an ObjectOutputStream connected to an output file
            // Inside try (...) java will close automatically when block finished
            // and no need for finally and dont have to declare outside
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Ch19ex27.out"))) {

                // serialize and write object
                out.writeObject(tbone);
            }

            // second try with resource:
            // input stream
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Ch19ex27.out"))) {
                // read one object back from the file
                // readObject() returns object, so it has to be cast back to steak
                Steak thawedTbone = (Steak) in.readObject();
                System.out.println(thawedTbone);
            }

        }
        catch (ClassNotFoundException e){
            System.err.println("Class not Found");
        }
        catch (FileNotFoundException e){
            System.err.println("File not Found");
        }
        catch (IOException e){
            System.err.println("IO Error");
        }


    }
}

class Steak implements Serializable {

    public String cut;
    public Seasoning seasoning; // referenced the other class

    public Steak(String cut, Seasoning seasoning){
        this.cut = cut;
        this.seasoning = seasoning;
    }

    // simple to string for testing
    public String toString() {
        return (cut + ", seasoning: " + seasoning.seasoning);
    }
}

// This is the class that will be referenced by Steak
class Seasoning implements Serializable {
    String seasoning;
    public Seasoning(String seasoning){
        this.seasoning = seasoning;
    }
}
