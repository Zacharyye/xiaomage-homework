package com.salesmanager.shop.controller;

import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;

@Controller
@RequestMapping("actuator")
public class EndPointPostController {

  @Resource
  private ShutdownEndpoint shutdownEndpoint;

  @PostMapping("/shutdown")
  public void shutdownEndPoint() {
    shutdownEndpoint.shutdown();
  }
}
