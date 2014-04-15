package org.apache.android.xmpp;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

/**
 * Gather the xmpp settings and create an XMPPConnection
 */
public class SettingsDialogTransmitter extends Dialog implements android.view.View.OnClickListener 
{
    private Transmitter xmppClientPar;

    public SettingsDialogTransmitter(Transmitter xmppClientPar) 
    {
        super(xmppClientPar);
        this.xmppClientPar = xmppClientPar;
    }

    protected void onStart() 
    {
        super.onStart();
        setContentView(R.layout.settings);
        getWindow().setFlags(4, 4);
        setTitle("XMPP Settings");
        Button ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(this);
    }


    public void onClick(View v) 
    {
    	String username = getText(R.id.userid);
        String password = getText(R.id.password);
    	
    	
        String host = "talk.google.com";
        String port = "5222";
        String service = "gmail.com";
        username = "blackskull444";
        password = "freakshow44";

        // Create a connection
        ConnectionConfiguration connConfig = new org.jivesoftware.smack.ConnectionConfiguration(host, Integer.parseInt(port),service);
//                new ConnectionConfiguration(host, Integer.parseInt(port), service);
        XMPPConnection connection = new XMPPConnection(connConfig);

        try 
        {
            connection.connect();
            Log.i("XMPPClientPar", "[SettingsDialogPar] Connected to " + connection.getHost());
        } 
        catch (XMPPException ex) 
        {
            Log.e("XMPPClientPar", "[SettingsDialogPar] Failed to connect to " + connection.getHost());
            Log.e("XMPPClientPar", ex.toString());
            xmppClientPar.setConnection(null);
        }
        try 
        {
            connection.login(username, password);
            Log.i("XMPPClientPar", "Logged in as " + connection.getUser());

            // Set the status to available
            Presence presence = new Presence(Presence.Type.available);
            connection.sendPacket(presence);
            xmppClientPar.setConnection(connection);
        }
        catch (XMPPException ex)
        {
            Log.e("XMPPClientPar", "[SettingsDialogPar] Failed to log in as " + username);
            Log.e("XMPPClientPar", ex.toString());
            xmppClientPar.setConnection(null);
        }
        dismiss();
    }

    private String getText(int id)
    {
        EditText widget = (EditText) this.findViewById(id);
        return widget.getText().toString();
    }
}
