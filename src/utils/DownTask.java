package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.star.R;

public class DownTask extends AsyncTask<String, Integer, String> {
	private static final String TAG = "DownTask";
	private Context mContext;
	private AlertDialog aDialog;
	int hasRead = 0;
	private TextView mView;
	private String filePath;
	private String fileName;

	/**
	 * ���캯��
	 * 
	 * @param ctx
	 * @param savePath
	 *            :������Դ����·��
	 */
	public DownTask(Context ctx, String savePath, String fileName) {
		mContext = ctx;
		filePath = savePath;
		this.fileName = fileName;
	}

	@SuppressWarnings("unused")
	@Override
	protected String doInBackground(String... params) {
		FileOutputStream outputStream = null;
		FileUtils fileUtil = new FileUtils();
		String result = null;
		File file;
		try {
			file = new File(fileUtil.getSDPATH() + filePath + "/" + fileName);
			if (file.exists()) {
				result = "File exist!";
				Log.i(TAG, result);
			} else {
				fileUtil.createSDDir(filePath);
				file.createNewFile();
				URL url = new URL(params[0]);
				HttpURLConnection urlConnection = (HttpURLConnection) url
						.openConnection();
				fileUtil.write2SDFromInput(filePath, fileName,
						urlConnection.getInputStream());
				if (file != null) {
					result = "success";
				} else {
					result = "Fail";
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Log.e(TAG, result);
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		Toast.makeText(mContext, "���°�Star�Ѿ����غ������Ͽ����ðɣ�", Toast.LENGTH_LONG);
		Log.i(TAG, "Finish download");
		aDialog.dismiss();
		installApk();
	}

	// ��װapk�ļ�
	private void installApk() {
		File apkfile = new File(filePath + "/" + fileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}

	@Override
	protected void onPreExecute() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("����Star").setMessage("�°汾������...")
				.setIcon(R.drawable.ic_update_dialog).show();
		aDialog = builder.create();
	}
}
