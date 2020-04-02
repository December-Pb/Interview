#### Leetcode 703

Design a class to find the kth largest element in a stream. Note that it is the kth largest element in the sorted order, not the kth distinct element.

Your `KthLargest` class will have a constructor which accepts an integer `k` and an integer array `nums`, which contains initial elements from the stream. For each call to the method `KthLargest.add`, return the element representing the kth largest element in the stream.

Example:

```java
int k = 3;
int[] arr = [4,5,8,2];
KthLargest kthLargest = new KthLargest(3, arr);
kthLargest.add(3);   // returns 4
kthLargest.add(5);   // returns 5
kthLargest.add(10);  // returns 5
kthLargest.add(9);   // returns 8
kthLargest.add(4);   // returns 8
```

- Note:
You may assume that `nums`' length �� `k-1` and `k` �� 1.

Solution 1 (TLE)Solution 1 (TLE):维护一个数组和一个priorityqueue，每次都将k-1个元素从队列中弹出，peek第k个元素后再将k-1个元素插回priorityqueue中。


```java
class KthLargest {
    
    List<Integer> bucket;
    PriorityQueue<Integer> pq;
    int k = 0;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        bucket = new ArrayList<>();
        pq = new PriorityQueue<>(new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        });
        for(int i = 0; i < nums.length; i++) {
            pq.offer(nums[i]);
        }
    }
    
    public int add(int val) {
        int result = 0;
        pq.offer(val);
        for(int i = 0; i < k - 1; i++) {
            bucket.add(pq.poll());
        }
        result = pq.peek();
        for(int i = bucket.size() - 1; i >= 0; i--) {
            pq.offer(bucket.remove(i));
        }
        return result;
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */
```

Solution 2:  
我们可以只维护一个长度为k的priorityqueue，当插入元素后priorityqueue的size大于k时，弹出元素，返回peek值

```java
class KthLargest {
    PriorityQueue<Integer> pq;
    int k = 0;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        pq = new PriorityQueue<>();
        for(int num : nums) {
            add(num);
        }
    }
    
    public int add(int val) {
        pq.offer(val);
        if(pq.size() > k) {
            pq.poll();
        }
        return pq.peek(); //此处的peek可以直接得到队首元素，也就是第k个大的元素
    }
}
```