package org.apache.android.xmpp;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

/**
 * Gather the xmpp settings and create an XMPPConnection
 */
public class SettingsDialogReceiver extends Dialog implements android.view.View.OnClickListener 
{
	
	String DEBUG_TAG=SettingsDialogReceiver.class.getSimpleName();
    private Receiver xmppClient;

    public SettingsDialogReceiver(Receiver xmppClient) 
    {
        super(xmppClient);
        this.xmppClient = xmppClient;
    }

    @Override
	protected void onStart() 
    {
     
        //
        String username = "codemen.ridwan";
        String password = "code0011";
        String host = "talk.google.com";
        String port = "5222";
        String service = "gmail.com";
      
        ConnectionConfiguration connConfig = new org.jivesoftware.smack.ConnectionConfiguration(host, Integer.parseInt(port),service);
        XMPPConnection connection = new XMPPConnection(connConfig);

        try 
        {
            connection.connect();
            Log.i("XMPPClient", "[SettingsDialog] Connected to " + connection.getHost());
        } 
        catch (XMPPException ex) 
        {
            Log.e("XMPPClient", "[SettingsDialog] Failed to connect to " + connection.getHost());
            Log.e("XMPPClient", ex.toString());
            xmppClient.setConnection(null);
        }
        try 
        {
            connection.login(username, password);
            Log.i("XMPPClient", "Logged in as " + connection.getUser());

            
            Presence presence = new Presence(Presence.Type.available);
            connection.sendPacket(presence);
            xmppClient.setConnection(connection);
        }
        catch (XMPPException ex)
        {
            Log.e("XMPPClient", "[SettingsDialog] Failed to log in as " + username);
            Log.e("XMPPClient", ex.toString());
            xmppClient.setConnection(null);
        }
        dismiss();
        //
        
    }

    @Override
	public void onClick(View v) 
    {
    	
    }

    

	private String getText(int id)
    {
        EditText widget = (EditText) this.findViewById(id);
        return widget.getText().toString();
    }
}
