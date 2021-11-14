package Collection_;

import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;

public class Collection_test {

        public static void main(String[] args) {
            propertiesDemo();
        }

        public static void propertiesDemo() {
            Properties prop = new Properties();
            prop.setProperty("02", "huangjianfeng");
            prop.setProperty("03", "jianfeng");
            prop.setProperty("04", "feng");

            prop.list(System.out);//该方法多用于调式，开发中很少用
        }
}
