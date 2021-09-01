### 作业：实现分布式事件，基于 Zookeeper 或者 JMS 来实现

>  Zookeeper:3.4.11
>
> 地址：1.116.154.131:2181 
>
> 节点：/zk_test
>
> 事件监听:
>
> ```
> 节点创建[create /zk_test my_data]: Got WatchedEvent state:SyncConnected type:NodeCreated path:/zk_test for sessionid 0x10186e1f3920006
> 
> 数据变化[set /zk_test junk]: setGot WatchedEvent state:SyncConnected type:NodeDataChanged path:/zk_test for sessionid 0x10186e1f3920006
> 
> 节点删除[delete /zk_test]: Got WatchedEvent state:SyncConnected type:NodeDeleted path:/zk_test for sessionid 0x10186e1f3920006
> ```

Zookeeper代码：https://github.com/Zacharyye/xiaomage-homework/blob/main/week09/homework/src/main/java/com/zacharye/homework/zk/Executor.java

补充：有尝试熟悉JMX事件监听方式，参考网上资料实现了GC行为监听，但是并未实现分布式事件监听

GC行为监听代码：https://github.com/Zacharyye/xiaomage-homework/blob/main/week09/homework/src/main/java/com/zacharye/homework/jmx/GarbageNotificationListener.java

