package cn.dreampie.web.interceptor;


import cn.dreampie.web.filter.ThreadLocalKit;
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

    if (!ThreadLocalKit.isJson(controller)) {
      //local 数据
      controller.setAttr("_localParas", request.getQueryString());
      controller.setAttr("_localUri", ai.getActionKey());
    }

    controller.keepPara("_webRootPath", "_localParas", "_localUri");
    //i18n
//    String tmp = controller.getCookie(Const.I18N_LOCALE);
//    String i18n = controller.getRequest().getLocale().toString();
//    if (!i18n.equals(tmp)) {
//      ai.getController().setCookie(Const.I18N_LOCALE, i18n, Const.DEFAULT_I18N_MAX_AGE_OF_COOKIE);
//    }

  }
}
