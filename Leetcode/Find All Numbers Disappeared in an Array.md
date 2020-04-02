#### Leetcode 448

Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.

Find all the elements of [1, n] inclusive that do not appear in this array.

Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.

Example:

```
Input:
[4,3,2,7,8,2,3,1]

Output:
[5,6]
```

Solution 1:
这道题需要用one pass和in place方法解题，可以通过改变数组元素的正负判断数组元素所在位置的元素是否出现过。  
例如[1,2,3,3]通过改变数组中元素的正负后得到[-1,-2,-3,3]，可以看到第四个元素正负值没有改变，说明4元素没有出现在数组中。

```java 
    public List<Integer> findDisappearedNumbers(int[] nums) {
        for(int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]) - 1;
            nums[index] = nums[index] > 0 ? -nums[index] : nums[index];
        }
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] > 0) {
                result.add(i + 1);
            }
        }
        return result;
    }
```