#### Leetcode 350

Given two arrays, write a function to compute their intersection.

Example 1:

```
Input: nums1 = [1,2,2,1], nums2 = [2,2]
Output: [2,2]
```

Example 2:

```
Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
Output: [4,9]
```

Note:

- Each element in the result should appear as many times as it shows in both arrays.
The result can be in any order.

Follow up:

- What if the given array is already sorted? How would you optimize your algorithm?
- What if nums1's size is small compared to nums2's size? Which algorithm is better?
- What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?

Solution 1:  
HashMap:   
Time Complexity: $O(n+m)$  
Space Complexity: $O(min(n,m))$


```java
public int[] intersect(int[] nums1, int[] nums2) {
    if (nums1.length > nums2.length) {
        return intersect(nums2, nums1);
    } //可以保证nums1的长度小于nums2
    HashMap<Integer, Integer> m = new HashMap<>();
    for (int n : nums1) {
        m.put(n, m.getOrDefault(n, 0) + 1);
    }
    int k = 0;
    for (int n : nums2) {
//inplace方法，将较短的数组替换成答案，最后截取答案部分
        int cnt = m.getOrDefault(n, 0);
        if (cnt > 0) {
            nums1[k++] = n;
            m.put(n, cnt - 1);
        }
    }
    return Arrays.copyOfRange(nums1, 0, k);
}
```

Solution 2:  
Sort:(双指针):  
Time Complexity: $O(n\log{n}+m\log{m})$  
Space Complexity: $O(1)$

```java
    public int[] intersect(int[] nums1, int[] nums2) {
    Arrays.sort(nums1);
    Arrays.sort(nums2);
    int i = 0, j = 0, k = 0;
    while (i < nums1.length && j < nums2.length) {
        if (nums1[i] < nums2[j]) {
            ++i;
        } else if (nums1[i] > nums2[j]) {
            ++j;
        } else {
            nums1[k++] = nums1[i++];
            ++j;
        }
    }
    return Arrays.copyOfRange(nums1, 0, k);
}
```

##### Follow-up Questions

1. 可以用Solution 2,这样Time complexity是$O(max(m,n))$ Space complexity是$O(1)$
2. Solution 1更好，因为我们用hashmap存储更小的array中的元素
3. - 如果nums1可以全部存入内存，我们用solution1将nums1放入hashmap，然后在处理nums2
   - 如果两个数组都存不了内存中，将两个数组分块存到内存