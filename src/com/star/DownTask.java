package com.star;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class DownTask extends AsyncTask<URL, Integer, String> {
	private Context mContext;
	private ProgressDialog pDialog;
	int hasRead = 0;
	private TextView mView;
	private String storePath;
	private String fileName;

	public DownTask(Context ctx, TextView v) {
		mContext = ctx;
		mView = v;
	}

	/**
	 * ���캯��
	 * 
	 * @param ctx
	 * @param savePath
	 *            :������Դ����·��
	 */
	public DownTask(Context ctx, String savePath, String fileName, TextView view) {
		mContext = ctx;
		storePath = savePath;
		this.fileName = fileName;
		this.mView = view;
	}

	@Override
	protected String doInBackground(URL... params) {
		FileOutputStream outputStream = null;
		String pathName = storePath + "/" + fileName;
		File file = new File(pathName);
		try {
			HttpURLConnection urlConnection = (HttpURLConnection) params[0]
					.openConnection();
			InputStream inputStream = urlConnection.getInputStream();
			if (file.exists()) {
				System.out.println("File exist!");
				return "File exist!";
			} else {
				new File(storePath).mkdir();
				file.createNewFile();
				outputStream = new FileOutputStream(file);
				// ��ȡ���ļ�
				byte[] buffer = new byte[4 * 1024];
				// while (inputStream.read(buffer) != -1) {
				// outputStream.write(buffer);
				// }
				int length;
				while ((length = (inputStream.read(buffer))) > 0) {
					outputStream.write(buffer, 0, length);
				}
				outputStream.flush();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				System.out.println("success");

			} catch (IOException e) {
				System.out.println("fail");
				e.printStackTrace();

			}
		}
		if (file != null) {
			return "success";
		} else {
			return "Fail";
		}
	}

	@Override
	protected void onPostExecute(String result) {
		// �첽���������ɺ󣬸��½���
		mView.setText("Doanload result: " + result);
		pDialog.dismiss();
	}

	@Override
	protected void onPreExecute() {
		pDialog = new ProgressDialog(mContext);
		pDialog.setTitle("Task progress dialog"); // ���öԻ������
		pDialog.setMessage("Task is progressing......"); // ���öԻ�����ʾ����
		pDialog.setCancelable(false); // ����ȡ����ť�Ƿ����
		pDialog.setIndeterminate(false); // ���öԻ���Ľ������Ƿ���ʾ����
		pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // ���öԻ������������ʽ
		pDialog.show(); // ��ʾ�öԻ���
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		mView.setText("Is downloading......");
		pDialog.setProgress(values[0]);
	}
}
