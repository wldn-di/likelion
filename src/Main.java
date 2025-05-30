
public class Main {
    public static void main(String[] args) {
        int age = 25;
        System.out.println(age);
        age = 26;
        System.out.println(age);
        double salary = 500.75;
        System.out.println(salary);

        String name = "정지우";
        System.out.println("정지우"==name);
        System.out.println("정지우".equals(name));

        String name2 = new String("정지우");
        System.out.println(name == name2);
    }
}