package concurrency.ch09_swingworker_and_fork_join.exercise_9_2;

/**
 * DO NOT CHANGE.
 */
public enum Direction {
    NORTH(0), WEST(90), SOUTH(180), EAST(270);
    private final int degrees;

    private Direction(int degrees) {
        this.degrees = degrees;
    }

    public int getDegrees() {
        return degrees;
    }

    public Direction opposite() {
        switch (this) {
            default:
                throw new IllegalStateException();
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
        }
    }
}
