package org.apache.android.xmpp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;

import java.util.ArrayList;
import java.util.Locale;

public class Receiver extends Activity implements OnInitListener {

    private ArrayList<String> messages = new ArrayList();
    private Handler mHandler = new Handler();
    private SettingsDialogReceiver mDialog;
    private EditText mRecipient;
    private EditText mSendText;
    private ListView mList;
    private XMPPConnection connection;
    private TextToSpeech tts;
    private String txt;
    String DEBUG_TAG=Receiver.class.getSimpleName();
    
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        Log.i("XMPPClient", "onCreate called");
        setContentView(R.layout.main);

        tts=new TextToSpeech(this,this);
        Log.i("XMPPClient", "mRecipient = " + mRecipient);
        Log.i("XMPPClient", "mSendText = " + mSendText);
        
        mList = (ListView) this.findViewById(R.id.listMessages);
        Log.i("XMPPClient", "mList = " + mList);
        setListAdapter();

        mDialog = new SettingsDialogReceiver(this);
        
        Button setup = (Button) this.findViewById(R.id.setup);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View view) {
                
                mDialog.onStart();
            	
            	
            }
        });

        Button back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent vintent=new Intent(v.getContext(),Starter.class);
				startActivityForResult(vintent, 0);
				setResult(0);
				finish();
			}
		});
    }

   
    public void setConnection(XMPPConnection connection) 
    {
        this.connection = connection;
        if (connection != null) 
        {
            
            PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
            connection.addPacketListener(new PacketListener() 
            {
                @Override
				public void processPacket(Packet packet) 
                {
                    Message message = (Message) packet;
                    if (message.getBody() != null) 
                    {
                        String fromName = StringUtils.parseBareAddress(message.getFrom());
                        Log.i("XMPPClient", "Got text [" + message.getBody() + "] from [" + fromName + "]");
                        txt= message.getBody();
                        Log.d(DEBUG_TAG, txt);
                        if(txt.length()!=0){
                        	String text=txt;
                    		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        	
                        }
                        
                        messages.add(fromName + ":");
                        messages.add(message.getBody());
                        mHandler.post(new Runnable() 
                        {
                            @Override
							public void run() 
                            {
                                setListAdapter();
                            }
                        });
                    }
                }
            }, filter);
        }
    }

    private void setListAdapter() 
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.multi_line_list_item,messages);
        mList.setAdapter(adapter);
    }


	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		if(status==TextToSpeech.SUCCESS){
			int result=tts.setLanguage(Locale.ENGLISH);
		
		}else{
			Log.e("TTS", "initialization failed");
		}
	}
	
	
	
}
