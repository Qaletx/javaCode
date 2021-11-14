package jdbc;

import java.sql.Connection;
import java.sql.SQLException;

//summary
//在平时开发中我们需要对一个具体表的操作类继承DAO（可以是自己写的，也可以是别人写好的）,然后用一些接口进行规范
//但平时我们就随便用用查某张表，就不用那么麻烦，以下是模板


public class JDBC02 {
    public static void main(String[] args) {

        //以下为考虑事务版本的，最好考虑事务，养成好习惯
//                获得连接
//                开启事务
//                执行sql
//                关闭事务
//                关闭连接

        Connection conn = null;
        try {
            //1.获取连接的操作（
               //1 手写连接：JDBCUtils.getConnection();
               //2 使用数据库连接池：C3P0;DBCP;Druid
               //3 获得数据库连接池对象并赋值给dbutils中的QueryRunner对象，通过它调用方法得到连接池对象再得带连接，此3是跟之后用QueryRunner操作一套的
            //2，关闭事务自动提交

            //3.对数据表进行一系列操作
               //1，使用自己实现的通用操作对之操作
              //2， 使用dbutils提供的jar包中提供的QueryRunner类

            //提交数据
            conn.commit();


        } catch (Exception e) {
            e.printStackTrace();


            try {
                //回滚数据
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }finally{
               //1，设置事务自动提交
               //2，关闭操作
                 //1，自己写的关闭类
                //2，使用dbutils提供的jar包中提供的DbUtils类提供了关闭的相关操作

        }
    }
    public void summary(){
//        未考虑事务的往往只执行一条，用于快速操作

//        方式一
//        直接调用自己写的通用操作

//        方式二
//        获得连接池对象并创建dbutils中的QueryRunner对象并给与该连接池对象
//        直接调用QueryRunner对象中的方法
    }
}
