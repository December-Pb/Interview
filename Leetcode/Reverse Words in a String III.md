#### Leetcode 557

Given a string, you need to reverse the order of characters in each word within a sentence while still preserving whitespace and initial word order.

Example 1:
```
Input: "Let's take LeetCode contest"
Output: "s'teL ekat edoCteeL tsetnoc"
```

Note: In the string, each word is separated by single space and there will not be any extra space in the string.

1. Solution 1:利用split和StringBuilder解题

2. Follow Up: 不用内置方法解题：
    - 将String转换成StringBuilder，遍历StringBuilder，遇到空格的时候取出这段StringBuilder，得到一个数组
    - 自定义的reverse方法中每次都像StringBuilder的首位插入字符即可实现倒叙，代码如下：

    ```java
        public String reverse(String s) {
            StringBuilder res=new StringBuilder();
            for (int i = 0; i < s.length(); i++)
                res.insert(0,s.charAt(i));
            return res.toString();
        }
    ```
