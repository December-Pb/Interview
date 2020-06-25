#### Leetcode 365

You are given two jugs with capacities x and y litres. There is an infinite amount of water supply available. You need to determine whether it is possible to measure exactly z litres using these two jugs.

If z liters of water is measurable, you must have z liters of water contained within **one or both buckets** by the end.

Operations allowed:

Fill any of the jugs completely with water.
Empty any of the jugs.
Pour water from one jug into another till the other jug is completely full or the first jug itself is empty.
**Example 1**: (From the famous "Die Hard" example)
```
Input: x = 3, y = 5, z = 4
Output: True
```
**Example 2:**
```
Input: x = 2, y = 6, z = 5
Output: False
```

Solution:
这道问题其实可以转换为有一个很大的容器，我们有两个杯子，容量分别为x和y，问我们通过用两个杯子往里倒水，和往出舀水，问能不能使容器中的水刚好为z升。那么我们可以用一个公式来表达： z = m * x + n * y,由[裴蜀定理](https://zh.wikipedia.org/wiki/%E8%B2%9D%E7%A5%96%E7%AD%89%E5%BC%8F)可知，此方程有整数解当且仅当z是m及n的最大公约数d的倍数,我们可以用[辗转相除](https://zh.wikipedia.org/wiki/%E8%BC%BE%E8%BD%89%E7%9B%B8%E9%99%A4%E6%B3%95)的算法求gcd(a, b).

```java
class Solution {
    public boolean canMeasureWater(int x, int y, int z) {
        if(z == 0) {
            return true;
        }
        return x + y >= z && z % gcd(x, y) == 0;
    }
    
    private int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }
}
```