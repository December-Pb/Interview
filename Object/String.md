# String相关操作

## 注意事项

1. Java的split分割字符串的参数是一个正则表达式，而具有特殊含义的字符例如`* ^ ： | , .`需要进行转义，例如需要正则匹配`*`需要使用`//*`
2. replace, replaceAll和replaceFirst区别：

    ```java
    public String replace(char oldChar,char newChar)

    public String replaceAll(String regex,String replacement)

    public String replaceFirst(String regex,String replacement)
    ```

    - replace:  
    替换顺序有前向后，`"aaa".replace("aa", "b")`得到的是ab而不是ba
    - replaceAll:  
    可以替换某个字符，也可以按照正则表达式匹配，替换全部的目标字符
    - replaceFirst：
    可以替换某个字符，也可以按照正则表达式匹配，替换第一个出现的目标字符
3. 常用的String操作：  
    - equals：Java中的equals重写了Object中的equals功能，源码如下：

        ```java
        public boolean equals(Object anObject) {  
        if (this == anObject) {  
            return true;  
        }  
        if (anObject instanceof String) {  
            String anotherString = (String) anObject;  
            int n = value.length;  
            if (n == anotherString.value.length) {  
                char v1[] = value;  
                char v2[] = anotherString.value;  
                int i = 0;  
                while (n-- != 0) {  
                    if (v1[i] != v2[i])  
                            return false;  
                    i++;  
                }  
                return true;  
            }  
        }  
        return false;
        }
        ```

4. 因为String是一个final类，所以不能被继承，在声明对象后该对象不能被更改，任何改变String变量的操作本质上都是开辟新的内存空间保存更改后的变量
5. String的trim方法：可以去掉字符串两端的空格，从下面的源码中我们可以看出，trim其实是去掉了所有ASCII码小于“空格”的的特殊字符

    ```java
        public String trim() {
            int len = value.length;
            int st = 0;
            char[] val = value;    /* avoid getfield opcode */

            while ((st < len) && (val[st] <= ' ')) {
                st++;
            }
            while ((st < len) && (val[len - 1] <= ' ')) {
                len--;
            }
            return ((st > 0) || (len < value.length)) ? substring(st, len) : this;
        }
    ```
