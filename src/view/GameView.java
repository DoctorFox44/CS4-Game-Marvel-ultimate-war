package view;

import java.util.ArrayList;

import java.awt.Point;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;

import engine.*;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.*;
import javafx.scene.DirectionalLight;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;
import javafx.util.Duration;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.world.*;
import javafx.*;

public class GameView extends Application {
	private Stage window;
	private Scene PlayersName;
	private Scene ChampSelect;
	private Game game;
	private Scene leaderSelect;
	private int click = 1;
	private Scene match;
	private Player f;
	private Player s;
	private Font bgFont;
	private Scene LanName;
	private Scene LanChamp;
	private Font titFont;

	public static void addTextLimiter(final TextField tf, final int maxLength) {
		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue,
					final String newValue) {
				if (tf.getText().length() > maxLength) {
					String s = tf.getText().substring(0, maxLength);
					tf.setText(s);
				}
			}
		});
	}

	@Override
	public void start(Stage arg0) throws Exception {
		MediaPlayer player = new MediaPlayer(new Media(getClass().getResource("start.mp4").toExternalForm()));
		player.setAutoPlay(true);
		MediaView mediaView = new MediaView(player);
		window = arg0;
		Game.loadAbilities("Abilities.csv");
		Game.loadChampions("Champions.csv");
		Image iiii = new Image("Icon.jpg");
		window.getIcons().add(iiii);
		bgFont = Font.loadFont(getClass().getResourceAsStream("Pixeboy.TTF"), 19);
		titFont = Font.loadFont(getClass().getResourceAsStream("BotsmaticOutlineItalic-Ox2p.ttf"), 60);
		Text title = new Text("marvel ultimate war");
		title.setFont(titFont);
		VBox layout1 = new VBox();
		layout1.setSpacing(15);
		layout1.setAlignment(Pos.CENTER);
		Button toName = new Button("PvP Mode");
		Image ha = new Image("ha.png");
		ImageView iv = new ImageView(ha);
		StackPane wallpaper = new StackPane();
		wallpaper.getChildren().add(iv);
		wallpaper.setPadding(new Insets(150));
		BorderPane p = new BorderPane();
		p.setLeft(layout1);
		p.setRight(wallpaper);
		Button cred = new Button("Credits");
		cred.setMinSize(80, 45);
		setBStyle(cred);
		VBox Names = new VBox();
		Names.setPadding(new Insets(150));
		Names.setAlignment(Pos.CENTER_LEFT);
		layout1.setPadding(new Insets(150));
		Button back = new Button("Back");
		setBStyle(back);
		Text name = new Text("Youssef Maged" + "\n" + "Mohamed Osama" + "\n" + "Omar Magdy");
//		Scene credits = new Scene(Names,Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
		back.setOnAction(e -> {
			p.setLeft(layout1);
			new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
		});
		cred.setOnAction(e -> {
			p.setLeft(Names);
			new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
		});
		name.setFont(bgFont);
		Names.getChildren().addAll(name, back);
		toName.setMinSize(80, 45);
		toName.setFont(bgFont);
		toName.setOnAction(e -> {
			window.setScene(PlayersName);
			new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
		});
		setBStyle(toName);

		Button exit = new Button("Exit");
		setBStyle(exit);
		exit.setOnAction(e -> {
			window.close();
			new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
		});
		window.setOnCloseRequest(e -> window.close());
		layout1.getChildren().addAll(title, toName, cred, exit);
		Scene l = new Scene(p, Screen.getPrimary().getBounds().getWidth(),
				Screen.getPrimary().getBounds().getHeight() - 60);
		BorderPane pn = new BorderPane();
		GridPane nt = new GridPane();
		Label l1 = new Label("First Player :");
		l1.setFont(bgFont);
		TextField p1 = new TextField();
		p1.setPromptText("First Player Name");
		p1.setMinWidth(200);
		p1.setFont(bgFont);
		addTextLimiter(p1, 10);
		Label l2 = new Label("Second Player :");
		l2.setFont(bgFont);
		TextField p2 = new TextField();
		p2.setPromptText("Second Player Name");
		p2.setFont(bgFont);
		p2.setPrefWidth(200);
		addTextLimiter(p2, 10);
		nt.setPadding(new Insets(10, 10, 10, 10));
		nt.setHgap(10);
		nt.setVgap(10);
		GridPane.setConstraints(l1, 3, 0);
		GridPane.setConstraints(p1, 4, 0);
		GridPane.setConstraints(l2, 3, 1);
		GridPane.setConstraints(p2, 4, 1);
		Button toSelect = new Button("Done");
		toSelect.setFont(bgFont);
		toSelect.setMinSize(80, 45);
		setBStyle(toSelect);
		p1.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.TAB) {
				p2.requestFocus();
			}
		});
		p2.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.TAB) {
				toSelect.fire();
			}
		});
		toSelect.setOnAction(e -> {
			new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
			try {
				if (p1.getText().equals("") || p2.getText().equals(""))
					throw new Exception();
				f = new Player(p1.getText());
				s = new Player(p2.getText());
				ChampSelect = new Scene(addChampTable(), Screen.getPrimary().getBounds().getWidth(),
						Screen.getPrimary().getBounds().getHeight() - 60);

				mediaPlayer.setMute(true);
				music("select.mp3");
				window.setScene(ChampSelect);
			} catch (Exception g) {
				AlertBox.display("Write a Name!");
			}
		});
		GridPane.setConstraints(toSelect, 5, 2);
		nt.getChildren().addAll(l1, p1, l2, p2, toSelect);
		nt.setAlignment(Pos.CENTER);
		pn.setCenter(nt);
		PlayersName = new Scene(pn, Screen.getPrimary().getBounds().getWidth(),
				Screen.getPrimary().getBounds().getHeight() - 60);
		StackPane start = new StackPane();
		start.getChildren().add(mediaView);
		mediaView.setOnMousePressed(e -> {
			music("maintheme.wav");
			window.setScene(l);
			player.stop();
		});
		player.setOnEndOfMedia(new Runnable() {
			public void run() {
				music("maintheme.wav");
				window.setScene(l);
			}
		});
		Scene ss = new Scene(start, Screen.getPrimary().getBounds().getWidth(),
				Screen.getPrimary().getBounds().getHeight() - 60);
		window.setScene(ss);
		window.setTitle("Marvel Ultimate War!");
		window.show();
	}

	MediaPlayer mediaPlayer;

	public void music(String s) {
		Media h = new Media(getClass().getResource(s).toExternalForm());
		mediaPlayer = new MediaPlayer(h);
		 mediaPlayer.setOnEndOfMedia(new Runnable() {
		       public void run() {
		         mediaPlayer.seek(Duration.ZERO);
		       }
		   });
		mediaPlayer.play();
	}

	private BorderPane leaderlayout() {
		BorderPane ls = new BorderPane();
		HBox h1 = new HBox();
		HBox h2 = new HBox();
		VBox v1 = new VBox();
		VBox v2 = new VBox();
		VBox V1 = new VBox();
		VBox V2 = new VBox();
		V1.getChildren().add(v1);
		V2.getChildren().add(v2);
		Rectangle rectt = new Rectangle(100, 100);
		rectt.setFill(Color.TRANSPARENT);
		Rectangle rectt1 = new Rectangle(100, 100);
		rectt1.setFill(Color.TRANSPARENT);
		V1.getChildren().add(rectt);
		V2.getChildren().add(rectt1);
		Text t1 = new Text(f.getName() + " Select Your Leader");
		t1.setFont(bgFont);
		v1.getChildren().add(t1);
		Text t2 = new Text(s.getName() + " Select Your Leader");
		t2.setFont(bgFont);
		v2.getChildren().addAll(t2, h2);
		v1.getChildren().add(h1);
		h1.setSpacing(15);
		h2.setSpacing(15);
		for (int i = 0; i < 3; i++) {
			StackPane s = new StackPane();
			Rectangle rec = new Rectangle(100, 100);
			rec.setFill(Color.WHITE);
			rec.setStroke(Color.DODGERBLUE);
			rec.setStrokeWidth(4);
			s.getChildren().add(rec);
			h1.getChildren().add(s);
		}
		for (int i = 0; i < 3; i++) {
			StackPane s = new StackPane();
			Rectangle rec = new Rectangle(100, 100);
			rec.setFill(Color.WHITE);
			rec.setStroke(Color.DARKRED);
			rec.setStrokeWidth(4);
			s.getChildren().add(rec);
			h2.getChildren().add(s);
		}
		for (int i = 0; i < f.getTeam().size(); i++) {
			Image image = new Image(getClass().getResource(f.getTeam().get(i).getName() + ".gif").toString());
			if (image.isError()) {
				image.getException().printStackTrace();
			}
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			((StackPane) h1.getChildren().get(i)).getChildren().add(imageView);
		}
		for (int i = 0; i < s.getTeam().size(); i++) {
			Image image = new Image(getClass().getResource(s.getTeam().get(i).getName() + ".gif").toString());
			if (image.isError()) {
				image.getException().printStackTrace();
			}
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			((StackPane) h2.getChildren().get(i)).getChildren().add(imageView);
		}
		for (int i = 0; i < h1.getChildren().size(); i++) {
			StackPane h = ((StackPane) h1.getChildren().get(i));
			int j = i;
			h.setOnMouseClicked(e -> {
				ImageView iv = (ImageView) h.getChildren().get(1);
				ImageView ii1 = new ImageView();
				ii1.setImage(iv.getImage());
				if (v1.getChildren().size() == 2) {
					v1.getChildren().add(iv);
				} else {
					int g = 0;
					for (int k = 0; k < h1.getChildren().size(); k++) {
						if (((StackPane) (h1.getChildren().get(k))).getChildren().size() == 1) {
							g = k;
						}
					}
					((StackPane) (h1.getChildren().get(g))).getChildren().add((ImageView) v1.getChildren().get(2));
					v1.getChildren().add(iv);
				}
				f.setLeader(f.getTeam().get(j));
				if (f.getTeam().get(j) instanceof Hero) {
					Text tt = new Text(
							"Hero's Leader Ability will remove in Debuff Crowed Control in your Team and will add Embrace to them");
					tt.setWrappingWidth(200);
					tt.setFont(bgFont);
					V1.getChildren().set(1, tt);
				} else if (f.getTeam().get(j) instanceof AntiHero) {
					Text tt = new Text(
							"AntiHero's Leader Ability will Apply Stun for 2 turns on each team champions except the Leaders");
					tt.setWrappingWidth(200);
					tt.setFont(bgFont);
					V1.getChildren().set(1, tt);
				} else {
					Text tt = new Text(
							"Villain's Leader Ability will kill any enemy champion that has HP less than 30% of his max HP");
					tt.setWrappingWidth(200);
					tt.setFont(bgFont);
					V1.getChildren().set(1, tt);
				}
			});
		}
		for (int i = 0; i < h2.getChildren().size(); i++) {
			StackPane h = ((StackPane) h2.getChildren().get(i));
			int j = i;
			h.setOnMouseClicked(e -> {
				ImageView iv = (ImageView) h.getChildren().get(1);
				ImageView ii = (ImageView) h.getChildren().get(1);
				ImageView ii1 = new ImageView();
				ii1.setImage(iv.getImage());
				if (v2.getChildren().size() == 2) {
					v2.getChildren().add(iv);
				} else {
					int g = 0;
					for (int k = 0; k < h2.getChildren().size(); k++) {
						if (((StackPane) (h2.getChildren().get(k))).getChildren().size() == 1) {
							g = k;
						}
					}
					((StackPane) (h2.getChildren().get(g))).getChildren().add((ImageView) v2.getChildren().get(2));
					v2.getChildren().add(iv);
				}
				s.setLeader(s.getTeam().get(j));
				if (s.getTeam().get(j) instanceof Hero) {
					Text tt = new Text(
							"Hero's Leader Ability will remove in Debuff Crowed Control in your Team and will add Embrace to them");
					tt.setWrappingWidth(200);
					tt.setFont(bgFont);
					V2.getChildren().set(1, tt);
				} else if (s.getTeam().get(j) instanceof AntiHero) {
					Text tt = new Text(
							"AntiHero's Leader Ability will Apply Stun for 2 turns on each team champions except the Leaders");
					tt.setWrappingWidth(200);
					tt.setFont(bgFont);
					V2.getChildren().set(1, tt);
				} else {
					Text tt = new Text(
							"Villain's Leader Ability will kill any enemy champion that has HP less than 30% of his max HP");
					tt.setWrappingWidth(200);
					tt.setFont(bgFont);
					V2.getChildren().set(1, tt);
				}
			});
		}
		Boolean l1 = false;
		Button b1 = new Button("Select");
		setBStyle(b1);
		Boolean l2 = false;
		Button b2 = new Button("Select");
		setBStyle(b2);
		ArrayList<Boolean> ll = new ArrayList<Boolean>();
		ll.add(l1);
		ll.add(l2);
		b1.setOnAction(e -> {
			new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
			if (f.getLeader() != null) {
				ll.set(0, Boolean.TRUE);
				b1.setDisable(true);
			}
			if (b1.isDisable() == true && b2.isDisable() == true) {
				game = new Game(f, s);
				BorderPane mm = addMatch();
				BackgroundImage upb = new BackgroundImage(new Image("gaame.jpg"), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
				Background bg = new Background(upb);
				mm.setBackground(bg);
				match = new Scene(mm, Screen.getPrimary().getBounds().getWidth(),
						Screen.getPrimary().getBounds().getHeight() - 60);
				mediaPlayer.setMute(true);
				music("match.mp3");
				window.setScene(match);
			}
		});
		b2.setOnAction(e -> {
			new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
			if (s.getLeader() != null) {
				ll.set(1, Boolean.TRUE);
				b2.setDisable(true);
			}
			if (b1.isDisable() == true && b2.isDisable() == true) {
				game = new Game(f, s);
				BorderPane mm = addMatch();
				BackgroundImage upb = new BackgroundImage(new Image("gaame.jpg"), BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
				Background bg = new Background(upb);
				mm.setBackground(bg);
				match = new Scene(mm, Screen.getPrimary().getBounds().getWidth(),
						Screen.getPrimary().getBounds().getHeight() - 60);
				mediaPlayer.setMute(true);
				music("match.mp3");
				window.setScene(match);
			}
		});
		V1.getChildren().add(b1);
		V2.getChildren().add(b2);
		if (b1.isDisable() == true && b2.isDisable() == true) {
			game = new Game(f, s);
			match = new Scene(addMatch(), Screen.getPrimary().getBounds().getWidth(),
					Screen.getPrimary().getBounds().getHeight() - 60);
			mediaPlayer.setMute(true);
			music("match.mp3");
			window.setScene(match);
		}
//		for (int i = 0; i < h2.getChildren().size(); i++) {
//			Champion c = s.getTeam().get(i);
//			((Button) (h2.getChildren().get(i))).setOnAction(e -> {
//				s.setLeader(c);
//				game = new Game(f, s);
//				match = new Scene(addMatch(), Screen.getPrimary().getBounds().getWidth(),
//						Screen.getPrimary().getBounds().getHeight());
//				window.setScene(match);
//			});
//		}
		HBox all = new HBox();
		all.getChildren().addAll(V1, V2);
		all.setAlignment(Pos.CENTER);
		all.setSpacing(20);
		ls.setCenter(all);
		return ls;
	}

	private void setBStyle(Button b) {
		b.setMinSize(80, 45);
		b.setFont(bgFont);
		if (b.getText().equals("End Your Turn")) {
			b.setStyle("-fx-font:13 " + bgFont.getFamily());
		}
		if (b.getText().equals("use Leader Ability")) {
			b.setStyle("-fx-font:9 " + bgFont.getFamily());
		}
		if (b.getText().equals("Abilities")) {
			b.setStyle("-fx-font:15 " + bgFont.getFamily());
		}
		BackgroundImage upb = new BackgroundImage(new Image("unpressedbutton.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background bg = new Background(upb);
		BackgroundImage hover = new BackgroundImage(new Image("hover.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		BackgroundImage pb = new BackgroundImage(new Image("pressedbutton.png"), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		b.setBackground(bg);
		b.setOnMouseMoved(e -> {
			Background bb = new Background(hover);
			b.setBackground(bb);
		});
		b.setOnMouseExited(e -> b.setBackground(bg));
		b.setOnMousePressed(e -> {
			Background bb = new Background(pb);
			b.setBackground(bb);
		});
	}

	private BorderPane addMatch() {
		BorderPane gm = new BorderPane();
		VBox abi = new VBox();
		Button move = new Button("Move");
		Button Attack = new Button("Attack");
		Button Abilities = new Button("Abilities");
//		Abilities.setStyle("-fx-font-size: 15");
		Button lAbili = new Button("use Leader Ability");
//		lAbili.setStyle("-fx-font-size: 8");
		Button back = new Button("Back");
		Button endTurn = new Button("End Your Turn");
//		endTurn.setStyle("-fx-font-size: 8");
		setBStyle(move);
		setBStyle(Attack);
		setBStyle(Abilities);
		setBStyle(lAbili);
		setBStyle(back);
		setBStyle(endTurn);
		move.setOnAction(e -> {
			new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
			move.setOnKeyPressed(event -> {
				try {
					if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)
						game.move(Direction.UP);
					else if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)
						game.move(Direction.DOWN);
					else if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT)
						game.move(Direction.RIGHT);
					else if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT)
						game.move(Direction.LEFT);
				} catch (NotEnoughResourcesException e1) {
					AlertBox.display(e1.getMessage());
				} catch (UnallowedMovementException e1) {
					AlertBox.display(e1.getMessage());
				} finally {
					addBoard(gm);
				}
			});
		});
		Attack.setOnAction(e -> {
			new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
			Attack.setOnKeyPressed(event -> {
				try {
					if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)
						game.attack(Direction.UP);
					else if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)
						game.attack(Direction.DOWN);
					else if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT)
						game.attack(Direction.RIGHT);
					else if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT)
						game.attack(Direction.LEFT);
				} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e1) {
					AlertBox.display(e1.getMessage());
				} finally {
					AudioClip aa = new AudioClip(getClass().getResource("attack.mp3").toExternalForm());
					aa.setVolume(500);
					aa.play();
					if (game.checkGameOver() != null)
						EndGameBox.display("Congrats " + game.checkGameOver().getName() + " Won!", window);
					addBoard(gm);
				}
			});
		});
		Abilities.setOnAction(e -> {
			new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
			VBox ab = new VBox();
			ab.setAlignment(Pos.CENTER);
			ab.setSpacing(10);
			Champion h = game.getCurrentChampion();
			for (int i = 0; i < h.getAbilities().size(); i++) {
				Ability a = h.getAbilities().get(i);
				Button b = new Button(a.getName());
				b.setStyle("-fx-font:8 " + bgFont.getFamily());
				abiUse(a, b, gm);
				ab.getChildren().add(b);
				setBStyle(b);
			}
			ab.getChildren().add(back);
			gm.setRight(ab);
			ab.setPadding(new Insets(0, 100, 0, 0));
		});
		lAbili.setOnAction(e -> {
			 new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
			try {
				game.useLeaderAbility();
			} catch (LeaderNotCurrentException | LeaderAbilityAlreadyUsedException e1) {
				AlertBox.display(e1.getMessage());
			} finally {
				if (game.checkGameOver() != null)
					EndGameBox.display("Congrats " + game.checkGameOver().getName() + " Won!", window);
				addBoard(gm);
			}
		});
		endTurn.setOnAction(e -> {
			new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
			game.endTurn();
			addBoard(gm);
		});
		VBox v = new VBox();
		v.setSpacing(10);
		v.getChildren().addAll(move, Attack, Abilities, lAbili, endTurn);
		addBoard(gm);
		gm.setRight(v);
		v.setAlignment(Pos.CENTER);
		v.setPadding(new Insets(0, 100, 0, 0));
		;
		back.setOnAction(e -> {
			new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
			gm.setRight(v);
		});
		return gm;
	}

	private HBox turnorder() {
		HBox h = new HBox();
		Text t = new Text("the Turn of ->");
		t.setFont(bgFont);
		t.setFill(Color.WHITE);
		h.getChildren().add(t);
		ArrayList<Champion> champ = new ArrayList<Champion>();
		ArrayList<Champion> stunned = new ArrayList<Champion>();
		while (!game.getTurnOrder().isEmpty()) {
			if (((Champion) game.getTurnOrder().peekMin()).getCondition() != Condition.INACTIVE)
				champ.add((Champion) game.getTurnOrder().remove());
			else
				stunned.add((Champion) game.getTurnOrder().remove());
		}
		for (int i = 0; i < champ.size(); i++) {
			StackPane s = new StackPane();
			Champion ch = champ.get(i);
			Image image = new Image(getClass().getResource(ch.getName() + ".gif").toString());
			if (image.isError()) {
				image.getException().printStackTrace();
			}
			ImageView imageView = new ImageView(image);
			imageView.setImage(image);
			s.getChildren().addAll(imageView);
			h.getChildren().add(s);
		}
		while (!champ.isEmpty()) {
			game.getTurnOrder().insert(champ.remove(0));
		}
		while (!stunned.isEmpty()) {
			game.getTurnOrder().insert(stunned.remove(0));
		}
		return h;
	}

	private void abiUse(Ability a, Button b, BorderPane gm) {
		if (a.getCastArea() == AreaOfEffect.DIRECTIONAL) {
			b.setOnAction(e -> {
				new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
				b.setOnKeyPressed(event -> {
					try {
						if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)
							game.castAbility(a, Direction.UP);
						else if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN)
							game.castAbility(a, Direction.DOWN);
						else if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT)
							game.castAbility(a, Direction.RIGHT);
						else if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT)
							game.castAbility(a, Direction.LEFT);
					} catch (AbilityUseException e1) {
						AlertBox.display(e1.getMessage());
					} catch (CloneNotSupportedException e1) {
						AlertBox.display(e1.getMessage());
					} catch (NotEnoughResourcesException e1) {
						AlertBox.display(e1.getMessage());
					} finally {
						if (game.checkGameOver() != null)
							EndGameBox.display("Congrats " + game.checkGameOver().getName() + " Won!", window);
						addBoard(gm);
					}
				});
			});
		} else if (a.getCastArea() == AreaOfEffect.SINGLETARGET) {
			GridPane g = (GridPane) ((VBox) gm.getCenter()).getChildren().get(1);
			b.setOnAction(e -> {
				new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
				for (int i = 0; i < g.getChildren().size(); i++) {
					StackPane s = (StackPane) g.getChildren().get(i);
					int x = 4 - GridPane.getRowIndex(s);
					int y = GridPane.getColumnIndex(s);
					s.setOnMouseClicked(event -> {
						try {
							game.castAbility(a, x, y);
						} catch (NotEnoughResourcesException | AbilityUseException | InvalidTargetException
								| CloneNotSupportedException e1) {
							AlertBox.display(e1.getMessage());
						} finally {
							if (game.checkGameOver() != null)
								EndGameBox.display("Congrats " + game.checkGameOver().getName() + " Won!", window);
							addBoard(gm);
						}
					});
				}
			});
		} else {
			b.setOnAction(e -> {
				new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
				try {
					game.castAbility(a);
				} catch (NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e1) {
					AlertBox.display(e1.getMessage());
				} finally {
					if (game.checkGameOver() != null)
						EndGameBox.display("Congrats " + game.checkGameOver().getName() + " Won!", window);
					addBoard(gm);
				}
			});
		}
	}

	private void addBoard(BorderPane p) {
		GridPane gb = new GridPane();
		Image h100 = new Image("health100.png");
		Image h75 = new Image("health75.png");
		Image h50 = new Image("health50.png");
		Image h25 = new Image("health25s.png");
		Image h100s = new Image("health100ss.png");
		Image h75s = new Image("health75ss.png");
		Image h50s = new Image("health50s.png");
		Image h25s = new Image("health25.png");
		ImageView iv = new ImageView();
		Text pInfo = new Text("");
		pInfo.setFont(bgFont);
		pInfo.setStyle("-fx-font-size: 16");
		pInfo.setFill(Color.WHITE);
		pInfo.setTextAlignment(TextAlignment.LEFT);
		int h = 4;
		int k = 1;
		for (int i = 0; i < 5; i++) {
			for (int j = 4; j >= 0; j--) {
				Rectangle rec = new Rectangle(100, 100);
				rec.setFill(Color.WHITE);
				rec.setStroke(Color.BLACK);
				rec.setStrokeWidth(4);
				Text t = new Text("");
				Damageable c = null;
				ImageView imageView = new ImageView();
				ImageView health = new ImageView();
				ArrayList<String> info = new ArrayList<String>();
				if (game.getBoard()[h - j][i] instanceof Champion) {
					if (game.getFirstPlayer().getTeam().contains((Champion) game.getBoard()[h - j][i])) {
						rec.setStroke(Color.DODGERBLUE);
					}
					if (game.getSecondPlayer().getTeam().contains((Champion) game.getBoard()[h - j][i])) {
						rec.setStroke(Color.DARKRED);
					}
					if (game.getCurrentChampion().equals((Champion) game.getBoard()[h - j][i])) {
						rec.setStroke(Color.GREEN);
					}

					Image image = new Image(getClass()
							.getResource(((Champion) game.getBoard()[h - j][i]).getName() + ".gif").toString());
					if (image.isError()) {
						image.getException().printStackTrace();
					}
					imageView.setImage(image);
					c = (Champion) game.getBoard()[h - j][i];
					info.add(c.getInfo() + " " + isLeader((Champion) c));
					if (c.getCurrentHP() <= (1 * ((Champion) c).getMaxHP()))
						health.setImage(h100s);
					if (c.getCurrentHP() <= (0.75 * ((Champion) c).getMaxHP()))
						health.setImage(h75s);
					if (c.getCurrentHP() <= (0.5 * ((Champion) c).getMaxHP()))
						health.setImage(h50s);
					if (c.getCurrentHP() <= (0.25 * ((Champion) c).getMaxHP()))
						health.setImage(h25s);
				} else if (game.getBoard()[h - j][i] instanceof Cover) {
					c = (Cover) game.getBoard()[h - j][i];
					if (c.getCurrentHP() <= 100) {
						Image image = new Image(getClass().getResource("coverbroken.gif").toString());
						if (image.isError()) {
							image.getException().printStackTrace();
						}
						imageView.setImage(image);
						if (c.getCurrentHP() <= (1 * 1000))
							health.setImage(h100s);
						if (c.getCurrentHP() <= (0.75 * 1000))
							health.setImage(h75s);
						if (c.getCurrentHP() <= (0.5 * 1000))
							health.setImage(h50s);
						if (c.getCurrentHP() <= (0.25 * 1000))
							health.setImage(h25s);
					} else {
						Image image = new Image(getClass().getResource("cover.gif").toString());
						if (image.isError()) {
							image.getException().printStackTrace();
						}
						imageView.setImage(image);
						if (c.getCurrentHP() <= (1 * 1000))
							health.setImage(h100s);
						if (c.getCurrentHP() <= (0.75 * 1000))
							health.setImage(h75s);
						if (c.getCurrentHP() <= (0.5 * 1000))
							health.setImage(h50s);
						if (c.getCurrentHP() <= (0.25 * 1000))
							health.setImage(h25s);
					}
					info.add("Cover HP: " + c.getInfo());
				}
				health.setTranslateY(-35);
				gb.add(new StackPane(rec, imageView,health), i, j);
				imageView.setOnMouseMoved(e -> {
					if (!info.isEmpty())
						pInfo.setText(info.get(0));
				});

			}
		}
		Champion f = game.getCurrentChampion();
		VBox infos = new VBox();
		if (game.getCurrentChampion().getCurrentHP() <= (1 * game.getCurrentChampion().getMaxHP()))
			iv.setImage(h100);
		if (game.getCurrentChampion().getCurrentHP() <= (0.75 * game.getCurrentChampion().getMaxHP()))
			iv.setImage(h75);
		if (game.getCurrentChampion().getCurrentHP() <= (0.5 * game.getCurrentChampion().getMaxHP()))
			iv.setImage(h50);
		if (game.getCurrentChampion().getCurrentHP() <= (0.25 * game.getCurrentChampion().getMaxHP()))
			iv.setImage(h25);

		infos.getChildren().add(iv);
		Text t = new Text(f.getInfo() + "\n" + isLeader(f) + "\n");
		infos.setTranslateY(-35);
		infos.getChildren().add(t);
		t.setFont(bgFont);
		t.setStyle("-fx-font-size: 16");
		t.setFill(Color.WHITE);
		for (int j = 0; j < f.getAbilities().size(); j++) {
			Text a1 = new Text(f.getAbilities().get(j).toString());
			a1.setFont(bgFont);
			a1.setStyle("-fx-font-size: 16");
			a1.setFill(Color.WHITE);
			if (f.getAbilities().get(j).getCurrentCooldown() > 0)
				a1.setFill(Color.RED);
			if (f.getAbilities().get(j).getCurrentCooldown() == 1)
				a1.setFill(Color.YELLOW);
			a1.setTextAlignment(TextAlignment.CENTER);
			infos.getChildren().add(a1);
		}
		HBox playersName = namess();
//		HBox h1 = new HBox();
//		h1.setSpacing(20);
//		h1.getChildren().addAll(pInfo, t, a1, a2, a3);
//		VBox v = new VBox();
		HBox turn = turnorder();
//		v.getChildren().addAll(turn, gb, h1);
		pInfo.setTranslateX(10);
		infos.getChildren().addAll(pInfo);
		infos.setMaxWidth(200);
		infos.setMinWidth(200);
		infos.setTranslateX(80);
		VBox m = new VBox();
		playersName.setAlignment(Pos.CENTER);
		gb.setAlignment(Pos.CENTER);
		turn.setAlignment(Pos.CENTER);
		m.getChildren().addAll(playersName, gb, turn);
		infos.setAlignment(Pos.TOP_CENTER);
//		infos.setPadding();
		p.setLeft(infos);
		p.setCenter(m);
	}

	private HBox namess() {
		HBox h = new HBox();
		Image fi = new Image("namef.png");
		Image si = new Image("names.png");
		Image ui = new Image("name.png");
		ImageView fiv = new ImageView(fi);
		ImageView siv = new ImageView(ui);
		StackPane fs = new StackPane();
		StackPane ss = new StackPane();
		Text ft = new Text(f.getName());
		Text st = new Text(s.getName());
		ft.setFont(bgFont);
		st.setFont(bgFont);
		fs.getChildren().addAll(fiv, ft);
		ss.getChildren().addAll(siv, st);
		h.setSpacing(10);
		if (f.getTeam().contains(game.getCurrentChampion())) {
			((ImageView) fs.getChildren().get(0)).setImage(fi);
			((ImageView) ss.getChildren().get(0)).setImage(ui);
		} else {
			((ImageView) fs.getChildren().get(0)).setImage(ui);
			((ImageView) ss.getChildren().get(0)).setImage(si);
		}
		h.getChildren().addAll(fs, ss);
		Text tt = new Text();
		fs.getChildren().add(tt);
		tt.setFont(bgFont);
		tt.setStyle("-fx-font-size: 11");
		Text tts = new Text();
		ss.getChildren().add(tts);
		tts.setFont(bgFont);
		tts.setStyle("-fx-font-size: 11");
		tt.setFill(Color.WHITE);
		tts.setFill(Color.WHITE);
		tt.setTranslateY(20);
		tts.setTranslateY(20);
		if (!game.isFirstLeaderAbilityUsed()) {
			((Text) fs.getChildren().get(2)).setText("Leader Ability not used yet");
		}
		if (!game.isSecondLeaderAbilityUsed()) {
			((Text) ss.getChildren().get(2)).setText("Leader Ability not used yet");
		}
		if (game.isFirstLeaderAbilityUsed()) {
			((Text) fs.getChildren().get(2)).setText("Leader Ability is used");
		}
		if (game.isSecondLeaderAbilityUsed()) {
			((Text) ss.getChildren().get(2)).setText("Leader Ability is used");
		}
		return h;
	}

	private String isLeader(Champion c) {
		if (game.getFirstPlayer().getLeader().equals(c))
			return "Leader of " + game.getFirstPlayer().getName();
		else if (game.getSecondPlayer().getLeader().equals(c))
			return "Leader of " + game.getSecondPlayer().getName();
		return "Not Leader";
	}

	private BorderPane addChampTable() {
		BorderPane cs = new BorderPane();
		GridPane ctable = new GridPane();
		int x = 0;
		int y = 0;
		VBox all = new VBox();
		all.getChildren().add(tables());
		cs.setCenter(all);
		ctable.setVgap(5);
		ctable.setHgap(5);
		ctable.setAlignment(Pos.CENTER);
		ctable.setPadding(new Insets(40, 40, 40, 40));
		for (int i = 0; i < Game.getAvailableChampions().size(); i++) {
			Image image = new Image(Game.getAvailableChampions().get(i).getName() + ".jpg");
			ImageView iv = new ImageView(image);
			Button b = new Button();
			b.setPrefSize(70, 70);
			b.setGraphic(iv);
			GridPane.setConstraints(b, x, y);
			x++;
			ctable.getChildren().add(b);
			if (x >= 8) {
				x = 0;
				y++;
			}
		}
		ArrayList<Button> buttons = new ArrayList<Button>();
		ArrayList<Champion> champs = new ArrayList<Champion>();
		StackPane infoPane = new StackPane();
		infoPane.setPrefWidth(100);
		infoPane.setPadding(new Insets(40));
		ImageView iv = new ImageView(new Image("Random.png"));
		Button rnd = new Button();
		rnd.setGraphic(iv);
		rnd.setPrefSize(70, 70);
		rnd.setOnAction(e -> {
			new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
			int max = ctable.getChildren().size() - 1;
			int t = (int) (Math.random() * (max - 0 + 1));
			while (ctable.getChildren().get(t).isDisable()) {
				t = (int) (Math.random() * (max - 0 + 1));
			}
			((Button) ctable.getChildren().get(t)).fire();
		});
		GridPane.setConstraints(rnd, 7, 1);
		ctable.getChildren().add(rnd);
		ImageView imageView = new ImageView();
		for (int i = 0; i < ctable.getChildren().size() - 1; i++) {
			Champion c = Game.getAvailableChampions().get(i);
			String s1 = c.toString() + c.apptoString();
			Text info = new Text(s1);
			info.setFont(bgFont);
			info.setStyle("-fx-font-size: 15");
			info.setWrappingWidth(200);
			Button k = ((Button) ctable.getChildren().get(i));
			k.setPrefSize(70, 70);
			((Button) ctable.getChildren().get(i)).setOnAction(e -> {
				new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
				if (!infoPane.getChildren().isEmpty())
					infoPane.getChildren().remove(0);
				infoPane.getChildren().add(info);
				champs.removeAll(champs);
				champs.add(c);
				buttons.removeAll(buttons);
				buttons.add(k);
				Image image = new Image(getClass().getResource(c.getName() + ".gif").toString());
				if (image.isError()) {
					image.getException().printStackTrace();
				}
				ImageView ii = new ImageView(image);
				((ImageView) (((StackPane) ((GridPane) all.getChildren().get(0)).getChildren().get(0)).getChildren()
						.get(1))).setImage(image);
			});
		}
		((Button) ctable.getChildren().get(0)).fire();
		Button select = new Button("Select");
		select.setMinSize(80, 45);
		setBStyle(select);
		StackPane turn = new StackPane();
		String t1 = (f.getName() + " selecting Champion " + (click));

		((Text) ((GridPane) all.getChildren().get(0)).getChildren().get(1)).setText(t1);
		select.setOnAction(e -> {
			new AudioClip(getClass().getResource("click.mp3").toExternalForm()).play();
			if (f.getTeam().size() < 3) {

				try {
					f.getTeam().add(champs.remove(champs.size() - 1));
					buttons.remove(buttons.size() - 1).setDisable(true);
					all.getChildren().set(0, tables());
					click++;
					int mm = 0;
					while (ctable.getChildren().get(mm).isDisable()) {
						mm++;
					}
					((Button) ctable.getChildren().get(mm)).fire();
					if (click <= 3) {
						String t = (f.getName() + " select Champion " + (click));
						((Text) ((GridPane) all.getChildren().get(0)).getChildren().get(1)).setText(t);
					} else {
						turn.getChildren().removeAll(turn.getChildren());
						String t = (s.getName() + " select Champion " + (click - 3));
						((Text) ((GridPane) all.getChildren().get(0)).getChildren().get(1)).setText(t);

					}
				} catch (Exception e1) {
					AlertBox.display("Select a Champion first");
				}
			} else if (s.getTeam().size() <= 2) {
				try {

					s.getTeam().add(champs.remove(champs.size() - 1));
					buttons.remove(buttons.size() - 1).setDisable(true);
					all.getChildren().set(0, tables());
//					rnd.fire();
					int mn = 0;
					while (ctable.getChildren().get(mn).isDisable()) {
						mn++;
					}
					((Button) ctable.getChildren().get(mn)).fire();
					click++;
					if (click <= 6) {
						turn.getChildren().removeAll(turn.getChildren());
						String t = (s.getName() + " select Champion " + (click - 3));
						((Text) ((GridPane) all.getChildren().get(0)).getChildren().get(1)).setText(t);
					}
				} catch (Exception e1) {
					AlertBox.display("Select a Champion first");
				}
			}
			if (s.getTeam().size() == 3) {
				leaderSelect = new Scene(leaderlayout(), Screen.getPrimary().getBounds().getWidth(),
						Screen.getPrimary().getBounds().getHeight() - 60);
				window.setScene(leaderSelect);
			}
		});
		HBox h = new HBox();
		h.setAlignment(Pos.CENTER);
		h.setPadding(new Insets(0, 0, 100, 0));
		h.getChildren().addAll(select);
		all.setAlignment(Pos.CENTER);
		turn.setPadding(new Insets(50, 0, 0, 0));
		all.getChildren().addAll(ctable, h);
//		cs.setCenter(ctable);
		cs.setRight(infoPane);
//		cs.setBottom(h);

		return cs;
	}

	private GridPane tables() {
		GridPane g = new GridPane();
		Text fl = new Text();
		fl.setFont(bgFont);
		Text sl = new Text();
		sl.setFont(bgFont);
		fl.setStyle("-fx-font-size: 20");
		sl.setStyle("-fx-font-size: 20");
		g.setHgap(10);
		StackPane ii = new StackPane();
		ImageView iv = new ImageView();
		Rectangle rect = new Rectangle(100, 100);
		rect.setFill(Color.TRANSPARENT);
		ii.getChildren().addAll(rect, iv);
		GridPane.setConstraints(ii, 1, 1);
		fl.setText(f.getName() + "'s Team");
		sl.setText(s.getName() + "'s Team");
		GridPane ft = new GridPane();
		GridPane st = new GridPane();
		for (int i = 0; i < 3; i++) {
			StackPane s = new StackPane();
			Rectangle rec = new Rectangle(100, 100);
			rec.setFill(Color.WHITE);
			rec.setStroke(Color.DODGERBLUE);
			rec.setStrokeWidth(4);
			s.getChildren().add(rec);
			GridPane.setConstraints(s, i, 0);
			ft.getChildren().add(s);
		}
		for (int i = 0; i < 3; i++) {
			StackPane s = new StackPane();
			Rectangle rec = new Rectangle(100, 100);
			rec.setFill(Color.WHITE);
			rec.setStroke(Color.DARKRED);
			rec.setStrokeWidth(4);
			s.getChildren().add(rec);
			GridPane.setConstraints(s, i, 0);
			st.getChildren().add(s);
		}
		for (int i = 0; i < f.getTeam().size(); i++) {
			Image image = new Image(getClass().getResource(f.getTeam().get(i).getName() + ".gif").toString());
			if (image.isError()) {
				image.getException().printStackTrace();
			}
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			((StackPane) ft.getChildren().get(i)).getChildren().add(imageView);
		}
		for (int i = 0; i < s.getTeam().size(); i++) {
			Image image = new Image(getClass().getResource(s.getTeam().get(i).getName() + ".gif").toString());
			if (image.isError()) {
				image.getException().printStackTrace();
			}
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			((StackPane) st.getChildren().get(i)).getChildren().add(imageView);
		}
		Text t = new Text();
		t.setStyle("-fx-font-size: 20");
		t.setFont(bgFont);
		GridPane.setConstraints(t, 1, 0);
		GridPane.setConstraints(fl, 0, 0);
		GridPane.setConstraints(sl, 2, 0);
		GridPane.setConstraints(ft, 0, 1);
		GridPane.setConstraints(st, 2, 1);
		g.getChildren().addAll(ii, t, fl, sl, ft, st);
		g.setAlignment(Pos.CENTER);
		return g;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void packetRec(Object o) {
	}

	public void close() {
	}

}
