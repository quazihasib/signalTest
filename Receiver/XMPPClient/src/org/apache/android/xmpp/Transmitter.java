package org.apache.android.xmpp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;

public class Transmitter extends Activity 
{
	//creating xmppConnection instance
	public static XMPPConnection connection;
	//creating xmppLogin instance for login
	public static XmppLogin transmitterLogin;
	public static String DEBUG_TAG = Transmitter.class.getSimpleName();
	
	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		Log.d(DEBUG_TAG, "onCreate called");
		setContentView(R.layout.transmitter_main);
		
		// Dialog for getting the xmpp settings
		transmitterLogin = new XmppLogin(this);
		
		//start login connection using transmitter id
		XmppLogin.SetLoginConnection("transmitterid@gmail.com", "transmitter123", 1);

		// Send signal1
		Button send1 = (Button) this.findViewById(R.id.sendSignal1);
		send1.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view) 
			{
				sendSignal("Signal1");
			}
		});
		
		
		// Send signal2
		Button send2 = (Button) this.findViewById(R.id.sendSignal2);
		send2.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view) 
			{
				sendSignal("Signal2");
			}
		});
				
		// Send signal3
		Button send3 = (Button) this.findViewById(R.id.sendSignal3);
		send3.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view) 
			{
				sendSignal("Signal3");
			}
		});
	}
	
	//setting the text as a message
	public static void sendSignal(String signalName)
	{
		String to = "receiverid123@gmail.com";
		String text = signalName;

		Message msg = new Message(to, Message.Type.chat);
		msg.setBody(text);
		connection.sendPacket(msg);
		Log.d(DEBUG_TAG, "Sending text [" + text + "] to [" + to+ "]");
	}

	
}
