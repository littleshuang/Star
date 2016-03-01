package utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
	private static String TAG = "JsonUtil";

	/**
	 * 解析服务器返回的Json字符串
	 * 
	 * @param response
	 *            服务器返回数据
	 * @return String 解析得到的字符串
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
