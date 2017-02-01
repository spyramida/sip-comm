package net.java.sip.communicator.forwarding;

import java.text.ParseException;
import java.util.NoSuchElementException;

import javax.sip.address.URI;
import javax.sip.header.ToHeader;
import javax.sip.message.Request;


import net.java.sip.communicator.db.ForwardDB;
import net.java.sip.communicator.sip.SipManager;

public class ForwardClient {
	ForwardDB dbManager;
	protected SipManager sipManCallback = null;
	
	public ForwardClient(SipManager sipManCallback) {
		dbManager = new ForwardDB();
		this.sipManCallback = sipManCallback;
	}
	
	public String getForward(String username) {
		return dbManager.getForward(username);
	}
	public String getFinalForward(String username) {
		return dbManager.getFinalForward(username);
	}
	
	/**
	 * 
	 * @param fromUser
	 * @param toUser
	 * @throws NoSuchElementException in case no such user found
	 * @throws RuntimeException in case of a circle forwarding
	 */
	public void setForward(String fromUser, String toUser) throws NoSuchElementException, RuntimeException{
		dbManager.setForward(fromUser, toUser);
	}
	
	public void resetForward(String fromUser) {
		dbManager.resetForward(fromUser);
	}
	
	private String getUsernameFromHeader(ToHeader header) {
		URI uri = header.getAddress().getURI();
		String uriString = uri.toString();
		return uriString.substring(uriString.indexOf("sip:") + 4,
				uriString.indexOf("@"));
	}
	
	public Request checkAndSetForwarding(Request request) {
		ToHeader header = (ToHeader) request.getHeader(ToHeader.NAME);
		String oldToUser = getUsernameFromHeader(header);
		String toUser = dbManager.getFinalForward(oldToUser);
		//System.out.println("\n\n\n\n\n\n oldTo:"+oldToUser+" NewTO:"+toUser+"\n\n\n\n\n\n");
		if (toUser != null) {
			String originalUri = header.getAddress().toString();
			String newUri = "sip:" + toUser
					+ originalUri.substring(originalUri.indexOf("@"));
			System.out.println(newUri);
			URI newURI;
			try {
				newURI = sipManCallback.addressFactory.createURI(newUri);
				ToHeader newTo = sipManCallback.headerFactory.createToHeader(
						sipManCallback.addressFactory.createAddress(newURI), null);
				Request newreq = (Request) request.clone();
				newreq.setHeader(newTo);
				return newreq;

			} catch (ParseException e) {
				e.printStackTrace();
			}

		} else
			return request;
		
		return null;
	}
	
	
}


	