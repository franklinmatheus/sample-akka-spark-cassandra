package model;

import akka.actor.ActorRef;

/**
 * Used to massages that pass client's id.
 * @author franklin
 *
 */
public class MessageInteger extends Message {
	private Integer integer;
	
	public MessageInteger(ActorRef rootSender, Integer integer) {
		super(rootSender);
		this.integer = integer;
	}
	
	public void setInteger(Integer integer) {
		this.integer = integer;
	}
	
	public Integer getInteger() {
		return this.integer;
	}

}
