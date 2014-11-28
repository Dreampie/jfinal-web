package cn.dreampie.web;

import cn.dreampie.PropertiesKit;
import com.jfinal.config.*;

import java.util.Properties;

/**
 * Created by wangrenhui on 14-1-3.
 */
public abstract class Config extends com.jfinal.config.JFinalConfig {
  /**
   * Config constant
   */
  public abstract void configConstant(Constants me);

  /**
   * Config route
   */
  public abstract void configRoute(Routes me);

  /**
   * Config plugin
   */
  public abstract void configPlugin(Plugins me);

  /**
   * Config interceptor applied to all actions.
   */
  public abstract void configInterceptor(Interceptors me);

  /**
   * Config handler
   */
  public abstract void configHandler(Handlers me);

  /**
   * Call back after JFinal start
   */
  public void afterJFinalStart() {
  }

  ;

  /**
   * Call back before JFinal stop
   */
  public void beforeJFinalStop() {
  }

  ;

  private Properties properties;

  /**
   * Load property file
   * Example: loadPropertyFile("db_username_pass.txt");
   *
   * @param file the file in WEB-INF directory  or resources
   */
  public Properties loadPropertyFile(String file) {
    properties = PropertiesKit.me().loadPropertyFile(file);
    return properties;
  }

  public String getProperty(String key) {
    checkPropertyLoading();
    return properties.getProperty(key);
  }

  public String getProperty(String key, String defaultValue) {
    checkPropertyLoading();
    return properties.getProperty(key, defaultValue);
  }

  public Integer getPropertyToInt(String key) {
    checkPropertyLoading();
    Integer resultInt = null;
    String resultStr = properties.getProperty(key);
    if (resultStr != null)
      resultInt = Integer.parseInt(resultStr);
    return resultInt;
  }

  public Integer getPropertyToInt(String key, Integer defaultValue) {
    Integer result = getPropertyToInt(key);
    return result != null ? result : defaultValue;
  }

  public Boolean getPropertyToBoolean(String key) {
    checkPropertyLoading();
    String resultStr = properties.getProperty(key);
    Boolean resultBool = null;
    if (resultStr != null) {
      if (resultStr.trim().equalsIgnoreCase("true"))
        resultBool = true;
      else if (resultStr.trim().equalsIgnoreCase("false"))
        resultBool = false;
    }
    return resultBool;
  }

  public Boolean getPropertyToBoolean(String key, boolean defaultValue) {
    Boolean result = getPropertyToBoolean(key);
    return result != null ? result : defaultValue;
  }

  private void checkPropertyLoading() {
    if (properties == null)
      throw new RuntimeException("You must load properties file by invoking loadPropertyFile(String) method in configConstant(Constants) method before.");
  }
}