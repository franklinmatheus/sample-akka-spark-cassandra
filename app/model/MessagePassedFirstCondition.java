package model;

import akka.actor.ActorRef;

public class MessagePassedFirstCondition extends Message {
	private Client client;

	public MessagePassedFirstCondition(ActorRef rootSender, Client client) {
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
