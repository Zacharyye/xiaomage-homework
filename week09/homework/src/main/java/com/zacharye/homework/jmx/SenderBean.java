package com.zacharye.homework.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class SenderBean extends NotificationBroadcasterSupport implements MyListenerInterface {
  private int seq = 0;

  @Override
  public void sendInfo() {
    Notification notification = new Notification("sender", this, seq++, "doubi");
    sendNotification(notification);
  }

  public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
    MBeanServer server = ManagementFactory.getPlatformMBeanServer();
    ObjectName objectName = new ObjectName("com.xp:name=listener,type=mybeans");
    SenderBean bean = new SenderBean();
    server.registerMBean(bean, objectName);
    bean.addNotificationListener((Notification notification, Object handback) -> {
      System.out.println(notification.getMessage());
    }, null, null);
    bean.sendInfo();
  }
}
