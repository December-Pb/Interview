### Leetcode 678

Given a string containing only three types of characters: '(', ')' and '*', write a function to check whether this string is valid. We define the validity of a string by these rules:

1. Any left parenthesis '(' must have a corresponding right parenthesis ')'.
2. Any right parenthesis ')' must have a corresponding left parenthesis '('.
3. Left parenthesis '(' must go before the corresponding right parenthesis ')'.
4. '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string.
5. An empty string is also valid.

**Example 1:**
```
Input: "()"
Output: True
```

**Example 2:**
```
Input: "(*)"
Output: True
```

**Example 3:**
```
Input: "(*))"
Output: True
```

**Note:**
1. The string size will be in the range [1, 100].

这道题可以用两个栈来完成，一个栈记录"\*"，一个栈记录"("，和传统的括号匹配不同，这里的栈还要记录*和(出现的先后顺序，所以栈中记录的是两个符号在字符串中出现的位置。

```java
class Solution {
    public boolean checkValidString(String s) {
        LinkedList<Integer> starStack = new LinkedList<>();
        LinkedList<Integer> paraStack = new LinkedList<>();
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '*') {
                starStack.addFirst(i);
            }
            if(s.charAt(i) == '(') {
                paraStack.addFirst(i);
            }
            if(s.charAt(i) == ')') {
                if(paraStack.isEmpty() && starStack.isEmpty()) {
                    return false;
                }
                else if(!paraStack.isEmpty()) {
                    paraStack.removeFirst();
                }
                else {
                    starStack.removeFirst();
                }
            }
        }
        while(!starStack.isEmpty() && !paraStack.isEmpty()) {
            int sInt = starStack.removeFirst();
            int pInt = paraStack.removeFirst();
            if(sInt < pInt) {
                return false;
            }
        }
        return paraStack.isEmpty();
    }
}
```

除此之外，我们还有一种更加简便的方法。考虑到如果没有星号的时候，我们可以用一个计数器来记录左右括号的平衡性，出现一个"("计数器加一，出现一个")"计数器减一，如果计数器小于零返回false，在循环最后计数器不等于0也返回false;  
回到本题，一个"\*"可以代表"("和")",这时我们可以用两个计数器，一个计数器记录现在有的最多的"(",一个计数器可以记录现在可以有的最少的"("。当最多的"("小于零的时候直接返回false，当循环结束时最少的"("不等于0时直接返回false。其中最多的计数器代表所有"\*"都算作"("时"("的个数，最少的计数器代表所有"\*"都算作")"时"("的个数

```java
class Solution {
    public boolean checkValidString(String s) {
        int low = 0;
        int high = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(') {
                low++;
                high++;
            }
            else if(s.charAt(i) == ')') {
                low--;
                high--;
            }
            else {
                low--;
                high++;
            }
            low = Math.max(low, 0);
            if(high < 0) {
                return false;
            }
        }
        return low == 0;
    }
}
```