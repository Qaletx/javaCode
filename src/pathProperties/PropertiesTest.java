package pathProperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.ResourceBundle;

/*
IO+Properties的联合应用。
非常好的一个设计理念：
    以后经常改变的数据，可以单独写到一个文件中，使用程序动态读取。
    将来只需要修改这个文件的内容，java代码不需要改动，不需要重新
    编译，服务器也不需要重启。就可以拿到动态的信息。

    类似于以上机制的这种文件被称为配置文件。
    并且当配置文件中的内容格式是：
        key1=value
        key2=value
    的时候，我们把这种配置文件叫做属性配置文件。

    java规范中有要求：属性配置文件建议以.properties结尾，但这不是必须的。
    且最好不要把等号换成冒号,且等号左右不要有空格,字符串也不需要引号
    这种以.properties结尾的文件在java中被称为：属性配置文件。
    其中Properties是专门存放属性配置文件内容的一个类,是个Map集合。
    所以key不能重，后来得会覆盖前面得
 */
public class PropertiesTest {
    public static void main(String[] args) {
//        我们不妨就来试试刚才学的一个动态获取路径的方法获取配置文件
        String path = Thread.currentThread().getContextClassLoader().getResource("./ThreadTest.properties").getPath();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);

            Properties properties = new Properties();
//            如果无中文我们直接将fileInputStream传入即可，但考虑到有中文，我们就用转换流转换一下，也就是说load也可传字符流
            properties.load(  new InputStreamReader(fileInputStream,"utf-8"));
            System.out.println(properties.getProperty("name"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

//        因为配置文件的常用java还提供了一个绑定类，用以绑定资源配置文件，该类有个静态方法简简单单只用提供文件的路径就行，而且调用value也非常简单
//        但是只能绑定properties的文件，且后缀名不能写，而且不能写绝对路径，只能是相对与类路径src下的相对路径，这就意味着我们用类加载的字符串需要切割
//        挺麻烦的，用得感觉不多,但默认就可以用中文,只是用来读，并不能设置
        ResourceBundle bundle = ResourceBundle.getBundle("ThreadTest");
        System.out.println(bundle.getString("name"));
    }
}