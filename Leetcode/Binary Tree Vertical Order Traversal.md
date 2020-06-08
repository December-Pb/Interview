#### LeetCode 314

Given a binary tree, return the vertical order traversal of its nodes' values. (ie, from top to bottom, column by column).

If two nodes are in the same row and column, the order should be from **left to right**.

**Examples 1:**

```
Input: [3,9,20,null,null,15,7]

   3
  /\
 /  \
 9  20
    /\
   /  \
  15   7 

Output:

[
  [9],
  [3,15],
  [20],
  [7]
]
```
**Examples 2:**

```
Input: [3,9,8,4,0,1,7]

     3
    /\
   /  \
   9   8
  /\  /\
 /  \/  \
 4  01   7 

Output:

[
  [4],
  [9],
  [3,0,1],
  [8],
  [7]
]
```
**Examples 3:**

```
Input: [3,9,8,4,0,1,7,null,null,null,2,5] (0's right child is 2 and 1's left child is 5)

     3
    /\
   /  \
   9   8
  /\  /\
 /  \/  \
 4  01   7
    /\
   /  \
   5   2

Output:

[
  [4],
  [9,5],
  [3,0,1],
  [8,2],
  [7]
]
```

Solution: 本题可以看做是一个树的层序遍历，将root节点的index置位0，接下来每当遍历左节点的时候index - 1， 遍历右节点的时候index + 1， 将相同的index节点放到一起(此处可以用treemap存放index节点)

```java
class Solution {
    TreeMap<Integer, List<Pair>> map = new TreeMap<>();
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) {
            return res;
        }
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(0, root));
        while(!queue.isEmpty()) {
            Pair pair = queue.poll();
            if(!map.containsKey(pair.index)) {
                map.put(pair.index, new ArrayList<Pair>());
            }
            map.get(pair.index).add(pair);
            if(pair.node.left != null) {
                queue.offer(new Pair(pair.index - 1, pair.node.left));
            }
            if(pair.node.right != null) {
                queue.offer(new Pair(pair.index + 1, pair.node.right));
            }
        }
        for(Map.Entry<Integer, List<Pair>> entry : map.entrySet()) {
            res.add(new ArrayList<Integer>());
            List<Pair> temp = entry.getValue();
            for(Pair pair : temp) {
                res.get(res.size() - 1).add(pair.node.val);
            }
        }
        return res;
    }
    
    class Pair {
        TreeNode node;
        int index;
        public Pair(int index, TreeNode node) {
            this.node = node;
            this.index = index;
        }
    }
}
```