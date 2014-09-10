package cn.dreampie.web.render;

import cn.dreampie.ThreadLocalKit;
import com.jfinal.render.ErrorRender;
import com.jfinal.render.IErrorRenderFactory;
import com.jfinal.render.Render;

/**
 * Created by wangrenhui on 14-4-28.
 */
public class JsonErrorRenderFactory implements IErrorRenderFactory {
  public Render getRender(int errorCode, String view) {
    if (ThreadLocalKit.isJson())
      return new JsonErrorRender(errorCode, view);
    else
      return new ErrorRender(errorCode, view);
  }
}
