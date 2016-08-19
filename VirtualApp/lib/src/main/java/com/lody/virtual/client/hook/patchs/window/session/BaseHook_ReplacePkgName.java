package com.lody.virtual.client.hook.patchs.window.session;

import java.lang.reflect.Method;

import com.lody.virtual.client.hook.base.Hook;
import com.lody.virtual.helper.utils.ArrayUtils;

import android.view.WindowManager;

/**
 * @author Lody
 *
 */
/* package */ abstract class BaseReplacePkgName extends Hook {

	private int cacheIndex = -1;

	@Override
	public Object onHook(Object who, Method method, Object... args) throws Throwable {
		if (cacheIndex == -1) {
			cacheIndex = ArrayUtils.indexOfFirst(args, WindowManager.LayoutParams.class);
		}
		if (cacheIndex != -1) {
			WindowManager.LayoutParams attrs = (WindowManager.LayoutParams) args[cacheIndex];
			if (attrs != null) {
				String pkgName = attrs.packageName;
				if (isAppPkg(pkgName)) {
					attrs.packageName = getHostPkg();
				}
			}
		}
		return method.invoke(who, args);
	}
}
