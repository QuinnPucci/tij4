/*
Ch15 pg562
Exercise 10: (3) Write a program to determine whether an array of char is a primitive type or a true Object.
 */

public class Ch15ex10 {
    public static void main(String[] args){
        char [] testArray={'a', 'b', 'c', 'd'};

        if (testArray instanceof Object){System.out.println("testArray is an object");}

        /*
        The key here is using instanceof -> a boolean to test if a object belongs to certain class
        and checking the array against the class Object itself (Object is the root class of all classes)
         */

    }
}
