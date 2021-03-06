package concurrency.ch09_swingworker_and_fork_join.exercise_9_2;

import org.junit.Test;

import java.util.List;

import static concurrency.ch09_swingworker_and_fork_join.exercise_9_2.Instrument.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * DO NOT CHANGE.
 */
public class TileTest {
    @Test
    public void testTwoTiles() {
        Tile tile1 = new Tile("tile7.jpg", Direction.NORTH,
                PIANO_BOTTOM, CLARINET_MOUTH, PIANO_TOP, VIOLIN_BODY);
        Tile tile2 = new Tile("tile4.jpg", Direction.NORTH,
                PIANO_TOP, CLARINET_END, VIOLIN_CHORD, HORN_MOUTH);
    }

    @Test
    public void testGetAt() {
        Tile tile1 = TileFactory.makeTiles()[0];
        assertEquals(Instrument.CLARINET_BAD, tile1.getInstrumentAt(Direction.NORTH));
        assertEquals(Instrument.CLARINET_END, tile1.getInstrumentAt(Direction.EAST));
        assertEquals(Instrument.VIOLIN_BODY, tile1.getInstrumentAt(Direction.SOUTH));
        assertEquals(Instrument.HORN_MOUTH, tile1.getInstrumentAt(Direction.WEST));

        tile1 = tile1.rotateTo(Direction.WEST);
        assertEquals(Instrument.CLARINET_BAD, tile1.getInstrumentAt(Direction.EAST));
        assertEquals(Instrument.CLARINET_END, tile1.getInstrumentAt(Direction.SOUTH));
        assertEquals(Instrument.VIOLIN_BODY, tile1.getInstrumentAt(Direction.WEST));
        assertEquals(Instrument.HORN_MOUTH, tile1.getInstrumentAt(Direction.NORTH));

        tile1 = tile1.rotateTo(Direction.SOUTH);
        assertEquals(Instrument.CLARINET_BAD, tile1.getInstrumentAt(Direction.SOUTH));
        assertEquals(Instrument.CLARINET_END, tile1.getInstrumentAt(Direction.WEST));
        assertEquals(Instrument.VIOLIN_BODY, tile1.getInstrumentAt(Direction.NORTH));
        assertEquals(Instrument.HORN_MOUTH, tile1.getInstrumentAt(Direction.EAST));

        tile1 = tile1.rotateTo(Direction.EAST);
        assertEquals(Instrument.CLARINET_BAD, tile1.getInstrumentAt(Direction.WEST));
        assertEquals(Instrument.CLARINET_END, tile1.getInstrumentAt(Direction.NORTH));
        assertEquals(Instrument.VIOLIN_BODY, tile1.getInstrumentAt(Direction.EAST));
        assertEquals(Instrument.HORN_MOUTH, tile1.getInstrumentAt(Direction.SOUTH));
    }

    @Test
    public void testSimpleMatch() throws InterruptedException {
        Tile[] tiles = TileFactory.makeTiles();
        Tile tile1 = tiles[0].rotateTo(Direction.SOUTH);
        Tile tile3 = tiles[2].rotateTo(Direction.EAST);
        assertTrue(tile1.matches(Direction.EAST, tile3));
        assertTrue(tile3.matches(Direction.WEST, tile1));

        Tile tile1_ = tile1.rotateTo(Direction.EAST);
        Tile tile3_ = tile3.rotateTo(Direction.NORTH);
        assertTrue(tile1_.matches(Direction.SOUTH, tile3_));
        assertTrue(tile3_.matches(Direction.NORTH, tile1_));
    }

    @Test
    public void testSimpleMatch23() throws InterruptedException {
        Tile[] tiles = TileFactory.makeTiles();
        Tile tile1 = tiles[0].rotateTo(Direction.SOUTH);
        Tile tile3 = tiles[2].rotateTo(Direction.EAST);

        Tile tile1_ = tile1.rotateTo(Direction.WEST);
        Tile tile3_ = tile3.rotateTo(Direction.NORTH);

    }

    @Test
    public void testSimpleMatch2() throws InterruptedException {
        Tile[] tiles = TileFactory.makeTiles();
        Tile tile1 = tiles[0].rotateTo(Direction.SOUTH);
        Tile tile3 = tiles[2].rotateTo(Direction.EAST);
        Tile tile4 = tiles[3].rotateTo(Direction.SOUTH);
        assertTrue(tile1.matches(Direction.EAST, tile3));
        assertTrue(tile3.matches(Direction.WEST, tile1));
        assertTrue(tile3.matches(Direction.EAST, tile4));
    }


    @Test
    public void testBruteForceTime() throws InterruptedException {
        long time = System.currentTimeMillis();
        PuzzleSolver brute = new PuzzleSolver();
        List<Tile[]> bruteSolutions = brute.findSolutions();
        System.out.println("bruteSolutions.size = " + bruteSolutions.size());
        List<Tile[]> solutions = bruteSolutions;
        System.out.println(solutions.size());
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
        PuzzleFrame frame = new PuzzleFrame();
        frame.pack();
        frame.setVisible(true);
    }
}
