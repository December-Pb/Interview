# TreeMap

## 介绍
TreeMap是基于红黑树的的NavigableMap实现。是基于自然序列的排序，也可以实现Comparator进行排序。

## TreeMap的相关操作
>>>
- Map.Entry<K,V> ceilingEntry(K key)  
          返回一个键-值映射关系，它与大于等于给定键的最小键关联；如果不存在这样的键，则返回 null。
- K  ceilingKey(K key)  
          返回大于等于给定键的最小键；如果不存在这样的键，则返回 null。
- Map.Entry<K,V> higherEntry(K key)  
          返回一个键-值映射关系，它与严格大于给定键的最小键关联；如果不存在这样的键，则返回 null。
- K  higherKey(K key)  
          返回严格大于给定键的最小键；如果不存在这样的键，则返回 null。 
>>>
>>>
- Map.Entry<K,V> floorEntry(K key)  
          返回一个键-值映射关系，它与小于等于给定键的最大键关联；如果不存在这样的键，则返回 null。
- K  floorKey(K key)  
          返回小于等于给定键的最大键；如果不存在这样的键，则返回 null。
- Map.Entry<K,V> lowerEntry(K key)  
          返回一个键-值映射关系，它与严格小于给定键的最大键关联；如果不存在这样的键，则返回 null。
- K  lowerKey(K key)  
          返回严格小于给定键的最大键；如果不存在这样的键，则返回 null。
>>>
>>>
- Map.Entry<K,V> pollFirstEntry()  
          移除并返回与此映射中的最小键关联的键-值映射关系；如果映射为空，则返回 null。
- Map.Entry<K,V> pollLastEntry()  
          移除并返回与此映射中的最大键关联的键-值映射关系；如果映射为空，则返回 null。
- Map.Entry<K,V> firstEntry()  
          返回一个与此映射中的最小键关联的键-值映射关系；如果映射为空，则返回 null。
- K  firstKey()  
          返回此映射中当前第一个（最低）键。
- Map.Entry<K,V> lastEntry()  
            返回与此映射中的最大键关联的键-值映射关系；如果映射为空，则返回 null。
- K  lastKey()  
          返回映射中当前最后一个（最高）键。
>>>

由上述api可知，higher和lower返回的是严格大于或小于的键值对或者键；ceiling和floor返回的是大于等于或小于等于的键值对或者键；first返回的是最小键，last返回的是最大键。