package xiu.neusoft.com.base.test;

public class TestEntity {
    private String name = "http://pic.rmb.bdstatic.com/f54083119edfb83c4cfe9ce2eeebc076.jpeg";
    private int age = 10;

    public TestEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
