package com.android.src.handwaving;


/*import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;



import java.io.File;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import java.io.FileNotFoundException;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore.Images.Media;
import android.content.ContentValues;
import android.database.Cursor;

public class PreviewActivity extends Activity implements SurfaceHolder.Callback, Camera.ShutterCallback, Camera.PictureCallback {

    Camera mCamera;
    SurfaceView mPreview;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    private Uri fileUri;
    
     private Socket client;
     private FileInputStream fileInputStream;
	 private OutputStream outputStream = null;
	 private InputStream inStream = null;
	 
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mPreview = (SurfaceView)findViewById(R.id.preview);
        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
       // mCamera = Camera.open();// Back End Camera 
        
 int n = getFrontCameraId();
        if(n==-1){
        	Toast.makeText(getApplicationContext(), "Back Camera....."+n, Toast.LENGTH_LONG).show();
          mCamera = Camera.open(0);
        }else{
        	Toast.makeText(getApplicationContext(), "Front Camera....."+n, Toast.LENGTH_LONG).show();
        	 mCamera = Camera.open(1);
        }
    }
    
    @Override
    public void onPause() {
        super.onPause();
        mCamera.stopPreview();
    }
    
   
    
    public void onCancelClick(View v) {
        finish();
    }
    
    public void onSnapClick(View v) {
      // mCamera.takePicture(this, null, null, this);
    }


    public void onShutter() {
        Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();
    }

  
  public void onPictureTaken(byte[] data, Camera camera) {
        //Here, we chose internal storage
    	
    	  
    	       
    	         //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
	  
	 Camera.Parameters params = mCamera.getParameters();
      List<Camera.Size> sizes = params.getSupportedPreviewSizes();
      Camera.Size selected = sizes.get(0);
      params.setPreviewSize(selected.width,selected.height);
      mCamera.setParameters(params);
      
      mCamera.setDisplayOrientation(90);
      mCamera.startPreview();
    	
    	 File mediaStorageDir = new File(
                 Environment
                         .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                 IMAGE_DIRECTORY_NAME);
  
         // Create the storage directory if it does not exist
         if (!mediaStorageDir.exists()) {
             if (!mediaStorageDir.mkdirs()) {
                 Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                         + IMAGE_DIRECTORY_NAME + " directory");
                
             }
         }
    	        
    	         Uri uriTarget = getContentResolver().insert//(Media.EXTERNAL_CONTENT_URI, image);
    	            (Media.EXTERNAL_CONTENT_URI, new ContentValues());
    	         	Toast.makeText(getApplicationContext(), "URI..."+uriTarget, Toast.LENGTH_LONG).show();
          OutputStream imageFileOS;
          try {
              imageFileOS = getContentResolver().openOutputStream(uriTarget);
              imageFileOS.write(data);
              imageFileOS.flush();
              imageFileOS.close();

              String path=mediaStorageDir.getPath()+"/IMG.jpg";
              Toast.makeText(getApplicationContext(), "path..."+path, Toast.LENGTH_LONG).show();
	             try{
	            	 Intent intent = new Intent(PreviewActivity.this,LockerApp.class);
	            	 startActivity(intent);
	            	 Toast.makeText(getApplicationContext(), "path..."+path, Toast.LENGTH_LONG).show();
	              }catch(Exception ex){
	            	  ex.printStackTrace();
	            	  System.out.println(ex);
	              }    
              
          }
          catch (FileNotFoundException e) {
              e.printStackTrace();
          }catch (IOException e) {
              e.printStackTrace();
          }
      // camera.startPreview();
         // bmp = BitmapFactory.decodeByteArray(data, 0, data.length);  
          //iv_image.setImageBitmap(bmp);  
    }
    
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
      Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        params.setPreviewSize(selected.width,selected.height);
        mCamera.setParameters(params);
        
        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();
        
        Camera.PictureCallback mCall = new Camera.PictureCallback()  
        {  
        	 public void onPictureTaken(byte[] data, Camera camera) {
        	        //Here, we chose internal storage
        	    	
        	    	  
        	    	       
        	    	         //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        	    	
        	    	 File mediaStorageDir = new File(
        	                 Environment
        	                         .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        	                 IMAGE_DIRECTORY_NAME);
        	  
        	         // Create the storage directory if it does not exist
        	         if (!mediaStorageDir.exists()) {
        	             if (!mediaStorageDir.mkdirs()) {
        	                 Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
        	                         + IMAGE_DIRECTORY_NAME + " directory");
        	                
        	             }
        	         }
        	        // Uri.fromFile();
        	    	        
        	    	   Uri uriTarget = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);//getContentResolver().insert
        	    	   //(getOutputMediaFileUri(MEDIA_TYPE_IMAGE), new ContentValues());
        	    	   	Toast.makeText(getApplicationContext(), "URI..."+uriTarget, Toast.LENGTH_LONG).show();
        	          OutputStream imageFileOS;
        	          try {
        	              imageFileOS = getContentResolver().openOutputStream(uriTarget);
        	              imageFileOS.write(data);
        	              imageFileOS.flush();
        	              imageFileOS.close();

        	            String path=mediaStorageDir.getPath()+"/IMG.jpg";
        	            Toast.makeText(getApplicationContext(), "path..."+path, Toast.LENGTH_LONG).show();
        	             try{
        	            	 finish();
        	            	 Thread.sleep(1000);
        	            	 Intent intent = new Intent(PreviewActivity.this,AudioRecord.class);
    	            		 startActivity(intent);
        	            	 Toast.makeText(getApplicationContext(), "path..."+path, Toast.LENGTH_LONG).show();
        	              }catch(Exception ex){
        	            	  ex.printStackTrace();
        	            	  System.out.println(ex);
        	              }
        	              
        	            
        	              
        	          }
        	          catch (FileNotFoundException e) {
        	              e.printStackTrace();
        	          }catch (IOException e) {
        	              e.printStackTrace();
        	          }catch (Exception e) {
        	              e.printStackTrace();
        	            
        	          }
        	       //camera.startPreview();
        	          //bmp = BitmapFactory.decodeByteArray(data, 0, data.length);  
        	          //iv_image.setImageBitmap(bmp);  
        	    }
        }; mCamera.takePicture(null, null, mCall);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(mPreview.getHolder());
        	
        	 int index = getFrontCameraId();
        	 Toast.makeText(getApplicationContext(), "index...."+index, Toast.LENGTH_LONG).show();
             if (index == -1){
        	        //Toast.makeText(getApplicationContext(), "No front camera", Toast.LENGTH_LONG).show();
        	        mCamera = Camera.open(CameraInfo.CAMERA_FACING_BACK);
        	    }
        	    else
        	    {
        	        mCamera = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
        	       // Toast.makeText(getApplicationContext(), "With front camera", Toast.LENGTH_LONG).show();
        	    }
        	    //  mCamera = Camera.open(1);  // front end camera
        	      try {  
        	         mCamera.setPreviewDisplay(holder);  

        	      } catch (IOException exception) {  
        	          mCamera.release();  
        	          mCamera = null;  
        	      }  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("PREVIEW","surfaceDestroyed");
        mCamera.stopPreview();  
        mCamera.release();  
        mCamera = null;  
    }
    
    int getFrontCameraId() {
        CameraInfo ci = new CameraInfo();
        for (int i = 0 ; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, ci);
            if (ci.facing == CameraInfo.CAMERA_FACING_FRONT) return i;
            }
    return -1; // No front-facing camera found
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    private static File getOutputMediaFile(int type) {
    	 
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
 
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
 
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
           // mediaFile = new File(mediaStorageDir.getPath() + File.separator
             //       + "IMG_" + timeStamp + ".jpg");
        	mediaFile = new File(mediaStorageDir.getPath() + File.separator
                          + "IMG.jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
 
        return mediaFile;
    }
    
    
  
}*/


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;



import java.io.File;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import java.io.FileNotFoundException;
import java.net.Socket;
import java.net.UnknownHostException;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore.Images.Media;
import android.content.ContentValues;
import android.database.Cursor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;



import java.io.File;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import java.io.FileNotFoundException;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore.Images.Media;
import android.content.ContentValues;
import android.database.Cursor;

public class PreviewActivity extends Activity implements SurfaceHolder.Callback, Camera.ShutterCallback, Camera.PictureCallback {

    Camera mCamera;
    SurfaceView mPreview;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    private Uri fileUri;
    
     private Socket client;
	 private FileInputStream fileInputStream;
	 private OutputStream outputStream = null;
	 private InputStream inStream = null;
	 
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mPreview = (SurfaceView)findViewById(R.id.preview);
        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
       // mCamera = Camera.open();// Back End Camera 
        
     int n = getFrontCameraId();
        if(n==-1){
        	Toast.makeText(getApplicationContext(), "Back Camera....."+n, Toast.LENGTH_LONG).show();
          mCamera = Camera.open(0);
        }else{
        	Toast.makeText(getApplicationContext(), "Front Camera....."+n, Toast.LENGTH_LONG).show();
        	 mCamera = Camera.open(1);
        }
    }
    
    @Override
    public void onPause() {
        super.onPause();
        mCamera.stopPreview();
    }
    
   
    
    public void onCancelClick(View v) {
        finish();
    }
    
    public void onSnapClick(View v) {
      //  mCamera.takePicture(this, null, null, this);
    }


    public void onShutter() {
        Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();
    }

  
    public void onPictureTaken(byte[] data, Camera camera) {
        //Here, we chose internal storage
    	
    	  
    	       
    	         //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
    	
    	 File mediaStorageDir = new File(
                 Environment
                         .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                 IMAGE_DIRECTORY_NAME);
  
         // Create the storage directory if it does not exist
         if (!mediaStorageDir.exists()) {
             if (!mediaStorageDir.mkdirs()) {
                 Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                         + IMAGE_DIRECTORY_NAME + " directory");
                
             }
         }
    	        
    	         Uri uriTarget = getContentResolver().insert//(Media.EXTERNAL_CONTENT_URI, image);
    	            (Media.EXTERNAL_CONTENT_URI, new ContentValues());
    	         	Toast.makeText(getApplicationContext(), "URI..."+uriTarget, Toast.LENGTH_LONG).show();
          OutputStream imageFileOS;
          try {
              imageFileOS = getContentResolver().openOutputStream(uriTarget);
              imageFileOS.write(data);
              imageFileOS.flush();
              imageFileOS.close();

                       
              
          }
          catch (FileNotFoundException e) {
              e.printStackTrace();
          }catch (IOException e) {
              e.printStackTrace();
          }
      // camera.startPreview();
         // bmp = BitmapFactory.decodeByteArray(data, 0, data.length);  
          //iv_image.setImageBitmap(bmp);  
    }
    
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        params.setPreviewSize(selected.width,selected.height);
        mCamera.setParameters(params);
        
        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();
        
        Camera.PictureCallback mCall = new Camera.PictureCallback()  
        {  
        	 public void onPictureTaken(byte[] data, Camera camera) {
        	        //Here, we chose internal storage
        	    	
        	    	  
        	    	       
        	    	         //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        	    	
        	    	 File mediaStorageDir = new File(
        	                 Environment
        	                         .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        	                 IMAGE_DIRECTORY_NAME);
        	  
        	         // Create the storage directory if it does not exist
        	         if (!mediaStorageDir.exists()) {
        	             if (!mediaStorageDir.mkdirs()) {
        	                 Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
        	                         + IMAGE_DIRECTORY_NAME + " directory");
        	                
        	             }
        	         }
        	        // Uri.fromFile();
        	    	        
        	    	   Uri uriTarget = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);//getContentResolver().insert
        	    	   //(getOutputMediaFileUri(MEDIA_TYPE_IMAGE), new ContentValues());
        	    	   	Toast.makeText(getApplicationContext(), "URI..."+uriTarget, Toast.LENGTH_LONG).show();
        	          OutputStream imageFileOS;
        	          try {
        	              imageFileOS = getContentResolver().openOutputStream(uriTarget);
        	              imageFileOS.write(data);
        	              imageFileOS.flush();
        	              imageFileOS.close();

        	            String path=mediaStorageDir.getPath()+"/IMG.jpg";
        	             try{
        	            	 
        	            	// SendMailTLS.sendMail(path,ServerIPAddress.getEmailId(),ServerIPAddress.getPassword());
        	            	 String reply = getServerCommunication("IMAGE",path);
        	            	 if(reply!=null && reply.startsWith("STORED")){
        	            		 SmsManager smsManager = SmsManager.getDefault();
        	            		 smsManager.sendTextMessage(HandWavingEntity.getGuardianNo(),null,"I am In Trouble... "+"http://"+HandWavingEntity.getIpaddress()+":8080/Handwaving/StoredImage/IMG.jpg",null,null);
        	            		 smsManager.sendTextMessage(HandWavingEntity.getPoliceNo(),null,"I am In Trouble... "+"http://"+HandWavingEntity.getIpaddress()+":8080/Handwaving/StoredImage/IMG.jpg",null,null);
        	            		 finish();
        	            		 Intent intent = new Intent(PreviewActivity.this,AudioRecord.class);
        	            		 startActivity(intent);
        	            	 }else{
        	            		 Toast.makeText(getApplicationContext(), "Image not stored in server",Toast.LENGTH_LONG).show();
        	            	 }
        	              }catch(Exception ex){
        	            	  ex.printStackTrace();
        	            	  System.out.println(ex);
        	              }
        	              
        	            
        	              
        	          }
        	          catch (FileNotFoundException e) {
        	              e.printStackTrace();
        	          }catch (IOException e) {
        	              e.printStackTrace();
        	          }
        	       //camera.startPreview();
        	          //bmp = BitmapFactory.decodeByteArray(data, 0, data.length);  
        	          //iv_image.setImageBitmap(bmp);  
        	    }
        }; mCamera.takePicture(null, null, mCall);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(mPreview.getHolder());
        	
        	 int index = getFrontCameraId();
        	 Toast.makeText(getApplicationContext(), "index...."+index, Toast.LENGTH_LONG).show();
             if (index == -1){
        	        //Toast.makeText(getApplicationContext(), "No front camera", Toast.LENGTH_LONG).show();
        	        mCamera = Camera.open(CameraInfo.CAMERA_FACING_BACK);
        	    }
        	    else
        	    {
        	        mCamera = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
        	       // Toast.makeText(getApplicationContext(), "With front camera", Toast.LENGTH_LONG).show();
        	    }
        	    //  mCamera = Camera.open(1);  // front end camera
        	      try {  
        	         mCamera.setPreviewDisplay(holder);  

        	      } catch (IOException exception) {  
        	          mCamera.release();  
        	          mCamera = null;  
        	      }  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("PREVIEW","surfaceDestroyed");
        mCamera.stopPreview();  
        mCamera.release();  
        mCamera = null;  
    }
    
    int getFrontCameraId() {
        CameraInfo ci = new CameraInfo();
        for (int i = 0 ; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, ci);
            if (ci.facing == CameraInfo.CAMERA_FACING_FRONT) return i;
            }
    return -1; // No front-facing camera found
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    private static File getOutputMediaFile(int type) {
    	 
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
 
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
 
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
           // mediaFile = new File(mediaStorageDir.getPath() + File.separator
             //       + "IMG_" + timeStamp + ".jpg");
        	mediaFile = new File(mediaStorageDir.getPath() + File.separator
                          + "IMG.jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
 
        return mediaFile;
    }
    
    
    public String getServerCommunication(String action,String imagePath){
    	String replyString = null;
		 File file = new File(imagePath);
	 try {
	      
	     
		 client = new Socket( HandWavingEntity.getIpaddress(),4444);
		 ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
		 fileInputStream = new FileInputStream(file);
		 byte[] mybytearray = new byte[(int) file.length()];
		 fileInputStream.read(mybytearray);
		 fileInputStream.close();
		 if(action.equals("IMAGE")){
		   out.writeObject(("IMAGE$"+HandWavingEntity.getEmailId()).getBytes());
		   out.writeObject(mybytearray);
		 }
		 
		 ObjectInputStream oin = new ObjectInputStream(client.getInputStream());
		  replyString = new String((byte [])oin.readObject());
		 Toast.makeText(getApplicationContext(), "Reply String..."+replyString, Toast.LENGTH_LONG).show();
	 }catch(Exception ex){
		 ex.printStackTrace();
		 System.out.println(ex);
	 }
	 return replyString;
    }
}
