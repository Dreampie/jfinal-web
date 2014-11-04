package cn.dreampie.web.handler;


import cn.dreampie.matcher.AntPathMatcher;
import cn.dreampie.web.filter.ThreadLocalKit;
import com.jfinal.handler.Handler;
import com.jfinal.i18n.I18N;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangrenhui on 14-1-5.
 */
public class FakeStaticHandler extends Handler {

  public static AntPathMatcher antPathMatcher = new AntPathMatcher();

  @Override
  public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
    target = target.replace(";JSESSIONID", "?JSESSIONID");
    //i18n不支持json
//    if (!ThreadLocalKit.isJson())
//      request.setAttribute("i18n", I18N.me());

    nextHandler.handle(target, request, response, isHandled);
  }
}
