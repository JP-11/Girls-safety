package com.android.src.handwaving;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AudioRecord extends Activity {

	   private MediaRecorder myAudioRecorder;
	   private String outputFile = null;
	   private Button start,stop,play;
	   
	     private Socket client;
		 private FileInputStream fileInputStream;
		 private OutputStream outputStream = null;
		 private InputStream inStream = null;
		 
		 
		 
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.main);
	      outputFile = Environment.getExternalStorageDirectory().
	      getAbsolutePath() + "/AudioRecording.3gp";;

	      myAudioRecorder = new MediaRecorder();
	      myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	      myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	      myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
	      myAudioRecorder.setOutputFile(outputFile);
	      
	      try {
		         myAudioRecorder.prepare();
		         myAudioRecorder.start();
		         
		         Thread.sleep(12000);
		         
		         myAudioRecorder.stop();
			      myAudioRecorder.release();
			      myAudioRecorder  = null;
			      
			      try{
 	            	 String reply = getServerCommunication("AUDIO",outputFile);
 	            	 if(reply!=null && reply.startsWith("STORED")){
 	            		 SmsManager smsManager = SmsManager.getDefault();
 	            		 smsManager.sendTextMessage(HandWavingEntity.getGuardianNo(),null,"I am In Trouble... "+"http://"+HandWavingEntity.getIpaddress()+":8080/Handwaving/StoredImage/AudioRecording.3gp",null,null);
 	            		smsManager.sendTextMessage(HandWavingEntity.getPoliceNo(),null,"I am In Trouble... "+"http://"+HandWavingEntity.getIpaddress()+":8080/Handwaving/StoredImage/AudioRecording.3gp",null,null);
 	            		 finish();
 	            		 Intent intent = new Intent(AudioRecord.this,GPSLocation.class);
 	            		 startActivity(intent);
 	            	 }else{
 	            		 Toast.makeText(getApplicationContext(), "Image not stored in server",Toast.LENGTH_LONG).show();
 	            	 }
 	              }catch(Exception ex){
 	            	  ex.printStackTrace();
 	            	  System.out.println(ex);
 	              }
 	              
			      
		      } catch (IllegalStateException e) {
		         // TODO Auto-generated catch block
		         e.printStackTrace();
		      } catch (IOException e) {
		         // TODO Auto-generated catch block
		         e.printStackTrace();
		      }catch(Exception ex){
		    	  ex.printStackTrace();
		      }finally{
		    	 // finish();
			   //   Intent intent = new Intent(VoiceRecording.this,GPSLocation.class);
			     // startActivity(intent);
		      }

	   }

	   public void start(View view){
	      try {
	         myAudioRecorder.prepare();
	         myAudioRecorder.start();
	      } catch (IllegalStateException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      start.setEnabled(false);
	      stop.setEnabled(true);
	      Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();

	   }

	   public void stop(View view){
	      myAudioRecorder.stop();
	      myAudioRecorder.release();
	      myAudioRecorder  = null;
	      stop.setEnabled(false);
	      play.setEnabled(true);
	      Toast.makeText(getApplicationContext(), "Audio recorded successfully",
	      Toast.LENGTH_LONG).show();
	   }
	 
	   public void play(View view) throws IllegalArgumentException,   
	   SecurityException, IllegalStateException, IOException{
	   
	   MediaPlayer m = new MediaPlayer();
	   m.setDataSource(outputFile);
	   m.prepare();
	   m.start();
	   Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();

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
			 if(action.equals("AUDIO")){
			   out.writeObject(("AUDIO").getBytes());
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