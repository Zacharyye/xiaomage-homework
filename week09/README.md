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

[代码地址]: 

