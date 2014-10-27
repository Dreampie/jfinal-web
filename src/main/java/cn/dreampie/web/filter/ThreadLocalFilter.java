package cn.dreampie.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wangrenhui on 13-12-31.
 */
public class ThreadLocalFilter extends HttpFilter {

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
      ThreadLocalKit.setDataTypeＮame(dataTypeName);
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

    //请求数据本地化
    ThreadLocalKit.init(request);

    chain.doFilter(request, response);
  }
}
