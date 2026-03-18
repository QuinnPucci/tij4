/*
Exercise 27: (1) Create a Serializable class containing a reference to an object of a second Serializable class.
Create an instance of your class, serialize it to disk, then restore it and verify that the process worked correctly
 */

// serialization = save an object for even while the program is not running

import java.io.*;
import java.util.*;

public class Ch19ex27 implements Serializable {
    static void main(String[] args){
        FreezeSeasoning steakspice = new FreezeSeasoning();
        try (FileOutputStream fileOut = new FileOutputStream("seasoning");
             ObjectOutputStream out = new ObjectOutputStream(fileOut);){
            out.writeObject(steakspice);

        } catch (IOException e){System.out.println("Error"); }


    }
}

class FreezeSteak implements Serializable {
    String cut;
    FreezeSeasoning seasoning;
    public FreezeSteak(FreezeSeasoning = seasoning){
        cut = "T-Bone";
        seasoning = this.seasoning;
    }
}

class FreezeSeasoning implements Serializable {
    String seasoning;

    public FreezeSeasoning(){
        seasoning = "steak spice";
    }
}
