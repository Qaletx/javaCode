package jdbc.preparedStatement;

//多事务的查询问题,之前是单事务的更新问题
//多个线程获得多个连接，其中一个线程对某张表进行更改内容，那么另外得连接查询就有可能出现脏读，不可重复读，和幻读
//其中脏读是指a连接改变后未提交，其他连接在未提交时也能查出变化，当然如果a回滚，自然其他连接再查就会回到原来得数据
//这些数据对于其他连接就像脏数据，因为可能是假得，毕竟a可以回滚，不可重复读，就是一旦a连接得更改一提交，那么其他连接在未提交的情况下
//得查询就能查出来，并且不能读到之前得数据了，即事已定，后者则是就跟不可重复读一样，只不过是对表的增行，或删行，不同于第二个只是更改内容
//
//针对四种问题就提出了四种隔离级别，隔离级别越高, 数据一致性就越好, 但并发性越弱
//三个问题都没解决：readUnCommit读未提交
//解决脏读即把脏读变成不可重复读: readCommit读已提交
//解决脏读和不可重复读:repeatableRead可重复读  即必须其他连接也提交当前事务后才能看到变化，即以重复读到a已提交的之前的数据
//解决全部问题serializable串型行
//很显然一般我们只解决脏读就行了，不可重复读和幻读没啥问题，还能保证执行效率
//不同数据库的默认隔离级别是不同的，如Mysql是可重复读
//我们在命令行设置隔离级别时可以设全局的(再服务关闭之前所有的连接)，还可以设置单连接的
//全局:set global transaction isolation level read committed;
//局部:set  transaction isolation level read committed;
//查询:SELECT @@tx_isolation;
//在代码层面则只能设置单连接的
//但除非特殊要求，不然我们也很少去设置隔离级别，用其默认的就非常好
//Connection接口中对应的常数为1,2,4,8
import jdbc.preparedStatement.classtest.Emp;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;

class TransactionQuery implements Runnable{

    public void run(){
        Connection connection = null;
        try {
             connection = PreparedStatementTest06.getConnection();
             connection.setAutoCommit(false);
            System.out.println(connection.getTransactionIsolation());
//            更改此
            connection.setTransactionIsolation(1);
            String sql = "select empno empNo, ename eName, job ,mgr MGR,hiredate hireDate,sal," +
                    "comm ,deptno deptNo from emp ";
            LinkedList<Emp> emps = PreparedStatementTest06.queryCommon(connection, sql, Emp.class);
            for (Emp emp : emps) {
                System.out.println(emp);
            }
            connection.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            PreparedStatementTest06.closeResource(connection,null,null);
        }
    }
}
public class PreparedStatementTest06 {

    //    用代码实现更改和验证多事务的隔离级别
//    需要用到多线程模拟双连接的双事务
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = PreparedStatementTest06.getConnection();
            connection.setAutoCommit(false);
//          查询默认多事务的隔离级别 为4 可见为可重复读
            System.out.println(connection.getTransactionIsolation());
//          设置多事务级别,但我们得知道每个连接都可以设置事务隔离等级,但在哪个连接设,是针对该连接上得查询操作
//            connection.setTransactionIsolation(4);
            String sql = "update emp set sal = ? where empno = ?";
            long l = PreparedStatementTest06.updateCommon(connection, sql, 10000, 1);
            TransactionQuery transactionQuery = new TransactionQuery();
            Thread thread = new Thread(transactionQuery);
            thread.start();
            Thread.sleep(3000);
            connection.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            PreparedStatementTest06.closeResource(connection,null,null);
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
        connection = DriverManager.getConnection(url, properties);

        if (resourceAsStream != null) {
            try {
                resourceAsStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }


    public static long updateCommon(Connection connection, String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        long updateNum = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            updateNum = preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
//            不关闭null,但需要关闭preparedStatement
            PreparedStatementTest06.closeResource(null, preparedStatement,null);
            return updateNum;

        }
    }

    public static void closeResource(Connection conn, PreparedStatement preparedStatement,ResultSet resultSet) {
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
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //    单事务优化的较通用查询
    public static <T> LinkedList<T> queryCommon(Connection connection,String sql, Class<T> tClass, Object...objects){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        LinkedList<T> linkedList = new LinkedList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i+1,objects[i]);
            }


            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            while(resultSet.next()) {
                T t = tClass.newInstance();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Object object = resultSet.getObject(i + 1);
                    Field declaredField = tClass.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    if( (object == null) && (declaredField.getType().isPrimitive())){
                        object = 0;
                    }
                    declaredField.set(t,object);
                }
                linkedList.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            PreparedStatementTest06.closeResource(null,preparedStatement,resultSet);
            return linkedList;
        }

    }

}


