# 二分法(binary search)

## 基本二分法写法

```java
// 非递归写法，
    public int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while(left <= right) {
            int mid = left + (right - left) / 2;
            if(nums[mid] == target) {
                return mid;
            }
            else if(nums[mid] > target) {
                right = mid - 1;
            }
            else if(nums[mid] < target) {
                left = mid + 1;
            }
        }
        return -1;
    }
    //递归写法
    public int binarySearchRecur(int[] nums, int target, int left, int right) {
        if(left > right) {
            return left;
        }
        int mid = left + (right - left) / 2;
        if(nums[mid] > target) {
            return binarySearchRecur(nums, target, left, mid - 1);
        }
        else if(nums[mid] < target) {
            return binarySearchRecur(nums, target, mid + 1, right);
        }
        else {
            return mid;
        }
    }
```

以上两个版本是数组中没有重复元素时的写法，其中有几个注意事项：

 1. `int mid = left + (right - left) / 2;`是一个向下元整的操作，`5 / 2 = 2`

 2. while循环体中是<=还是<？  
    如果是<的话，证明终止的区间形式是[left, right] (left == right),例如一个数组的元素为：[1,2,3,4,5],
    target元素为2，则循环过程如下：  

    |left|right|middle|
    |---|---|---|
    |0|4|2|
    |0|1|0|
    |1|1|1|

    此时left == right跳出循环，返回值为-1，但是真实值应该为1，所以如果
    没有=的话，要在最后返回值的时候判断区间是否为空  
    `return nums[left] == target ? left : -1;`
    相比之下，如果是<=的话，则在循环终止时的区间为[left, right] (left > right)，此时区间里不可能有任何元素，直接返回-1即可。  

 3. 以上代码只适合于数组中没有重复元素出现的情况，如果数组中有重复元素，返回的索引可能是重复元素中任意一个值的索引，而此时我们可能想要重复值中最右端或者最左端的值的索引。

## 有重复元素的二分法

```java
// 找到从左到右第一个目标元素
public int binarySearchFindLeftEle(int[] nums, int target) {
    if(nums.length == 0) {
        return -1;
    }
    int left = 0;
    int right = nums.length - 1;
    while(left <= right) {
        int mid = left + (right - left) / 2;
        if(nums[mid] == target) {
            right = mid - 1;
        }
        else if(nums[mid] < target) {
            left = mid + 1;
        }
        else if(nums[mid] > target) {
            right = mid - 1;
        }
    }
    return left;
}
```

```java
// 找到从右到左第一个目标元素,需要更改
public int binarySearchFindLeftEle(int[] nums, int target) {
    if(nums.length == 0) {
        return -1;
    }
    int left = 0;
    int right = nums.length;
    while(left < right) {
        int mid = left + (right - left) / 2;
        if(nums[mid] == target) {
            left = mid + 1;
        }
        else if(nums[mid] < target) {
            left = mid + 1;
        }
        else if(nums[mid] > target) {
            right = mid;
        }
    }
    return nums[left - 1] == target ? left - 1 : -1;
}
