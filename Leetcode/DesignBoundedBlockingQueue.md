#### LeetCode 1188

Implement a thread safe bounded blocking queue that has the following methods:

- `BoundedBlockingQueue(int capacity)` The constructor initializes the queue with a maximum `capacity`.
- `void enqueue(int element)` Adds an `element` to the front of the queue. If the queue is full, the calling thread is blocked until the queue is no longer full.
- `int dequeue()` Returns the element at the rear of the queue and removes it. If the queue is empty, the calling thread is blocked until the queue is no longer empty.
- `int size()` Returns the number of elements currently in the queue.  

Your implementation will be tested using multiple threads at the same time. Each thread will either be a producer thread that only makes calls to the `enqueue` method or a consumer thread that only makes calls to the `dequeue` method. The `size` method will be called after every test case.

Please do not use built-in implementations of bounded blocking queue as this will not be accepted in an interview.

 

**Example 1:**

```
Input:
1
1
["BoundedBlockingQueue","enqueue","dequeue","dequeue","enqueue","enqueue","enqueue","enqueue","dequeue"]
[[2],[1],[],[],[0],[2],[3],[4],[]]

Output:
[1,0,2,2]

Explanation:
Number of producer threads = 1
Number of consumer threads = 1

BoundedBlockingQueue queue = new BoundedBlockingQueue(2);   // initialize the queue with capacity = 2.

queue.enqueue(1);   // The producer thread enqueues 1 to the queue.
queue.dequeue();    // The consumer thread calls dequeue and returns 1 from the queue.
queue.dequeue();    // Since the queue is empty, the consumer thread is blocked.
queue.enqueue(0);   // The producer thread enqueues 0 to the queue. The consumer thread is unblocked and returns 0 from the queue.
queue.enqueue(2);   // The producer thread enqueues 2 to the queue.
queue.enqueue(3);   // The producer thread enqueues 3 to the queue.
queue.enqueue(4);   // The producer thread is blocked because the queue's capacity (2) is reached.
queue.dequeue();    // The consumer thread returns 2 from the queue. The producer thread is unblocked and enqueues 4 to the queue.
queue.size();       // 2 elements remaining in the queue. size() is always called at the end of each test case.
 ```

**Example 2:**

```
Input:
3
4
["BoundedBlockingQueue","enqueue","enqueue","enqueue","dequeue","dequeue","dequeue","enqueue"]
[[3],[1],[0],[2],[],[],[],[3]]

Output:
[1,0,2,1]

Explanation:
Number of producer threads = 3
Number of consumer threads = 4

BoundedBlockingQueue queue = new BoundedBlockingQueue(3);   // initialize the queue with capacity = 3.

queue.enqueue(1);   // Producer thread P1 enqueues 1 to the queue.
queue.enqueue(0);   // Producer thread P2 enqueues 0 to the queue.
queue.enqueue(2);   // Producer thread P3 enqueues 2 to the queue.
queue.dequeue();    // Consumer thread C1 calls dequeue.
queue.dequeue();    // Consumer thread C2 calls dequeue.
queue.dequeue();    // Consumer thread C3 calls dequeue.
queue.enqueue(3);   // One of the producer threads enqueues 3 to the queue.
queue.size();       // 1 element remaining in the queue.

Since the number of threads for producer/consumer is greater than 1, we do not know how the threads will be scheduled in the operating system, even though the input seems to imply the ordering. Therefore, any of the output [1,0,2] or [1,2,0] or [0,1,2] or [0,2,1] or [2,0,1] or [2,1,0] will be accepted.
```

Solution:下列代码之所以将wait放在while循环中，是因为避免了伪唤醒。  
#### 什么是伪唤醒
假设有两个消费者C1和C2，一个生产者P，线程通信开始前，产品数量为0，当用wait/notify进行线程通信时，C1执行到wait方法，释放锁等待，此时P生产产品唤醒线程C1，但被C2获取锁消费此产品，C2执行完成后释放锁，此时C1获取锁后进行产品的消费。以上过程出现了只有一个产品，但是被两次消费的现象。通过以上过程的阐述，造成伪唤醒的根本原因是notify唤醒线程和被唤醒的线程获取锁不是原子操作。在线程被唤醒过程中，如果锁被其他线程抢占执行，等持锁线程执行完后，被唤醒线程获得锁执行，就有可能造成临界资源为0的情况下被过度消费为负数的现象（在生产者消费者模式中。综上来看，当我们在线程唤醒后，再加一次对临界资源的判断，就能有效规避此问题，对此，JDK官方给出解决方案在使用wait方法时遵循以下格式：
```java
synchronized (obj) {
while (<condition does not hold>)
obj.wait();
... // Perform action appropriate to condition
     }
```

此方法用whlie循环在wait后再进行一次临界资源的判断，如果临界资源不满足要求继续设置线程为等待状态，避免了以上过度消费的问题出现。



```java
class BoundedBlockingQueue {
    private Queue<Integer> queue;
    int capacity;

    public BoundedBlockingQueue(int capacity) {
        queue = new LinkedList<>();
        this.capacity = capacity;
    }
    
    public void enqueue(int element) throws InterruptedException {
        synchronized(queue) {
            while(queue.size() == capacity) {
                queue.wait();
            }
            queue.offer(element);
            queue.notify();
        }
    }
    
    public int dequeue() throws InterruptedException {
        int res;
        synchronized(queue) {
            while(queue.size() == 0) {
                queue.wait();
            }
            res = queue.poll();
            queue.notify();
        }
        return res;
    }
    
    public int size() {
        return queue.size();
    }
}
```