package jdbc.dao;

import jdbc.preparedStatement.PreparedStatementTest02;
import jdbc.preparedStatement.classtest.Emp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;

//回顾之前的学习到了较通用的表的查询和通用表的更新,连接池,还有单事务的处理,多事务的处理等
//其实前面的操作就是一堆通用的操作,对于数据库而言是操作数据的DAO(data access object)我们可以将之封装起来
//然后被特别的表的操作类继承,以备各种表来使用这些方法,同时,对于这些方法而言,其实有点泛了,即并不是那么浅显,我们可以让
//该表的操作类去实现一个针对该表的一个规范接口,该接口可以规范DAO的同时,还可以针对特定表规定一些操作,以备该操作类去实现
public class EmpIMPTest {
    public static void main(String[] args) {
        EmpIMP empIMP = new EmpIMP();
//        插入
//        empIMP.insertNewEmp(2,"zheng",null,3,new java.sql.Date(System.currentTimeMillis()),20000,null,8);
//        按编号删除
//        empIMP.dismissEmpNoEmp(2);
//        按名字删除
//        empIMP.dismissNameEmp("zheng");
//        按部门编号查询员工列表
//        LinkedList<Emp> emps = empIMP.queryDeptNoList(30);
//        emps.forEach(System.out::println);
//        按工作查询员工列表
//        LinkedList<Emp> emps = empIMP.queryJobList("clerk");
//        emps.forEach(System.out::println);
//        查询最早入职的员工信息
//        Date date = empIMP.queryEarliestEntryDate();
//        System.out.println(date);
//        查询最高工资
//        double v = empIMP.queryMaximumWage();
//        System.out.println(v);
//        按编号更新信息
//        empIMP.upDataEmpNoEmp(2,"kangQin","boss",8888,new java.sql.Date(System.currentTimeMillis()),49999,39999,1);

//      单事务型
        Connection connection = null;
        try {
           connection = EmpIMP.optimizeGetConnection();
           connection.setAutoCommit(false);
            String sql = "insert into emp(empNo,eName,job,MGR,hireDate,sal,comm,deptNo) values(?,?,?,?,?,?,?,?); ";
            empIMP.updateCommonTransaction(connection,sql,3,"kang","client",4,new java.sql.Date(System.currentTimeMillis()),9,8,3);
            empIMP.dismissEmpNoEmp(1);
            connection.commit();
        } catch (Exception e) {
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
            EmpIMP.closeResource(connection,null,null);
        }
    }
}
