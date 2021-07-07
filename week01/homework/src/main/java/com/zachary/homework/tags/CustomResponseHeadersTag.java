package com.zachary.homework.tags;

import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class CustomResponseHeadersTag extends SimpleTagSupport {
  private String cacheControl;
  private String pragma;
  private long expires;

  @Override
  public void doTag() throws JspException, IOException {
    super.doTag();
    getJspContext();
    System.out.println(getJspContext());
    JspContext jspContext = getJspContext();
    PageContext pageContext = (PageContext) jspContext;
    HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
    if(StringUtils.hasText(cacheControl)) {
      response.setHeader("Cache-Control", cacheControl);
    }
    if(StringUtils.hasText(pragma)) {
      response.setHeader("Pragma", pragma);
    }
    response.setDateHeader("Expires", expires);
  }

  public String getCacheControl() {
    return cacheControl;
  }

  public void setCacheControl(String cacheControl) {
    this.cacheControl = cacheControl;
  }

  public String getPragma() {
    return pragma;
  }

  public void setPragma(String pragma) {
    this.pragma = pragma;
  }

  public long getExpires() {
    return expires;
  }

  public void setExpires(long expires) {
    this.expires = expires;
  }

}
