package cn.dreampie.web.cache;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;

/**
 * Created by wangrenhui on 14-4-18.
 */
public class CacheInterceptor implements Interceptor {
  private static final String SLASH = "/";
  private static final String QMARK = "?";

  @Override
  public void intercept(ActionInvocation ai) {
    ai.invoke();
    String removeCacheName = buildRemoveCacheName(ai);
    String[] removeCacheKeys = buildRemoveCacheKeys(ai);
    String controllerKey = ai.getControllerKey();
    if (removeCacheName != null) {
      String keyPrefix = null;
      if (removeCacheKeys != null) {
        for (String removeKey : removeCacheKeys) {
          keyPrefix = controllerKey + SLASH + removeKey;
          removeByCacheKey(removeCacheName, keyPrefix);
        }
      } else {
        keyPrefix = controllerKey;
        removeByCacheKey(removeCacheName, keyPrefix);
      }
    }
  }

  private void removeByCacheKey(String removeCacheName, String keyPrefix) {
    List keys = CacheKit.getKeys(removeCacheName);
    String keyStr = null;
    for (Object key : keys) {
      if (key != null) {
        keyStr = key.toString();
        if ((keyStr.equals(keyPrefix) || keyStr.startsWith(keyPrefix + SLASH) || keyStr.startsWith(keyPrefix + QMARK))) {
          CacheKit.remove(removeCacheName, key);
        }
      }
    }
  }

  private String buildRemoveCacheName(ActionInvocation ai) {
    CacheRemove removeCacheName = ai.getMethod().getAnnotation(CacheRemove.class);
    if (removeCacheName != null)
      return removeCacheName.name();

    removeCacheName = ai.getController().getClass().getAnnotation(CacheRemove.class);
    if (removeCacheName != null)
      return removeCacheName.name();
    return null;
  }

  private String[] buildRemoveCacheKeys(ActionInvocation ai) {
    CacheRemove removeCacheName = ai.getMethod().getAnnotation(CacheRemove.class);
    if (removeCacheName != null)
      return removeCacheName.keys();

    removeCacheName = ai.getController().getClass().getAnnotation(CacheRemove.class);
    if (removeCacheName != null)
      return removeCacheName.keys();
    return null;
  }
}
