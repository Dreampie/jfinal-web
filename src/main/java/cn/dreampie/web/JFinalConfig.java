package cn.dreampie.web;

import com.jfinal.config.*;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wangrenhui on 14-1-3.
 */
public abstract class JFinalConfig extends com.jfinal.config.JFinalConfig {
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
   * @param file the file in WEB-INF directory
   */
  public Properties loadPropertyFile(String file) {
    if (StrKit.isBlank(file))
      throw new IllegalArgumentException("Parameter of file can not be blank");
    if (file.contains(".."))
      throw new IllegalArgumentException("Parameter of file can not contains \"..\"");

    InputStream inputStream = null;
    String fullFile;  // String fullFile = PathUtil.getWebRootPath() + file;
    //判断是否带有文件分隔符
    boolean startStuff = file.startsWith(File.separator);
    if (startStuff)
      fullFile = PathKit.getWebRootPath() + File.separator + "WEB-INF" + file;
    else
      fullFile = PathKit.getWebRootPath() + File.separator + "WEB-INF" + File.separator + file;
    File propFile = new File(fullFile);
    //判断文件是否存在WebInf
    if (!propFile.exists()) {
      if (startStuff)
        fullFile = PathKit.getRootClassPath() + file;
      else
        fullFile = PathKit.getRootClassPath() + File.separator + file;
      propFile = new File(fullFile);
      //判断文件是否存在class
      if (!propFile.exists()) {
        throw new IllegalArgumentException("Properties file not found: " + fullFile);
      }
    }
    try {
      inputStream = new FileInputStream(propFile);
      Properties p = new Properties();
      p.load(inputStream);
      properties = p;
    } catch (IOException e) {
      throw new IllegalArgumentException("Properties file can not be loading: " + fullFile);
    } finally {
      try {
        if (inputStream != null) inputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (properties == null)
      throw new RuntimeException("Properties file loading failed: " + fullFile);
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