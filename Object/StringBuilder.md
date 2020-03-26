# StringBuilder相关操作

1. 由于Java中的String是final修饰的，所以本身不能被更改，这个时候需要将String转换成StringBuilder再更改StringBuilder的内容，最后调用toString方法转换回String

```java
    String str = "Test";
    StringBuilder sb = new StringBuilder(str);
    sb.setCharAt(0, "t");
    sb.reverse(); //倒置字符串
```