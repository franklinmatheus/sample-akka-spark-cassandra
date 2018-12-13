package model;

import akka.actor.ActorRef;

public class MessageAnswer extends Message {
	private Answer answer;
	
	public MessageAnswer(ActorRef rootSender, Answer answer) {
		super(rootSender);
		this.answer = answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
	public Answer getAnswer() {
		return this.answer;
	}
}
