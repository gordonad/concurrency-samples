package usingactors;
//ActorRun.java

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActorRun extends AbstractRun {
    @Override
    protected Map<String, Object> computeFinancials() {
//        final ActorRef accumulator = Actors.actorOf(Accumulator.class).start();
        ActorSystem system = ActorSystem.create("MySystem");
        final ActorRef accumulator = system.actorOf(new Props(Accumulator.class), "accumulator");
        ExecutorService service = Executors.newFixedThreadPool(100);
        try {
            for (int i = 0; i < Stocks.tickers.length; i++) {
                final int index = i;
                service.execute(new Runnable() {
                    public void run() {
                        double price = YahooFinance.getPrice(Stocks.tickers[index]);
//                        accumulator.sendOneWay(new StockInfo(Stocks.tickers[index], Stocks.shares[index] * price, price));
                        accumulator.tell(new StockInfo(Stocks.tickers[index], Stocks.shares[index] * price, price), accumulator);
                    }
                });
            }

            Map<String, Object> result =
                    (Map<String, Object>) accumulator.tell(new FetchResult(Stocks.tickers.length), accumulator);
            accumulator.stop();
            return result;
        } finally {
            service.shutdown();
        }
    }

    public static void main(String[] args) {
        new ActorRun().computeFinancialsAndPrint();
    }
}