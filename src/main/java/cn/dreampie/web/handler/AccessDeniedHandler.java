package cn.dreampie.web.handler;

import com.jfinal.render.RenderFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangrenhui on 2014/6/24.
 */
public class AccessDeniedHandler extends FakeStaticHandler {

  /**
   * 拒绝访问的url
   */
  private String[] accessDeniedUrls;


  public AccessDeniedHandler(String... accessDeniedUrls) {
    this.accessDeniedUrls = accessDeniedUrls;
  }

  @Override
  public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
    if (checkView(target)) {
      isHandled[0] = true;
      RenderFactory.me().getErrorRender(403).setContext(request, response).render();
      return;
    }
    nextHandler.handle(target, request, response, isHandled);
  }

  public boolean checkView(String viewUrl) {

    if (accessDeniedUrls != null && accessDeniedUrls.length > 0) {
      for (String url : accessDeniedUrls) {
        if (antPathMatcher.match(url, viewUrl)) {
          return true;
        }
      }
    }
    return false;
  }
}
