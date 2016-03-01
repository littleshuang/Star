package activity;

import utils.Constants;
import utils.DownTask;
import utils.JsonUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.star.R;

public class AutoUpdateActivity extends Activity {
	private static final String TAG = "AutoUpdateActivity";
	private static int localVersionCode, serverVersionCode = 0;
	private String fileName, filePath;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_update);
		context = this;
		filePath = "Star";
	}

	@Override
	protected void onResume() {
		super.onResume();
		// ����Ƿ���Ҫ���а汾����
		checkUpdate();
	}

	// ����Ƿ����µİ汾
	private void checkUpdate() {

		// ��ȡ����Ӧ�ó���汾��
		try {
			localVersionCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		// ��ȡ��������Ӧ�ó���汾��
		if (getServerVersionCode() > localVersionCode) {
			fileName = "Star.apk";
			showUpdateDialog();
		}
	}

	// ��ȡ��������Ӧ�ó���汾��
	private int getServerVersionCode() {
		RequestParams params = new RequestParams();
		StringBuilder versionCode = new StringBuilder();
		versionCode.append("{\"getVersionCode\"}");
		params.put("param", versionCode.toString());

		AsyncHttpClient client = new AsyncHttpClient();
		String urlString = utils.Constants.SERVER_BASE_URL
				+ utils.Constants.GET_VERSION_CODE;
		System.out.println(urlString + params);
		client.post(urlString, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				serverVersionCode = Integer.parseInt(JsonUtil
						.getResult(response));
			}

			@Override
			public void onFailure(Throwable error, String content) {
				serverVersionCode = 0;
				Log.d(TAG, content + " ");
			}
		});
		System.out.println(serverVersionCode);
		return serverVersionCode;
	}

	// ��ʾӦ�ó�����¶Ի���
	private void showUpdateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("�汾����").setMessage("Star���°汾���������������~")
				.setIcon(com.star.R.drawable.ic_update_dialog);
		builder.setPositiveButton("����", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				DownTask downTask = new DownTask(context, filePath, fileName);
				downTask.execute(Constants.SERVER_BASE_URL
						+ Constants.GET_NEW_VERSION);
			}
		});
		builder.setNegativeButton("�Ժ���˵", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

}
