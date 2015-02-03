package io.urqa.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.example.urqafunctiontest.R;
import com.urqa.clientinterface.URQAController;
import com.urqa.common.StateData;
import com.urqa.eventpath.EventPathManager;

public class MainActivity extends Activity {


	Toast myToast;
	
	/*
	 * proguard 占쏙옙占쎈만 占싹몌옙占�
	 * 占쌓뤄옙占쏙옙 git push占쌀곤옙
	 * (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		//占쏙옙占쏙옙 占십깍옙화 
		StateData.AppContext = getBaseContext();
		StateData.ServerAddress = "http://ur-qa.com:33335/urqa/";
		StateData.APIKEY = "5A901452";
		URQAController.InitializeAndStartSession(getBaseContext(), StateData.APIKEY);
		
		findViewById(R.id.sessionBtn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				URQAController.SendSession(getBaseContext(), StateData.APIKEY);
			}
		});
		
		findViewById(R.id.exceptionBtn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {
					int i = 0;
					int c = 3;
					int d = c/i;
				} catch(Exception e) {
					URQAController.SendException(e);
				}
			}
		});
		


	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	public void setOnClick(View v) {
		int localViewId = v.getId();
		switch(localViewId) {
		case R.id.pathABtn:
			URQAController.leaveBreadcrumb("a");
			break;
		case R.id.pathBBtn:
			URQAController.leaveBreadcrumb("b");
			break;
		case R.id.pathCBtn:
			URQAController.leaveBreadcrumb("c");
			break;
		case R.id.clear:
			EventPathManager.ClearEvent();
		}
		
	}

}
