package cse.msu.edu.project3;



import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private LocationManager locationManager = null;
    
    private double latitude = 0;
    private double longitude = 0;
    private boolean valid = false;
    
    private double spartyLat = 42.724303;
    private double spartyLong = -84.480507;
    
    private double beaumontLat = 42.732031;
    private double beaumontLong = -84.482177;
    
    private double breslinLat = 42.728191;
    private double breslinLong = -84.492277;
    
 
    public float[] distance = new float[3];
    

    
    private ActiveListener activeListener = new ActiveListener();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("Project 3");
		
        
        // Get the location manager
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        // Force the screen to say on and bright
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
	} 
	
  @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}







private void setUI() {
	  TextView longitudeDummy = (TextView)findViewById(R.id.longitudeSparty);
	  TextView latitudeDummy = (TextView)findViewById(R.id.latitudeSparty);
	  TextView distanceSparty = (TextView)findViewById(R.id.distanceSparty);
	  TextView distanceBeaumont = (TextView)findViewById(R.id.distanceBeaumont);
	  TextView distanceBreslin = (TextView)findViewById(R.id.distanceBreslin);
	  
	  
	  if(!valid)
	  {
		  //set values to ""
		  distanceBeaumont.setText("");
		  distanceBreslin.setText("");
		  distanceSparty.setText("");
		  longitudeDummy.setText("");
		  latitudeDummy.setText(""); 

	  }else{
		  //set values and compute distance
		  
		  
		  longitudeDummy.setText(""+longitude);
		  latitudeDummy.setText(""+latitude); 
		  Location.distanceBetween(latitude, longitude, spartyLat, spartyLong, distance); 
		  distanceSparty.setText(String.format("%1$6.1fm", distance[0]));
		  
		  Location.distanceBetween(latitude, longitude, beaumontLat, beaumontLong, distance); 
		  distanceBeaumont.setText(String.format("%1$6.1fm", distance[0]));
		  
		  Location.distanceBetween(latitude, longitude, breslinLat, breslinLong, distance); 
		  distanceBreslin.setText(String.format("%1$6.1fm", distance[0]));
		  
	  }
        
    }


  

    /**
     * Called when this application becomes foreground again.
     */
    @Override
    protected void onResume() {
        super.onResume();
        
        TextView viewProvider = (TextView)findViewById(R.id.providerDummy);
        viewProvider.setText("");
        
        setUI();  
        registerListeners();
    }

    /**
     * Called when this application is no longer the foreground application.
     */
    @Override
    protected void onPause() {
        unregisterListeners();
        super.onPause();
    }
	
    
    
    
    private void registerListeners() {
        unregisterListeners();
        
        // Create a Criteria object
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(false);
        
        String bestAvailable = locationManager.getBestProvider(criteria, true);

        if(bestAvailable != null) {
            locationManager.requestLocationUpdates(bestAvailable, 500, 1, activeListener);
            TextView viewProvider = (TextView)findViewById(R.id.providerDummy);
            viewProvider.setText(bestAvailable);
            Location location = locationManager.getLastKnownLocation(bestAvailable);
            onLocation(location);
        }
    }
    
    private void unregisterListeners() {
        locationManager.removeUpdates(activeListener);

    } 
    
    
    
    private void onLocation(Location location) {
        if(location == null) {
            return;
        }
        
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        valid = true;

        setUI();
    }
    
	
    private class ActiveListener implements LocationListener {

    	
        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
        	onLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        	registerListeners();
            
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        	registerListeners();
            
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
            
        }
        
    };
	
	
	
	
}
