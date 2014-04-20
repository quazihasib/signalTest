package org.apache.android.xmpp;

import java.util.Collection;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;

import android.util.Log;

public class Functions
{
	public static String DEBUG_TAG = Functions.class.getSimpleName();
	
	public static void friendlist(XMPPConnection connection)
	{
		Roster roster = null;
		try 
		{
			roster = connection.getRoster();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collection<RosterEntry> entries = roster.getEntries();
 
		for(RosterEntry entry : entries)
		{
		    System.out.println(entry.getUser());
		    if(entry.getUser().equals("codemen.ridwan@gmail.com"))
		    {
		    	Log.d(DEBUG_TAG, "YESS");
		    }
		    else
		    {
		    	Log.d(DEBUG_TAG, "NO");
		    }
		}
	}
	
	public static void addFriend()
	{
		
	}
}
