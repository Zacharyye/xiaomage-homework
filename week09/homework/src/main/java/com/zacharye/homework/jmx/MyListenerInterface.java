package com.zacharye.homework.jmx;

import javax.management.MXBean;

@MXBean
public interface MyListenerInterface {
  void sendInfo();
}
