package com.zacharye.homework.jmx;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class GarbageNotificationListener implements NotificationListener {
  @Override
  public void handleNotification(Notification notification, Object handback) {
    String notifyType = notification.getType();
    if (notifyType.equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
      CompositeData cd = (CompositeData) notification.getUserData();
      GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from(cd);
      System.out.println(info.getGcCause() + " | " + info.getGcName());
    }
  }

  public static void main(String[] args) {
    List<GarbageCollectorMXBean> garbageCollectorMXBeanList = ManagementFactory.getGarbageCollectorMXBeans();
    for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeanList) {
      NotificationEmitter notificationEmitter = (NotificationEmitter) garbageCollectorMXBean;
      NotificationListener listener = new GarbageNotificationListener();
      notificationEmitter.addNotificationListener(listener, null, null);
    }
    try {
//      Thread.sleep(Long.MAX_VALUE);
      while(true) {
        new Object();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
