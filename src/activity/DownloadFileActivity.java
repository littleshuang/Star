package activity;

import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.star.DownTask;
import com.star.R;

public class DownloadFileActivity extends Activity implements OnClickListener {
	// ²¼¾Ö¿Ø¼þ
	private TextView mTextView;
	private Button mBtn;
	private String filePath;
	private String fileName;
	private String downUrl;
	private URL mUrl;
	private OutputStream outputStream = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		downUrl = "http://119.97.246.76:8089/CallAlert.apk";
		filePath = Environment.getExternalStorageDirectory()
				+ "/CallAlert/APK/";
		System.out.println(filePath);
		fileName = "CallAlert.apk";
		setContentView(R.layout.activity_async_task_test);
		mTextView = (TextView) findViewById(R.id.ast_tv);
		mBtn = (Button) findViewById(R.id.ast_btn);
		mBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.ast_btn) {
			DownTask downTask = new DownTask(this, filePath, fileName,
					mTextView);
			try {
				downTask.execute(new URL(downUrl));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}
}
