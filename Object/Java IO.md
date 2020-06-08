### 按操作分类

- 字节流和字符流：
    - 字节流：以字节为单位，每次次读入或读出是8位数据。可以读任何类型数据。
    - 字符流：以字符为单位，每次次读入或读出是16位数据。其只能读取字符类型数据。
- 输出流和输入流：
    - 输出流：从内存读出到文件。只能进行写操作。
    - 输入流：从文件读入到内存。只能进行读操作。

```
注意：这里的出和入，都是相对于系统内存而言的。
```

- 节点流和处理流：
    - 节点流：直接与数据源相连，读入或读出。
    - 处理流：与节点流一块使用，在节点流的基础上，再套接一层，套接在节点流上的就是处理流。

```
为什么要有处理流？直接使用节点流，读写不方便，为了更快的读写文件，才有了处理流。
```