package jdbc.preparedStatement.classtest;

import java.util.Date;

/*
 * ORM编程思想  （object relational mapping）
 * 一个数据表对应一个java类
 * 表中的一条记录对应java类的一个对象
 * 表中的一个字段对应java类的一个属性
 *
 */
public class Emp {
    private int empNo;
    private String eName;
    private String job;
    private int MGR;
    private Date hireDate;
    private double sal;
    private double comm;
    private int deptNo;

    public Emp() {

    }

    public Emp(int empNo, String eName, String job, int MGR, Date hireDate, double sal, double comm, int deptNo) {
        this.empNo = empNo;
        this.eName = eName;
        this.job = job;
        this.MGR = MGR;
        this.hireDate = hireDate;
        this.sal = sal;
        this.comm = comm;
        this.deptNo = deptNo;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getMGR() {
        return MGR;
    }

    public void setMGR(int MGR) {
        this.MGR = MGR;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    public double getComm() {
        return comm;
    }

    public void setComm(double comm) {
        this.comm = comm;
    }

    public int getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(int deptNo) {
        this.deptNo = deptNo;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "empNo=" + empNo +
                ", eName='" + eName + '\'' +
                ", job='" + job + '\'' +
                ", MGR=" + MGR +
                ", hireDate=" + hireDate +
                ", sal=" + sal +
                ", comm=" + comm +
                ", deptNo=" + deptNo +
                '}';
    }
}
