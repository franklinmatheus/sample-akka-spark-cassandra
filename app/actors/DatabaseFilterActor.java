package actors;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.*;
import com.datastax.spark.connector.japi.SparkContextJavaFunctions;
import com.datastax.*;

import akka.actor.AbstractActor;
import akka.actor.Props;

import model.Client;
import model.ClientsAux;
import model.MessageClient;
import model.MessageInteger;
import model.MessageString;
import model.MessageAnswer;
import model.MessageClientsNear;
import model.UserNotExists;

public class DatabaseFilterActor extends AbstractActor {

    private SparkConf conf;
    private JavaSparkContext context;

    public static Props getProps() {
		return Props.create(DatabaseFilterActor.class);
	}

    @Override
    public void preStart() {
        this.conf = new SparkConf(true).setAppName("unicredit").setMaster("local");
        this.context = new JavaSparkContext(SparkContext.getOrCreate(this.conf));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(MessageInteger.class, msg -> {
        	SparkContextJavaFunctions functions = javaFunctions(context);
        	JavaRDD<Client> result = functions.cassandraTable("unicredit", "clients", mapRowTo(Client.class))
                .where("id=?", msg.getInteger());

            if(result.count() == 0)
                getSender().tell(new MessageAnswer(msg.getRootSender(), new UserNotExists()), self());
            else
        	    getSender().tell(new MessageClient(msg.getRootSender(), result.first()), self());
        }).match(MessageClient.class, msg -> {
            SparkContextJavaFunctions functions = javaFunctions(context);
            JavaRDD<Client> result = functions.cassandraTable("unicredit", "clients", mapRowTo(Client.class))
                .where("hascar=true")
                .where("address>?", msg.getClient().getAddress() - 500)
                .where("address<?", msg.getClient().getAddress() + 500);

            getSender().tell(new MessageClientsNear(msg.getRootSender(), result.collect()), self());
        }).build();
    }

    @Override
    public void postStop() throws Exception {
    	this.context.close();
    }
}
