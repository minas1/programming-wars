package greenbits.programmingwars;

/**
 * Helps with the conversion between grid rows / columns to world units.
 */
public class GridCalculator {

    private final int gridSize;

    private float originX;
    private float originY;
    private float cellDimensions;

    public GridCalculator(int gridSize) {

        this.gridSize = gridSize;
    }

    public void setOrigin(float originX, float originY) {

        this.originX = originX;
        this.originY = originY;
    }

    public float getOriginX() {

        return originX;
    }

    public float getOriginY() {

        return originY;
    }

    public void setCellDimensions(float cellDimensions) {

        this.cellDimensions = cellDimensions;
    }

    public float getCellDimensions() {

        return cellDimensions;
    }

    public float getX(int column) {

        return originX + column * cellDimensions;
    }

    public float getY(int column) {

        return originY + column * cellDimensions;
    }

    public float getCenterX(int column) {

        return getX(column) + cellDimensions * 0.5f;
    }

    public float getCenterY(int column) {

        return getY(column) + cellDimensions * 0.5f;
    }
}
