package com.zacharye.homework.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Executor implements Watcher, Runnable, DataMonitor.DataMonitorListener {

  String znode;

  DataMonitor dm;

  ZooKeeper zk;

  String filename;

  String exec[];

  Process child;

  public Executor(String hostPort, String znode, String filename, String exec[]) throws IOException {
    this.filename = filename;
    this.exec = exec;
    zk = new ZooKeeper(hostPort, 3000, this);
    dm = new DataMonitor(zk, znode, null, this);
  }

  public static void main(String[] args) {
    String hostPort = "1.116.154.131:2181";
    String znode = "/zk_test";
    String filename = "./zookeeper.txt";
    String exec[] = {"vscode"};
    try {
      Executor executor = new Executor(hostPort, znode, filename, exec);
      WatchedEvent watchedEvent = new WatchedEvent(Event.EventType.NodeDataChanged, Event.KeeperState.SyncConnected, "/zk_test");
      executor.process(watchedEvent);
            executor.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    try {
      synchronized (this) {
        while (!dm.dead) {
          wait();
        }
      }
    } catch (InterruptedException e) {

    }
  }

  /**
   * 自行处理事件，需要向前转发
   * @param watchedEvent
   */
  @Override
  public void process(WatchedEvent watchedEvent) {
    dm.process(watchedEvent);
  }

  @Override
  public void exists(byte[] data) {
    if (data == null) {
      if (child != null) {
        System.out.println("Killing process");
        child.destroy();
        try {
          child.waitFor();
        } catch (InterruptedException e) {}
        child = null;
      }
    } else {
      if (child != null) {
        System.out.println("Stopping child");
        child.destroy();
        try {
          child.waitFor();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      try {
        FileOutputStream fos = new FileOutputStream(filename);
        fos.write(data);
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      try {
        System.out.println("Starting child");
        child = Runtime.getRuntime().exec(exec);
        new StreamWriter(child.getInputStream(), System.out);
        new StreamWriter(child.getErrorStream(), System.err);
      } catch (IOException e) {

      }
    }
  }

  @Override
  public void closing(int rc) {
    synchronized (this) {
      notifyAll();
    }
  }

  static class StreamWriter extends Thread {
    OutputStream os;
    InputStream is;

    StreamWriter(InputStream is, OutputStream os) {
      this.is = is;
      this.os = os;
      start();
    }

    @Override
    public void run() {
      byte b[] = new byte[80];
      int rc;
      try {
        while ((rc = is.read(b)) > 0) {
          os.write(b, 0, rc);
        }
      } catch (IOException e)  {
      }
    }
  }
}
