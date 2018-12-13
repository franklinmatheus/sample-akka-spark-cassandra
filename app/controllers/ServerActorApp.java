package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import actors.SupervisorClientFilterActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import play.libs.Akka;
import play.mvc.*;
import scala.compat.java8.FutureConverters;

import static akka.pattern.Patterns.ask;

@Singleton
public class ServerActorApp extends Controller {
    final ActorRef serviceDecider;

    @Inject
    public ServerActorApp(ActorSystem system) {
        serviceDecider = system.actorOf(SupervisorClientFilterActor.getProps());
    }

    public CompletionStage<Result> process(Integer id) {
        return FutureConverters.toJava(ask(serviceDecider, id, 100000)).thenApply(response -> ok((String) response));
    }
}