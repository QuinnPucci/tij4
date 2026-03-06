/* Exercise 1: (2) Create a new class called Gerbil with an int gerbilNumber that’s initialized
in the constructor. Give it a method called hop( ) that displays which gerbil
number this is, and that it’s hopping.
Create an ArrayList and add Gerbil objects to the List.
Now use the get( ) method to move through the List and call hop( ) for each Gerbil.*/

import java.util.*;

public class Gerbil {
    int gerbilNumber;
    static int counter = 0; // has to be static so all gerbils share the same counter

    public Gerbil(){
        gerbilNumber = ++counter;
    }

    public void hop(){
        System.out.println("Gerbil number " + gerbilNumber + " is currently hopping");
    }

    public static void main(String[] args){
        ArrayList<Gerbil> gerbils = new ArrayList<Gerbil>();

        Gerbil clint = new Gerbil();
        Gerbil dinky = new Gerbil();
        Gerbil grubs = new Gerbil();

        gerbils.add(clint);
        gerbils.add(dinky);
        gerbils.add(grubs);

        for (int i = 0; i < gerbils.size(); i++){
           Gerbil current = gerbils.get(i);
           current.hop();
        }
    }
}
