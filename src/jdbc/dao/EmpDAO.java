package jdbc.dao;

import jdbc.preparedStatement.classtest.Emp;

import java.sql.Connection;
import java.util.Date;
import java.util.LinkedList;

//即针对EmpDAO的规范接口
public interface EmpDAO {

//    以下是要实现的功能,为特定表的一些操作,规范DAO供具体表对象类实现

//    增加一名新员工
    public abstract boolean insertNewEmp(Object...objects);
    public abstract boolean insertNewEmpTransaction(Connection connection,Object...objects);

//    按员工编号辞职一名员工
    public abstract boolean dismissEmpNoEmp(int empNo);
    public abstract boolean dismissEmpNoEmpTransaction(Connection connection,int empNo);
//    按员工编号更改信息
    public abstract boolean upDataEmpNoEmp(int empNo,Object...objects);
    public abstract boolean upDataEmpNoEmpTransaction(Connection connection,int empNo,Object...objects);
//    按员工名字辞职一名员工
    public abstract boolean dismissNameEmp(String name);
    public abstract boolean dismissNameEmpTransaction(Connection connection,String name);
//    查询对应工作岗位的员工列表
    public abstract LinkedList<Emp> queryJobList(String job);
    public abstract LinkedList<Emp> queryJobListTransaction(Connection connection,String job);
//    得到刚最早入职的员工
    public abstract Date queryEarliestEntryDate();
    public abstract Date queryEarliestEntryDateTransaction(Connection connection);
//    得到工资最高的员工信息
    public abstract double queryMaximumWage();
    public abstract double queryMaximumWageTransaction(Connection connection);
//    得到具体部门编号下的员工列表
    public abstract LinkedList<Emp> queryDeptNoList(int deptNo);
    public abstract LinkedList<Emp> queryDeptNoListTransaction(Connection connection,int deptNo);
}
