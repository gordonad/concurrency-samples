package concurrency.ch09_swingworker_and_fork_join.exercise_9_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * The algorithm tries putting tiles into each of the spaces and rotating them.
 * As soon as it finds two tiles that match, it tries to find three that match.
 * It recursively goes through the entire board until it either finds a solution
 * or discovers that it needs to back out again.
 */
public class PuzzleSolver {
    private final static Tile[] tiles = TileFactory.makeTiles();

    public List<Tile[]> findSolutions() {
        // TODO: Over here we should instead construct a PuzzleSolverAction and then
        // TODO: fork that and let it find solutions.
        return findSolutionsRecursive(new ArrayList<Tile[]>(), new Tile[tiles.length]);
    }

    /**
     * TODO: Instead of a recursive solution, we want to rather use recursive
     * TODO: decomposition with the Fork/Join Framework.
     */
    private List<Tile[]> findSolutionsRecursive(List<Tile[]> results, Tile[] previousTiles) {
        int pos = 0;
        for (Tile tile : previousTiles) {
            if (tile == null) break;
            pos++;
        }
        if (pos == 9) {
            results.add(previousTiles);
            return results; // recursive end condition
        }

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
                        findSolutionsRecursive(results, newPreviousTiles);
                    }
                }
            }
        }
        return results;
    }

    private static class PuzzleSolverAction extends RecursiveAction {
        @Override
        protected void compute() {
            // TODO: We need a recursive end condition after which we stop searching.
            // TODO: This would typically be when the tiles have all been filled.


            // TODO: We then need to create new sub-actions that solve further parts
            // TODO: of the puzzle.

            // TODO: If we are deep enough into the puzzle, we can use a compute()
            // TODO: the result directly, rather than using fork() and join().

            // TODO: At every point in the algorithm, we need to check that the
            // TODO: solution is still correct.
        }
    }
}