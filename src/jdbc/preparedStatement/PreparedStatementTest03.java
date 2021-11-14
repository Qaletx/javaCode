package jdbc.preparedStatement;

import jdbc.preparedStatement.classtest.Emp;

import java.io.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;

//上个测试中已经完成任意表的更新,甚至能操作一些不带返回表的DDL了
//但任意表的查询就稍微比较麻烦了,因为需要用ORM思想,需要用带反射,泛型,new具体表的记录对象,并存在一个列表中进行返回
//且以下只能用于
public class PreparedStatementTest03 {
    public static void main(String[] args) throws Exception {
//        非常注意当数据库中字段名与类中属性不匹配,导致返回的结果集获得的字段名,反射后在类中找不到属性
//        所以务必将字段名起别名,跟类的属性一模一样,即使大小也要一样,所以一般*不用,毕竟不能起别名
//        String sql = "select * from emp where deptNo = ?";
        /*String sql = "select empno empNo, ename eName, job ,mgr MGR,hiredate hireDate,sal," +
                "comm ,deptno deptNo from emp where deptno > ? ";
        LinkedList<Emp> emps = queryCommon(sql,Emp.class,20);
        emps.forEach(System.out::println);*/


//        测试单项特别的查询
        String sql = "select count(*) from emp";
        long num = PreparedStatementTest03.selectSole(sql);
        System.out.println(num);

    }

//    获得连接
    public static Connection getConnection() throws Exception{
        Properties properties = new Properties();
        String path = PreparedStatementTest03.class.getResource("/JDBC.properties").getPath();
        FileInputStream fileInputStream = new FileInputStream(path);
        properties.load(fileInputStream);
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, properties);
        if (fileInputStream != null) {
            fileInputStream.close();
        }
        return connection;
    }
//    任意表通用的查询操作
    public static <T>  LinkedList<T> queryCommon(String sql,Class<T> tClass,Object...objects){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        LinkedList<T> linkedList = new LinkedList<>();
        try {
            connection = PreparedStatementTest03.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i+1,objects[i]);
            }

//            以下为特别的部分,因为执行的方法不一样,且会返回一张结果集表,该表需要关闭
            resultSet = preparedStatement.executeQuery();
//            获得该结果集的一个元数据,其实就是结构,便于获得这张临时表的列数(因为是结构所以跟记录无关不能行),便于每行遍历几次
            ResultSetMetaData metaData = resultSet.getMetaData();
            while(resultSet.next()) {   //next()方法会有个针指在整个结果集前,判断如果有下一行则返回true并移动
                T t = tClass.newInstance();  //new对象已被存入
                for (int i = 0; i < metaData.getColumnCount(); i++) { //循环每行中每列
//                    获得该格的列名,只能传整数
//                    String columnName = metaData.getColumnName(i + 1);   非常注意此获得的方法无视别名,不建议使用
                    String columnLabel = metaData.getColumnLabel(i + 1);
//                    获得该格的数据
//                    resultSet.getObject("字段名")   也可以跨格取数据

                    Object object = resultSet.getObject(i + 1);

//                    利用反射以名字找到该属性并赋值
                    Field declaredField = tClass.getDeclaredField(columnLabel);
//                    注意打破私有
                    declaredField.setAccessible(true);
//                    注意因为Double等包装类型可能为null不能拆箱,所以得判断后赋值0
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
            PreparedStatementTest03.closeResource(connection,preparedStatement,resultSet);
            return linkedList;
        }

    }




//    关闭资源,增加了结果集的关闭
    public static void closeResource(Connection connection, PreparedStatement preparedStatement,ResultSet resultSet){
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

//    上面的同用查询仅仅适应与一些字段都存在对象中的,但例如count max等就不是一条记录一个,自然也不能,设为局部变量
//     也不能设为静态变量，因为返回的可能是count的long或者max可能返回的是long double Date，而返会的特点是结果集只有一个，所以运用泛型
//     将该值返回的
    public static <T> T selectSole(String sql,Object...objects){
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;
        Object object = null;
        try {
            connection = PreparedStatementTest03.getConnection();
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
            PreparedStatementTest03.closeResource(connection,preparedStatement,resultSet);
            return (T) object;
        }
    }

//    多表链接查询等就不用写了，以后会有很多现有的库调用，有空可以实现下
}
