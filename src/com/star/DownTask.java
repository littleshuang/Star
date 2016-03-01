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
	 * 构造函数
	 * 
	 * @param ctx
	 * @param savePath
	 *            :下载资源保存路径
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
				// 读取大文件
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
		// 异步任务加载完成后，更新进度
		mView.setText("Doanload result: " + result);
		pDialog.dismiss();
	}

	@Override
	protected void onPreExecute() {
		pDialog = new ProgressDialog(mContext);
		pDialog.setTitle("Task progress dialog"); // 设置对话框标题
		pDialog.setMessage("Task is progressing......"); // 设置对话框显示内容
		pDialog.setCancelable(false); // 设置取消按钮是否可用
		pDialog.setIndeterminate(false); // 设置对话框的进度条是否显示进度
		pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // 设置对话框进度条的样式
		pDialog.show(); // 显示该对话框
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		mView.setText("Is downloading......");
		pDialog.setProgress(values[0]);
	}
}
