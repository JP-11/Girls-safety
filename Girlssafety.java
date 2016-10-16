package com.android.src.handwaving;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.widget.Toast;



public class Girlssafety extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.main);
        Toast.makeText(this, "Girls safety started", Toast.LENGTH_SHORT).show();
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	 switch(keyCode){
    	 
    	   case KeyEvent.KEYCODE_VOLUME_UP:
    		   Toast.makeText(this, "Volume Up pressed", Toast.LENGTH_SHORT).show();
    		   SmsManager smsManager = SmsManager.getDefault();
      		 smsManager.sendTextMessage(HandWavingEntity.getGuardianNo(),null,"I am In Trouble... "+"http://"+HandWavingEntity.getIpaddress()+":8080/Handwaving/StoredImage/IMG.jpg",null,null);
      		 smsManager.sendTextMessage(HandWavingEntity.getPoliceNo(),null,"I am In Trouble... "+"http://"+HandWavingEntity.getIpaddress()+":8080/Handwaving/StoredImage/IMG.jpg",null,null);
    			   Intent intent = new Intent(Girlssafety.this,PreviewActivity.class);
    			   startActivity(intent);
    		  
    	   
    	     event.startTracking();
    	     return true;
    	   case KeyEvent.KEYCODE_VOLUME_DOWN:
    	     Toast.makeText(this,"Volumen Down pressed", Toast.LENGTH_SHORT).show();
    	     SmsManager smsManager1 = SmsManager.getDefault();
    		 smsManager1.sendTextMessage(HandWavingEntity.getGuardianNo(),null,"I am In Trouble... "+"http://"+HandWavingEntity.getIpaddress()+":8080/Handwaving/StoredImage/IMG.jpg",null,null);
    		 smsManager1.sendTextMessage(HandWavingEntity.getPoliceNo(),null,"I am In Trouble... "+"http://"+HandWavingEntity.getIpaddress()+":8080/Handwaving/StoredImage/IMG.jpg",null,null);
  			  Intent intent1 = new Intent(Girlssafety.this,PreviewActivity.class);
  			  startActivity(intent1);
  		  
  		
    	     return true;
    	     
    	         	    	 
    	 }
    	 return super.onKeyDown(keyCode, event);
    	}
    
   /* @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
     switch(keyCode){
       case KeyEvent.KEYCODE_MENU:
         Toast.makeText(this, "Menu key released", Toast.LENGTH_SHORT).show();
         return true;
       case KeyEvent.KEYCODE_SEARCH:
         Toast.makeText(this, "Search key released", Toast.LENGTH_SHORT).show();
         return true;
       case KeyEvent.KEYCODE_VOLUME_UP:​
         if(event.isTracking() && !event.isCanceled())
           Toast.makeText(this, "Volume Up released", Toast.LENGTH_SHORT).show();
           return true;
       case KeyEvent.KEYCODE_VOLUME_DOWN:
         Toast.makeText(this, "Volume Down released", Toast.LENGTH_SHORT).show();
         return true;
       }
       return super.onKeyDown(keyCode, event);
    }*/
    
    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
     Toast.makeText(this, "Pressed for a long time =) ", Toast.LENGTH_SHORT).show();
     return true;
    }
    
   /* @Override
    public void onBackPressed() {
     Toast.makeText(this, "Back key pressed =)", Toast.LENGTH_SHORT).show();
     super.onBackPressed();
     return false;
    }*/
    
    @Override
    public void onBackPressed() {
        // Don't allow back to dismiss.
    	Toast.makeText(this, "Back key pressed =)", Toast.LENGTH_SHORT).show();
        return;
    }

}