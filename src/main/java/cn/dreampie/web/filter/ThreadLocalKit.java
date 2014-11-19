package cn.dreampie.web.filter;

import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.jfinal.render.JsonRender;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by wangrenhui on 13-12-31.
 */
public class ThreadLocalKit {
  protected static final Logger logger = Logger.getLogger(ThreadLocalKit.class);
  // request线程对象
  private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
  private static ThreadLocal<ReTurnType> returnTypeLocal = new ThreadLocal<ReTurnType>();
  public static ThreadLocal<Boolean> isApplyLocal = new ThreadLocal<Boolean>();

  private static String dataTypeName = "returnType";

  public static String getDataTypeName() {
    return dataTypeName;
  }

  public static void setDataTypeName(String dataTypeＮame) {
    ThreadLocalKit.dataTypeName = dataTypeＮame;
  }

  public static void init(HttpServletRequest request) {
    setRequest(request);
    setReturnType(request);
    isApplyLocal.set(true);
  }

  public static boolean isApply() {
    return isApplyLocal.get().booleanValue();
  }

  public static HttpServletRequest getRequest() {
    return requestLocal.get();
  }

  public static void setRequest(HttpServletRequest request) {
    requestLocal.set(request);
  }


  public static HttpSession getSession() {
    if (requestLocal.get() != null) {
      return requestLocal.get().getSession();
    } else {
      return null;
    }
  }

  public static ServletContext getServletContext() {
    if (requestLocal.get() != null) {
      return requestLocal.get().getServletContext();
    } else {
      return null;
    }
  }

  /**
   * 获取返回值类型
   *
   * @return type
   */
  public static void setReturnType(HttpServletRequest request) {
    if (isJson(request)) {
      logger.debug("uri:" + request.getRequestURI() + ",return:json");
      returnTypeLocal.set(ReTurnType.JSON);
    } else {
      logger.debug("uri:" + request.getRequestURI() + ",return:default");
      returnTypeLocal.set(ReTurnType.DFAULT);
    }
  }

  public static ReTurnType getReturnType() {
    return returnTypeLocal.get();
  }

  public static boolean isAjax() {
    return isAjax(getRequest());
  }

  public static boolean isAjax(HttpServletRequest request) {
    return ("XMLHttpRequest").equalsIgnoreCase(request.getHeader("X-Requested-With"));// 如果是ajax请求响应头会有，x-requested-with；
  }

  public static boolean isJson(HttpServletRequest request) {
    return (isAjax(request) && !("default").equalsIgnoreCase(request.getParameter(dataTypeName))) ||
        ("json").equalsIgnoreCase(request.getParameter(dataTypeName));// 如果是ajax请求响应头会有，x-requested-with；
  }

  public static boolean isJson() {
    return getReturnType() == ReTurnType.JSON;
  }

  public static boolean isJson(Controller controller) {
    return controller.getRender() instanceof JsonRender;
  }

  public enum ReTurnType {
    DFAULT(0), JSON(1);
    private final int value;

    private ReTurnType(int value) {
      this.value = value;
    }

    public int value() {
      return this.value;
    }
  }

}