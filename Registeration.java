package com.android.src.handwaving;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registeration extends Activity {
    /** Called when the activity is first created. */
	
	SqliteController sqliteController = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerationxml);
        sqliteController = new SqliteController(this);
        
        Button btn_submit = (Button)findViewById(R.id.btn_submit);
        Button btn_reset = (Button)findViewById(R.id.btn_reset);
        final EditText txt_password = (EditText)findViewById(R.id.txt_password);
        final EditText txt_emergency_password = (EditText)findViewById(R.id.txt_emergency_password);
        final EditText txt_emailid = (EditText)findViewById(R.id.txt_emailid);
        final EditText txt_police_No = (EditText)findViewById(R.id.txt_police_No);
        final EditText txt_guardian_No = (EditText)findViewById(R.id.txt_guardian_No);
        final EditText txt_ipaddress = (EditText)findViewById(R.id.txt_ipaddress);
        
        btn_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pass = txt_password.getText().toString();
				String emerPass = txt_emergency_password.getText().toString();
				String emailId = txt_emailid.getText().toString();
				String policeNo = txt_police_No.getText().toString();
				String guardianNo = txt_guardian_No.getText().toString();
				String ipaddress = txt_ipaddress.getText().toString();
								
				if(!pass.equals("") && !emerPass.equals("")&& !policeNo.equals("") && !guardianNo.equals("") && !ipaddress.equals("")){
					long i = sqliteController.insertdetail(pass, emerPass,emailId,policeNo, guardianNo, ipaddress);
					if(i>0){
						Toast.makeText(getApplicationContext(), "Values are inserted successfully...", Toast.LENGTH_LONG).show();
						finish();
						Intent intent = new Intent(Registeration.this,IPAddressForm.class);
						startActivity(intent);
					}else{
						Toast.makeText(getApplicationContext(), "Values are not inserted...", Toast.LENGTH_LONG).show();
					}
				}else{
					Toast.makeText(getApplicationContext(), "Please enter all details", Toast.LENGTH_LONG).show();
				}
			}
		});
        
        btn_reset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txt_password.setText("");
				txt_emergency_password.setText("");
				txt_emailid.setText("");
				txt_police_No.setText("");
				txt_guardian_No.setText("");
				txt_ipaddress.setText("");
				
				
			}
		});
    }
}
