/*
pg654 Exercise 20: (1) Create an interface with two methods, and a class that implements that interface
and adds another method. In another class, create a generic method with an argument type that is
bounded by the interface, and show that the methods in the interface are callable inside this generic method.
In main( ), pass an instance of the implementing class to the generic method.
 */

/*
An interface is like a contract that says what a class must be able to do.
It mainly describes behavior (methods), not shared state.

Inheritance is when one class extends another class and gains its fields(state) and methods(behaviour).
It is used to share structure and implementation.

So:
- interface = what a class can do
- inheritance = what a class is and what it gets from a parent class
*/

interface Engine {
    void start();
    void turnOff();

    // “Any class that implements Engine must provide these two methods.”
}

class ignitionTest implements Engine{

    // ignitionTest agrees to follow the Engine contract.
    public void start() {
        System.out.println("Engine Running");
    }

    public void turnOff() {
        System.out.println("Engine Off");
    }

    public void rev() {
        System.out.println("Revving engine");
    }
}

public class CarInter {
    public <T extends Engine> void tester(T x) { // “T can be any type, as long as that type implements Engine.”
        /*
        So T is not one specific class. It is a placeholder for any compatible class.
        Examples of valid T could be:
            ignitionTest
            some future TruckEngine
            some future SportsCarEngine
        as long as each one implements Engine.
        */

        // This means the method takes one argument named x, and its type is T
        x.start();
        x.turnOff();
        // Since T extends Engine, Java knows that x is guaranteed to have at least the methods from Engine.
        // “Trust me, whatever x is, it will at least behave like an Engine.”
    }


    public static void main(String[] args){
        new CarInter().tester(new ignitionTest());
        // “Create a CarInter object, then call its tester() method, giving it a new ignitionTest object as the argument.”
        /* same as writing this:
            CarInter car = new CarInter();
            ignitionTest test = new ignitionTest();
            car.tester(test);
         */
    }
}