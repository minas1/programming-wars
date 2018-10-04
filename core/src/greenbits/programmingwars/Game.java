package greenbits.programmingwars;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Game extends ApplicationAdapter {

    private static final float MIN_VIEWPORT_DIMENSION = 100f;

	SpriteBatch batch;
	Texture img;

    private ShapeRenderer shapeRenderer;
    private Camera camera;

    private GridCalculator gridCalculator;

	@Override
	public void create() {

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera(calculateViewportWidth(), calculateViewportHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        gridCalculator = new GridCalculator(GRID_SIZE);
        float cellDimensions = (MIN_VIEWPORT_DIMENSION - 2f) / GRID_SIZE;
        gridCalculator.setCellDimensions(cellDimensions);
        gridCalculator.setOrigin(1f, 1f);
	}

	private float calculateViewportWidth() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        if (w > h) {
            return MIN_VIEWPORT_DIMENSION * (w / h);
        }
        else {
            return MIN_VIEWPORT_DIMENSION;
        }
    }

    private float calculateViewportHeight() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        if (h > w) {
            return MIN_VIEWPORT_DIMENSION * (h / w);
        }
        else {
            return MIN_VIEWPORT_DIMENSION;
        }
    }

    @Override
    public void resize(int width, int height) {

        camera.viewportWidth = calculateViewportWidth();
        camera.viewportHeight = calculateViewportHeight();
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
    }

    @Override
	public void render() {

	    camera.update();

	    Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawGrid();

        batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img, gridCalculator.getX(2), gridCalculator.getY(5), gridCalculator.getCellDimensions(), gridCalculator.getCellDimensions());
		batch.end();
	}

	private static final int GRID_SIZE = 10;

	private void drawGrid() {

	    shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // horizontal
        for (int i = 0; i < GRID_SIZE + 1; ++i) {

            float lineY = gridCalculator.getY(i);
            shapeRenderer.line(gridCalculator.getOriginX(), lineY, gridCalculator.getX(GRID_SIZE), lineY);
        }

        // vertical
        for (int i = 0; i < GRID_SIZE + 1; ++i) {

            float lineX = gridCalculator.getX(i);
            shapeRenderer.line(lineX, gridCalculator.getOriginY(), lineX, gridCalculator.getY(GRID_SIZE));
        }

        shapeRenderer.end();
    }

	@Override
	public void dispose() {

		batch.dispose();
		img.dispose();
		shapeRenderer.dispose();
	}
}
