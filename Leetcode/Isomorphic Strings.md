#### Leetcode 205

Given two strings s and t, determine if they are isomorphic.

Two strings are isomorphic if the characters in s can be replaced to get t.

All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character but a character may map to itself.

Example 1:

```
Input: s = "egg", t = "add"
Output: true
```

Example 2:

```
Input: s = "foo", t = "bar"
Output: false
```

Example 3:

```
Input: s = "paper", t = "title"
Output: true
```

Note:
You may assume both s and t have the same length.

本题的策略是找到一个数字代表字母并且保证对于s和t出现在同一位置的字母应该有同一个数字代替他们，所以可以想到用字母在字符串中出现的位置代表他们，不断地更新hashmap知道字符串结尾

- Solution 1:

```java
    public boolean isIsomorphic(String s, String t) {
        if(s.length() == 0 && t.length() == 0) {
            return true;
        }
        HashMap<Character, Integer> sMap = new HashMap<>();
        HashMap<Character, Integer> tMap = new HashMap<>();
        for(int i = 0; i < s.length(); i++) {
            int sIndex = sMap.getOrDefault(s.charAt(i), -1);
            int tIndex = tMap.getOrDefault(t.charAt(i), -1);
            if(sIndex != tIndex) {
                return false;
            }
            sMap.put(s.charAt(i), i);
            tMap.put(t.charAt(i), i);
        }
        return true;
    }
```

- Solution 2:
假设出现的字符仅为ASCII编码，那么我们可以用两个数组代替两个hashmap以提高效率:

```java
    public boolean isIsomorphic(String sString, String tString) {

        char[] s = sString.toCharArray();
        char[] t = tString.toCharArray();

        int length = s.length;
        if(length != t.length) return false;

        char[] sm = new char[256];
        char[] tm = new char[256];

        for(int i=0; i<length; i++){
            char sc = s[i];
            char tc = t[i];
            if(sm[sc] == 0 && tm[tc] == 0){
                sm[sc] = tc;
                tm[tc] = sc;
            }else{
                if(sm[sc] != tc || tm[tc] != sc){
                    return false;
                }
            }
        }
        return true;
    }
```
