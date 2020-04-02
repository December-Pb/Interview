#### Leetcode 1047

Given a string S of lowercase letters, a duplicate removal consists of choosing two adjacent and equal letters, and removing them.

We repeatedly make duplicate removals on S until we no longer can.

Return the final string after all such duplicate removals have been made.  It is guaranteed the answer is unique.

 

Example 1:

```
Input: "abbaca"
Output: "ca"
Explanation: 
For example, in "abbaca" we could remove "bb" since the letters are adjacent and equal, and this is the only possible move.  The result of this move is that the string is "aaca", of which only "aa" is possible, so the final string is "ca".
```
 

Note:

1. `1 <= S.length <= 20000`
2. `S` consists only of English lowercase letters.

Solution 1:  
replace方法，建立一个table存储如下元素`{"aa", "bb", "cc" ...}`，然后将字符串中所有的重复元素全部替换成`""(空串)`

```java
    public String removeDuplicates(String S) {
    // generate 26 possible duplicates
    HashSet<String> duplicates = new HashSet();
    StringBuilder sb = new StringBuilder();
    for(char i = 'a'; i <= 'z'; ++i) {
      sb.setLength(0);
      sb.append(i); sb.append(i);
      duplicates.add(sb.toString());
    }

    int prevLength = -1;
    while (prevLength != S.length()) {
      prevLength = S.length();
      for (String d : duplicates) S = S.replace(d, "");
    }

    return S;
  }
```

Solution 2:  
用stack：将元素依次压栈，压栈之前如果与栈顶元素相同则删除两个元素

```java
    public String removeDuplicates(String S) {
        Stack<Character> s = new Stack<>();
        s.push(S.charAt(0));
        String result = "";
        for(int i = 1; i < S.length(); i++) {
            if(!s.isEmpty() && S.charAt(i) == s.peek()) {
                s.pop();
            }
            else {
                s.push(S.charAt(i));
            }
        }
        while(!s.isEmpty()) {
            result = Character.toString(s.pop()) + result;
        }
        return result;
    }
```