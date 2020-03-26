# Object的相关方法

## equals()  

- equals是Java Object类自带的方法，默认的equals方法等同于==，是通过比较两个对象地址来判断他们是否相等。我们可以通过复写equals来自定义此方法。

## hashcode()  

- hashcode用于hash table类容器存储对象，在设计hashcode方法时应该遵循以下准则：
  - 在equals方法中用到的信息没有被改变的前提下，无论何时在同一个程序中对同一对象调用此方法，都应该返回同样的hashcode整数值（不同的程序中可以返回不同的hashcode
  - 如果两个对象调用equals方法返回true，则这两个对象应该有同样的hashvalue
  - 如果两个对象调用equals方法返回false，则这两个对象不一定拥有不同的hashcode（哈希碰撞），但是开发者应该尽量保持此时的两个对象拥有不同的hashcode以增加程序效率。
  - hashcode和equals的联系：当一个方法仅重写equals时，可以深度比较两个对象是否相等，但是不能保证插入hash容器中的唯一性。例如

    ```java
    Person p1 = new Person("Tom", "16");
    Person p2 = new Person("Tom", "16");
    ```

    此时调用`p1.equals(p2)`得到的是true，但是向HashSet中同时添加p1和p2后此HashSet的size是2。但是在重写hashcode方法后，同时向HashSet添加p1和p2后此HashSet的size是1。----`CRD面试`
