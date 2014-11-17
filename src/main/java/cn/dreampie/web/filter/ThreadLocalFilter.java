package cn.dreampie.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangrenhui on 13-12-31.
 */
public class ThreadLocalFilter extends HttpFilter {
  private List<String> excludes = new ArrayList<String>() {
    {
      add("*.js");
      add("*.css");
      add("*.png");
      add("*.gif");
      add("*.jpg");
    }
  };

  /**
   * 初始化filter，获取过滤例外参数
   *
   * @param filterConfig filterConfig
   * @throws ServletException ServletException
   */
  public void init(FilterConfig filterConfig) throws ServletException {
    FilterConfig config = filterConfig;
    String dataTypeName = filterConfig.getInitParameter("dataTypeName");
    if (dataTypeName != null && !dataTypeName.isEmpty()) {
      ThreadLocalKit.setDataTypeName(dataTypeName);
    }
    String s = config.getInitParameter("excludes");
    if (s != null) {
      String[] ary = s.split(",");
      for (String a : ary) {
        excludes.add(a.trim());
      }
    }
  }

  /**
   * 过滤字符和数据本地化存储
   *
   * @param request  request
   * @param response response
   * @param chain    chain
   */
  public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

    //request.setCharacterEncoding(AppConstants.DEFAULT_ENCODING);
    //response.setCharacterEncoding(AppConstants.DEFAULT_ENCODING);
    String uri = request.getRequestURI();

    boolean excluding = false;
    if (!uri.equals("/")) {
      String[] patts = null;
      for (String exclude : excludes) {
        if (exclude.startsWith("*")) {
          if (uri.endsWith(exclude.replaceFirst("\\*", ""))) {
            excluding = true;
            break;
          }
        } else {
          patts = exclude.split("\\*");
          if (patts.length == 1 && uri.startsWith(patts[0])) {
            excluding = true;
            break;
          }
          if (patts.length == 2 && uri.startsWith(patts[0]) && uri.endsWith(patts[1])) {
            excluding = true;
            break;
          }
        }
      }
    }
    if (!excluding) {
      //请求数据本地化
      ThreadLocalKit.init(request);
    }
    chain.doFilter(request, response);
  }
}
