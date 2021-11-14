
package jdbc.preparedStatement;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

//Connection是sql下的一个规范类接口，获得它的实现类的对象能进行很多操作
//其实我们之前获得的Connection对象是MySQL的实现类的对象，因为conn是
//Connection conn=DriverManager.getConnection(...) 来的，DriverManager自然会去调用实现类的Driver.connect();
public class PreparedStatementTest01 {
//    PreparedStatement是Statement的一个子接口，Statement是由conn的方法获得的，像一个使者，用于和数据库进行交互
//    同理，俩者都需要Connection的实现类对象来获得对应的实现类的对象,对应的实现类也是叫这个名字
//    前者是预处理的，即得到其实现类对象时需要传sql语句，然后插入具体数据类型得占位，sql具体结构，其对象会得知
//    不会因为插入得数据不同而其逻辑发生变化，后者不是，所以有以下区别
//            * 1.解决Statement的拼串、sql问题之外，PreparedStatement还有哪些好处呢？
//            * 2.PreparedStatement操作Blob的数据，而Statement做不到（因为补充占位时要插入对应数据类型变量）。
//            * 3.PreparedStatement可以实现更高效的批量操作(因为PreparedStatement只用传一次sql进行占位)。

//    了解比较简单的更新(增,删,改)
//    我们先来简单版的示例
public static void main(String[] args) {
    Properties properties = new Properties();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("JDBC.properties");
    try {
        properties.load(resourceAsStream);
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");
        Class.forName(driverClass);
//        获得连接
        connection = DriverManager.getConnection(url, properties);
//        定义sql语句,记录用占位符占位,最后分号可以省
        String sql = "insert into emp(empNo,eName,job,MGR,hireDate,sal,comm,deptNo) values(?,?,?,?,?,?,?,?); ";
//        利用conn获得preparedStatement
        preparedStatement = connection.prepareStatement(sql);
//        俺索引填充占位符，注意索引是从1开始与sql语句中的字段一一对应(因为数据库的列是从1开始的),
//        当不是insert时如update则占位从左到右1开始,且注意占位的数据类型以便选择适当的setXXX填充具体类型的变量
//        注意sql中数据类型和Java之间的对应，没有8种包装类型,但可以自动拆箱其实也差不多,所以有setObject(注意基本类型返回包装类型)
        preparedStatement.setInt(1,2);
        preparedStatement.setString(2,new String("ZHENG"));
        preparedStatement.setString(3,new String("CLERK"));
        preparedStatement.setInt(4,2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date parse = simpleDateFormat.parse("2021/10/29");
//        非常注意该Date为java.sql.Date不是java.util.Date;，但可以将之调用毫秒传过去作为参数传过去
//        注意俩个重名的类就不能都导包了，因为不知道用啥，所以表明类的全名
        preparedStatement.setDate(5,  new java.sql.Date(parse.getTime()));
//        如果是目前时间可以直接这样
//        preparedStatement.setDate(5,new java.sql.Date(System.currentTimeMillis()));

        preparedStatement.setDouble(6,6.0);
        preparedStatement.setDouble(7,7.0);
        preparedStatement.setInt(8,8);

//        执行，此返回更新几个个记录，因为之后回学习到，几个sql语句可以堆成组，一下子执行或delete可以删除很多条记录
        int num = preparedStatement.executeUpdate();
        System.out.println("改变了一条数据"+num);
//        执行,此为执行更新,但返回的是是否成功的布尔值;
//        preparedStatement.execute();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    } catch (ParseException e) {
        e.printStackTrace();
    } finally {
//        以下三个都得关闭
        if (resourceAsStream != null) {
            try {
                resourceAsStream.close();
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
        if (preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }


}
}
