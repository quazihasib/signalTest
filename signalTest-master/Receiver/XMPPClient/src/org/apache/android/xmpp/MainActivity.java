package org.apache.android.xmpp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity 
{

	Button BtnTransmitter,BtnReceiver;
	public static MainActivity instance;
	
    @Override
    public void onCreate(Bundle savedInstancestate)
    {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.starter_main);
        
        instance=this;
        
        BtnTransmitter=(Button)findViewById(R.id.parent);
        BtnReceiver=(Button)findViewById(R.id.child);
        
        BtnReceiver.setOnClickListener(new View.OnClickListener()
        {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
			
				Intent mIntent=new Intent(v.getContext(),Receiver.class);
				startActivityForResult(mIntent, 0);
				setResult(0);
				finish();
			}
			
		});
        
        BtnTransmitter.setOnClickListener(new View.OnClickListener() 
        {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
			
				Intent nIntent=new Intent(v.getContext(),Transmitter.class);
				startActivityForResult(nIntent, 0);
				setResult(0);
				finish();
			}
			
		});
        
    }
	
	
}
