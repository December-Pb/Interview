#### Leetcode 299

You are playing the following `Bulls and Cows` game with your friend: You write down a number and ask your friend to guess what the number is. Each time your friend makes a guess, you provide a hint that indicates how many digits in said guess match your secret number exactly in both digit and position (called "bulls") and how many digits match the secret number but locate in the wrong position (called "cows"). Your friend will use successive guesses and hints to eventually derive the secret number.

Write a function to return a hint according to the secret number and friend's guess, use `A` to indicate the bulls and `B` to indicate the cows.

Please note that both secret number and friend's guess may contain duplicate digits.

Example 1:

```md
Input: secret = "1807", guess = "7810"

Output: "1A3B"

Explanation: 1 bull and 3 cows. The bull is 8, the cows are 0, 1 and 7.
```

Example 2:

```md
Input: secret = "1123", guess = "0111"

Output: "1A1B"

Explanation: The 1st 1 in friend's guess is a bull, the 2nd or 3rd 1 is a cow.
```

Note: You may assume that the secret number and your friend's guess only contain digits, and their lengths are always equal.

Solution 1: one pass:
维护一个数组存放10个数字，如果secret中数字出现则相应的数组元素-1，如果guess中数字出现则相应的数组元素+1，当判断guess中是否出现secret数字时仅需判断table数组中相应的数字是否<0,反之亦然，最后可以得到bull和cow。

```java
    public String getHint(String secret, String guess) {
        int cow = 0;
        int bull = 0;
        if(secret.length() == 0) {
            return "0A0B";
        }
        int[] table = new int[10];
        for(int i = 0; i < secret.length(); i++) {
            if(secret.charAt(i) == guess.charAt(i)) {
                bull++;
            }
            else {
                if(table[secret.charAt(i) - '0'] > 0) {
                    cow++;
                }
                if(table[guess.charAt(i) - '0'] < 0) {
                    cow++;
                }
                table[secret.charAt(i) - '0']--;
                table[guess.charAt(i) - '0']++;
            }
        }
        return String.valueOf(bull) + "A" + String.valueOf(cow) + "B";
    }
```