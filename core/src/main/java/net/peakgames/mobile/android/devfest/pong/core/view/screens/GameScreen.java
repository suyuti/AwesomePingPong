package net.peakgames.mobile.android.devfest.pong.core.view.screens;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.peakgames.mobile.android.devfest.pong.core.AwesomePingPong;
import net.peakgames.mobile.android.devfest.pong.core.view.effects.ParticleActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameScreen extends AbstractScreen{
	
	public enum Score {
		NONE, MY, OPPONENT
	}
	
	private int myScore = 0;
	private int aIScore = 0;
	private Image ball;
	private Image topBar;
	private Image bottomBar;
	private Image scoreBar;
	private Label myScoreLabel;
	private Label aIScoreLabel;
	private Label goalLabel;
	private Label failLabel;
	private Sound whistleSound;
	private Sound pingBoard;
	private Sound pingWall;
	private ParticleActor particle;
	private Map<Integer, Float> barVelocityMap = new TreeMap<Integer, Float>();
	
	private static final float MIN_VELOCITY_X_PER_SECOND = 50;
	private float ballVelocityXPerSecond = 120;
	private float ballVelocityYPerSecond = 200;
	private static final int GAME_AREA_TOP = 700;
	
	public GameScreen(AwesomePingPong game) {
		super(game);
	}

	@Override
	public void show() {
		
		initializeBarVelocityMap();
		
		Image bgImage = new Image(new Texture(Gdx.files.internal("gameBg.png")));
		bgImage.setY(-110);
		this.stage.addActor(bgImage);
		
		pingBoard = Gdx.audio.newSound(Gdx.files.internal("ping_wood.wav"));
		pingWall = Gdx.audio.newSound(Gdx.files.internal("ping_wall.wav"));
		whistleSound = Gdx.audio.newSound(Gdx.files.internal("whistle.mp3"));
		ball = new Image(new Texture(Gdx.files.internal("pongball.png")));
		ball.setOrigin(ball.getX() + ball.getWidth() / 2, ball.getY() + ball.getHeight() / 2);
		topBar = new Image(new Texture(Gdx.files.internal("bar.png")));
		bottomBar = new Image(new Texture(Gdx.files.internal("bar.png")));
		scoreBar = new Image(new Texture(Gdx.files.internal("scoreBar2.png")));
		scoreBar.setWidth(480);
		scoreBar.setHeight(110);
		scoreBar.setPosition(0, 690);
		
		Label.LabelStyle scoreLabelStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("steelfish.fnt")), Color.BLUE);
		myScoreLabel = new Label("0", scoreLabelStyle);
		myScoreLabel.setPosition(440, 747);
        aIScoreLabel = new Label("0", scoreLabelStyle);
        aIScoreLabel.setPosition(440, 707);
        
        Label.LabelStyle textLabelStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("steelfish.fnt")), Color.BLACK);
        Label meText = new Label("ME :", textLabelStyle);
        Label aiText = new Label("AI   :", textLabelStyle);
        meText.setPosition(390, 747);
        aiText.setPosition(390, 707);
        
        Label.LabelStyle goalLabelStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("steelfish.fnt")), Color.BLACK);
		goalLabel = new Label("GOAL!!!", goalLabelStyle);
		goalLabel.setWidth(300);
		goalLabel.setAlignment(Align.center);
		goalLabel.setPosition(30, 735);
		goalLabel.addAction(Actions.alpha(0));
		goalLabel.getStyle().font.setScale(2.5f);
		goalLabel.setOriginX(goalLabel.getWidth()/2);
        
		resetGame();
		loadParticleEffect();
		addActorsToStage();
		this.stage.addActor(meText);
        this.stage.addActor(aiText);
		setListeners();

		
		
	}

	private void setListeners() {
		this.stage.addListener(new ClickListener(){
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				//Prevent bar from going off screen
				float newX = x - bottomBar.getWidth() / 2;
				if (x >= game.getScreenWidth() - bottomBar.getWidth() / 2) {
					bottomBar.setX(game.getScreenWidth() - bottomBar.getWidth());
				} else if (x <= 0 + bottomBar.getWidth() / 2) {
					bottomBar.setX(0);
				} else {
					bottomBar.setX(x - bottomBar.getWidth() / 2);
				}
				return true;
			}
			
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				//Prevent bar from going off screen
				float newX = x - bottomBar.getWidth() / 2;
				if (x >= game.getScreenWidth() - bottomBar.getWidth() / 2) {
					bottomBar.setX(game.getScreenWidth() - bottomBar.getWidth());
				} else if (x <= 0 + bottomBar.getWidth() / 2) {
					bottomBar.setX(0);
				} else {
					bottomBar.setX(x - bottomBar.getWidth() / 2);
				}
			}
		});
	}

	private void addActorsToStage() {
		this.stage.addActor(topBar);
		this.stage.addActor(bottomBar);
		this.stage.addActor(ball);
		this.stage.addActor(particle);
		this.stage.addActor(scoreBar);
		this.stage.addActor(myScoreLabel);
		this.stage.addActor(aIScoreLabel);
		this.stage.addActor(goalLabel);

	}

	private void loadParticleEffect() {
		ParticleEffect particleEffect = new ParticleEffect();
		particleEffect.load(Gdx.files.internal("particle.p"), Gdx.files.internal(""));
		particle = new ParticleActor(particleEffect);
	}
	
	private void initializeBarVelocityMap() {
		barVelocityMap.put(8, -2.5f);
		barVelocityMap.put(23, -1.25f);
		barVelocityMap.put(45, -0.6f);
		barVelocityMap.put(75, 0f);
		barVelocityMap.put(97, 0.6f);
		barVelocityMap.put(112, 1.25f);
		barVelocityMap.put(120, 2.5f);
	}

	@Override
    public void render(float delta )
    {
        super.render(delta);
        float ballXPixel = ballVelocityXPerSecond * delta;
        float ballYPixel = ballVelocityYPerSecond * delta;
		ball.setX(ball.getX() + ballXPixel);
        ball.setY(ball.getY() + ballYPixel);
        
        ball.rotate(-ballXPixel);
        
        float ballTopPanelDiffX = (ball.getX() + ball.getWidth() / 2) - (topBar.getX() + topBar.getWidth() / 2);
        float thresholdedDiff = ballTopPanelDiffX * 3f;
        
		topBar.setX(topBar.getX() + thresholdedDiff * delta);
		
		Score scoreResult = processBallMovement();
		
        if (scoreResult == Score.MY) {
        	goalAction();
        	resetGame();
        	myScore++;
        	whistleSound.play();
        	myScoreLabel.setText(myScore + "");
        } else if (scoreResult == Score.OPPONENT) {
        	aIScore++;
        	whistleSound.play();
        	aIScoreLabel.setText(aIScore + "");
        	resetGame();
        }
        
    }
	
	private void goalAction() {
    	goalLabel.setText("GOAL!!!");
    	goalLabel.getStyle().fontColor = Color.GREEN;
		
		SequenceAction goalAction = new SequenceAction();
		goalAction.addAction(Actions.alpha(1));
		goalAction.addAction(Actions.delay(0.15f));
		goalAction.addAction(Actions.alpha(0,0.15f));		
		goalAction.addAction(Actions.delay(0.15f));
		goalAction.addAction(Actions.alpha(1,0.15f));		
		goalAction.addAction(Actions.delay(0.15f));
		goalAction.addAction(Actions.alpha(0,0.15f));
		goalAction.addAction(Actions.delay(0.15f));
		goalAction.addAction(Actions.alpha(1,0.15f));
		goalAction.addAction(Actions.alpha(0,1));
		goalLabel.addAction(goalAction);
	}

	private void resetGame() {
		topBar.setPosition(177, 660);
		bottomBar.setPosition(177, 30);
		ball.setPosition(220, 380);
		ballVelocityXPerSecond = 120;
		ballVelocityYPerSecond = 200;
	}
	
	private Score processBallMovement() {
		float ballRight = ball.getRight();
		float ballLeft = ball.getX();
		float ballTop = ball.getY() + ball.getHeight();
		float ballBottom = ball.getY();
		float ballCenterX = ball.getX() + ball.getWidth() / 2;
		
		float bottomBarTop = bottomBar.getY() + bottomBar.getHeight();
		float bottomBarLeft = bottomBar.getX();
		float bottomBarRight = bottomBar.getRight();
		
		float topBarBottom = topBar.getY();
		float topBarLeft = topBar.getX();
		float topBarRight = topBar.getRight();
		
		if(ballBottom <= bottomBarTop) {
			if(ballCenterX >= bottomBarLeft && ballCenterX <= bottomBarRight) {
				System.out.println("HIT THE BOTTOM BAR");
				updateBallVelocity(ballCenterX, bottomBarLeft);
				particle.show(new Vector2(ballCenterX, ballBottom), new Vector2(30,150));
				ball.setY(bottomBarTop);
				pingBoard.play();
			}
		}
		
		if(ballTop >= topBarBottom) {
			if(ballCenterX >= topBarLeft && ballCenterX <= topBarRight) {
				System.out.println("HIT THE TOP BAR");
				updateBallVelocity(ballCenterX, topBarLeft);
				particle.show(new Vector2(ballCenterX, ballTop), new Vector2(210,330));
				ball.setY(topBarBottom - ball.getHeight());
				pingBoard.play();
			}
		}
		
		if(ballRight >= game.getScreenWidth() || ballLeft <= 0) {
			correctBallXCoordinateIfRequired(ballRight);
			System.out.println("HIT THE SCREEN WIDTH");
			ballVelocityXPerSecond = flipDirection(ballVelocityXPerSecond);
			pingWall.play();
		}
		
		if(ballTop >= GAME_AREA_TOP) {
			return Score.MY;
		} else if (ballBottom <= 0) {
			return Score.OPPONENT;
		}
		
		return Score.NONE;
		
	}

	private void correctBallXCoordinateIfRequired(float ballRight) {
		if(ballRight > game.getScreenWidth()) {
			ball.setX(game.getScreenWidth() - ball.getWidth());
		} else {
			ball.setX(0);
		}
	}

	private void updateBallVelocity(float ballCenterX, float bottomBarLeft) {
		Set<Integer> barVelocityMapKeySet = barVelocityMap.keySet();
		for(int key : barVelocityMapKeySet) {
			if(ballCenterX < bottomBarLeft + key) {
				System.out.println("before velocityX " + ballVelocityXPerSecond);
				ballVelocityXPerSecond += Math.abs(ballVelocityXPerSecond) * barVelocityMap.get(key);
				System.out.println("after velocityX " + ballVelocityXPerSecond);
				if(ballVelocityXPerSecond < MIN_VELOCITY_X_PER_SECOND && ballVelocityXPerSecond > 0) {
					ballVelocityXPerSecond = MIN_VELOCITY_X_PER_SECOND;
				} else if (ballVelocityXPerSecond > -MIN_VELOCITY_X_PER_SECOND && ballVelocityXPerSecond < 0) {
					ballVelocityXPerSecond = - MIN_VELOCITY_X_PER_SECOND;
				}
				break;
			}
		}
		ballVelocityYPerSecond = flipDirection(ballVelocityYPerSecond);
	}
	
	private float flipDirection(float velocity) {
		return velocity * -1;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
