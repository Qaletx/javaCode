package jdbc.preparedStatement;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Target;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

//现在我们要对每个部分进行优化
public class PreparedStatementTest04 {
    public static void main(String[] args) throws Exception{
        System.out.println(PreparedStatementTest04.optimizeGetConnection());
    }

//    连接池的入门引入
//在实际开发的时候，调用更新查询语句是非常频繁的，从之前写的代码可以发现每次调用较通用的查询和通用的更新
//时，首先得获得其连接，再完成具体操作后再关闭连接，这就有关闭和连接很浪费时间得问题，有没有像线程池的方法
//开完线程后关闭其实是放进一个池子中，没关闭?有的，如C3P0和DBCP、Druid都是实现了jdk下的DataSource接口
//的实现类，其中又快又稳定的就是阿里开发的德鲁伊，所以我们就先浅浅地学它

/*    public static  Connection optimizeGetConnection() throws Exception{
//        德鲁伊上地实现类叫DruidDataSource
//        DataSource dataSource = new DruidDataSource();
//        其实现类有很多自己的方法
//       DruidDataSource dataSource = new DruidDataSource();
//       dataSource.setUrl("jdbc:mysql://localhost:3306/test");
//       等等，可以这样配置，但我们不建议这样，我们可以用DruidDataSourceFactory.createDataSource()直接调用配置文件即可
//        请转到配置文件查看

        Properties properties = new Properties();
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Druid.properties");
        properties.load(resourceAsStream);
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
//        其实得到的就是其实现类而已
        DruidDataSource dataSource1 = (DruidDataSource) dataSource;



//        注意此方法获得的连接不是普通连接，是德鲁伊连接，关闭时放回池子
        Connection connection = dataSource1.getConnection();
//        本质上是
        DruidPooledConnection connection1 = dataSource1.getConnection();

        dataSource1.close();

//       目前处于被获得且未关闭的个数
        System.out.println(dataSource1.getActiveCount());
//      此为连接池到现在为止总共的获得数
        System.out.println(dataSource1.getConnectCount());
//      此为连接池到现在为止总共的关闭数
        System.out.println(dataSource1.getCloseCount());
//        已建立物理连接的个数
        System.out.println(dataSource1.getCreateCount());
        return connection;

    }*/


//    很显然我们的德鲁伊池子其实只用new一个，以上是为了方便讲述该连接池

//    我们设定一个静态属性
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

    public static  Connection optimizeGetConnection() throws Exception{
        return dataSource.getConnection();
    }
}