package model;

import java.util.List;
import akka.actor.ActorRef;

public class MessageClientsNear extends Message {
	private List<Client> clients;
	
	public MessageClientsNear(ActorRef rootSender, List<Client> clients) {
		super(rootSender);
		this.clients = clients;
	}
	
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	
	public List<Client> getClients() {
		return this.clients;
	}
}
