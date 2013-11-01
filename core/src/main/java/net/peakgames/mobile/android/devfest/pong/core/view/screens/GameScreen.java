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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class GameScreen extends AbstractScreen{
	
	// PARTICLE
	private ParticleActor hitParticle;
	private ParticleActor ballParticle;
	private ParticleActor engineParticleLeft;
	private ParticleActor engineParticleRight;
	
	// VARIABLES
	private int myScore = 0;
	private int aIScore = 0;

	// IMAGES
	private Image scoreBoard;

	// LABELS
	private Label myScoreLabel;
	private Label aIScoreLabel;
	private Label goalLabel;
	private Label meText;
	private Label aIText;
	
	// SOUNDS
	private Sound whistleSound;
	private Sound pingBoard;
	private Sound pingWall;
	
	// IMAGES
	private Image ball;
	private Image topBar;
	private Image bottomBar;

	// VARIABLES
	private float ballVelocityXPerSecond = 120;
	private float ballVelocityYPerSecond = 200;
	private Map<Integer, Float> barVelocityMap = new TreeMap<Integer, Float>();
	
	public enum Score {
		NONE, MY, OPPONENT
	}

	// CONSTANTS
	private static final float MIN_VELOCITY_X_PER_SECOND = 50;
	private static final int GAME_AREA_TOP = 700;
	private static final float AI_COEFFICIENT = 3f;

	public GameScreen(AwesomePingPong game) {
		super(game);
	}

	@Override
	public void show() {
		initializeBarVelocityMap();
		loadBackgroundImage();
		loadTheBall();
		loadBars();
		resetGame();
		setListeners();
		loadScoreBoard();
		loadLabels();
		loadSoundEffects();
		loadParticleEffect();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		updateBallPosition(delta);
		updateTopBarPosition(delta);
		
		Score scoreResult = processBallMovement();
		if (scoreResult == Score.MY) {
			myScore++;
			myScoreLabel.setText(myScore + "");
			whistleSound.play();
			executeGoalAction();
			resetGame();
		} else if (scoreResult == Score.OPPONENT) {
			aIScore++;
			aIScoreLabel.setText(aIScore + "");
			whistleSound.play();
			executeGoalAction();
			resetGame();
		}
		
		ballParticle.show(ball.getX() + ball.getWidth() / 2, ball.getY() + ball.getHeight() / 2, 0, 360);
		engineParticleLeft.show(bottomBar.getX() + 10, bottomBar.getY());
		engineParticleRight.show(bottomBar.getX() + bottomBar.getWidth() - 10, bottomBar.getY());
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
	
	private void loadBackgroundImage() {
		Image bgImage = new Image(new Texture(Gdx.files.internal("gameBg.png")));
		bgImage.setY(0);
		this.stage.addActor(bgImage);
	}
	
	private void loadTheBall() {
		ball = new Image(new Texture(Gdx.files.internal("pongball.png")));
		ball.setOrigin(ball.getX() + ball.getWidth() / 2, ball.getY() + ball.getHeight() / 2);
		this.stage.addActor(ball);
	}
	
	private void loadBars() {
		topBar = new Image(new Texture(Gdx.files.internal("bar.png")));
		bottomBar = new Image(new Texture(Gdx.files.internal("bar.png")));
		this.stage.addActor(topBar);
		this.stage.addActor(bottomBar);
	}
	
	private void resetGame() {
		topBar.setPosition(177, 660);
		bottomBar.setPosition(177, 30);
		ball.setPosition(220, 380);
		ballVelocityXPerSecond = 120;
		ballVelocityYPerSecond = 200;
	}
	
	private void setListeners() {
		this.stage.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				moveTheBottomBar(x);
				return true;
			}
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				moveTheBottomBar(x);
			}
		});
	}
	
	private void moveTheBottomBar(float x) {
		if (x >= game.getScreenWidth() - bottomBar.getWidth() / 2) {
			bottomBar.setX(game.getScreenWidth() - bottomBar.getWidth());
		} else if (x <= 0 + bottomBar.getWidth() / 2) {
			bottomBar.setX(0);
		} else {
			bottomBar.setX(x - bottomBar.getWidth() / 2);
		}
	}
	
	private void updateBallPosition(float delta) {
		float ballXPixel = ballVelocityXPerSecond * delta;
        float ballYPixel = ballVelocityYPerSecond * delta;
		ball.setX(ball.getX() + ballXPixel);
        ball.setY(ball.getY() + ballYPixel);
        ball.rotate(-ballXPixel);
	}
	
	private void updateTopBarPosition(float delta) {
		float ballTopPanelDiffX = (ball.getX() + ball.getWidth() / 2) - (topBar.getX() + topBar.getWidth() / 2);
        float thresholdedDiff = ballTopPanelDiffX * AI_COEFFICIENT;
		topBar.setX(topBar.getX() + thresholdedDiff * delta);
	}
	
	private float flipDirection(float velocity) {
		return velocity * -1;
	}
	
	private void updateBallVelocity(float ballCenterX, float bottomBarLeft) {
		Set<Integer> barVelocityMapKeySet = barVelocityMap.keySet();
		for(int key : barVelocityMapKeySet) {
			if(ballCenterX < bottomBarLeft + key) {
				ballVelocityXPerSecond += Math.abs(ballVelocityXPerSecond) * barVelocityMap.get(key);
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
	
	private void correctBallXCoordinateIfRequired(float ballRight) {
		if(ballRight > game.getScreenWidth()) {
			ball.setX(game.getScreenWidth() - ball.getWidth());
		} else {
			ball.setX(0);
		}
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
		
		// BOTTOM BAR HIT CHECK
		if(ballBottom <= bottomBarTop) {
			if(ballCenterX >= bottomBarLeft && ballCenterX <= bottomBarRight) {
				updateBallVelocity(ballCenterX, bottomBarLeft);
				hitParticle.show(ballCenterX, ballBottom, 30, 150);
				pingBoard.play();
				ball.setY(bottomBarTop);
			}
		}
		
		// TOP BAR HIT CHECK
		if(ballTop >= topBarBottom) {
			if(ballCenterX >= topBarLeft && ballCenterX <= topBarRight) {
				updateBallVelocity(ballCenterX, topBarLeft);
				hitParticle.show(ballCenterX, ballTop, 210, 330);
				pingBoard.play();
				ball.setY(topBarBottom - ball.getHeight());
			}
		}
		
		// WALL HIT CHECK
		if(ballRight >= game.getScreenWidth() || ballLeft <= 0) {
			correctBallXCoordinateIfRequired(ballRight);
			pingWall.play();
			ballVelocityXPerSecond = flipDirection(ballVelocityXPerSecond);
		}
		
		// GOAL HIT CHECK
		if(ballTop >= GAME_AREA_TOP) {
			return Score.MY;
		} else if (ballBottom <= 0) {
			return Score.OPPONENT;
		}
		
		return Score.NONE;
		
	}
	
	private void loadScoreBoard() {
		scoreBoard = new Image(new Texture(Gdx.files.internal("scoreBar2.png")));
		scoreBoard.setWidth(480);
		scoreBoard.setHeight(110);
		scoreBoard.setPosition(0, 690);
		this.stage.addActor(scoreBoard);
	}
	
	private void loadLabels() {
		Label.LabelStyle scoreLabelStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("steelfish.fnt")), Color.BLUE);
		myScoreLabel = new Label("0", scoreLabelStyle);
		myScoreLabel.setPosition(440, 747);
        aIScoreLabel = new Label("0", scoreLabelStyle);
        aIScoreLabel.setPosition(440, 707);
        
        Label.LabelStyle textLabelStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("steelfish.fnt")), Color.BLACK);
        meText = new Label("ME :", textLabelStyle);
        aIText = new Label("AI   :", textLabelStyle);
        meText.setPosition(390, 747);
        aIText.setPosition(390, 707);
        
        Label.LabelStyle goalLabelStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("steelfish.fnt")), Color.BLACK);
		goalLabel = new Label("GOAL!!!", goalLabelStyle);
		goalLabel.setWidth(300);
		goalLabel.setAlignment(Align.center);
		goalLabel.setPosition(30, 730);
		goalLabel.addAction(Actions.alpha(0));
		goalLabel.getStyle().font.setScale(2.5f);
		goalLabel.setOriginX(goalLabel.getWidth()/2);
		
		this.stage.addActor(myScoreLabel);
		this.stage.addActor(aIScoreLabel);
		this.stage.addActor(goalLabel);
		this.stage.addActor(meText);
        this.stage.addActor(aIText);
	}
	
	private void loadSoundEffects() {
		pingBoard = Gdx.audio.newSound(Gdx.files.internal("ping_wood.wav"));
		pingWall = Gdx.audio.newSound(Gdx.files.internal("ping_wall.wav"));
		whistleSound = Gdx.audio.newSound(Gdx.files.internal("whistle.mp3"));
	}
	
	private void executeGoalAction() {
    	goalLabel.setText("GOAL!!!");
    	goalLabel.getStyle().fontColor = Color.GREEN;
		
		SequenceAction goalAction = Actions.sequence();
		goalAction.addAction(Actions.alpha(1));
		float actionDelay = 0.15f;
		goalAction.addAction(Actions.delay(actionDelay));
		goalAction.addAction(Actions.alpha(0,actionDelay));		
		goalAction.addAction(Actions.delay(actionDelay));
		goalAction.addAction(Actions.alpha(1,actionDelay));		
		goalAction.addAction(Actions.delay(actionDelay));
		goalAction.addAction(Actions.alpha(0,actionDelay));
		goalAction.addAction(Actions.delay(actionDelay));
		goalAction.addAction(Actions.alpha(1,actionDelay));
		goalAction.addAction(Actions.alpha(0,1));
		goalLabel.addAction(goalAction);
	}
	
	private void loadParticleEffect() {
		ParticleEffect particleEffect = new ParticleEffect();
		particleEffect.load(Gdx.files.internal("hitParticle.p"), Gdx.files.internal(""));
		hitParticle = new ParticleActor(particleEffect, 0.6f);
		this.stage.addActor(hitParticle);
		
		ParticleEffect ballParticleEffect = new ParticleEffect();
		ballParticleEffect.load(Gdx.files.internal("ballParticle.p"), Gdx.files.internal(""));
		ballParticle = new ParticleActor(ballParticleEffect, Float.MAX_VALUE);
		this.stage.addActor(ballParticle);
		
		ParticleEffect engineEffect1 = new ParticleEffect();
		engineEffect1.load(Gdx.files.internal("engineParticle.p"), Gdx.files.internal(""));
		engineParticleLeft = new ParticleActor(engineEffect1, Float.MAX_VALUE);
		this.stage.addActor(engineParticleLeft);
		
		ParticleEffect engineEffect2 = new ParticleEffect();
		engineEffect2.load(Gdx.files.internal("engineParticle.p"), Gdx.files.internal(""));
		engineParticleRight = new ParticleActor(engineEffect2, Float.MAX_VALUE);
		this.stage.addActor(engineParticleRight);
		
	}
	
	
}