package io;

import java.io.Serializable;

//因为一个类对象被切成很多字节，然后标上序列，所以叫序列化，注意要序列化的类必须实现这个接口，而该接口只是给编译器看的，没有任何方法和常数
//如果没有指定序列化版本号每次编译后，jvm会随机给你一个独一无二的序列化版本号，此为jvm给你添加的属性，而不是继承而来(很显然也不是)
//很显然序列化这存在是防止你又新建一个属性
//自我认为，序列化和反序列化就是将本地类的对向进行文件存，然后需要的时候就拿出来拷贝
public class StudentObjectIOTest implements Serializable {
//    只要实现接口默认得属性都是参与到序列化得，除了加上transient修饰，则会在new新的对象时不会拷贝值
//    序列化之与属性有关，虽然方法也会参与序列化，但方法不能加transient
    public int no;
    private transient  String name;
//    序列化版本号只要写上一个在项目中独一无二的就行，目前理解是文件中的对象有该值，而在new对象时会和该值比较(目前认为本地需要有该类)
    private static final long serialVersionUID = 1L;
    public StudentObjectIOTest(){

    }
    public StudentObjectIOTest(int no,String name){
        this.no = no;
        this.name = name;
    }

    public  int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StudentObjectIOTest{" +
                "no=" + no +
                ", name='" + name + '\''+
                '}';
    }

    public void doSome(){
        System.out.println("我是认真的");
    }
}
