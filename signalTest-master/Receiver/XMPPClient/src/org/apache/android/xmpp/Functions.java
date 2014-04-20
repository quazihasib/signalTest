package org.apache.android.xmpp;

import java.util.Collection;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.RosterPacket;
import org.jivesoftware.smack.util.StringUtils;

import android.util.Log;

public class Functions
{
	public static int counter=0;
	public static Roster roster ;
	public static Collection<RosterEntry> entries;
	public static String DEBUG_TAG = Functions.class.getSimpleName();
	
	public static void friendlist(XMPPConnection connection, int number)
	{
		try 
		{
			roster = connection.getRoster();
			entries = roster.getEntries();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(RosterEntry entry : entries)
		{ 
		    System.out.println(entry.getUser());
		    
		    //for transmitter
		    if(number==1)
			{
		    	//if the receiver is in the friend list
		    	if(entry.getUser().equals(Transmitter.trId))
		    	{
		    		Log.d(DEBUG_TAG, "YESS");
		    	}
		    	else
		    	{
		    		Log.d(DEBUG_TAG, "NO");
		    		counter++;
		    		if(counter==1)
		    		{
//		    			removeFriend(connection, Transmitter.trId);
		    			addFriend(connection, Transmitter.trId);
		    			allowFriend(connection, Transmitter.trId);
		    		}
		    	}
		    }
		    //for receiver
		    else if(number==2)
	    	{
		    	//if the transmitter is in the friend list
		    	if(entry.getUser().equals(Receiver.rtId))
		    	{
		    		Log.d(DEBUG_TAG, "Allow");
//		    		removeFriend(connection, Receiver.rtId);
		    		allowFriend(connection,Receiver.rtId);
		    	}
	    	}
		}
	}
	
	public static void addFriend(XMPPConnection connection, String jid)
	{
		 String nickname = null;
	     String idExtension = jid;
	     nickname = StringUtils.parseBareAddress(jid);
	        
	     if (!roster.contains(idExtension)) 
	     {
	        try 
	        {   
	        	roster.createEntry(idExtension, nickname, null);
	            //to subscribe the user in the entry
	            Presence subscribe = new Presence(Presence.Type.subscribe);
	            subscribe.setTo(idExtension);               
	            connection.sendPacket(subscribe);   

	        } 
	        catch (XMPPException e)
	        {
	            System.err.println("Error in adding friend");

	        }
	     }
	}
	
	public static void allowFriend(XMPPConnection connection,String jid)
	{
		Presence subscribed = new Presence(Presence.Type.subscribed);
        subscribed.setTo(jid);               
        connection.sendPacket(subscribed);
	}
	
	public static void removeFriend(XMPPConnection connection, String jid)
	{
		RosterPacket packet = new RosterPacket();
		packet.setType(IQ.Type.SET);
		RosterPacket.Item item  = new RosterPacket.Item(jid, null);
		item.setItemType(RosterPacket.ItemType.remove);
		packet.addRosterItem(item);
		connection.sendPacket(packet);
	}
}
