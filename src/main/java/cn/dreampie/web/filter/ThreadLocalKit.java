package cn.dreampie.web.filter;

import cn.dreampie.web.ReturnKit;
import com.jfinal.log.Logger;

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
  private static ThreadLocal<ReturnKit.ReturnType> returnTypeLocal = new ThreadLocal<ReturnKit.ReturnType>();
  public static boolean autoJson = true;

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
  }

  public static void init(HttpServletRequest request, boolean autoJson) {
    setRequest(request);
    setReturnType(request);
    ThreadLocalKit.autoJson = autoJson;
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
    if (ReturnKit.isJson(request)) {
      logger.debug("uri:" + request.getRequestURI() + ",return:json");
      returnTypeLocal.set(ReturnKit.ReturnType.JSON);
    } else {
      logger.debug("uri:" + request.getRequestURI() + ",return:default");
      returnTypeLocal.set(ReturnKit.ReturnType.DFAULT);
    }
  }

  public static ReturnKit.ReturnType getReturnType() {
    return returnTypeLocal.get();
  }

  public static boolean isJson() {
    return getReturnType() == ReturnKit.ReturnType.JSON;
  }

  public static void remove() {
    requestLocal.remove();
    returnTypeLocal.remove();
    logger.debug("remove  threadlocal");
  }
}