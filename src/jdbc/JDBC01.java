package jdbc;



import com.mysql.jdbc.Driver;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

//实现java代码控制数据库的第一肯定是获得连接
public class JDBC01 {
    public static void main(String[] args) {
/*
//       第一种方式

//        注意此为mysql实现java.sql下的接口实现类
//        Driver继承的NonRegisteringDriver供实现
        Driver driver = null;
        Connection connect = null;
        try {

//           driver = new Driver();

//            也可以用反射new对象
            Class aClass = Class.forName("com.mysql.jdbc.Driver");
            driver = (Driver)aClass.newInstance();
            // jdbc:mysql:协议
            // localhost:ip地址
            // 3306：默认mysql的端口号
            // test:test数据库
//           url大小写没区别
//           注意协议处的//是固定的，而/加数据库名称，只能有一个/
            String url = "jdbc:mysql://localhost:3306/test";
            Properties properties = new Properties();
//            注意key要规范,大小写也要规范
            properties.setProperty("user","root");
            properties.setProperty("password","qq13924756717");

//            获得连接
            connect = driver.connect(url, properties);
            System.out.println(connect);

//            总结，1，获得diver对象
//                 2，构造url与properties
//                 3，利用diver对象获得连接
//                 4，关闭连接
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }finally{
        if (connect != null) {
               try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        }
*/

/*

//        第二种方式
//        其实第一种方法并不规范,因为jdbc标准上是各个厂商实现Driver接口并在静态代码块注册Driver对象
//        利用jdk自己给的DriverManager去管理各个厂家的Driver对象,利用DriverManager去执行各种操作
//        而不是用各自厂商的Driver实现类,这是一种规范
//        我们来手动注册DriverManager
        Class<Driver> aClass = null;
        try {


            aClass= (Class<Driver>) Class.forName("com.mysql.jdbc.Driver");


            Driver driver = aClass.newInstance();
            DriverManager.registerDriver(driver);   //            注册driver,去调用在registerDriver中调用注册mysql的方法


            String url = "jdbc:mysql://localhost:3306/test";
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password","qq13924756717");
//            获得连接
//            DriverManager.getConnection(可以直接传url 加账号 密码)
            Connection connection = DriverManager.getConnection(url, properties);
            System.out.println(connection);
            if (connect != null) {

                connect.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
*/

/*
//        第三种方法是第二种的补充

        try {
//            在jdk6后DriverManager支持去读取jar包中的配置文件路径如/META-INF/services/中的配置文件中的Driver路径,并
//             自动加载类,也就是以下行还可以省,但我们不建议这样做.因为有些sql实现类不支持,且写上的话我们会更灵活
            Class.forName("com.mysql.jdbc.Driver");

//        因为JDBC规范实现类Driver中的静态代码块会执行new对象，并注册，所以我们只用负责将类加载进来就行
 //           Driver driver = aClass.newInstance();
 //           DriverManager.registerDriver(driver);

//          "jdbc:mysql:///test" 如果中间为localhost:3306可以不写
            String url = "jdbc:mysql://localhost:3306/test";
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password","qq13924756717");
            Connection connection = DriverManager.getConnection(url, properties);
            System.out.println(connection);
            if (connect != null) {

                connect.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
*/

//        第四种方法就是在第三种的基础上实现类加载的灵活与url user password的灵活变更
//        自然是我们比较熟悉的了
        InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream("JDBC.properties");
        Properties properties = new Properties();
        Connection connection = null;
        try {
            properties.load(systemResourceAsStream);
            String driverClass = properties.getProperty("driverClass");
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            Class.forName(driverClass);
           connection = DriverManager.getConnection(url,user,password);
//            Connection connection = DriverManager.getConnection(url,properties);
            System.out.println(connection);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (systemResourceAsStream != null) {
                try {
                    systemResourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        }


    }
}
