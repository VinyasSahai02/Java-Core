//WRAPPER CLASSES
//java is not a purely object oriented programming language
// because not everything in it is a object 
//as it has primitive data types like int, char, float, double, boolean etc.
//Wrapper Class in Java provides a way to use primitive data types (like int, char, double, etc.) as objects.
//Wrapper classes are used to convert primitive data types into objects
int a = 10; //stored in stack
// a. -> nothing appears as it is not an object
Integer a = 10; //reference variable to Integer class. Stored in heap
// a is now can be used as a object
// a. ->it shows all the methods of Integer class

// Autoboxing -> Java automatically converts primitive to object when needed.
int x = 10;
Integer objX = Integer.valueOf(10); // manualy converted
Integer objX = x; // automatically
// Unboxing -> Java automatically extracts primitive value from an object.
Integer objY = 20;
int y = objY.intValue(); // manualy converted
int y = objY; // automatically

Integer a = null;  //✅ Allowed
Integer a =0;  //✅ Allowed
int a = null; // ❌ Compilation Error!
int a = 0;  //✅ Allowed

public class Student{
    public int id;
}
public static void main(String[] args) {
    Student x = new Student();
    x.id=1;
    func(x);
    System.out.println(x.id); //1
}
public static void func(Student a){
    Student Student = new Student();
    student.id=2;
    a=student
}
//
public static void main(String[] args) {
    Student x = new Student();
    x.id=1;
    func(x);
    System.out.println(x.id); //2
}
public static void func(Student a){
    a.id=2;
}
//using above logic
public static void main(String[] args) {
    Integer b = 1;
    func(b);
    System.out.println(b); //1
}
public static void func(Integer a){
    a = 2;
}
//so we can say that wrapper classes are immutable

//Equality
Integer a = 10;
Integer b = 10;
int c=10;
int d=10;
System.out.println(a==b); //true
System.out.println(d==c); //true
System.out.println(a.equals(b)); //true -> can use these also as these are objects


//MATH CLASS
int a=10;
int b=20;
int c=-2;
Math.max(a,b); //20
Math.min(a,b); //10
Math.abs(c); //2
Math.sqrt(4); //2.0
Math.pow(2,3); //8.0
Math.log(10); //2.302585092994046
Math.log10(10); //1
Math.random(); //[0.0,1.0) -> returns a random number between 0 and 1]


//ENUMS ->represents a fixed set of constants.
// freq used data should be made const in a class/interface
public interface Day{
    String MONDAY="MONDAY"; //public static final by default
    String TUESDAY="TUESDAY";
    String WEDNESDAY="WEDNESDAY";   
    String THURSDAY="THURSDAY";
    String FRIDAY="FRIDAY";
    String SATURDAY="SATURDAY";
    String SUNDAY="SUNDAY";
}
// we can use enums rather than this
public enum Day{ //public, static, and final by default.
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
    //these are not variables but objects/instances of the enum class

    //we can have methods/variable also but thy need to be below enums
    public void Display(){
        System.out.println(this);
    }
}
public static void main(String[] args) {
    Day day = Day.MONDAY; //day is a reference variable of type Day
    System.out.println(day); //MONDAY
    System.out.println(Day.FRIDAY); //FRIDAY
    System.out.println(Day.MONDAY.ordinal()); //0-> index of the enum value in the enum class
    System.out.println(Day.MONDAY.name()); //MONDAY -> String value of the enum value
    System.out.println(Day.values()); //[LDay;@1b6d3586 -> array of all enum values
    System.out.println(Day.valueOf("MONDAY")); //MONDAY-> enum value of type Day
    System.out.println(Day.valueOf("MONDAY").ordinal()); //0 -> index of the enum value in the enum class
}`