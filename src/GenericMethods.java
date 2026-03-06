public class GenericMethods {
    public <T, S, R> void f(T x, S y, R z) {
        System.out.println(x.getClass().getName());
        System.out.println(y.getClass().getName());
        System.out.println(z.getClass().getName());
    }
    public static void main(String[] args) {
        GenericMethods gm = new GenericMethods();
        gm.f("k", 6, 5.7089);
    }
}