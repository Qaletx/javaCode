package pathProperties;

/*相对路径的讨论  首先我们得知道"" ./  ../  /三者的区别，第一个即不学和第二个是一样的表示当前路径是从哪开始的，
* ../表示当前路径的上一层，/表示根目录，
* 在idea中io的当前路径是工程路径那，而/是对应盘的根目录，但可能在eclipse等就不同了
* 绝对路径更不可能一样，如将整个工程换到别处，即使在idea中也行不通了
* 怎么才能更灵活呢，即将工程移到别的编译器也可以用？首先一点我们排除写死的绝对路径
* */

/*四种类加载器
* 启动类加载器
* 扩展类加载器
* 应用类加载器
* 自定义类加载器
* */
import io.StudentObjectIOTest;

import java.io.FileOutputStream;

public class path {
    public static void main(String[] args) {
//如上面所说的，如果io参数都写成相对路径如下
//        FileOutputStream fileOutputStream = new FileOutputStream("./src/test01.txt");
//换到eclipse中可能就不是从工程开始，就找不到src了，就要改代码，很麻烦，有没有什么能够动态地获得一个可以用地址？
//        还真有
//        首先得找一处不会变得地方做起始路径，那么就是类加载的工程路径，或类加载的工程路径中对应的包处
//        不管将工程放到哪个软件，class放的位置都在那，也就是out/production/工程名

//        注意以下都是较相对路径后返回的可用的绝对路径

//        java提供了俩种方法来获得对应文件的路径，注意找不到目录或文件即返回null,而且文件可以忽略大小写


//        注意点路径如果有中文获得的getPath(）会乱码


//        第一种class中的getResource()和getResourceAsStream()，利用类路径去找文件
//        前者是返回一个URL对象，URL对象中有一方法getPath能获得该正确路径String ，其实URL的toString就调用的getPath
//        得到字符串后就可以进行拼接，用于FileOutputStream，为什么要拼接呢，因为有时只需获得到目录而不是具体文件
//        而FileOutputStream往往有从无到有的过程   后者则直接返回一个InputStream的对象，可以直接用于读取，跟
//        FileInputStream基本一样用法
//        可见我们这俩种方法主要还是来读取东西的而不是创造东西

//        因为它们跟class有关，故而跟自身class所在的位置有关
//        "" ./ 都是自身所在类包下  而/是在类加载的工程路径下
//        但是这只对于自创的类有用，如果是String.class.getResource().getPath()则以上三个都为null

//        因为URL重写了toString所以不用写getPath()
        System.out.println(StudentObjectIOTest.class.getResource(""));
        System.out.println(StudentObjectIOTest.class.getResource("./"));
        System.out.println(StudentObjectIOTest.class.getResource("/"));
        System.out.println(StudentObjectIOTest.class.getResourceAsStream("./"));
        System.out.println(String.class.getResource("./"));


//        第二种利用应用类类加载器去找文件，有class中的getClassLoader()方法得到加载器或线程中的getContextClassLoader()
//        再getResource()和getResourceAsStream()

//        因为是应用类加载器所以只对自己创的类有用，对于String getClassLoader()后压根就找不到类加载器自然为null，再调用
//        getResource()和getResourceAsStream()自然会空指针异常
//        而且是应用类加载器,所以当前路径就在类加载器工程下如以下
//        "" "./" 在类加载的工程路径下 特别的/为null
        System.out.println("-----------------------------------------------------------------------------------------------");

        System.out.println(StudentObjectIOTest.class.getClassLoader().getResource(""));
        System.out.println(StudentObjectIOTest.class.getClassLoader().getResource("./"));
        System.out.println(StudentObjectIOTest.class.getClassLoader().getResource("/"));
        System.out.println(StudentObjectIOTest.class.getClassLoader().getResourceAsStream("./"));
        System.out.println(String.class.getClassLoader());

//        同理还有下面三种也是应用类加载器

//        一
        System.out.println(ClassLoader.getSystemClassLoader().getResource(""));
        System.out.println(ClassLoader.getSystemClassLoader().getResource("./"));
        System.out.println(ClassLoader.getSystemClassLoader().getResource("/"));
        System.out.println(ClassLoader.getSystemClassLoader().getResourceAsStream("./"));

//        二(直接加载文件了)
        System.out.println(ClassLoader.getSystemResource(""));
        System.out.println(ClassLoader.getSystemResource("./"));
        System.out.println(ClassLoader.getSystemResource("/"));
        System.out.println(ClassLoader.getSystemResourceAsStream("./"));

//        特别是这个比较常用
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("JDBC.properties"));
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("./"));
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("/"));
        System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream("./"));

//   以上非常重要,以后要把配置文件等需要读的文件就放在类的路径下就非常灵活
    }
}
