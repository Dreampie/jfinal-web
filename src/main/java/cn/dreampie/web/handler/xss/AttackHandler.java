package cn.dreampie.web.handler.xss;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangrenhui on 2014/6/25.
 */
public class AttackHandler extends Handler {
  @Override
  public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
    request = new HttpServletRequestWrapper(request);
    nextHandler.handle(target, request, response, isHandled);
  }
}
