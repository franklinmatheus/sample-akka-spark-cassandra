package actors;

import java.util.Iterator;

import org.apache.spark.streaming.akka.ActorReceiverSupervisor.Supervisor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import model.Answer;
import model.Positive;
import model.Negative;
import model.Client;
import model.ClientsAux;
import model.Message;
import model.MessageString;
import model.MessageInteger;
import model.MessageClient;
import model.MessageAnswer;
import model.MessageClientsNear;
import model.MessagePassedFirstCondition;
import model.UserNotExists;

public class SupervisorClientFilterActor extends AbstractActor {

	private ActorRef child;
	private ActorRef databaseActor;

	public static Props getProps() {
		return Props.create(SupervisorClientFilterActor.class);
	}

	@Override
	public void preStart() throws Exception {
		this.child = getContext().actorOf(ClientFilterActor.getProps(), "filter-worker");
		this.databaseActor = getContext().actorOf(DatabaseFilterActor.getProps(), "database-worker");
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(Integer.class, id -> {
			Message msg = new MessageInteger(sender(), id);
			databaseActor.tell(msg, self());
		}).match(MessageAnswer.class, msg -> {
            if(msg.getAnswer() instanceof UserNotExists)
                msg.getRootSender().tell("client id not found", self());
            else if(msg.getAnswer() instanceof Positive)
                msg.getRootSender().tell("offer car insurance", self());
            else if(msg.getAnswer() instanceof Negative)
                msg.getRootSender().tell("don't offer car insurance", self());
		}).match(MessageClient.class, msg -> {
			child.tell(msg, self());
        }).match(MessagePassedFirstCondition.class, msg -> {
            databaseActor.tell(new MessageClient(msg.getRootSender(), msg.getClient()), self());
        }).match(MessageClientsNear.class, msg -> {
            child.tell(msg, self());
        }).match(MessageString.class, msg -> {
			msg.getRootSender().tell(msg.getString(), self());
		}).build();
	}
}
