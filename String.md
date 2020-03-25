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
