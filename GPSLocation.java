package com.android.src.handwaving;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class GPSLocation extends Activity {
    
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    String message = "",mobileNo = null;
    StringBuffer smsBody =  null;
    
    protected LocationManager locationManager;
    
    protected Button retrieveLocationButton;
    private int i =0;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(i==0){
       locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 
                MINIMUM_TIME_BETWEEN_UPDATES, 
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
                
                
        );
       
        }else{
        	i++;
        }
        
      showCurrentLocation();       
        
    }    

    protected void showCurrentLocation() {

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
             message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
             
             Toast.makeText(GPSLocation.this, message,
                     Toast.LENGTH_LONG).show();
             
             
             
       	 SmsManager smsManager = SmsManager.getDefault();
             Toast.makeText(getApplicationContext(), "Mobile No...."+HandWavingEntity.getGuardianNo(), Toast.LENGTH_LONG).show();
             Toast.makeText(getApplicationContext(), "Mobile No...."+HandWavingEntity.getPoliceNo(), Toast.LENGTH_LONG).show();
       	 smsBody = new StringBuffer();
       	 smsBody.append("http://maps.google.com?q=");
       	// smsBody.append(location.getLongitude());
       	smsBody.append(location.getLatitude());
       	 smsBody.append(",");
         //smsBody.append(location.getLatitude());
         smsBody.append(location.getLongitude());
         
       //	 String mobileNo = 
         Toast.makeText(GPSLocation.this, "Mobile No........."+HandWavingEntity.getGuardianNo(),
                 Toast.LENGTH_LONG).show();
       	 if(HandWavingEntity.getGuardianNo()!=null && HandWavingEntity.getPoliceNo()!=null){
       		 smsManager.sendTextMessage(HandWavingEntity.getGuardianNo(),null,"I am In Trouble... "+smsBody.toString(),null,null);
       		 smsManager.sendTextMessage(HandWavingEntity.getPoliceNo(),null,"I am In Trouble... "+smsBody.toString(),null,null);
             Toast.makeText(GPSLocation.this, smsBody.toString(),
                     Toast.LENGTH_LONG).show();
         }
       	 }else{
       		 Toast.makeText(getApplicationContext(), "No mobile No set", Toast.LENGTH_LONG).show();
       	 }
    	

    }   

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
           SmsManager smsManager = SmsManager.getDefault();
           
           	smsBody = new StringBuffer();
         	smsBody.append("http://maps.google.com?q=");
         	//smsBody.append(location.getLongitude());
         	smsBody.append(location.getLatitude());
         	smsBody.append(",");
         	//smsBody.append(location.getLatitude());
         	smsBody.append(location.getLongitude());
         	
      	    smsManager.sendTextMessage(HandWavingEntity.getGuardianNo(),null,"I am In Trouble... "+smsBody.toString(),null,null);
      	  smsManager.sendTextMessage(HandWavingEntity.getPoliceNo(),null,"I am In Trouble... "+smsBody.toString(),null,null);
              Toast.makeText(GPSLocation.this, smsBody.toString(), Toast.LENGTH_LONG).show();
        }

        public void onStatusChanged(String s, int i, Bundle b) {
            //Toast.makeText(GPSLocation.this, "Provider status changed",
                 //   Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            //Toast.makeText(GPSLocation.this,
                 //   "Provider disabled by the user. GPS turned off",
                 //   Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
           // Toast.makeText(GPSLocation.this,
                 //   "Provider enabled by the user. GPS turned on",
                //    Toast.LENGTH_LONG).show();
        }

    }
    
}
