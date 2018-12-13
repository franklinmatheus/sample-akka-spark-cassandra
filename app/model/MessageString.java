package model;

import akka.actor.ActorRef;

public class MessageString extends Message {
	private String string;
	
	public MessageString(ActorRef rootSender, String string) {
		super(rootSender);
		this.string = string;
	}

	public void setString(String string) {
		this.string = string;
	}
	
	public String getString() {
		return this.string;
	}
}
