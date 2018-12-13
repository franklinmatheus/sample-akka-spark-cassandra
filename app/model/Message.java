package model;

import java.io.Serializable;

import akka.actor.ActorRef;

public abstract class Message implements Serializable {
	// save first sender's reference
	private ActorRef rootSender;
	
	public Message(ActorRef rootSender) {
		this.rootSender = rootSender;
	}
	
	public void setRootSender(ActorRef rootSender) {
		this.rootSender = rootSender;
	}
	
	public ActorRef getRootSender() {
		return this.rootSender;
	}
}
