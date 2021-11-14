package jdbc.dao;



import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;
//因为需要特点表的class对象是一定的,则可以用泛型去应对简化查询后需要传class的麻烦
//因为不同的表操作类继承它不是泛型,所以必将指明其泛型的具体类型
public class DAO <T>{


    private static DataSource dataSource = null;

    static {
        Properties properties = new Properties();
        DataSource dataSource1 = null;
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Druid.properties");
        try {
            properties.load(resourceAsStream);
            dataSource1 = DruidDataSourceFactory.createDataSource(properties);
            dataSource = dataSource1;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            if (resourceAsStream != null) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //  因为泛型属性不能为静态的,所以必须为实例的,可能这是一个缺点
    //  且运用它的方法也不能是静态的,所以除了获得连接和关闭外其他都是静态
    private Class<T> clazz = null;

  //以下局部代码块为其表的操作类new对象时执行的
  {
      //运行反射泛型
      Type genericSuperclass = this.getClass().getGenericSuperclass();
      ParameterizedType genericSuperclass1 = (ParameterizedType) genericSuperclass;
      Type[] actualTypeArguments = genericSuperclass1.getActualTypeArguments();
      clazz = (Class<T>)actualTypeArguments[0];
  }

//    连接池获取连接
    public  static  Connection optimizeGetConnection() throws Exception{
        return DAO.dataSource.getConnection();
    }




    //普通的获取连接
    @Deprecated
    public static Connection getConnection() throws Exception {
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

    //考虑事务的更新
    public long updateCommonTransaction(Connection connection,String sql,Object...args){
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
            closeResource(null,preparedStatement,null);
            return updateNum;

        }
    }

//    不考虑事务的更新
    public long updateCommon(String sql,Object...args){
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        long updateNum = 0;
        try {
//            获得连接
            conn =optimizeGetConnection();
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
           closeResource(conn,preparedStatement,null);
//            注意返回得写在这里
            return updateNum;

        }
    }


    //    单事务优化的较通用查询
    public  LinkedList<T> queryCommonTransaction(Connection connection, String sql,  Object...objects){
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
                T t = clazz.newInstance();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Object object = resultSet.getObject(i + 1);
                    Field declaredField = clazz.getDeclaredField(columnLabel);
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
            closeResource(null,preparedStatement,resultSet);
            return linkedList;
        }

    }

//不考虑事务的查询
    public  LinkedList<T> queryCommon(String sql,Object...objects){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        LinkedList<T> linkedList = new LinkedList<>();
        try {
            connection = optimizeGetConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i+1,objects[i]);
            }

            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            while(resultSet.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String columnLabel = metaData.getColumnLabel(i + 1);

                    Object object = resultSet.getObject(i + 1);

                    Field declaredField = clazz.getDeclaredField(columnLabel);
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
            closeResource(connection,preparedStatement,resultSet);
            return linkedList;
        }

    }

//考虑事务的单数据查询
    public  <T> T selectSoleTransaction(Connection connection,String sql,Object...objects){
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;
        Object object = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length ; i++) {
                preparedStatement.setObject(i+1,objects[i]);
            }
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                object = resultSet.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeResource(null,preparedStatement,resultSet);
            return (T) object;
        }
    }


//  非事务的单数据查询
    public <T> T selectSole(String sql,Object...objects){
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;
        Object object = null;
        try {
            connection = optimizeGetConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length ; i++) {
                preparedStatement.setObject(i+1,objects[i]);
            }
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                object = resultSet.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeResource(connection,preparedStatement,resultSet);
            return (T) object;
        }
    }

    //    通用的关闭
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

}
