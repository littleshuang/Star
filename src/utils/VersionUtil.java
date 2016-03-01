package utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class VersionUtil {
	private static final String TAG = "VersionUtil";
	private int localVerCode = -1;
	private String localVerName;
	private String localPkgName;
	private int serverVerCode;
	private String serverInfo;

	/**
	 * 获取本地应用程序包名
	 * 
	 * @param context
	 *            : 应用程序上下文信息
	 * @return:应用程序包名
	 */
	public String getLocalPkgName(Context context) {
		localPkgName = context.getPackageName();
		return localPkgName;
	}

	/**
	 * 获取本地应用程序版本号
	 * 
	 * @param context
	 *            :应用程序上下文信息
	 * @return:应用程序版本号
	 */
	public int getLocalVersionCode(Context context) {
		try {
			localVerCode = context.getPackageManager().getPackageInfo(
					getLocalPkgName(context), 0).versionCode;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return localVerCode;
	}

	/**
	 * 获取本地应用程序版本名称
	 * 
	 * @param context
	 *            :应用程序上下文信息
	 * @return:应用程序版本名称
	 */
	public String getLocalVerName(Context context) {
		try {
			localVerName = context.getPackageManager().getPackageInfo(
					getLocalPkgName(context), 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return localVerName;
	}

	/**
	 * 获取本地应用程序名
	 * 
	 * @param context
	 *            :应用程序上下文信息
	 * @return:应用程序名
	 */
	public String getLocalAppName(Context context) {
		return (String) context.getPackageManager().getApplicationLabel(
				context.getApplicationInfo());
	}
}
