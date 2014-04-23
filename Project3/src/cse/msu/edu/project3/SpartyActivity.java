package cse.msu.edu.project3;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class SpartyActivity extends Activity {

	private Context mContext;
	
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		
		setContentView(R.layout.sparty);
	}
	
	public Context getContext()
	{
		return mContext;
	}

	
	
}
