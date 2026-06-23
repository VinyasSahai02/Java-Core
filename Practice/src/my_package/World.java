package my_package;

@FunctionalInterface
interface G {
    void sayHello();
}

public class World{
    public static void main(String[] args) {

        G g = new G() {
            @Override
            public void sayHello() {
                System.out.println("Hello!");
            }
        };
        g.sayHello();
//        G g = () -> System.out.println("Hello!"); //with lambda expression


    }
}

