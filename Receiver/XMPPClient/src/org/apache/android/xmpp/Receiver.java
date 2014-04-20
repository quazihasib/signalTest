package org.apache.android.xmpp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.jivesoftware.smack.XMPPConnection;
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
    
    String DEBUG_TAG=Receiver.class.getSimpleName();
    
    
    
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        Log.d(DEBUG_TAG, "onCreate called");
        setContentView(R.layout.receiver_main);
        receive = this;

        receiverLogin = new XmppLogin(this);
        
        //start login connection using transmitter id
        XmppLogin.SetLoginConnection("receiverid123@gmail.com", "receiver123", 2);
        
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

}
