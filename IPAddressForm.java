package com.android.src.handwaving;

import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IPAddressForm extends Activity {
    /** Called when the activity is first created. */
	
	private StringTokenizer stringTokenizer = null;
	SqliteController sqliteController = null;
	
	  private String dbPass = "",dbEmailId="",dbPoliceNo="",dbGuardianNo="";
 	  private String dbEmergencyPass = "";
 	  private String dbIPAddress = "";
 	  private String dbValues = "";
 	  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipformxml);
        
   
        Button btn_ip_submit = (Button)findViewById(R.id.btn_ip_submit);
        Button btn_edit_Form = (Button)findViewById(R.id.btn_edit_Form);
        Button btn_registeration_Form = (Button)findViewById(R.id.btn_registeration_Form);
        
        btn_ip_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//String IPAddress = txt_ip_ipaddress.getText().toString();
				//HandWavingEntity.setIpaddress(IPAddress);
				finish();
				
				Intent intent = new Intent(IPAddressForm.this,HandWavingAct.class);
				startActivity(intent);
			}
		});
        
        btn_edit_Form.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent intent = new Intent(IPAddressForm.this,EditForm.class);
				startActivity(intent);
			}
		});
 
        btn_registeration_Form.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
			Intent intent = new Intent(IPAddressForm.this,Registeration.class);
			startActivity(intent);
		}
	});
 
        
    }

}
