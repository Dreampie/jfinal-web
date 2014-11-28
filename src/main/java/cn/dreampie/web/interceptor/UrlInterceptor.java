package cn.dreampie.web.interceptor;


import cn.dreampie.web.ReturnKit;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangrenhui on 14-4-16.
 */
public class UrlInterceptor implements Interceptor {
  @Override
  public void intercept(ActionInvocation ai) {
    Controller controller = ai.getController();
    HttpServletRequest request = controller.getRequest();
    //webRoot
    controller.setAttr("_webRootPath", request.getScheme() + "://"
        + request.getServerName() + (request.getServerPort() == 80 ? "" : ":" + request.getServerPort())
        + request.getContextPath());

    ai.invoke();

    if (!ReturnKit.isJson(controller)) {
      //local 数据
      controller.setAttr("_localParas", request.getQueryString());
      controller.setAttr("_localUri", ai.getActionKey());
    }
  }
}
