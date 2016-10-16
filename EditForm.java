package com.android.src.handwaving;

import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditForm extends Activity {
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
        setContentView(R.layout.editxml);
        sqliteController = new SqliteController(this);
        String details = sqliteController.getEmergencyPassword();
       Toast.makeText(getApplicationContext(), details, Toast.LENGTH_SHORT).show();
		if(details!=null && details.startsWith("VALID")){
			stringTokenizer = new StringTokenizer(details,"$");
			stringTokenizer.nextToken();
			 dbPass = stringTokenizer.nextToken();
			 dbEmergencyPass = stringTokenizer.nextToken();
			dbEmailId = stringTokenizer.nextToken();
			dbPoliceNo = stringTokenizer.nextToken();
			dbGuardianNo = stringTokenizer.nextToken();
			dbIPAddress = stringTokenizer.nextToken();
			
			
		}
        
        Button btn_submit = (Button)findViewById(R.id.btn_edit_submit);
      /*  Button btn_edit_ip_submit = (Button)findViewById(R.id.btn_edit_ip_submit);*/
        
        final EditText txt_password = (EditText)findViewById(R.id.txt_edit_password);
        final EditText txt_emergency_password = (EditText)findViewById(R.id.txt_edit_emergency_password);
        final EditText txt_edit_emailid = (EditText)findViewById(R.id.txt_edit_emailid);
        
        final EditText txt_police_No = (EditText)findViewById(R.id.txt_edit_policeNo);
        final EditText txt_guardian_No = (EditText)findViewById(R.id.txt_edit_guardianNo);
        final EditText txt_ipaddress = (EditText)findViewById(R.id.txt_edit_ipaddress);
        txt_password.setText(dbPass);
        txt_emergency_password.setText(dbEmergencyPass);
        txt_edit_emailid.setText(dbEmailId);
        txt_police_No.setText(dbPoliceNo);
        txt_guardian_No.setText(dbGuardianNo);
        txt_ipaddress.setText(dbIPAddress);
        
        
        btn_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pass = txt_password.getText().toString();
				String emerPass = txt_emergency_password.getText().toString();
				String emailId = txt_edit_emailid.getText().toString();
				String policeNo = txt_police_No.getText().toString();
				String guardianNo = txt_guardian_No.getText().toString();
				String ipaddress = txt_ipaddress.getText().toString();
								
				if(!pass.equals("") && !emerPass.equals("")&& !policeNo.equals("") && !guardianNo.equals("") && !ipaddress.equals("")){
					int i = sqliteController.updatePassword(pass, emerPass, emailId,policeNo, guardianNo, ipaddress);
					if(i>0){
						Toast.makeText(getApplicationContext(), "Values are updated successfully...", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(EditForm.this,IPAddressForm.class);
						startActivity(intent);
					}else{
						Toast.makeText(getApplicationContext(), "Values are not updated...", Toast.LENGTH_LONG).show();
					}
				}else{
					Toast.makeText(getApplicationContext(), "Please enter all details", Toast.LENGTH_LONG).show();
				}
			}
		});
        
     /*   btn_edit_ip_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(EditForm.this,IPAddressForm.class);
				startActivity(intent);
			}
		});*/
        
    }
}

