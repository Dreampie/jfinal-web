package cn.dreampie.web.handler.xss;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangrenhui on 2014/6/25.
 */
public class HttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {

  public HttpServletRequestWrapper(HttpServletRequest request) {
    super(request);
  }

  /**
   * 重写并过滤getParameter方法
   *
   * @param name name
   * @return param
   */
  @Override
  public String getParameter(String name) {
    return escapeAll(super.getParameter(name));
  }

  /**
   * 重写并过滤getParameterValues方法
   *
   * @param name name
   * @return value
   */
  @Override
  public String[] getParameterValues(String name) {
    String[] values = super.getParameterValues(name);
    if (null == values) {
      return null;
    }
    for (int i = 0; i < values.length; i++) {

      values[i] = escapeAll(values[i]);
    }
    return values;
  }

  /**
   * 重写并过滤getParameterMap方法
   *
   * @return parammap
   */
  @Override
  public Map<String, String[]> getParameterMap() {
    Map<String, String[]> paramsMap = super.getParameterMap();
    // 对于paramsMap为空的直接return
    if (null == paramsMap || paramsMap.isEmpty()) {
      return paramsMap;
    }

    HashMap newParamsMap = new HashMap(paramsMap);
    for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
      String key = entry.getKey();
      String[] values = entry.getValue();
      if (null == values) {
        continue;
      }
      String[] newValues = new String[values.length];
      for (int i = 0; i < values.length; i++) {
        newValues[i] = escapeAll(values[i]);
      }
      newParamsMap.put(key, values);
    }
    return Collections.unmodifiableMap(newParamsMap);
  }

  public String escapeAll(String text) {

    String value = text;
    if (text == null) {
      return text;
    } else {
      value = escapeString(value);
    }
    return value;
  }

  public String escapeString(String text) {

    String value = text;
    if (text == null) {
      return text;
    } else {
      value = escapeHtml(value);
      value = escapeScript(value);
    }
    return value;
  }


  public String escapeHtml(String text) {

    String value = text;
    if (text == null) {
      return text;
    } else {
      value = StringEscapeUtils.escapeHtml3(value);
      value = StringEscapeUtils.escapeHtml4(value);
    }
    return value;
  }

  public String escapeScript(String text) {

    String value = text;
    if (text == null) {
      return text;
    } else {
      value = StringEscapeUtils.escapeEcmaScript(value);
    }
    return value;
  }
//  public String escapeFile(String text) {
//
//    String value = text;
//    if (text == null) {
//      return text;
//    } else {
//      value = escapeCsv(value);
//      value = escapeXml(value);
//    }
//    return value;
//  }
//
//  public String escapeCsv(String text) {
//
//    String value = text;
//    if (text == null) {
//      return text;
//    } else {
//      value = StringEscapeUtils.escapeCsv(value);
//    }
//    return value;
//  }
//
//  public String escapeXml(String text) {
//
//    String value = text;
//    if (text == null) {
//      return text;
//    } else {
//      value = StringEscapeUtils.escapeXml10(value);
//      value = StringEscapeUtils.escapeXml11(value);
//    }
//    return value;
//  }

}
