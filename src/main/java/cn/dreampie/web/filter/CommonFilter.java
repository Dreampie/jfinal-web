package cn.dreampie.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wangrenhui on 13-12-31.
 */
public class CommonFilter extends HttpFilter {
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
