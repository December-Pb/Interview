####  Leetcode 201

Given a range [m, n] where 0 <= m <= n <= 2147483647, return the bitwise AND of all numbers in this range, inclusive.

**Example 1:**

```
Input: [5,7]
Output: 4
```

**Example 2:**

```
Input: [0,1]
Output: 0
```

Solution: 此题想到的最简单的办法就是用一个mask（全部位是1的一个数字）与所有m到n的数字按位与得出结果，但是一个问题是mask会有多出来的1没法处理，继续观察发现此题想要求m到n所有数字左边最大公共数字  
eg：5 = 101； 6 = 110； 7 = 111，他们最左边的公共数字是1，其他位用0占位，所以5 ^ 6 ^ 7 = 4 (100)  
所以每次将掩码左移一位直到找到m和n和掩码三者的公共数字

```java
class Solution {
    public int rangeBitwiseAnd(int m, int n) {
        int result = Integer.MAX_VALUE;
        while((m & result) != (n & result)) { //此处&的优先级低于比较符号，所以要用()
            result <<= 1;
        }
        return m & result;
    }
}
```