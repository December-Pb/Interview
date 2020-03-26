#### Leetcode 28

Implement strStr().

Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.

##### Example 1

```java
Input: haystack = "hello", needle = "ll"
Output: 2
```

##### Example 2

```java
Input: haystack = "aaaaa", needle = "bba"
Output: -1
```

Clarification:
What should we return when needle is an empty string? This is a great question to ask during an interview.

For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf()

- Solution 1:
    循环调用string.substring(startIndex, needle.length).equals(needle)方法得到结果，复杂度为$O((N-L)L)$

- Solution 2:
    Solution 1中遍历每个字符都要调用一次substring方法，Solution 2中我们可以仅当遍历到needle的首字母的时候调用substring方法，该算法最优的time complexity是$O(n)$

- Solution 3:
    以上两种方法的最坏情况时间复杂度都是$O((N-L)L)$，现在设计一个时间复杂度稳定在$O(n)$的算法：Rabin Karp算法。
    该算法的核心思想是保证one pass，每一次计算substring的hash值，一次向前移动一个字符长度，直到匹配成功。
    这里用到了rolling hash的思想。
  1. 将一个字符串变成一个数字的过程可以利用26进制转换为十进制(假设全部为小写字母)：
  - e.g: [a,b,c,d]--->$$h_{0} = 0 \times 26^{3} + 1 \times 26^{2} + 2 \times 26^{1} + 3 \times 26^{0}$$
    In general,
    $$h_{0} = \sum_{i=0}^{L-1}c_{i}a^{L-1-i}$$
  2. 滚动计算hash:
  - e.g: [a,b,c,d]--->[b,c,d,e]:
  $$h_{1} = (h_{0} - 0 \times 26^{3}) \times 26 + 4 \times 26^{0}$$
    In general,
  $$h_{1} = (h_{0}a - c_{0}a^{L})+ c_{L+1}$$
  3. 如何避免溢出：
  可以将h值取模，$$h \mod 2^{31}$$

  ```java
    public int strStr(String haystack, String needle) {
        if(needle.length() > haystack.length()) {
            return -1;
        }
        long moduler = (long)Math.pow(2, 31);
        long hayStackHash = 0;
        long needleHash = 0;
        int a = 26;
        for(int i = 0; i < needle.length(); i++) {
            hayStackHash = (hayStackHash * a + (haystack.charAt(i) - 'a')) % moduler;
            needleHash = (needleHash * a + (needle.charAt(i) - 'a')) % moduler;
        }
        if(hayStackHash == needleHash) {
            return 0;
        }
        long al = 1;
        for(int i = 1; i <= needle.length(); i++) {
            al = (al * a) % moduler;
        }
        for(int i = 1; i < haystack.length() - needle.length() + 1; i++) {
            hayStackHash = (hayStackHash * a - (haystack.charAt(i - 1) - 'a') * al + haystack.charAt(i + needle.length() - 1) - 'a') % moduler;
            // 此处减去的是上一个字母的hash值，所以是haystack.charAt(i - 1) - 'a'
            if(hayStackHash == needleHash) {
                return i;
            }
        }
        return -1;
    }
  ```