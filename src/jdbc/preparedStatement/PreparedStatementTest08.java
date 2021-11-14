package jdbc.preparedStatement;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;


import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


//既然DAO这么常用，那么没人封装了一个吗？
//其实是有的，其中就有一个叫dbUtils，
//其中一个QueryRunner基本上可以等同我们之前的DAO，里面也有考虑到事务的和没考虑事务的，除了一些获取连接，查询等一些操作些许不同，其他基本都一样
//我们可以用具体表的操作类去继承它，再用接口去规范它，很妙，同时里面自动关闭结果集和preparedStatement跟我们写的是一样的，但关闭连接也跟上面是
//protected所以不能调用之关闭连接，所以我们有另一个类DbUtils，此DbUtils工具类中，
// 有一些对连接的操作，如关闭，提交等，当然有些操作没有，但我们可以直接用连接调用方法，如设置自动提交和事务隔离性等，
//ResultSetHandler其实是为了处理ResultSet而设的接口，在我们之前所写的DAO中对于表的查询我们是将结果以LinkedList形式返回，当我们要以Map返回
//怎么办，此就提供了各种类去实现这个接口，其中ResultSetHandler只有T handle(ResultSet var1) throws SQLException;一个方法，可见
//在查询时是将结果集给与它，让之去处理，并返回T。

public class PreparedStatementTest08 {
    public static void main(String[] args) {
//        其构造方法有俩，第一种无参构造则不会传一个连接池对象给它，那么就不能使用其自带的非事务的操作，这不同与我们自己写的DAO指定死了一个
//        连接池对象给之
        QueryRunner queryRunner = new QueryRunner();
//        protected final DataSource ds;因为不是静态的，所以与我们之前写的DAO中的连接池不太一样，但其实也差不多，或者说前者更灵活
//        前者可以在具有表的操作类中可以加入一个局部方法(或专门设一个设置方法)为该值附上特定的连接池，而我们自己写的DAO类则是用的静态方法，其实也差不多，前者每次new个
//        表操作类对象都会有个新的连接池，但对象是要用很多次的操作的，也无妨，后者只能写在DAO中

//        非业务要求，单纯使用QueryRunner的话，第一种new法仅能是来事务操作，以下给个连接池是来让它进行非事务操作和事务操作都行的
        Properties properties = new Properties();
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Druid.properties");
        System.out.println(resourceAsStream);
        DataSource dataSource = null;
        try {
            properties.load(resourceAsStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
            QueryRunner queryRunner1 = new QueryRunner(dataSource);
            System.out.println(queryRunner1);

//            接下来可以用queryRunner1来进行
//            可以来或得一个conn进行事务操作
//            Connection connection = queryRunner1.getDataSource().getConnection();
//            或直接来非事务操作
//            操作分为以下三种
//            update方法的事务版和非事务版
//            batch操作用于insert和update delete的批量操作，同理分事务版和非事务版
//            query的事务版和非事务版


//            update
//            更新之删除
          /*   String sql = "delete from emp where empno = ?";
            int update = queryRunner1.update(sql, 1);
            System.out.println(update);*/

//            更新之插入
         /*   String sql1 = "insert into emp(empNo,eName,job,MGR,hireDate,sal,comm,deptNo) values(?,?,?,?,?,?,?,?); ";
            int update1 = queryRunner1.update(sql1, 1, "kang", "client", 4, new Date(System.currentTimeMillis()), 9.0, 2.0, 5);
            System.out.println(update1);*/

//           批量插入
/*            long mills1 = System.currentTimeMillis();
            String sql2 =  "insert into goods(name)values(?)";
//            之前我们写的DAO没有对批量操作进行封装，而此DAO却有
//            public int[] batch(String sql,Object[][] params)throws SQLException
//            后者为一个sql的不同填充效果
            Object[][] objects = new Object[1000000][];
            for (int i = 0; i < 1000000; i++) {
                objects[i] = new Object[1];
                objects[i][0] = i+1;
            }
            int[] batch = queryRunner1.batch(sql2, objects);
            long mills2 = System.currentTimeMillis();
            System.out.println(mills2-mills1);  //没有用取消自动提交，所以效率很慢，不如自己实现*/


//            查询
//            先来看常见ResultSetHandler接口的实现类
//            BeanHandler：将结果集中的第一行数据封装到一个对应的JavaBean实例中并返回该对象(基本类型的null会处理)
//            BeanListHandler：将结果集中的每一行数据都封装到一个对应的JavaBean实例中，存放到List里，并返回(基本类型的null会处理)。
//            ScalarHandler：查询单个值第一行中某个列，注意用默认泛型，所以最后得自己强转为对应数据类型
//            MapHandler：将结果集中的第一行数据封装到一个Map里，key是列名，value就是对应的值，即属性都封在一个Map中而不是具体类的对象(好处就是不用起别名sql语句,
//            都是列的名，即使起别名了，而且因为value是Object所以对基本类型没处理)
//            MapListHandler：将结果集中的每一行数据都封装到一个Map里，然后再存放到List,并返回它
//            还可以自己写一个匿名内部类然后实现ResultSetHandler中的handle方法

//            BeanHandler
           /* String sql = "select empno empNo, ename eName, job ,mgr MGR,hiredate hireDate,sal," +
                    "comm ,deptno deptNo from emp where empno = ?";
            BeanHandler<Emp> empBeanHandler = new BeanHandler<>(Emp.class);
            Emp query = queryRunner1.query(sql, empBeanHandler, 1);
            System.out.println(query);*/

//            BeanListHandler
       /*     String sql = "select empno empNo, ename eName, job ,mgr MGR,hiredate hireDate,sal," +
                    "comm ,deptno deptNo from emp where deptno = ?";
            BeanListHandler<Emp> empBeanListHandler = new BeanListHandler<>(Emp.class);
            List<Emp> query = queryRunner1.query(sql, empBeanListHandler, 30);
            query.forEach(System.out::println);*/

//            ScalarHandler
 /*           String sql = "select max(sal) from emp";
//            可以无参构造，默认第一列，同理可以传列数，列名，也可以俩个都传，名字是更高查找优先级的
//            而且其handle是返回Object意味着要强转
            ScalarHandler scalarHandler = new ScalarHandler();
            double query = (double) queryRunner1.query(sql, scalarHandler);
            System.out.println(query);*/

//            MapHandler
        /*    String sql = "select empno empNo, ename eName, job ,mgr MGR,hiredate hireDate,sal," +
                    "comm ,deptno deptNo from emp where empno = ?";
//            因为key总是String，而value干脆也不弄了，是object，所以用默认泛型
            MapHandler mapHandler = new MapHandler();
            Map<String, Object> query = queryRunner1.query(sql, mapHandler, 1);
            Set<Map.Entry<String, Object>> entries = query.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                System.out.println(entry.getKey()+"="+entry.getValue());
            }*/

//            MapListHandler
      /*      String sql = "select empno empNo, ename eName, job ,mgr MGR,hiredate hireDate,sal," +
                    "comm ,deptno deptNo from emp where deptno = ?";
            MapListHandler mapListHandler = new MapListHandler();
//            返回是固定的所以没用泛型
            List<Map<String, Object>> query = queryRunner1.query(sql, mapListHandler, 30);
            for (Map<String, Object> stringObjectMap : query) {
                Set<String> strings = stringObjectMap.keySet();
                for (String string : strings) {
                    System.out.print( string + "=" + stringObjectMap.get(string)+"    ");
                }
                System.out.println("");
            }*/

//            自定义的ResultSetHandler实现类DbUtils类

            String sql = "select * from emp";
            ResultSetHandler<String> resultSetHandler= new ResultSetHandler<String>() {
                @Override
                public String handle(ResultSet resultSet) throws SQLException {
                    return "如果有必要我们就实现下";
                }
            };
            String query = queryRunner1.query(sql, resultSetHandler);
            System.out.println(query);

//            了解下DbUtils
//              DbUtils.close("可加Statement/Connection/Resultset之一");会抛异常
//              DbUtils.closeQuietly("同理");有点像我们写的close将异常封闭了
//              DbUtils.commitAndClose("Connection"); 不用多说
//              DbUtils.commitAndCloseQuietly("Connection"); 不用多说
//              DbUtils.rollback("Connection");不用多说
//              DbUtils.loadDriver("传的driverClass")去代替forName而且还new了个对象但没返回，很鸡肋，现在都要连接池了

//            综上还是关闭多一些
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resourceAsStream != null) {
            try {
                resourceAsStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
