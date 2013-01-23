package concurrency.ch09_swingworker_and_fork_join.solution_9_2;

import concurrency.ch09_swingworker_and_fork_join.exercise_9_2.Direction;
import concurrency.ch09_swingworker_and_fork_join.exercise_9_2.SolutionChecker;
import concurrency.ch09_swingworker_and_fork_join.exercise_9_2.Tile;
import concurrency.ch09_swingworker_and_fork_join.exercise_9_2.TileFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * In our fork/join solution, we only search to a depth of 2 tiles using
 * additional threads.  Beyond that, we might create too many threads.  Instead,
 * we compute() the solution directly once we are down to a depth of 2.
 */
public class PuzzleSolver {
    private final static Tile[] tiles = TileFactory.makeTiles();
    private final static int THRESHHOLD = 2;

    public List<Tile[]> findSolutions() {
        ForkJoinPool fjp = new ForkJoinPool(2 * Runtime.getRuntime().availableProcessors());
        Vector<Tile[]> result = new Vector<>(); // thread safe
        fjp.invoke(new PuzzleSolverAction(result, new Tile[tiles.length]));
        System.out.println("fjp = " + fjp);
        return result;
    }

    private static class PuzzleSolverAction extends RecursiveAction {
        private final List<Tile[]> results;
        private final Tile[] previousTiles;

        private PuzzleSolverAction(List<Tile[]> results, Tile[] previousTiles) {
            this.results = results;
            this.previousTiles = previousTiles;
        }

        @Override
        protected void compute() {
            int pos = 0;
            for (Tile tile : previousTiles) {
                if (tile == null) break;
                pos++;
            }
            if (pos == 9) {
                results.add(previousTiles);
                return; // recursive end condition
            }
            List<PuzzleSolverAction> forked = new Vector<>();
            for (Tile tile2 : tiles) {
                boolean alreadyUsed = false;
                for (int i = 0; i < previousTiles.length && previousTiles[i] != null; i++) {
                    if (previousTiles[i].equals(tile2)) alreadyUsed = true;
                }
                if (!alreadyUsed) {
                    for (Direction direction2 : Direction.values()) {
                        Tile[] newPreviousTiles = previousTiles.clone();
                        newPreviousTiles[pos] = tile2.rotateTo(direction2);
                        if (SolutionChecker.check(newPreviousTiles)) {
                            System.out.println("found some match: " + Arrays.toString(newPreviousTiles));
                            PuzzleSolverAction task = new PuzzleSolverAction(results, newPreviousTiles);
                            if (pos < THRESHHOLD) {
                                task.fork();
                                forked.add(task);
                            } else {
                                task.compute();
                            }
                        }
                    }
                }
            }
            for (PuzzleSolverAction task : forked) {
                task.join();
            }
        }
    }
}