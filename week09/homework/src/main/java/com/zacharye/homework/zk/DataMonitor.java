package com.zacharye.homework.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;

public class DataMonitor implements Watcher, AsyncCallback.StatCallback {
  ZooKeeper zk;

  String znode;

  Watcher chainedWatcher;

  boolean dead;

  DataMonitorListener listener;

  byte prevData[];

  public DataMonitor(ZooKeeper zk, String znode, Watcher chainedWatcher, DataMonitorListener listener) {
    this.zk = zk;
    this.znode = znode;
    this.chainedWatcher = chainedWatcher;
    this.listener = listener;
    // 从校验节点是否存在开始，将完全由事件驱动
    zk.exists(znode, true, this, null);

  }

  /**
   * 其他类要使用DataMonitor，需要实现这个方法
   */
  public interface DataMonitorListener {
    /**
     * 节点存在状态发生变化
     * @param data
     */
    void exists(byte data[]);

    /**
     * ZooKeeper会话失效
     * @param rc
     */
    void closing(int rc);
  }

  @Override
  public void processResult(int rc, String path, Object ctx, Stat stat) {
    boolean exists;
    switch (rc) {
      case KeeperException.Code.Ok:
        exists = true;
        break;
      case KeeperException.Code.NoNode:
        exists = false;
        break;
      case KeeperException.Code.SessionExpired:
      case KeeperException.Code.NoAuth:
        dead = true;
        listener.closing(rc);
        return;
      default:
        // 重新尝试
        zk.exists(znode, true, this, null);
        return;
    }
    byte b[] = null;
    if (exists) {
      try {
        b = zk.getData(znode, false, null);
      } catch (KeeperException e) {
        // 不需要立刻解决，监视器回调机制会剔除异常处理器
        e.printStackTrace();
      } catch (InterruptedException e) {
        return;
      }
    }
    if ((b == null && b != prevData)
        || (b != null && !Arrays.equals(prevData, b))) {
      listener.exists(b);
      prevData = b;
    }
  }

  @Override
  public void process(WatchedEvent watchedEvent) {
    String path = watchedEvent.getPath();
    if (watchedEvent.getType() == Event.EventType.None) {
      // 连接状态发生变化
      switch (watchedEvent.getState()) {
        case SyncConnected:
          // 此状态下不需要做任何操作，监视器会自动注册到服务器，当客户端断开连接触发的监视器会安发生顺序进行传播
          break;
        case Expired:
          // 全部结束
          dead = true;
          listener.closing(KeeperException.Code.SessionExpired);
          break;
        default:
          break;
      }
    } else {
      if (path != null && path.equals(znode)) {
        // 节点发生变化
        zk.exists(znode, true, this, null);
      }
    }
    if (chainedWatcher != null) {
      chainedWatcher.process(watchedEvent);
    }
  }
}
