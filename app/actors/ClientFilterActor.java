package actors;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;

import akka.actor.Props;
import akka.actor.AbstractActor;

import model.Client;
import model.MessageClient;
import model.MessageAnswer;
import model.MessageString;
import model.Client;
import model.Negative;
import model.Positive;
import model.MessageClientsNear;
import model.MessagePassedFirstCondition;

public class ClientFilterActor extends AbstractActor {

    private SparkConf conf;
    private JavaSparkContext context;

    public static Props getProps() {
		return Props.create(ClientFilterActor.class);
	}

    @Override
    public void preStart() {
        this.conf = new SparkConf(true).setAppName("unicredit").setMaster("local");
        this.context = new JavaSparkContext(SparkContext.getOrCreate(this.conf));
    }

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(MessageClientsNear.class, msg -> {
            JavaRDD<Client> clients = context.parallelize(msg.getClients());
            Long satisfy = clients.filter(client -> {
                if(client.getHasjob()) {
                    if(client.getLimitCredit() < client.getBalance())
                        return true;
                } else if(client.isBrokecar())
                    if(client.getBalance() > 20000)
                        return true;
                return false;
            }).count();

            Double varSatisfy = satisfy.doubleValue();
            Double varAmount = (new Long(clients.count())).doubleValue();
            if(varSatisfy/varAmount >= 0.5)
                getSender().tell(new MessageAnswer(msg.getRootSender(), new Positive()), self());
            else
                getSender().tell(new MessageAnswer(msg.getRootSender(), new Negative()), self());
		}).match(MessageClient.class, msg -> {
            Boolean passed = false;
            if(msg.getClient().getHascar()) {
                if(msg.getClient().getHasjob()) {
                    if(msg.getClient().getLimitCredit() < msg.getClient().getBalance())
                        passed = true;
                } else if(msg.getClient().isBrokecar())
                    if(msg.getClient().getBalance() > 20000)
                        passed = true;
            }

            if(passed) getSender().tell(new MessagePassedFirstCondition(msg.getRootSender(), msg.getClient()), self());
            else getSender().tell(new MessageAnswer(msg.getRootSender(), new Negative()), self());
        }).build();
	}

    @Override
    public void postStop() throws Exception {
    	this.context.close();
    }
}
