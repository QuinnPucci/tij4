/*
TIJ pg562
Exercise 8: (5)
Write a method that takes an object and recursively prints all the classes in that object’s hierarchy.
*/

class Vehicle {}
class Motorcycle extends Vehicle{}
class Ducati extends Motorcycle{}
class Scrambler extends Ducati{}

/*
The Java Class getName() method returns the name of the entity (class, interface, array class, primitive type, or void)
represented by this Class object, as a String.

The Java Class getSuperclass() method returns the Class representing the superclass of the entity
(class, interface, primitive type or void) represented by this Class.
 */

public class Vehicles {
    static void HierRecurCall(Class x){
        while (x != null){
            System.out.println(x.getName());
            x = x.getSuperclass(); // technically this is iterative not recursive.
        }
    }

    public static void main(String[] args){
        Scrambler ruby = new Scrambler();
        HierRecurCall(ruby.getClass());
    }
}