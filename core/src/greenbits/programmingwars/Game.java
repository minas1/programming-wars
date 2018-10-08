package greenbits.programmingwars;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import greenbits.programmingwars.board.Board;
import greenbits.programmingwars.board.objects.BoardObject;
import greenbits.programmingwars.board.objects.Pawn;
import greenbits.programmingwars.board.objects.Trail;

public class Game extends ApplicationAdapter {

    private static final float MIN_VIEWPORT_DIMENSION = 1000f;
    private static final int GRID_SIZE = 10;

    private static final float BOARD_MARGIN = MIN_VIEWPORT_DIMENSION / 100f;

    private static final Color PLAYER_0_PAWN_COLOR = Color.RED;
    private static final Color PLAYER_1_PAWN_COLOR = Color.GREEN;
    private static final Color PLAYER_2_PAWN_COLOR = Color.BLUE;
    private static final Color PLAYER_3_PAWN_COLOR = Color.YELLOW;

    private static final Color PLAYER_0_TRAIL_COLOR = Color.valueOf("#FA8072"); // salmon
    private static final Color PLAYER_1_TRAIL_COLOR = Color.valueOf("#6B8E23"); // olive drab
    private static final Color PLAYER_2_TRAIL_COLOR = Color.valueOf("#B0E0E6"); // powder blue
    private static final Color PLAYER_3_TRAIL_COLOR = Color.valueOf("#F5DEB3"); // light yellow

    private final Map<Pawn, Color> pawnColors;

	private SpriteBatch batch;
	private Texture img;

    private ShapeRenderer shapeRenderer;
    private Camera camera;
    private BitmapFont font;

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

    public Game() {

        Map<Pawn, Color> pawnColorTemp = new HashMap<>();
        pawnColorTemp.put(player0, PLAYER_0_PAWN_COLOR);
        pawnColorTemp.put(player1, PLAYER_1_PAWN_COLOR);
        pawnColorTemp.put(player2, PLAYER_2_PAWN_COLOR);
        pawnColorTemp.put(player3, PLAYER_3_PAWN_COLOR);
        pawnColors = Collections.unmodifiableMap(pawnColorTemp);
    }

	@Override
	public void create() {

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

        shapeRenderer = new ShapeRenderer();
        generateFont();

        camera = new OrthographicCamera(calculateViewportWidth(), calculateViewportHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        gridToWorldUnitsConverter = new GridToWorldUnitsConverter(GRID_SIZE);
        float cellDimensions = (MIN_VIEWPORT_DIMENSION - 2 * BOARD_MARGIN) / GRID_SIZE;
        gridToWorldUnitsConverter.setCellDimensions(cellDimensions);
        gridToWorldUnitsConverter.setOrigin(BOARD_MARGIN, BOARD_MARGIN);

        setUpBoard();
	}

	private void generateFont() {

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        font = fontGenerator.generateFont(parameter);
        fontGenerator.dispose();
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

        drawingFunctions.put(player0, (x, y) -> drawPlayerAtCell(x, y, PLAYER_0_PAWN_COLOR));
        drawingFunctions.put(player1, (x, y) -> drawPlayerAtCell(x, y, PLAYER_1_PAWN_COLOR));
        drawingFunctions.put(player2, (x, y) -> drawPlayerAtCell(x, y, PLAYER_2_PAWN_COLOR));
        drawingFunctions.put(player3, (x, y) -> drawPlayerAtCell(x, y, PLAYER_3_PAWN_COLOR));

        drawingFunctions.put(player0.getTrail(), ((x, y) -> drawTrailAtCell(x, y, PLAYER_0_TRAIL_COLOR)));
        drawingFunctions.put(player1.getTrail(), ((x, y) -> drawTrailAtCell(x, y, PLAYER_1_TRAIL_COLOR)));
        drawingFunctions.put(player2.getTrail(), ((x, y) -> drawTrailAtCell(x, y, PLAYER_2_TRAIL_COLOR)));
        drawingFunctions.put(player3.getTrail(), ((x, y) -> drawTrailAtCell(x, y, PLAYER_3_TRAIL_COLOR)));
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
    }

    private void drawPlayerAtCell(int x, int y, Color color) {

        float worldUnitsX = gridToWorldUnitsConverter.getX(x) + gridToWorldUnitsConverter.getCellDimensions() * 0.5f;
        float worldUnitsY = gridToWorldUnitsConverter.getY(y) + gridToWorldUnitsConverter.getCellDimensions() * 0.5f;
        float radius = gridToWorldUnitsConverter.getCellDimensions() * 0.375f;

        drawPlayer(worldUnitsX, worldUnitsY, radius, color);
    }

    private void drawPlayer(float worldUnitsX, float worldUnitsY, float radius, Color color) {

        shapeRenderer.setColor(color);
        shapeRenderer.circle(
                worldUnitsX,
                worldUnitsY,
                radius,
                Math.max(1, (int)(15 * (float)Math.cbrt(radius))));
    }

    private void drawTrailAtCell(int x, int y, Color color) {

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

        float y = camera.viewportHeight - BOARD_MARGIN;

        for (ScoreCalculator.Score score : scores) {

            String text = String.format(Locale.getDefault(), "%d", score.getScore());
            GlyphLayout layout = new GlyphLayout(font, text);

            // draw pawn color
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            float x = (camera.viewportWidth + getEndOfBoardX() - layout.width) * 0.5f;
            float radius = MIN_VIEWPORT_DIMENSION * 0.025f;
            Pawn pawn = score.getPawn();
            drawPlayer(x - radius, y - radius, radius, pawnColors.get(pawn));
            shapeRenderer.end();

            // draw score number
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            font.draw(batch, text, x + MIN_VIEWPORT_DIMENSION * 0.02f, y - layout.height * 0.5f);
            batch.end();
            y -= layout.height + MIN_VIEWPORT_DIMENSION * 0.075f;
        }
    }

    /**
     * @return The X value where the board ends.
     */
    private float getEndOfBoardX() {

	    return BOARD_MARGIN + gridToWorldUnitsConverter.getCellDimensions() * GRID_SIZE;
    }

	@Override
	public void dispose() {

		batch.dispose();
		img.dispose();
		shapeRenderer.dispose();
	}
}
