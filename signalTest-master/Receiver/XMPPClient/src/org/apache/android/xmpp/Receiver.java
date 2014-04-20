package org.apache.android.xmpp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;

import java.util.ArrayList;

public class Receiver extends Activity  
{
	//list for message
    static ArrayList<String> messages = new ArrayList<String>();
    //handler for posting message
    static Handler mHandler = new Handler();
    private static ListView mList;
    //xmppconnection instance
    public static XMPPConnection connection;
    //creating xmppLogin instance for receiver login
    public static XmppLogin receiverLogin;
    //creating receiver instance
    public static Receiver receive;
    
    static String DEBUG_TAG=Receiver.class.getSimpleName();
    
    public static String rId, rPassword, rtId;
    
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        Log.d(DEBUG_TAG, "onCreate called");
        setContentView(R.layout.receiver_main);
        receive = this;

        receiverLogin = new XmppLogin(this);
        
        rId = "quazi.hasib13@gmail.com";
        rPassword = "masters13";
        rtId = "quazi.hasib14@gmail.com";
        
        //start login connection using transmitter id
        XmppLogin.SetLoginConnection(rId, rPassword, 2);
        
        //instantiating the list for setting messages in the list
        mList = (ListView) this.findViewById(R.id.listMessages);
        Log.d(DEBUG_TAG, "mList = " + mList);
        setListAdapter();

    }
   
    //set the list adapter for multi line messages
    public static void setListAdapter() 
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Receiver.receive, R.layout.multi_line_list_item_receiver,messages);
        mList.setAdapter(adapter);
    }
    
  //set connection for receiver; after login, it will only receive text
  	public static void setReceiverConnection(XMPPConnection connection)
  	{
  		Receiver.connection = connection;
  		if (connection != null)	
  		{
  			
  			//check friend list
			Functions.friendlist(connection,2);
			
  			// Add a packet listener to get messages sent to us
  			PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
  			connection.addPacketListener(new PacketListener() 
  			{
  				public void processPacket(Packet packet) 
  				{
  					Message message = (Message) packet;
  					if (message.getBody() != null) 
  					{
  						String fromName = StringUtils.parseBareAddress(message.getFrom());
  							Log.d(DEBUG_TAG, "Got text [" + message.getBody()+ "] from [" + fromName + "]");
  						//Receiver.messages.add(fromName + ":");
  						Receiver.messages.add(message.getBody());
  						// Add the incoming message to the list view
  						Receiver.mHandler.post(new Runnable() 
  						{
  							public void run()
  							{
  								Receiver.setListAdapter();
  							}
  						});
  					}
  				}
  			}, filter);
  		}
  	}
  	

}
