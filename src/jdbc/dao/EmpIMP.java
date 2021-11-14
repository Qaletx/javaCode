package jdbc.dao;

import jdbc.preparedStatement.classtest.Emp;

import java.sql.Connection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

//IMP为(interface message process)接口信息处理,即处理接口的实现类
public class EmpIMP extends DAO<Emp> implements EmpDAO {
    @Override
    public boolean insertNewEmp(Object... objects) {
        EmpIMP empIMP = new EmpIMP();
        String sql = "insert into emp(empNo,eName,job,MGR,hireDate,sal,comm,deptNo) values(?,?,?,?,?,?,?,?); ";
        long l = empIMP.updateCommon(sql, objects);
        if(l != 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean insertNewEmpTransaction(Connection connection, Object... objects) {
        EmpIMP empIMP = new EmpIMP();
        String sql = "insert into emp(empNo,eName,job,MGR,hireDate,sal,comm,deptNo) values(?,?,?,?,?,?,?,?); ";
        long l = empIMP.updateCommonTransaction(connection, sql, objects);
        if(l != 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean dismissEmpNoEmp(int empNo) {
        EmpIMP empIMP = new EmpIMP();
        String sql  = "delete from emp where empno = ?";
        long l = empIMP.updateCommon(sql, empNo);
        if (l != 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean dismissEmpNoEmpTransaction(Connection connection, int empNo) {
        EmpIMP empIMP = new EmpIMP();
        String sql  = "delete from emp where empno = ?";
        long l = empIMP.updateCommonTransaction(connection, sql, empNo);
        if (l != 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean upDataEmpNoEmp(int empNo, Object... objects) {
        EmpIMP empIMP = new EmpIMP();
        String sql ="update emp set eName = ? ,job = ? ,MGR = ? ,hireDate = ?,sal = ?, comm = ?,deptNo = ?  where empno = "+empNo;
        long l = empIMP.updateCommon(sql, objects);
        if(l != 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean upDataEmpNoEmpTransaction(Connection connection, int empNo, Object... objects) {
        EmpIMP empIMP = new EmpIMP();
        String sql ="update emp set eName = ? ,job = ? ,MGR = ? ,hireDate = ?,sal = ?, comm = ?,deptNo = ?  where empno = "+empNo;
        long l = empIMP.updateCommonTransaction(connection, sql, objects);
        if(l != 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean dismissNameEmp(String name) {
        EmpIMP empIMP = new EmpIMP();
        String sql  = "delete from emp where ename = ?";
        long l = empIMP.updateCommon(sql, name);
        if (l != 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean dismissNameEmpTransaction(Connection connection, String name) {
        EmpIMP empIMP = new EmpIMP();
        String sql  = "delete from emp where ename = ?";
        long l = empIMP.updateCommonTransaction(connection, sql, name);
        if (l != 0){
            return true;
        }
        return false;
    }

    @Override
    public LinkedList<Emp> queryJobList(String job) {
        EmpIMP empIMP = new EmpIMP();
        String sql = "select empno empNo, ename eName, job ,mgr MGR,hiredate hireDate,sal," +
                "comm ,deptno deptNo from emp where job = ?";
        LinkedList<Emp> emps = empIMP.queryCommon(sql, job);
        return emps;


    }

    @Override
    public LinkedList<Emp> queryJobListTransaction(Connection connection, String job) {
        EmpIMP empIMP = new EmpIMP();
        String sql = "select empno empNo, ename eName, job ,mgr MGR,hiredate hireDate,sal," +
                "comm ,deptno deptNo from emp where job = ?";
        LinkedList<Emp> emps = empIMP.queryCommonTransaction(connection,sql,job);
        return emps;
    }

    @Override
    public Date queryEarliestEntryDate() {
        EmpIMP empIMP = new EmpIMP();
        String sql  = "select min(hireDate) from emp";
        Date date = empIMP.selectSole(sql);
        return date;
    }

    @Override
    public Date queryEarliestEntryDateTransaction(Connection connection) {
        EmpIMP empIMP = new EmpIMP();
        String sql  = "select min(hireDate) from emp";
        Date date = empIMP.selectSoleTransaction(connection,sql);
        return date;
    }

    @Override
    public double queryMaximumWage() {
        EmpIMP empIMP = new EmpIMP();
        String sql  = "select max(sal) from emp";
        double aDouble = empIMP.selectSole(sql);
        if(Double.compare(aDouble,0)!=0){
            return aDouble;
        }
        return -1;
    }

    @Override
    public double queryMaximumWageTransaction(Connection connection) {
        EmpIMP empIMP = new EmpIMP();
        String sql  = "select max(sal) from emp";
        double aDouble = empIMP.selectSoleTransaction(connection,sql);
        if(Double.compare(aDouble,0)!=0){
            return aDouble;
        }
        return -1;
    }

    @Override
    public LinkedList<Emp> queryDeptNoList(int deptNo) {
        EmpIMP empIMP = new EmpIMP();
        String sql = "select empno empNo, ename eName, job ,mgr MGR,hiredate hireDate,sal," +
                "comm ,deptno deptNo from emp where deptno = ?";
        LinkedList<Emp> emps = empIMP.queryCommon(sql, deptNo);
        return emps;

    }

    @Override
    public LinkedList<Emp> queryDeptNoListTransaction(Connection connection,int deptNo) {
        EmpIMP empIMP = new EmpIMP();
        String sql = "select empno empNo, ename eName, job ,mgr MGR,hiredate hireDate,sal," +
                "comm ,deptno deptNo from emp where deptno = ?";
        LinkedList<Emp> emps = empIMP.queryCommonTransaction(connection,sql,deptNo);
        return emps;
    }
}
