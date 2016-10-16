package com.android.src.handwaving;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class HandWavingAct extends Activity implements SensorEventListener {
    TextView textView;
    StringBuilder builder = new StringBuilder();
    HttpResponse response;
	HttpClient httpclient;
	String responseText;
	static String pass = "";
	private StringTokenizer stringTokenizer = null;

    float [] history = new float[2];
    String [] direction = {"NONE","NONE"};
    
    boolean currentFocus;

	  // To keep track of activity's foreground/background status
	  boolean isPaused;

	  Handler collapseNotificationHandler;
	  
	  static int i = 0;

 	  private LayoutParams layoutParams;
 	  private String dbPass = "",dbEmailId="",dbPoliceNo="",dbGuardianNo="";
 	  private String dbEmergencyPass = "";
 	  private String dbIPAddress = "";
 	  private String dbValues = "";
 	
 	  

 	  
 	 public void onAttachedToWindow() {
  		// TODO Auto-generated method stub
  		  
  		//   getWindow().requestFeature(Window.FEATURE_NO_TITLE);   //new
  		  this.getWindow().setType(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG|WindowManager.LayoutParams.FLAG_FULLSCREEN);
  		 
  		  super.onAttachedToWindow();
  	 }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_FULLSCREEN);
        HomeKeyLocker mHomeKeyLocker = new HomeKeyLocker();
	        mHomeKeyLocker.lock(this);
        setContentView(R.layout.main);
       if(i==0){
        SqliteController sqlliteController = new SqliteController(this);
		String str  = sqlliteController.getFirstRow();
		
		if(str.equals("NOTAVAILABLE")){
			finish();
			Intent intent = new Intent(HandWavingAct.this,Registeration.class);
			startActivity(intent);
		}else if(str.equals("AVAILABLE")){
			dbValues = sqlliteController.getEmergencyPassword();
			if(dbValues!=null && dbValues.startsWith("VALID")){
				stringTokenizer = new StringTokenizer(dbValues,"$");
				stringTokenizer.nextToken();
				dbPass = stringTokenizer.nextToken();
				dbEmergencyPass = stringTokenizer.nextToken();
				dbEmailId = stringTokenizer.nextToken();
				dbPoliceNo = stringTokenizer.nextToken();
				dbGuardianNo = stringTokenizer.nextToken();
				dbIPAddress = stringTokenizer.nextToken();
				Toast.makeText(getApplicationContext(), dbIPAddress, 10).show();
				HandWavingEntity.setIpaddress(dbIPAddress);
				
				//	Intent intent = new Intent(HandWavingAct.this,EditForm.class);
					//startActivity(intent);
					
				
				
			}
		
			
		}
		
       }else{
    	   i++;
       }
        
        if(getIntent()!=null&&getIntent().hasExtra("kill")&&getIntent().getExtras().getInt("kill")==1){
	         	finish();
	    	}

  try{



  startService(new Intent(this,MyService.class));


  StateListener phoneStateListener = new StateListener();
  TelephonyManager telephonyManager =(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
  telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);

	   
	   
  }catch(Exception ex){
	  ex.printStackTrace();
	  System.out.println(ex);
  }
  
  
  
        Display display = ((WindowManager)this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
       
       
        switch (display.getRotation()) {
              case 0:
            	  
                  Toast.makeText(getApplicationContext(), "A", Toast.LENGTH_LONG).show();
                  pass = pass+"A";
                  break;
              case 1:
                  Toast.makeText(getApplicationContext(), "B", Toast.LENGTH_LONG).show();
                  pass = pass+"B";
                  break;
              case 2:
            	  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            	  Toast.makeText(getApplicationContext(), "C", Toast.LENGTH_LONG).show();
            	  pass = pass+"C";
                 
                 
                  break;
              case 3:
                
            	  Toast.makeText(getApplicationContext(), "C", Toast.LENGTH_LONG).show();
            	  pass = pass+"C";
                 break;
          }
        
        Toast.makeText(getApplicationContext(), "PASS........."+pass ,Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "dbPass........."+dbPass ,Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "dbEmergencyPass........."+dbEmergencyPass ,Toast.LENGTH_LONG).show();
        
		
		
        if(pass.trim().indexOf(dbPass.trim())>-1)
			{
			Toast.makeText(this, "********Correct sequence Found*******", Toast.LENGTH_SHORT).show();
			// dd.setText("Correct Sequence Found");
			pass = "";
			finish();
			
			Intent i = new Intent();
	         i.setAction(Intent.ACTION_MAIN);
	         i.addCategory(Intent.CATEGORY_HOME);
	         startActivity(i); 
			
			}else if(pass.trim().indexOf(dbEmergencyPass.trim())>-1)
			{
				Toast.makeText(this, "********Emergency*******", Toast.LENGTH_SHORT).show();
				// dd.setText("Correct Sequence Found");
				//HandWavingEntity.setIpaddress(dbIPAddress);
				HandWavingEntity.setEmailId(dbEmailId);
				HandWavingEntity.setGuardianNo(dbGuardianNo);
				HandWavingEntity.setPoliceNo(dbPoliceNo);
				pass = "";
				finish();
				Intent intent = new Intent(HandWavingAct.this,Girlssafety.class);
				startActivity(intent);
				
				}
		
    }

  
    public void onSensorChanged(SensorEvent event) {

  /*      float xChange = history[0] - event.values[0];
        float yChange = history[1] - event.values[1];

        history[0] = event.values[0];
        history[1] = event.values[1];

        if (xChange > 2){
          direction[0] = "A";
        }
        else if (xChange < -2){
          direction[0] = "B";
        }

        if (yChange > 2){
          direction[1] = "C";
        }
        else if (yChange < -2){
          direction[1] = "D";
        }

        builder.setLength(0);
        builder.append("x: ");
        builder.append(direction[0]);
        builder.append(" y: ");
        builder.append(direction[1]);

        textView.setText(builder.toString());
    }*/
    	
    	
    	Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int orientation = display.getRotation();
        Toast.makeText(getApplicationContext(),"TEST......"+orientation ,Toast.LENGTH_LONG).show();
        if (orientation == 1) {
            /* The device is rotated to the left. */
          Log.v("Left", "Rotated Left");
          Toast.makeText(getApplicationContext(), "Rotated Left",Toast.LENGTH_LONG).show();
        } else if (orientation == 3) {
            /* The device is rotated to the right. */
       Log.v("Right", "Rotated Right");
       Toast.makeText(getApplicationContext(), "Rotated right",Toast.LENGTH_LONG).show();
       } 
    	
    	//if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            // return;
    	/*float mSensorX, mSensorY;
    	Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	Toast.makeText(getApplicationContext(), display.getRotation(), Toast.LENGTH_SHORT).show();
       /*   switch (display.getRotation()) {
              case Surface.ROTATION_0:
                  mSensorX = event.values[0];
                  mSensorY = event.values[1];
                  Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_LONG).show();
                  break;
              case Surface.ROTATION_90:
                  mSensorX = -event.values[1];
                  mSensorY = event.values[0];
                  Toast.makeText(getApplicationContext(), "90", Toast.LENGTH_LONG).show();
                  break;
              case Surface.ROTATION_180:
                  mSensorX = -event.values[0];
                  mSensorY = -event.values[1];
                  Toast.makeText(getApplicationContext(), "180", Toast.LENGTH_LONG).show();
                  break;
              case Surface.ROTATION_270:
                  mSensorX = event.values[1];
                  mSensorY = -event.values[0];
                 Toast.makeText(getApplicationContext(), "270", Toast.LENGTH_LONG).show();
                 break;
          }*/
           
    }

    
    

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nothing to do here
    }
    
    
    class StateListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            super.onCallStateChanged(state, incomingNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    System.out.println("call Activity off hook");
                	finish();



                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
            }
        }
    };
    
    
    public void onWindowFocusChanged(boolean hasFocus) {

        currentFocus = hasFocus;

        if (!hasFocus) {

            // Method that handles loss of window focus
            //collapseNow();
            
           windowCloseHandler.postDelayed(windowCloserRunnable, 250);
        }
    }
    
    
    public void collapseNow() {
    	//windowCloseHandler.postDelayed(windowCloserRunnable, 250);
        // Initialize 'collapseNotificationHandler'
        if (collapseNotificationHandler == null) {
            collapseNotificationHandler = new Handler();
        }

        // If window focus has been lost && activity is not in a paused state
        // Its a valid check because showing of notification panel
        // steals the focus from current activity's window, but does not 
        // 'pause' the activity
        if (!currentFocus && !isPaused) {

            // Post a Runnable with some delay - currently set to 300 ms
            collapseNotificationHandler.postDelayed(new Runnable() {

              
                public void run() {

                    // Use reflection to trigger a method from 'StatusBarManager'                

                    Object statusBarService = getSystemService("statusbar");
                    Class<?> statusBarManager = null;

                    try {
                        statusBarManager = Class.forName("android.app.StatusBarManager");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    Method collapseStatusBar = null;

                    try {

                        // Prior to API 17, the method to call is 'collapse()'
                        // API 17 onwards, the method to call is `collapsePanels()`

                        if (Build.VERSION.SDK_INT > 16) {
                            collapseStatusBar = statusBarManager .getMethod("collapsePanels");
                        } else {
                            collapseStatusBar = statusBarManager .getMethod("collapse");
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    collapseStatusBar.setAccessible(true);

                    try {
                        collapseStatusBar.invoke(statusBarService);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    // Check if the window focus has been returned
                    // If it hasn't been returned, post this Runnable again
                    // Currently, the delay is 100 ms. You can change this
                    // value to suit your needs.
                    if (!currentFocus && !isPaused) {
                        collapseNotificationHandler.postDelayed(this, 100L);
                    }

                }
            }, 300L);
        }   
    }
    
    
    private void toggleRecents() {
	     Intent closeRecents = new Intent("com.android.systemui.recent.action.TOGGLE_RECENTS");
	     closeRecents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
	     ComponentName recents = new ComponentName("com.android.systemui", "com.android.systemui.recent.RecentsActivity");
	     closeRecents.setComponent(recents);
	     this.startActivity(closeRecents);
	 }

    
    private Handler windowCloseHandler = new Handler();
	 private Runnable windowCloserRunnable = new Runnable() {
	 
	     public void run() {
	         ActivityManager am = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
	         ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

	         if (cn != null && cn.getClassName().equals("com.android.systemui.recent.RecentsActivity")) {
	             toggleRecents();
	         }
	     }
	 };
	
   public void onDestroy(){
     super.onDestroy();
   }
      
    
    @Override
    public void onBackPressed() {
        // Don't allow back to dismiss.
        return;
    }

    //only used in lockdown mode
    @Override
    protected void onPause() {
         	super.onPause();
         	
         	  isPaused = true;
    	//return ;
    }

    @Override
    protected void onStop() {
        super.onStop();
    	//return;

        // Don't hang around.
       // finish();
    }


    protected void onResume() {
        super.onResume();

        // Activity's been resumed
        isPaused = false;
    }
}