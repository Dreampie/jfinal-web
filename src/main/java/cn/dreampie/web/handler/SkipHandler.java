package cn.dreampie.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangrenhui on 2014/6/24.
 */
public class SkipHandler extends FakeStaticHandler {


  /**
   * 跳过的url
   */
  private String[] skipUrls;


  public SkipHandler(String... skipUrls) {
    this.skipUrls = skipUrls;
  }

  @Override
  public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
    if (checkSkip(target)) {
      return;
    }
    nextHandler.handle(target, request, response, isHandled);
  }


  public boolean checkSkip(String skipUrl) {
    if (skipUrls != null && skipUrls.length > 0) {
      for (String url : skipUrls) {
        if (antPathMatcher.match(url, skipUrl)) {
          return true;
        }
      }
    }
    return false;
  }
}
