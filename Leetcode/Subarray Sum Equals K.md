#### Leetcode 560

Given an array of integers and an integer **k**, you need to find the total number of continuous subarrays whose sum equals to **k**.

**Example 1:**

```
Input:nums = [1,1,1], k = 2
Output: 2
```

**Note:**

1. The length of the array is in range [1, 20,000].
2. The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].

Solution 1:  
Brute Force

```java
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        for(int i = 0; i < nums.length; i++) {
            int sum = 0;
            for(int j = i; j < nums.length; j++) {
                sum += nums[j];
                if(sum == k) {
                    count++;
                }
            }
        }
        return count;
    }
```

Solution 2:  
Hashmap
我们可以容易得知，一个数组中从0到i的元素和为a，从0到j的元素和为b，若b-a=k则从i到j之间的元素和为k，所以我们维护一个hashmap，里面存放了从0开始到数组各个元素的和以及这些和出现的个数，每一次更新map之前，我们都会找这个map里有没有k-curSum元素，如果有，获取这个元素出现的个数加到count里，最终返回count

```java
     public int subarraySum(int[] nums, int k) {
        int count = 0;
        int sum = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if(map.containsKey(sum - k)) {
                count += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
```

注：该题目不能用slidewindow，因为slidewindow方法是在当前所有元素和小于k时扩大window，当前所有元素和大于k时缩小window。在数组中所有元素为正的情况下这个方法可行，但是如果数组中存在负值的话，遇到负值的时候元素和减小但是不一定需要缩小window的长度，例如在论坛里有人给出了如下例子：当数组遇到-70的时候会缩小window的长度，但是结果应该是一整个数组的和

```
[28,54,7,-70,22,65,-6] k = 100
```