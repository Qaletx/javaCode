package jdbc.preparedStatement;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class PreparedStatementTest02 {
//    很显然之前的更新是面对过程的,不利于代码的复用
//    PreparedStatement的更新无非就是以下几点
//    1,获得数据库的连接
//    2,获得PreparedStatement的对象,填充占位符,并执行
//    3,关闭具体的流
//    为了实现通表的更新.我们就要对以上三个方法进行封装,然后不同表其实创建不同类就行(查询才用得到,更新用不到)
    public static void main(String[] args) {
//        emp表中增加入一行记录
        String sql = "insert into emp(empNo,eName,job,MGR,hireDate,sal,comm,deptNo) values(?,?,?,?,?,?,?,?); ";
        long updateNum =
        PreparedStatementTest02.updateCommon(sql,2,"kang","client",4,new java.sql.Date(System.currentTimeMillis())
        ,1000.0,7.0,8);
        System.out.println(updateNum);

//        emp中删除一条记录
//        很显然这样不行,占位符只能在记录上,不会在字段上
//        String sql  = "delete from emp where ? = 8";
       /* String sql  = "delete from emp where empno = ?";
        long update = PreparedStatementTest02.updateCommon(sql,8);
        System.out.println(update);*/

//        改
     /*   String sql = "update emp set empno = ? where empno = ?";
        long updateNum = PreparedStatementTest02.updateCommon(sql,12,9);
        System.out.println(updateNum)*/;

//        首先我们来了解为啥要占位符
//        不能只传一个确定的sql语句吗,传一个不完整的sql语句用Statement还要怕sql注入,不得不使用PreparedStatement,这又是何必呢
//        其实能传一个确定的sql语句,比如实现一些DDL(某些DDL有返回值如查数据库列表)或传一些确定的DML,但我们要用占位符,其实就是想达到String的复用而Statement的拼串是
//        为了达到任何部位的灵活,在前者占位只支持其记录的占位,但因为是记录所以可以插入一些图片,甚至音频,
//        之后我们会学到插入多条数据,PreparedStatement的String复用性就体现出来了.

//        DDl的操作
/*        String sql = "create dataBase kang;";
        long l = PreparedStatementTest02.updateCommon(sql);
        System.out.println(l);*/

//        注意事项
//        1,当字段名或者表名为order等关键字时记得加上引号括起来,以此作为区分,在MySQL中也应该如此

    }




    //        获得连接(会引起不想中断操作都得向上抛 抛出异常,因为调用者方法要进行位置中断,可分别上抛自己的异常,也可抛出一个父类异常)
    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        Connection connection = null;
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("JDBC.properties");
        properties.load(resourceAsStream);
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");
        Class.forName(driverClass);
        connection= DriverManager.getConnection(url, properties);

        if (resourceAsStream != null) {
            try {
                resourceAsStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

//   2,获得PreparedStatement的对象,填充占位符,并执行
//    可以返回更新了几条信息
//    此方法最为关键整个框架还是有的,需要调用1和3,同时还要关注参数得传递
//    此为单事务的更新
    public static long updateCommon(String sql,Object...args){
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        long updateNum = 0;
        try {
//            获得连接
            conn = PreparedStatementTest02.getConnection();
//            获得PreparedStatement对象
            preparedStatement = conn.prepareStatement(sql);
//            将传进来的sql填充
            for (int i = 0; i < args.length ; i++) {
                preparedStatement.setObject(i+1,args[i]);
            }
//            执行后记录返回值
            updateNum = preparedStatement.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            PreparedStatementTest02.closeResource(conn,preparedStatement);
//            注意返回得写在这里
         return updateNum;

        }
    }

//   3,关闭具体流
    public static void closeResource(Connection conn,PreparedStatement preparedStatement){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
