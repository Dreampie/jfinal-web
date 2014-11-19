package cn.dreampie.web;

import cn.dreampie.web.filter.ThreadLocalKit;
import com.jfinal.kit.StrKit;
import com.jfinal.render.JsonRender;
import com.jfinal.render.Render;
import com.jfinal.render.RenderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ice on 14-11-19.
 */
public class JFController extends com.jfinal.core.Controller {

  protected static RenderFactory renderFactory = RenderFactory.me();

  public Render getRender() {
    if (ThreadLocalKit.autoJson()) {
      if (ThreadLocalKit.isJson() && !(super.getRender() instanceof JsonRender)) {
        return renderFactory.getJsonRender();
      }
    }
    return super.getRender();
  }

  public <T> List<T> getModels(Class<T> modelClass) {
    return getModels(modelClass, StrKit.firstCharToLowerCase(modelClass.getSimpleName()));
  }

  /**
   * 获取前端传来的数组对象并响应成Model列表
   */
  public <T> List<T> getModels(Class<T> modelClass, String modelName) {
    List<String> indexes = getIndexes(modelName);
    List<T> list = new ArrayList<T>();
    for (String index : indexes) {
      T m = getModel(modelClass, modelName + "[" + index + "]");
      if (m != null) {
        list.add(m);
      }
    }
    return list;
  }

  /**
   * 提取model对象数组的标号
   */
  private List<String> getIndexes(String modelName) {
    // 提取标号
    List<String> list = new ArrayList<String>();
    String modelNameAndLeft = modelName + "[";
    Map<String, String[]> parasMap = getRequest().getParameterMap();
    for (Map.Entry<String, String[]> e : parasMap.entrySet()) {
      String paraKey = e.getKey();
      if (paraKey.startsWith(modelNameAndLeft)) {
        String no = paraKey.substring(paraKey.indexOf("[") + 1,
            paraKey.indexOf("]"));
        if (!list.contains(no)) {
          list.add(no);
        }
      }
    }
    return list;
  }

}
