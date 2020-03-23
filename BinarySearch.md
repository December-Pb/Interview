# Contents
## 二分法(binary search)
```java
// 非递归写法，
public class algorithm {
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
}
```
以上两个版本是数组中没有重复元素时的写法，其中有几个注意事项：  
 1. `int mid = left + (right - left) / 2;`是一个向下元整的操作，`5 / 2 = 2` 
   
非递归版本：  
1. while循环体中是<=还是<？  
如果是<的话，证明终止的区间形式是[left, right] (left == right),例如一个数组的元素为：[1,2,3,4,5],
target元素为2，则循环过程如下：  
| right | left | middle |
| 0 | 4 | 2 |
| 0 | 1 | 0 |
| 1 | 1 | 1 |  
此时left == right跳出循环，返回值为-1，但是真实值应该为1，所以如果
没有=的话，要在最后返回值的时候判断区间是否为空  
`return nums[left] == target ? left : -1;`