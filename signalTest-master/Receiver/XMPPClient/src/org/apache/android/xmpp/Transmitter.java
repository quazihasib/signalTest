package org.apache.android.xmpp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;

public class Transmitter extends Activity 
{
	//creating xmppConnection instance
	public static XMPPConnection connection;
	//creating xmppLogin instance for login
	public static XmppLogin transmitterLogin;
	public static String DEBUG_TAG = Transmitter.class.getSimpleName();
	public static String tId, tPassword, trId;
	
	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		Log.d(DEBUG_TAG, "onCreate called");
		setContentView(R.layout.transmitter_main);
		
		// Dialog for getting the xmpp settings
		transmitterLogin = new XmppLogin(this);
		
		tId = "quazi.hasib14@gmail.com";
		tPassword = "masters14";
		trId = "quazi.hasib13@gmail.com";
		
		//start login connection using transmitter id
		XmppLogin.SetLoginConnection(tId, tPassword, 1);

		
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
	
	//set connection for transmitter; after login, it will only send text
	public static void setTransmitterConnection(XMPPConnection connection)
	{
		Transmitter.connection = connection;
	}
	
	//setting the text as a message
	public static void sendSignal(String signalName)
	{
		String to = trId;
		String text = signalName;

		Message msg = new Message(to, Message.Type.chat);
		msg.setBody(text);
		connection.sendPacket(msg);
		Log.d(DEBUG_TAG, "Sending text [" + text + "] to [" + to+ "]");
	}

	
}
