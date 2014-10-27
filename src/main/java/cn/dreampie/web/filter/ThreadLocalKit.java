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

  private static String dataTypeＮame = "returnType";

  private static ReTurnType reTurnType = ReTurnType.HTML;

  public static String getDataTypeＮame() {
    return dataTypeＮame;
  }

  public static void setDataTypeＮame(String dataTypeＮame) {
    ThreadLocalKit.dataTypeＮame = dataTypeＮame;
  }

  public static void init(HttpServletRequest request) {
    setRequest(request);
    reTurnType = getReturnType(request);
  }

  public static HttpServletRequest getRequest() {
    return (HttpServletRequest) requestLocal.get();
  }

  public static void setRequest(HttpServletRequest request) {
    requestLocal.set(request);
  }


  public static HttpSession getSession() {
    if (requestLocal.get() != null) {
      return (HttpSession) ((HttpServletRequest) requestLocal.get())
          .getSession();
    } else {
      return null;
    }
  }

  public static ServletContext getServletContex() {
    if (requestLocal.get() != null) {
      return (ServletContext) ((HttpServletRequest) requestLocal.get())
          .getServletContext();
    } else {
      return null;
    }
  }

  /**
   * 获取返回值类型
   *
   * @return type
   */
  public static ReTurnType getReturnType(HttpServletRequest request) {
    if (request != null) {
      String header = request.getHeader("X-Requested-With");
      if ((("XMLHttpRequest").equalsIgnoreCase(header) && !("html").equalsIgnoreCase(request.getParameter(dataTypeＮame))) ||
          ("json").equalsIgnoreCase(request.getParameter(dataTypeＮame))) {// 如果是ajax请求响应头会有，x-requested-with；
        return ReTurnType.JSON;
      }
    }
    return ReTurnType.HTML;
  }

  public static boolean isAjax() {
    HttpServletRequest request = getRequest();
    return isAjax(request);
  }

  public static boolean isAjax(HttpServletRequest request) {
    if (request != null && ("XMLHttpRequest").equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
      return true;
    }
    return false;
  }

  public static boolean isJson() {
    if (reTurnType == ReTurnType.JSON) {
      return true;
    }
    return false;
  }

  public static boolean isJson(Controller controller) {
    if (controller.getRender() instanceof JsonRender) {
      return true;
    }
    return false;
  }

  public enum ReTurnType {
    HTML(0), JSON(1);
    private final int value;

    private ReTurnType(int value) {
      this.value = value;
    }

    public int value() {
      return this.value;
    }
  }

}