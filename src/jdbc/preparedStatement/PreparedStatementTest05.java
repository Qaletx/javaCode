package jdbc.preparedStatement;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

//基于事务对较通用更新的优化
//在MySQL中我们知道有事务这件事，对于一些操作会存在事务自动提交(临时表对内存表的覆盖)，也就是一条操作就是一个事务,而
//其实一个事务就是在同一连接中多个对表更新或查询操作得一个集合,可见一个连接可以有很多个事务,一个接着一个
//数据一旦提交，就不可回滚
//
//   哪些操作会导致数据的自动提交？
//      >DDL操作一旦执行，都会自动提交。
//      >set autocommit = false 对DDL操作失效
//      >DML默认情况下，一旦执行，就会自动提交。
//      >我们可以通过set autocommit = false的方式取消DML操作的自动提交。
//      >默认在关闭连接时，会自动的提交数据
//
// 可见一般DDL不能主动事务,而DML一些对表的更新就可以用主动事务,那么我们要主动事务就得实现以上俩点
// 因为之前的通用更新的连接获得和关闭都在其中,所以我们要实现在同一连接中的对个操作集合就必须从外传连接
// 第二点就是关闭问题,一关闭,则该连接的事务提交.
// 所以解决办法就是1,从外获得连接传给更新或查询,2,从外关闭连接,3,设置DML关闭自动事务提交,手动提交,出现异常回滚
//
// 以下为事务版本的更新
public class PreparedStatementTest05 {
    public static void main(String[] args) {
        Connection connection = null;
        try {
//            从外面获得连接
           connection = PreparedStatementTest05.getConnection();
//            设置DML事务不自动提交
           connection.setAutoCommit(false);

//           事务操作1
           String sql1 = "update emp set sal = sal - 100 where empno = ?";
            long l = updateCommon(connection, sql1, 1);
            System.out.println(l);

//            模拟发生异常,已完成事务操作1,但未提交
            System.out.println(10/0);

//            事务操作2
            String sql2 = "update emp set sal = sal + 100 where empno = ?";
            long l2 = updateCommon(connection, sql2, 2);
            System.out.println(l2);
//            事务提交
            connection.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e){
            System.out.println("其他错误,回滚数据");
            try {
//                发生中断事务回滚
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }finally {
            try {
//            为了迎合连接池,记住要设置回来DML事务自动提交,否者被下个用,也是事务不提交
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
//            此处来关闭连接,而另一个不用关,自然为0
            PreparedStatementTest05.closeResource(connection,null);
        }


    }




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

//传一个connection
    public static long updateCommon(Connection connection,String sql,Object...args){
        PreparedStatement preparedStatement = null;
        long updateNum = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length ; i++) {
                preparedStatement.setObject(i+1,args[i]);
            }
            updateNum = preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
//            不关闭null,但需要关闭preparedStatement
            PreparedStatementTest05.closeResource(null,preparedStatement);
            return updateNum;

        }
    }

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
//    很显然我们对更新进行了事务的优化，而查询虽然没有更改表，不会实现不安全的操作，但是我们知道数据库是实现了多并发的，意味着能获得多个连接
//    而对同张表进行更新查询操作，这样就可能因为a连接对表进行了更改，而b连接查询得到底是改变得还是没改变得种种问题，这就设置到多并发得问题，
//    为了解决也把查询与事务联系起来，所以查询也要实现事务话，另外，一般要对同一连接进行很多操作，不可能还用之前得查询获得新的连接查询，虽然
//    有连接池得加持，但还是比较慢得，所以也要实现查询得事务化，并传连接得参数进去
}
