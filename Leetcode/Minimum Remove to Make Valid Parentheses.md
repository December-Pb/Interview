#### Leetcode 1249

Given a string `s` of `'('` , `')'` and lowercase English characters. 

Your task is to remove the minimum number of parentheses ( `'('` or `')'`, in any positions ) so that the resulting *parentheses* string is valid and return **any** valid string.

Formally, a parentheses string is valid if and only if:

- It is the empty string, contains only lowercase characters, or
- It can be written as `AB` (`A` concatenated with `B`), where `A` and `B` are valid strings, or
- It can be written as `(A)`, where `A` is a valid string.

**Example 1:**
```
Input: s = "lee(t(c)o)de)"
Output: "lee(t(c)o)de"
Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.
```
**Example 2:**
```
Input: s = "a)b(c)d"
Output: "ab(c)d"
```
**Example 3:**
```
Input: s = "))(("
Output: ""
Explanation: An empty string is also valid.
```
**Example 4:**
```
Input: s = "(a(b(c)d)"
Output: "a(b(c)d)"
```
 

Constraints:

- 1 <= s.length <= 10^5
- s[i] is one of  '(' , ')' and lowercase English letters.

Solution:此题和一般的“括号题”相似，可以用一个stack记录左括号，每当遇到一个右括号就删除一个左括号，如果遇到右括号时stack里没有左括号则将右括号的坐标加入一个removeSet里，当遍历完所有字符串后发现stack还剩余左括号时将所有剩余的左括号加入removeSet里，最后用stringbuilder筛去所有removeSet里的坐标

```java
class Solution {
    public String minRemoveToMakeValid(String s) {
        LinkedList<Integer> stack = new LinkedList<>();
        Set<Integer> removeSet = new HashSet<>();
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(') {
                stack.addFirst(i);
            }
            else if(s.charAt(i) == ')' && stack.isEmpty()) {
                removeSet.add(i);
            }
            else if(s.charAt(i) == ')' && !stack.isEmpty()){
                stack.removeFirst();
            }
        }
        while(!stack.isEmpty()) {
            removeSet.add(stack.removeFirst());
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            if(!removeSet.contains(i)) {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
}
```