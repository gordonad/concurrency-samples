package creatingactors;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Sample {
    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef depp = system.actorOf(new Props(HollywoodActor.class), "myactor");

        System.out.println(Thread.currentThread());
        depp.tell("Sparow", depp);

        depp.tell("Wonka", depp);

        system.shutdown();
    }
}