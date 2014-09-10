package cn.dreampie.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangrenhui on 2014/6/24.
 */
public class ResourceHandler extends FakeStaticHandler {


  /**
   * 资源文件目录
   */
  private String[] resourceUrls;


  public ResourceHandler(String... resourceUrls) {
    this.resourceUrls = resourceUrls;
  }


  @Override
  public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
    if (checkResource(target)) {
      return;
    }
    nextHandler.handle(target, request, response, isHandled);
  }

  /**
   * 判断是否是静态的资源文件
   *
   * @param resouceUrl resource url
   * @return boolean
   */
  public boolean checkResource(String resouceUrl) {
    if (resourceUrls != null && resourceUrls.length > 0) {
      for (String url : resourceUrls) {
        if (antPathMatcher.match(url, resouceUrl)) {
          return true;
        }
      }
    }
    return false;
  }
}
