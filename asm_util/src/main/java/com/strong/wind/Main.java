package com.strong.wind;


public class Main {
    public static void main(String[] args) throws Exception {
        Source source = new Source();
        source.setName("John");
        source.setAge(30);

        Target target = ObjectConverter.create(Source.class, Target.class)
                .field("name", "name")
                .field("age", "age")
                .convertNullFields(false)
                .convert(source);

        System.out.println("name============="+target.getName()); // 输出: John
        System.out.println("age=============="+target.getAge());  // 输出: 30
    }
}

 class Source {
    private String name;
    private int age;

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public int getAge() {
         return age;
     }

     public void setAge(int age) {
         this.age = age;
     }
 }
class Target {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}