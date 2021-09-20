package com.zachary.configuration;

public class HelloServiceConfiguration {
  private String name;

  private String hobby;

  public String getName() {
    return "name is " + name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHobby() {
    return "hobby is " + hobby;
  }

  public void setHobby(String hobby) {
    this.hobby = hobby;
  }
}
