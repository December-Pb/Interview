## String&Character
- character -> string: Character.toString(s)
- string -> character: String.charAt(i)
- string -> character array: string.toCharArray()
- character array -> string: new String(charArray)
- character(number) -> int: Character.getNumericValue(character)
- character(letter) -> int: character - 'a' or character - 'A'
- character check if it is uppercase: Character.isUpperCase(character)
- ASCII: lowercase character begin from 97, uppercase character begin from 65,there are 6 special character between lowercase and uppercase.

## Java Collections下的接口和继承关系简易结构图：
- Collection Class Diagram
![Image of Collection Class](https://github.com/December-Pb/Interview/Image/CollectionClass.jpg)
- Map Class Diagram
![Image of Map Class](https://github.com/December-Pb/Interview/Image/MapClass.jpg)

## Stack&Queue
### Stack的方法
- 声明：Stack继承与Vector
- `boolean empty()`判断栈顶是否为空 $O(1)$
- `Object peek()`查看栈顶对象但是不移除 $O(1)$
- `Object pop()`移除栈顶对象，返回此对象 $O(1)$
- `Object push(Object element)`把元素压到栈中 $O(1)$
- `int search(Object element)`返回对象在栈中的位置，以1为基数(该查找是从后向前遍历，所以复杂度为$O(n)$)

### Queue的方法
- 用LinkedList初始化一个Queue，LinkedList implements Deque，而Deque继承与Queue，Dequeue是一个双向队列，而Queue是一个简单的FIFO队列
- LinkedList继承于Queue是可以当成是一个队列，其中方法：
  - `boolean offer(Object element)`加入元素，如果大于capacity抛出异常
  - `add(Object element)`加入元素，如果大于capacity返回false
  - `Object poll()`从队首取出元素返回并删除此元素，如果队列为空返回null
  - `Object remove()`从队首取出元素返回并删除此元素，如果队列为空抛出异常
  - `Object peek()`从队首取出元素返回不删除此元素，如果队列为空返回null
  - `Object element()`从队首取出元素返回不删除此元素，如果队列为空抛出异常

### 实现队列的三种方法

- 可以用继承于Vector的Stack，实现了Deque的ArrayDeque和实现了List和Deque的LinkedList实现队列
- 三者的组别
  - 底层数据存储方式  
    | Stack | ArrayDeque | LinkedList |  
    | :--- |:--- |:--- |
    | 长度为10的数组 | 长度为16的数组 | 链表 |
  - 方法参照表
    | Stack | ArrayDeque | LinkedList |  
    | :--- |:--- |:--- |
    | push(e) | addFirst(e)/offerFirst(e) | addFirst(e)/offerFirst(e) |
    | pop() | removeFirst()/pollFirst() | removeFirst()/pollFirst() |
    | peek() | getFirst()/peekFirst() | getFirst()/peekFirst() |
  - 线程安全
    | Stack | ArrayDeque | LinkedList |  
    | :--- |:--- |:--- |
    | 线程同步 | 线程不同步 | 线程不同步 |
  - 通常情况不推荐使用Vector以及其子类的Stack

1. 需要线程同步，使用Collections工具类中的synchronizedXXX()将不同步的数据结构转换成线程同步
2. 频繁的插入删除操作，未知初始数据量用LinkedList
3. 频繁的随机访问操作用ArrayDeque