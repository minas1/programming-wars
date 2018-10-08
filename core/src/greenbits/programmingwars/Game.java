package greenbits.programmingwars;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import greenbits.programmingwars.board.Board;
import greenbits.programmingwars.board.objects.BoardObject;
import greenbits.programmingwars.board.objects.Pawn;
import greenbits.programmingwars.board.objects.Trail;

public class Game extends ApplicationAdapter {

    private static final float MIN_VIEWPORT_DIMENSION = 100f;

    private static final int GRID_SIZE = 10;

	private SpriteBatch batch;
	private Texture img;

    private ShapeRenderer shapeRenderer;
    private Camera camera;

    private Board board = new Board(GRID_SIZE);
    private GridToWorldUnitsConverter gridToWorldUnitsConverter;

    private Pawn player0 = new Pawn("Player 1", new Trail());
    private Pawn player1 = new Pawn("Player 2", new Trail());
    private Pawn player2 = new Pawn("Player 3", new Trail());
    private Pawn player4 = new Pawn("Player 4", new Trail());

	@Override
	public void create() {

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera(calculateViewportWidth(), calculateViewportHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        gridToWorldUnitsConverter = new GridToWorldUnitsConverter(GRID_SIZE);
        float cellDimensions = (MIN_VIEWPORT_DIMENSION - 2f) / GRID_SIZE;
        gridToWorldUnitsConverter.setCellDimensions(cellDimensions);
        gridToWorldUnitsConverter.setOrigin(1f, 1f);

        setUpBoard();
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

    private void setUpBoard() {



        board.setElement(0, 2, player0);

        // TODO put players on the board
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

	    Color clearColor = Color.ROYAL;
	    Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawGrid();
        drawBoard();


	}

	private void drawGrid() {

	    shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // horizontal
        for (int i = 0; i < GRID_SIZE + 1; ++i) {

            float lineY = gridToWorldUnitsConverter.getY(i);
            shapeRenderer.line(gridToWorldUnitsConverter.getOriginX(), lineY, gridToWorldUnitsConverter.getX(GRID_SIZE), lineY);
        }

        // vertical
        for (int i = 0; i < GRID_SIZE + 1; ++i) {

            float lineX = gridToWorldUnitsConverter.getX(i);
            shapeRenderer.line(lineX, gridToWorldUnitsConverter.getOriginY(), lineX, gridToWorldUnitsConverter.getY(GRID_SIZE));
        }

        shapeRenderer.end();
    }

    private void drawBoard() {

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

	    for (int x = 0; x < board.getBoardSize(); ++x) {

	        for (int y = 0; y < board.getBoardSize(); ++y) {

                BoardObject boardObject = board.getElement(x, y);

                // TODO add more conditions here
                if (boardObject == player0) {

                    batch.draw(img, gridToWorldUnitsConverter.getX(x), gridToWorldUnitsConverter.getY(y), gridToWorldUnitsConverter.getCellDimensions(), gridToWorldUnitsConverter.getCellDimensions());
                }
            }
	    }

        batch.end();
    }

	@Override
	public void dispose() {

		batch.dispose();
		img.dispose();
		shapeRenderer.dispose();
	}
}
