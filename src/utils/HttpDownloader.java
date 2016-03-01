package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpDownloader {

	private URL url = null;

	/**
	 * ����URL�����ļ�,ǰ��������ļ����е��������ı�,�����ķ���ֵ�����ı����е����� 1.����һ��URL����
	 * 2.ͨ��URL����,����һ��HttpURLConnection���� 3.�õ�InputStream 4.��InputStream���ж�ȡ����
	 * 
	 * @param urlStr
	 * @return
	 */
	public String download(String urlStr) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			buffer = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param urlStr
	 * @param path
	 * @param fileName
	 * @return -1:�ļ����س��� 0:�ļ����سɹ� 1:�ļ��Ѿ�����
	 */
	public int downFile(URL url, String path, String fileName) {
		InputStream inputStream = null;
		try {
			FileUtils fileUtils = new FileUtils();

			if (fileUtils.isFileExist(path + fileName)) {
				return 1;
			} else {
				inputStream = getInputStreamFromUrl(url);
				File resultFile = fileUtils.write2SDFromInput(path, fileName,
						inputStream);
				if (resultFile == null) {
					return -1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * ����URL�õ�������
	 * 
	 * @param urlStr
	 * @return
	 */
	public InputStream getInputStreamFromURLString(String urlStr) {
		InputStream inputStream = null;
		try {
			inputStream = getInputStreamFromUrl(new URL(urlStr));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	public InputStream getInputStreamFromUrl(URL url) {
		HttpURLConnection urlConn = null;
		InputStream inputStream = null;
		try {
			urlConn = (HttpURLConnection) url.openConnection();
			inputStream = urlConn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}
}