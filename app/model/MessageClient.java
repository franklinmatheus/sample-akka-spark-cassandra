package model;

import akka.actor.ActorRef;

public class MessageClient extends Message {
	private Client client;
	
	public MessageClient(ActorRef rootSender, Client client) {
		super(rootSender);
		this.client = client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public Client getClient() {
		return this.client;
	}
}
