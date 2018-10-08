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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import greenbits.programmingwars.board.Board;
import greenbits.programmingwars.board.objects.BoardObject;
import greenbits.programmingwars.board.objects.Pawn;
import greenbits.programmingwars.board.objects.Trail;

public class Game extends ApplicationAdapter {

    private static final float MIN_VIEWPORT_DIMENSION = 100f;
    private static final int GRID_SIZE = 10;

    private static final Color PLAYER_0_PAWN_COLOR = Color.RED;
    private static final Color PLAYER_1_PAWN_COLOR = Color.GREEN;
    private static final Color PLAYER_2_PAWN_COLOR = Color.BLUE;
    private static final Color PLAYER_3_PAWN_COLOR = Color.YELLOW;

    private static final Color PLAYER_0_TRAIL_COLOR = Color.valueOf("#FA8072"); // salmon
    private static final Color PLAYER_1_TRAIL_COLOR = Color.valueOf("#6B8E23"); // olive drab
    private static final Color PLAYER_2_TRAIL_COLOR = Color.valueOf("#B0E0E6"); // powder blue
    private static final Color PLAYER_3_TRAIL_COLOR = Color.valueOf("#F5DEB3"); // light yellow

	private SpriteBatch batch;
	private Texture img;

    private ShapeRenderer shapeRenderer;
    private Camera camera;

    private Board board = new Board(GRID_SIZE);
    private GridToWorldUnitsConverter gridToWorldUnitsConverter;
    private final ScoreCalculator scoreCalculator = new ScoreCalculator();

    private Pawn player0 = new Pawn("Player 1", new Trail());
    private Pawn player1 = new Pawn("Player 2", new Trail());
    private Pawn player2 = new Pawn("Player 3", new Trail());
    private Pawn player3 = new Pawn("Player 4", new Trail());

    interface DrawFunc {

        void draw(int x, int y);
    }

    private final Map<BoardObject, DrawFunc> drawingFunctions = new HashMap<>();

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

        board.moveTo(0, 0, player0);
        board.moveTo(GRID_SIZE - 1, 0, player1);
        board.moveTo(GRID_SIZE - 1, GRID_SIZE - 1, player2);
        board.moveTo(0, GRID_SIZE - 1, player3);

        drawingFunctions.put(player0, (x, y) -> drawPlayer(x, y, PLAYER_0_PAWN_COLOR));
        drawingFunctions.put(player1, (x, y) -> drawPlayer(x, y, PLAYER_1_PAWN_COLOR));
        drawingFunctions.put(player2, (x, y) -> drawPlayer(x, y, PLAYER_2_PAWN_COLOR));
        drawingFunctions.put(player3, (x, y) -> drawPlayer(x, y, PLAYER_3_PAWN_COLOR));

        drawingFunctions.put(player0.getTrail(), ((x, y) -> drawTrail(x, y, PLAYER_0_TRAIL_COLOR)));
        drawingFunctions.put(player1.getTrail(), ((x, y) -> drawTrail(x, y, PLAYER_1_TRAIL_COLOR)));
        drawingFunctions.put(player2.getTrail(), ((x, y) -> drawTrail(x, y, PLAYER_2_TRAIL_COLOR)));
        drawingFunctions.put(player3.getTrail(), ((x, y) -> drawTrail(x, y, PLAYER_3_TRAIL_COLOR)));
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
        drawScore();
	}

	private void drawGrid() {

	    shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);

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

	    // TODO remove?
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

	    for (int x = 0; x < board.getBoardSize(); ++x) {

	        for (int y = 0; y < board.getBoardSize(); ++y) {

                Set<BoardObject> boardObjects = board.getElement(x, y);
                for (BoardObject boardObject : boardObjects) {

                    DrawFunc drawFunc = drawingFunctions.get(boardObject);
                    if (drawFunc != null) {
                        drawFunc.draw(x, y);
                    }
                }
            }
	    }

	    shapeRenderer.end();
//        batch.end();
    }

    private void drawPlayer(int x, int y, Color color) {

        float worldUnitsX = gridToWorldUnitsConverter.getX(x);
        float worldUnitsY = gridToWorldUnitsConverter.getY(y);
        float radius = gridToWorldUnitsConverter.getCellDimensions() * 0.375f;

        shapeRenderer.setColor(color);
        shapeRenderer.circle(
                worldUnitsX + gridToWorldUnitsConverter.getCellDimensions() * 0.5f,
                worldUnitsY + gridToWorldUnitsConverter.getCellDimensions() * 0.5f,
                radius,
                Math.max(1, (int)(15 * (float)Math.cbrt(radius))));
    }

    private void drawTrail(int x, int y, Color color) {

        float worldUnitsX = gridToWorldUnitsConverter.getX(x);
        float worldUnitsY = gridToWorldUnitsConverter.getY(y);
        float widthHeight = gridToWorldUnitsConverter.getCellDimensions() * 0.95f;

        shapeRenderer.setColor(color);
        shapeRenderer.rect(
                worldUnitsX + gridToWorldUnitsConverter.getCellDimensions() * 0.5f - widthHeight * 0.5f,
                worldUnitsY + gridToWorldUnitsConverter.getCellDimensions() * 0.5f - widthHeight * 0.5f,
                widthHeight,
                widthHeight);
	}

	private void drawScore() {

        List<ScoreCalculator.Score> scores = scoreCalculator.calculate(board);

        System.out.println("--------");
        for (ScoreCalculator.Score score : scores) {

            System.out.println(score.getPawn().getId() + ": " + score.getScore());
        }
        System.out.println("--------");
    }

	@Override
	public void dispose() {

		batch.dispose();
		img.dispose();
		shapeRenderer.dispose();
	}
}
