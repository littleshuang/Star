package activity;

import utils.UpdateManager;
import android.app.Activity;
import android.os.Bundle;

import com.star.R;

public class DownFile extends Activity {

	private UpdateManager mUpdateManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_async_task_test);

		// ���������汾�Ƿ���Ҫ����
		mUpdateManager = new UpdateManager(this);
		mUpdateManager.checkUpdateInfo();
	}
}