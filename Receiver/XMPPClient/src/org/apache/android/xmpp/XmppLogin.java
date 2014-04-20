package org.apache.android.xmpp;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

import android.util.Log;

public class XmppLogin
{
	//creating transmitter instance
	public static Transmitter xmppClientTransmitter;
	//creating receiver instance
	public static Receiver xmppClientReciver;
	public static String DEBUG_TAG = XmppLogin.class.getSimpleName();
	
	//constructor for transmitter
	public XmppLogin(Transmitter xmppClientPar)
	{
        XmppLogin.xmppClientTransmitter = xmppClientPar;
	}

	//constructor for receiver
	public XmppLogin(Receiver xmppClientReciver)
	{
        XmppLogin.xmppClientReciver = xmppClientReciver;
	}

	//start login when the app starts
	public static void SetLoginConnection(String userName, String passWord, int Number)
	{
		String host = "talk.google.com";
		String port = "5222";
		String service = "gmail.com";
		String username = userName;
		String password = passWord;
		//number=1 is for Transmitter login and number=2 is for receiver login
		int number = Number;

		// Create a connection
		ConnectionConfiguration connConfig = new org.jivesoftware.smack.ConnectionConfiguration(
				host, Integer.parseInt(port), service);
		// new ConnectionConfiguration(host, Integer.parseInt(port), service);
		XMPPConnection connection = new XMPPConnection(connConfig);

		try 
		{
			connection.connect();
			Log.d(DEBUG_TAG, "[SettingsDialogPar] Connected to "
					+ connection.getHost());
		}
		catch (XMPPException ex) 
		{
			Log.d(DEBUG_TAG, "[SettingsDialogPar] Failed to connect to "
					+ connection.getHost());
			Log.d(DEBUG_TAG, ex.toString());
			
			if(number==1)
			{
				setTransmitterConnection(null);
			}
			else if(number==2)
			{
				setReceiverConnection(null);
			}
		}
		
		try 
		{
			connection.login(username, password);
			Log.d(DEBUG_TAG, "Logged in as " + connection.getUser());

			// Set the status to available
			Presence presence = new Presence(Presence.Type.available);
			connection.sendPacket(presence);
			if(number==1)
			{
				setTransmitterConnection(connection);
			}
			else if(number==2)
			{
				setReceiverConnection(connection);
			}
			
		}
		catch (XMPPException ex) 
		{
			Log.d(DEBUG_TAG, "[SettingsDialogPar] Failed to log in as "
					+ username);
			Log.d(DEBUG_TAG, ex.toString());
			
			if(number==1)
			{
				setTransmitterConnection(null);
			}
			else if(number==2)
			{
				setReceiverConnection(null);
			}
		}
	}
	
	//set connection for receiver; after login, it will only receive text
	public static void setReceiverConnection(XMPPConnection connection)
	{
		Receiver.connection = connection;
		if (connection != null)	
		{
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
	
	//set connection for transmitter; after login, it will only send text
	public static void setTransmitterConnection(XMPPConnection connection)
	{
		Transmitter.connection = connection;
		if (connection != null)
		{
			// Add a packet listener to get messages sent to us
			PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
			connection.addPacketListener(new PacketListener() 
			{
				public void processPacket(Packet packet) 
				{
					Message message = (Message) packet;
					if (message.getBody() != null) 
					{
						String fromName = StringUtils.parseBareAddress(message
								.getFrom());
						Log.d(DEBUG_TAG, "Got text [" + message.getBody()+ "] from [" + fromName + "]");

					}
				}
			}, filter);
		}
	}
	
	
	
}
