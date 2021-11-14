package jdbc.preparedStatement;

import java.beans.Statement;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

//补充一些PreparedStatement的优点的示例
//除了防止sql注入外还可以实现高效高批量操作
//还能插入大数据类型的数据,Statement只能读取,不能插入
public class PreparedStatementTest07 {
    public static void main(String[] args) {
//        尝试讨论下批量的操作
//        PreparedStatementTest07.BatchInsert();
//        探究下大数据类型(Blob)的插入与读取
        PreparedStatementTest07.blob();
    }

    public static void BatchInsert(){
//        为啥只研究批量的insert呢（其实delete和update有时也要一些动态的批量操作，同理）
//        其实也可以研究下DDL但没必要,其他DML其实都自带对表的批量操作如select和delete

//        第一种方法自然是用Statement了,但每次都能重新生产String对象还有其他很多缺点所以直接来研究第二种

//        第二种,模拟下
    /*    Connection connection = null;
        PreparedStatement preparedStatement = null;
        long mill1 = System.currentTimeMillis();
        try {
            connection = PreparedStatementTest07.getConnection();
            String sql = "insert into goods(name)values(?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < 400; i++) {
                preparedStatement.setObject(1,i);
                preparedStatement.execute();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            PreparedStatementTest07.closeResource(connection,preparedStatement,null);
        }
        long mill2 = System.currentTimeMillis();
        System.out.println(mill2-mill1);  //400行1713毫秒,10w行的话要很久很久,可见即使一个连接去插入也要很长时间
        */

//        第二种方法,我们可以用MySQL新加上Batch对sql语句进行积累,然后到一定量再去与数据库交互,防止每次一条就去交互
//        同时因为DML语句都自带事务提交.即使开启Batch也会,这无疑会浪费很多时间,我们可以更进
//        非常注意需要在配置文件后面加上?rewriteBatchedStatements=true，同时导入新的MySQLjar包
//        mysql-connector-java-5.1.37-bin.jar 缺一不可，否者没效果，但不会报错
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        long mill1 = System.currentTimeMillis();
        try {
            connection = PreparedStatementTest07.getConnection();
            String sql = "insert into goods(name)values(?)";
           preparedStatement = connection.prepareStatement(sql);
           connection.setAutoCommit(false);
            for (int i = 1; i <= 1000000; i++) {
                preparedStatement.setObject(1,i);
//                攒preparedStatement自身的sql语句但不执行execute
                preparedStatement.addBatch();
                if(i % 10000 == 0){
//                    执行batch并清空batch
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }
            connection.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }catch(Exception e) {
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
            PreparedStatementTest07.closeResource(connection,preparedStatement,null);
        }
        long mill2 = System.currentTimeMillis();
        System.out.println(mill2-mill1); //100w的数据5秒即可，可见非常之快

    }

    public static void blob(){
//        数据库中有如下几种大数据类型
//        TinyBlob  最大255字节    Blob 最大65k字节   MediumBlob 最大16M字节  LongBlob 最大4G字节

//        插入和获取都为字节输入流

//        插入
//        非常注意需要在C:\Program Files (x86)\MySQL\MySQL Server 5.5设置max_allowed_packet=16M并重新启动mysql服务才可以，当然还可以设置最多4G
//        不管是以上哪种类型都用Blob mysql已经对接口进行实现
   /*     Connection connection = null;
        InputStream resourceAsStream = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = PreparedStatementTest07.getConnection();
            String sql = "insert into goods(imgOrVideo) values(?)";
            preparedStatement = connection.prepareStatement(sql);
            resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("海贼王.jfif");
            preparedStatement.setBlob(1,resourceAsStream);
            preparedStatement.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (resourceAsStream != null) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            PreparedStatementTest07.closeResource(connection,preparedStatement,null);
        }*/

//        读取
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        InputStream binaryStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            connection = PreparedStatementTest07.getConnection();
            String sql = "select imgOrVideo from goods where id = ?";
           preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setInt(1,7);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
//                返回的是mysql的实现类
                Blob blob = resultSet.getBlob(1);
                binaryStream = blob.getBinaryStream();
            }
            if (binaryStream != null) {
                fileOutputStream = new FileOutputStream("C:\\Users\\33001\\OneDrive\\桌面\\海泽王.jfif");
                byte[] bytes = new byte[10];
                int length = 0;
                while( (length = binaryStream.read(bytes)) != -1){
                    fileOutputStream.write(bytes,0,length);
                }
                fileOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (binaryStream != null) {
                try {
                    binaryStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            PreparedStatementTest07.closeResource(connection,preparedStatement,null);
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

    public static void closeResource(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){
        if (connection != null) {
            try {
                connection.close();
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
}
