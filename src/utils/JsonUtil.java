package utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
	private static String TAG = "JsonUtil";

	/**
	 * �������������ص�Json�ַ���
	 * 
	 * @param response
	 *            ��������������
	 * @return String �����õ����ַ���
	 */
	public static String getResult(String response) {
		String result = "";
		try {
			JSONObject jsonObject = new JSONObject(response);
			result = jsonObject.getString("result");
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
