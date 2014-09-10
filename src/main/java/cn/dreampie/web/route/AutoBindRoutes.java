package cn.dreampie.web.route;

import cn.dreampie.ClassSearchKit;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Logger;

import java.util.List;

/**
 * Created by wangrenhui on 14-1-2.
 */
public class AutoBindRoutes extends Routes {

  private boolean autoScan = true;

  private List<Class<? extends Controller>> excludeClasses = Lists.newArrayList();
  private List<Class<? extends Controller>> includeClasses = Lists.newArrayList();
  private List<String> includeClassPaths = Lists.newArrayList();
  private List<String> excludeClassPaths = Lists.newArrayList();
//  private boolean includeAllJarsInLib;

//  private List<String> includeJars = Lists.newArrayList();

  protected final Logger logger = Logger.getLogger(getClass());

  private String suffix = "Controller";

  public AutoBindRoutes autoScan(boolean autoScan) {
    this.autoScan = autoScan;
    return this;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public AutoBindRoutes addExcludeClasses(Class<? extends Controller>... clazzes) {
    for (Class<? extends Controller> clazz : clazzes) {
      excludeClasses.add(clazz);
    }
    return this;
  }

  @SuppressWarnings("rawtypes")
  public AutoBindRoutes addExcludeClasses(List<Class<? extends Controller>> clazzes) {
    if (clazzes != null) {
      excludeClasses.addAll(clazzes);
    }
    return this;
  }

  public AutoBindRoutes addExcludePaths(String... paths) {
    for (String path : paths) {
      excludeClassPaths.add(path);
    }
    return this;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public AutoBindRoutes addIncludeClasses(Class<? extends Controller>... clazzes) {
    for (Class<? extends Controller> clazz : clazzes) {
      includeClasses.add(clazz);
    }
    return this;
  }

  @SuppressWarnings("rawtypes")
  public AutoBindRoutes addIncludeClasses(List<Class<? extends Controller>> clazzes) {
    if (clazzes != null) {
      includeClasses.addAll(clazzes);
    }
    return this;
  }

  public AutoBindRoutes addIncludePaths(String... paths) {
    for (String path : paths) {
      includeClassPaths.add(path);
    }
    return this;
  }


  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  public void config() {
    List<Class<? extends Controller>> controllerClasses = ClassSearchKit.of(Controller.class).includepaths(includeClassPaths).search();
    ControllerBind controllerBind = null;
    for (Class controller : controllerClasses) {
      if (excludeClasses.contains(controller)) {
        continue;
      }
      controllerBind = (ControllerBind) controller.getAnnotation(ControllerBind.class);
      if (controllerBind == null) {
        if (!autoScan) {
          continue;
        }
        this.add(controllerKey(controller), controller);
        logger.debug("routes.add(" + controllerKey(controller) + ", " + controller.getName() + ")");
      } else if (StrKit.isBlank(controllerBind.viewPath())) {
        this.add(controllerBind.controllerKey(), controller);
        logger.debug("routes.add(" + controllerBind.controllerKey() + ", " + controller.getName() + ")");
      } else {
        this.add(controllerBind.controllerKey(), controller, controllerBind.viewPath());
        logger.debug("routes.add(" + controllerBind.controllerKey() + ", " + controller + ","
            + controllerBind.viewPath() + ")");
      }
    }
  }

  private String controllerKey(Class<Controller> clazz) {
    Preconditions.checkArgument(clazz.getSimpleName().endsWith(suffix),
        " does not has a @ControllerBind annotation and it's name is not end with " + suffix);
    String simpleName = clazz.getSimpleName();
    String controllerKey = "/";
    if (!simpleName.equalsIgnoreCase(suffix)) {
      controllerKey += StrKit.firstCharToLowerCase(simpleName.replace(suffix, ""));
    }
    return controllerKey;
  }

//  public AutoBindRoutes includeAllJarsInLib(boolean includeAllJarsInLib) {
//    this.includeAllJarsInLib = includeAllJarsInLib;
//    return this;
//  }

  public AutoBindRoutes suffix(String suffix) {
    this.suffix = suffix;
    return this;
  }

}
