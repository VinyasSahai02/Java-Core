package my_package;

public class Cat {
    String name;
    public static void main(String[] args){
        System.out.println("Hello");
    }
    class LittleKitten{}
}

// package in Java is a way to group related classes and interfaces together
//package in Java is similar to a folder in a file system, where related files are kept together.
// 2 types of packages in Java:
// 1️⃣ Built-in Packages → Provided by Java (e.g., java.util, java.io, java.lang).
// 2️⃣ User-defined Packages → Created by the user to organize custom classes.
//Naming Convention - com.companyname.something

// CLASSPATH
//\Java\Practice\src>my_package> javac Test.java
//\Java\Practice\src>my_package> java Test
//Error: Could not find or load main class Test -> usually runs but here error why ?
// when we write java Test, jvm tries to find the Test.class file from src
//so what is happening is jvm is trying to find my_package inside my_package and Test.class inside my_package
//my_package->my_package->Test.class
//and since it does not exist it shows error

//Java\Practice\src\my_package>cd ..
//\Java\Practice\src>java my_package/Test  -> now it runs


//Note:
//i can write multiple classes here
//class LittleKitten{}
//after writing this Cat changed into Cat.java
//but make sure that the class that you make public should be the name of the file
//i.e. Cat
//we cannot access this class outside my_package
//if you want to access this class outside my_package, put this class inside public class
//public class Cat {
//    class LittleKitten{}
//}

//public class Test {
//    public static void main(String[] args){
//          Cat.LittleKitten smolCat = new Cat.LittleKitten()-> ERROR ('my_package.Cat' is not an enclosing class)
//          this means that in order to make obj of LittleKitten you need to make an obj of Cat first
//          Cat cat= new Cat();
//          Cat.LittleKitten smolCat = cat.new LittleKitten();
//    }
//}