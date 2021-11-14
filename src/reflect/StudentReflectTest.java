package reflect;

public class StudentReflectTest {
    public int no;
    protected String name;
    String classNo;
    private long score;
    public static final String SCHOOlNAME = "LI_QIN";

    static {
        System.out.println("我是来测试类加载的");
    }

    public StudentReflectTest() {
    }

    public StudentReflectTest(int no, String name, String classNo, long score) {
        this.no = no;
        this.name = name;
        this.classNo = classNo;
        this.score = score;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public static String getSCHOOlNAME() {
        return SCHOOlNAME;
    }

    @Override
    public String toString() {
        return "StudentReflectTest{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", classNo='" + classNo + '\'' +
                ", score=" + score +
                '}';
    }

    public void testRe(int a, int b)
    {
        System.out.println(a+" "+b);
    }
    public void testRe(String a,String b)
    {
        System.out.println(a+""+b);
    }

    public static void doSome()
    {
        System.out.println("我是来测试单纯类引用它,会不会类加载");
    }
    private static void doThings(){
        System.out.println("我来做事来了");
    }
    private void didThings(){
        System.out.println("我已经做过某事了");
    }
}
