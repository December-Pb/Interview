#### Leetcode 981

Create a timebased key-value store class `TimeMap`, that supports two operations.

1. `set(string key, string value, int timestamp)`

   - Stores the key and value, along with the given timestamp.  

2. `get(string key, int timestamp)`

    - Returns a value such that `set(key, value, timestamp_prev)` was called previously, with `timestamp_prev <= timestamp`.
    - If there are multiple such values, it returns the one with the largest `timestamp_prev`.
    - If there are no values, it returns the empty string `("")`.

**Example 1:**

```Input: inputs = ["TimeMap","set","get","get","set","get","get"], inputs = [[],["foo","bar",1],["foo",1],["foo",3],["foo","bar2",4],["foo",4],["foo",5]]
Output: [null,null,"bar","bar",null,"bar2","bar2"]
Explanation:   
TimeMap kv;   
kv.set("foo", "bar", 1); // store the key "foo" and value "bar" along with timestamp = 1   
kv.get("foo", 1);  // output "bar"   
kv.get("foo", 3); // output "bar" since there is no value corresponding to foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 ie "bar"   
kv.set("foo", "bar2", 4);   
kv.get("foo", 4); // output "bar2"   
kv.get("foo", 5); //output "bar2"   
```
**Example 2:**
```
Input: inputs = ["TimeMap","set","set","get","get","get","get","get"], inputs = [[],["love","high",10],["love","low",20],["love",5],["love",10],["love",15],["love",20],["love",25]]
Output: [null,null,null,"","high","high","low","low"]
 ```

**Note:**

1. All key/value strings are lowercase.
2. All key/value strings have length in the range [1, 100]
3. The timestamps for all TimeMap.set operations are strictly increasing.
4. 1 <= timestamp <= 10^7
5. TimeMap.set and TimeMap.get functions will be called a total of 120000 times (combined) per test case.

Solution:  
本题中我们想要实现一个数据结构，这个数据结构是一个map结构，map里的所有value对应一个时间戳，每当调用get时，函数返回一个与时间戳最近的value，我们既可以用一个Map保存ArrayList，用二分查找找到对应的value，也可以在Map里存入一个[treeMap](../Object/TreeMap.md)。

```java
class TimeMap {
    Map<String, TreeMap<Integer, String>> M;
    /** Initialize your data structure here. */
    public TimeMap() {
        M = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        if(!M.containsKey(key)) {
            M.put(key, new TreeMap());
        }
        M.get(key).put(timestamp, value);
    }
    
    public String get(String key, int timestamp) {
        if(!M.containsKey(key)) {
            return "";
        }
        TreeMap<Integer, String> tree = M.get(key);
        Integer t = tree.floorKey(timestamp);
        return t != null ? tree.get(t) : "";
    }
}

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */
```