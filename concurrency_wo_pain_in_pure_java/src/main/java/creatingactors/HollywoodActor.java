package creatingactors;

import akka.actor.UntypedActor;

public class HollywoodActor extends UntypedActor {
    @Override
    public void onReceive(Object message) {
        System.out.println(Thread.currentThread());
        System.out.println("Playing..." + message);
    }

}
