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
	 * ��ȡ����Ӧ�ó������
	 * 
	 * @param context
	 *            : Ӧ�ó�����������Ϣ
	 * @return:Ӧ�ó������
	 */
	public String getLocalPkgName(Context context) {
		localPkgName = context.getPackageName();
		return localPkgName;
	}

	/**
	 * ��ȡ����Ӧ�ó���汾��
	 * 
	 * @param context
	 *            :Ӧ�ó�����������Ϣ
	 * @return:Ӧ�ó���汾��
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
	 * ��ȡ����Ӧ�ó���汾����
	 * 
	 * @param context
	 *            :Ӧ�ó�����������Ϣ
	 * @return:Ӧ�ó���汾����
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
	 * ��ȡ����Ӧ�ó�����
	 * 
	 * @param context
	 *            :Ӧ�ó�����������Ϣ
	 * @return:Ӧ�ó�����
	 */
	public String getLocalAppName(Context context) {
		return (String) context.getPackageManager().getApplicationLabel(
				context.getApplicationInfo());
	}
}
