import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application{
	
	//turn debug stuff on or off
	static Debug debug = new Debug(false);
	static Boolean ManualBoardGen = false;
	//static Boolean StopTimer = false;
	
	/**
	 * main() is the main method, and is only used to launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		Application.launch(args);
	}
	
	static public GamePiece[][] pieceArray = new GamePiece[7][7];
	public ImageView[][] gridArray = new ImageView[7][7];
	static Random r = new Random();
	public static int rand;
	static int matchNum = 0;
	private static String highScore = "";
	StackPane messageDisplay = new StackPane();
	static Text message = new Text("Game Start!");
	static GridPane gPane = new GridPane();
	StackPane sPane = new StackPane();
	static GamePiece[] array = new GamePiece[7];
	static int numOfPieces = 7;		// This can be an option that can be changed perhaps?
	static int score = 0;
	StackPane stack = new StackPane();
	ImageView back = new ImageView(new Image("background.png"));
	Label timer = new Label("80"); 
	static Label scoreDisplay = new Label("Score : 0");
	static Label highScores = new Label(GetHighScore());
	static GamePiece swap1, swap2;
	public static String name;
	HBox hbox = new HBox();
	VBox gameVbox = new VBox();
	VBox gameDisplay = new VBox();
	Stage gameStage = new Stage();
	static Stage menuStage = new Stage();
	
	/**
	 * start() is the main method in this program. It runs the rest of the game.
	 * <p>
	 * The start method handles numerous things. It handles creating and modifying attributes for numerous panes, scenes and stages.
	 * It holds the array of GamePiece's used for the game itself, and also displays the menu.
	 * It displays the menuStage which contains the Start, LeaderBoard, and Exit buttons.
	 * It displays the GameStage which contains the game itself, a timer and score label.
	 * It displays the LeaderStage which contains the current highscore, as well as Start and Exit buttons.
	 * It does not, however, ever show the primaryStage. This was done because the main menu needed to be accessed
	 * from outside of the start method.
	 * </p>
	 * @author Cliff Cartier
	 * @author James Pierce
	 * @author Joey Guenette
	 * 
	 * @param primaryStage is the main stage created by the application. This never ends up being used.
	 */
	public void start(Stage primaryStage) throws Exception {
		
		//Pane property modifications
		gameDisplay.setAlignment(Pos.CENTER);
		gPane.setAlignment(Pos.CENTER);
		gPane.setPadding(new Insets(10, 10, 10, 10));
		hbox.setAlignment(Pos.CENTER);
		gameVbox.setAlignment(Pos.CENTER);
		VBox.setMargin(scoreDisplay, new Insets(100, 0, 0, 0));
		gPane.setHgap(10);
		gPane.setVgap(10);
		messageDisplay.getChildren().add(message);
		messageDisplay.getStyleClass().add("stackpane");
		stack.getChildren().addAll(gPane, back);
		gameDisplay.setSpacing(10);
		gameDisplay.getChildren().addAll(messageDisplay, stack);
		menuStage.setResizable(false);
		
		//GamePiece object declaration
		GamePiece red = new GamePiece("red", 0, 1000, new Image("red.png"));
		GamePiece green = new GamePiece("green", 1, 1000, new Image("green.png"));
		GamePiece blue = new GamePiece("blue" , 2, 1000, new Image("blue.png"));
		GamePiece purple = new GamePiece("purple", 3, 1000, new Image("purple.png"));
		GamePiece gray = new GamePiece("gray", 4, 1000, new Image("gray.png"));
		GamePiece orange = new GamePiece("orange", 5, 1000, new Image("orange.png"));
		GamePiece pink = new GamePiece("pink", 6, 1000, new Image("pink.png"));
		
		//GamePiece object array declaration
		
		array[0] = red;
		array[1] = green;
		array[2] = blue;
		array[3] = purple;
		array[4] = gray;
		array[5] = orange;
		array[6] = pink;
		
		//Create a border pane
		HBox menuPane = new HBox();
		HBox gamePane = new HBox();
		Scene gameScene = new Scene(gamePane, 500, 400);
    	gameScene.setUserAgentStylesheet("customStyle.css");
    	gameStage.setScene(gameScene);
    	gameStage.setTitle("Game!!!");
		
		menuPane.setSpacing(20);
	    menuPane.setAlignment(Pos.CENTER);
	    
		VBox mainMenu = new VBox();
		mainMenu.setSpacing(50);
		mainMenu.setAlignment(Pos.CENTER);
		
		//Create a buttons
		Button start = new Button("Start");
		start.setAlignment(Pos.CENTER);//Place nodes in the pane
	    start.setOnAction(e -> {
	    	gPane.getChildren().clear();
	    	gamePane.getChildren().clear();
	    	gameVbox.getChildren().clear();
			gameVbox.getChildren().addAll(new Timer(), scoreDisplay);
	    	gamePane.getChildren().addAll(gameVbox, gameDisplay);
	    	boardGen(array, gPane, pieceArray);
	    	menuStage.close();
	    	gameStage.show();
	    });
	    
	    Button leader = new Button("Leader Board");
	    leader.setAlignment(Pos.CENTER);//Place nodes in the pane
	  
	   leader.setOnAction(e -> {
		   menuStage.close();
		   
		   HBox leaderHPane = new HBox();
			leaderHPane.setSpacing(20);
		    leaderHPane.setAlignment(Pos.CENTER);
			
		    VBox leaderVbox = new VBox();
			leaderVbox.setSpacing(50);
			leaderVbox.setAlignment(Pos.CENTER);
			
			StackPane leaderStack = new StackPane();
			
			Stage leaderStage = new Stage();
			leaderStage.setTitle("Leader Board");
			
			leaderHPane.setSpacing(20);
			leaderHPane.setAlignment(Pos.CENTER);
			
			VBox leaderMenu = new VBox();
			leaderMenu.setSpacing(50);
			leaderMenu.setAlignment(Pos.CENTER);
			
			//Create a buttons
			Button leaderStart = new Button("Start");
		    leaderStart.setAlignment(Pos.CENTER);//Place nodes in the pane
		    //start.setFont(null);
		    leaderStart.setOnAction(f -> {
		    	gPane.getChildren().clear();
		    	gamePane.getChildren().clear();
		    	gameVbox.getChildren().clear();
		    	gameVbox.getChildren().addAll(new Timer(), scoreDisplay);
		    	gamePane.getChildren().addAll(gameVbox, gameDisplay);
		    	boardGen(array, gPane, pieceArray);
		    	leaderStage.close();
		    	gameStage.show();
		    });
			       
			Button leaderExit = new Button("Exit");
			leaderExit.setAlignment(Pos.CENTER);//Place nodes in the pane
			leaderExit.setOnAction(f -> {
				leaderStage.close();
				menuStage.show();
		    });
			
			Scene leaderScene = new Scene(leaderHPane, 300, 200);
			leaderScene.setUserAgentStylesheet("customStyle.css");
			leaderStage.setScene(leaderScene);
			leaderStage.show();
			
			leaderHPane.getChildren().clear();
			leaderHPane.getChildren().addAll(leaderVbox,leaderStack);
			
			leaderVbox.setSpacing(20);
			leaderVbox.getChildren().addAll(leaderStart,leaderExit);
			leaderStack.getChildren().clear();
			leaderStack.getChildren().add(new Label(GetHighScore()));
			
			leaderStage.setOnCloseRequest(f -> {
				menuStage.show();
			});
			
	    });
		       
		Button exit = new Button("Exit");
		exit.setAlignment(Pos.CENTER);//Place nodes in the pane
		exit.setOnAction(e -> {
			System.exit(0);
		});
		
		
		mainMenu.getChildren().addAll(start,leader,exit);
		menuPane.getChildren().addAll(mainMenu);
		
		//Create a scene and place it in the stage
		Scene menu = new Scene(menuPane, 500, 400);
		menu.setUserAgentStylesheet("customStyle.css");
		menuStage.setTitle("Main Menu");//Set the stage title
		menuStage.setScene(menu);
		start.setTooltip(curser());
		menuStage.show();	
		
		
		gameStage.setOnCloseRequest(w -> {
		menuStage.show();
	});
	
	
}
	/**
	 * boardGen() is used to generate pieces to fill the board with. It then places the pieces to the pieceArray array and the gPane GridPane.
	 * <p>
	 * boardGen starts by looking for an existing high score. If one does not exist, it sets a default high score
	 * by calling the GetHighScore() method. It then loops through all the positions of the pieceArray array
	 * and fills them pieces. These pieces are copies made from the array Array, and are randomly selected.
	 * It then sets the iIndex and jIndex for each piece so they can self-reference their position on the screen
	 * relative to the gPane. Lastly the pieces are placed into the gPane using a FadeTransition.
	 * </p>
	 * @author James Pierce
	 * 
	 * @param array is used to store the different types of pieces. Copies of the pieces are made from here.
	 * @param gPane is used for displaying the pieces to the screen. 
	 * @param pieceArray is used in accordance with the positions of the pieces on the gPane. Allows the program
	 * 		  	to reference the pieces more easily.
	 */
	public static void boardGen(GamePiece[] array, GridPane gPane, GamePiece[][] pieceArray){
		
		//generation array
		if (highScore.equals(""))
		{
			//initialize the highscore
			highScore = GetHighScore();
		}
		for(int i = 0; i < 7 ; i++){
			for(int j = 0; j < 7; j++){
				rand = r.nextInt(numOfPieces);
				
				// Because GamePiece extends ImageView, we can place use the GamePiece array
				// to directly display the objects in the GridPane.
				pieceArray[i][j] = new GamePiece(array[rand].getName(), array[rand].getType(), array[rand].getValue(), array[rand].getImage());
				
				// These set the iIndex and jIndex values inside the objects when they are first created.
				pieceArray[i][j].setiIndex(i);
				pieceArray[i][j].setjIndex(j);
				
				// Adds the GamePiece to the array.
				FadeTransition fade = new FadeTransition(Duration.millis(1000), pieceArray[i][j]);
				fade.setFromValue(0.1);
				fade.setToValue(1);
				fade.setCycleCount(1);
				gPane.add(pieceArray[i][j], i, j);
				fade.play();
				
			}
		}
	}

	/**
	 * delete() is used to remove pieces from the board. It requires the detect() method to do so.
	 * <p>
	 * delete uses row and col to reference the pieces it should be deleting. It does this by
	 * calling the detect() method using the row and col variables, and removing the pieces that were
	 * returned in the ArrayList from both the pieceArray array and the gPane where they are being
	 * displayed. This method also calculates and updates the score as neccesary.
	 * 
	 * This method will also calculate how far to drop pieces and checks for null points in the board
	 * so that it can generate new pieces for those empty spaces.
	 * </p>
	 * @author Joey Guenette
	 * 
	 * @param row is used for referencing pieces by their position in the gPane/pieceArray
	 * @param col is used for referencing pieces by their position in the gPane/pieceArray
	 */
	public static void delete(int row, int col){
			
		ArrayList<GamePiece> delList = Pieces.detect(row, col);
		int max = 7;
		int min = 0;
		double tScore = 0;
		GamePiece temp;
		int index = 0;
		Boolean vert = false;
		Boolean doneFall = false;
		
		if(delList.get(0).getjIndex() == delList.get(1).getjIndex()){
			
			vert = false;
		}else{
			
			vert = true;
		}
		
		
		if(delList.size() >= 3){

					if(!vert){
						for(int i = 0; i < delList.size(); i++){
						index = 0;
					
						tScore += delList.get(i).getValue();
					
					
						temp = delList.get(i);

						row = temp.getiIndex();
						col = temp.getjIndex();

					
						gPane.getChildren().remove(temp);
						
							while(col-1 >= 0 && col-(index+1) >= 0){
								System.out.println("horizontal");
								temp = pieceArray[row][col-index];
								gPane.getChildren().remove(temp);
								pieceArray[row][col-index] = new GamePiece (pieceArray[row][col-(index+1)].getName(), pieceArray[row][col-(index+1)].getType(), pieceArray[row][col-(index+1)].getValue(), pieceArray[row][col-(index+1)].getImage());
								pieceArray[row][col-index].setiIndex(row);
								pieceArray[row][col-index].setjIndex(col-index);
								FadeTransition fade = new FadeTransition(Duration.millis(1000), pieceArray[row][col-index]);
								fade.setFromValue(0.1);
								fade.setToValue(1);
								fade.setCycleCount(1);
								gPane.add(pieceArray[row][col-index], row, col-index);
								fade.play();
								System.out.println(index);
								index++;
							}
							
							rand = r.nextInt(numOfPieces);							
							temp = pieceArray[delList.get(i).getiIndex()][0];
							gPane.getChildren().remove(temp);
							pieceArray[delList.get(i).getiIndex()][0] = new GamePiece (array[rand].getName(), array[rand].getType(), array[rand].getValue(), array[rand].getImage());
							pieceArray[delList.get(i).getiIndex()][0].setiIndex(delList.get(i).getiIndex());
							pieceArray[delList.get(i).getiIndex()][0].setjIndex(0);
							FadeTransition fade = new FadeTransition(Duration.millis(1000), pieceArray[delList.get(i).getiIndex()][0]);
							fade.setFromValue(0.1);
							fade.setToValue(1);
							fade.setCycleCount(1);
							gPane.add(pieceArray[delList.get(i).getiIndex()][0], delList.get(i).getiIndex(), 0 );
							fade.play();
						}
						
					}else{
						
						for(int i = 0; i < delList.size(); i++){
							
							tScore += delList.get(i).getValue();
							
							
							temp = delList.get(i);

							row = temp.getiIndex();
							col = temp.getjIndex();

							
							gPane.getChildren().remove(temp);
							pieceArray[row][delList.get(i).getjIndex()] = null;
						}
						
						for(int j = 0; j < delList.size(); j++){
							if(delList.get(j).getjIndex() > min){
								min = delList.get(j).getjIndex();
								System.out.println("min is " + min);
							}
							if(delList.get(j).getjIndex() < max){
								max = delList.get(j).getjIndex();
								System.out.println("max is " + max);
							}
						}
						
						//moves pieces at top to fill bottom
						while(!doneFall && col-1 >= 0 && max-(index+1) >= 0){
							
							temp = pieceArray[row][max-(index+1)];
							gPane.getChildren().remove(temp);
							pieceArray[row][min-index] = new GamePiece(pieceArray[row][max-(index+1)].getName(), pieceArray[row][max-(index+1)].getType(), pieceArray[row][max-(index+1)].getValue(), pieceArray[row][max-(index+1)].getImage());
							pieceArray[row][min-index].setiIndex(row);
							pieceArray[row][min-index].setjIndex(min-index);
							pieceArray[row][max-(index+1)] = null;
							//gPane.add(pieceArray[row][min-index], row, min-index);
							FadeTransition fade = new FadeTransition(Duration.millis(1000), pieceArray[row][min-index]);
							fade.setFromValue(0.1);
							fade.setToValue(1);
							fade.setCycleCount(1);
							gPane.add(pieceArray[row][min-index], row, min-index);
							fade.play();
							System.out.println("Index: " +  index);
							index++;
						}
						
						System.out.println("got out of while");
						for(int j = 0; j < 6 ; j++){
							System.out.println("Got in for loop and doneFall is " + doneFall);
							if(pieceArray[row][j] == null){
								System.out.println("test");
								rand = r.nextInt(numOfPieces);							
								temp = pieceArray[row][j];
								gPane.getChildren().remove(temp);
								pieceArray[row][j] = new GamePiece (array[rand].getName(), array[rand].getType(), array[rand].getValue(), array[rand].getImage());
								pieceArray[row][j].setiIndex(row);
								pieceArray[row][j].setjIndex(j);
								FadeTransition fade = new FadeTransition(Duration.millis(1000), pieceArray[row][j]);
								fade.setFromValue(0.1);
								fade.setToValue(1);
								fade.setCycleCount(1);
								gPane.add(pieceArray[row][j], row, j);
								fade.play();
							}
						}
						if(index == 0 && min >= 6 && max <= 0){
							rand = r.nextInt(numOfPieces);							
							temp = pieceArray[row][6];
							gPane.getChildren().remove(temp);
							pieceArray[row][6] = new GamePiece (array[rand].getName(), array[rand].getType(), array[rand].getValue(), array[rand].getImage());
							pieceArray[row][6].setiIndex(row);
							pieceArray[row][6].setjIndex(6);
							FadeTransition fade = new FadeTransition(Duration.millis(1000), pieceArray[row][6]);
							fade.setFromValue(0.1);
							fade.setToValue(1);
							fade.setCycleCount(1);
							gPane.add(pieceArray[row][6], row, 6);
							fade.play();
						}
				
					}
					
					
					
					
					FadeTransition fade2 = new FadeTransition(Duration.millis(1000), pieceArray[row][col]);
					fade2.setFromValue(0.1);
					fade2.setToValue(1);
					fade2.setCycleCount(1);
					//gPane.getChildren().remove(temp);
						
					//pieceArray[row][j] = pieceArray[row][j+1];
						
					
					//System.out.println("other peice is now: " + pieceArray[row][col].getType());
					//Pieces.fall(row, col, delList);
					//gPane.add(pieceArray[row][col], row, col+1);
					fade2.play();
					
					

			//}
			// *** - FRANKENSTEIN THIS
			// ***
			// this is the algorithm to calculate score based on NUMBER of pieces returned
			// (25% bonus score for the match for every piece past 3 (5 match is +50% bonus score)
			PauseTransition pause = new PauseTransition(Duration.millis(250));
			FadeTransition fadeIn = new FadeTransition(Duration.millis(250), message);
			fadeIn.setFromValue(0.1);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);
			SequentialTransition st = new SequentialTransition(message, pause,  fadeIn);
			tScore = (tScore * (1 + (0.25 * (delList.size() - 3 ) ) ) );
			matchNum++;
			message.setText("Match #" + matchNum + " Points Value: " + (int)tScore);
			st.play();
			score += tScore;
			scoreDisplay.setText("Score : " + (int)score);
			// this is reset to prevent the score from getting broken
			tScore = 0;
			// ***
			// ***
			delList.clear();
		} else {
			delList.clear();
		}
			
	}
	
	
	/**
	 * curser() is used to display a message when the user hovers over the start button.
	 * 
	 * @author Cliff Cartier
	 * 
	 * @return tooltip
	 */
	static Tooltip curser(){
		Tooltip tooltip = new Tooltip();
		tooltip.setText("Start Game!");
		return tooltip;

		}
	
	/**
	 * GetHighScore() is used to read a high score from highscore.dat.
	 * <p>
	 * GetHighScore will look for an existing highscore.dat file, and if it exists it will open it
	 * and read it looking for a highscore. If it doesn't exist, it will return a default
	 * value of "Nobody:0".
	 * </p>
	 * @author Cliff Cartier
	 */
	public static String GetHighScore(){
		//format:  Paul:10000
		FileReader readFile =null;
		BufferedReader reader = null;
		try
		{
			readFile = new FileReader("highscore.dat");
			reader = new BufferedReader(readFile);
			return reader.readLine();
		}
		catch (Exception e)
		{
		return "Nobody:0";
		}
		finally
		{
			try {
				if(reader !=null)
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	
	/**
	 * CheckScore() is used to update the Leader Board if necessary.
	 * <p>
	 * CheckScore starts by checking for an existing highscore, if there isn't one, 
	 * it will return to end the method. If a score exists, and the player beat it,
	 * the method will open a new window and prompt the player to input their name
	 * for the leaderboard. It will then write the result of the score and name to
	 * highscore.dat for safely storing the highscore across playthroughs.	 * 
	 * </p>
	 * @author Cliff Cartier
	 */
	public static void CheckScore()
	{
		highScore = GetHighScore();
		if(highScore.equals(""))
			return;
		if(score>Integer.parseInt((highScore.split(":")[1]))){
			//user has set a new record
			Stage nameInput = new Stage();
			
			VBox inputVBox = new VBox();
			inputVBox.setSpacing(50);
			inputVBox.setAlignment(Pos.CENTER);
			
			StackPane inputPane = new StackPane();
			inputPane.setAlignment(Pos.CENTER);
			inputPane.getChildren().add(inputVBox);
			
			TextField tfInput = new TextField();
			tfInput.setMaxWidth(300);
			tfInput.getStyleClass().add("textfield");
			
			Button inputButton = new Button("OK");
			Label inputLabel = new Label("Please enter your name:");
			Scene inputScene = new Scene(inputPane, 500, 500);
			inputScene.setUserAgentStylesheet("customStyle.css");
			
			inputButton.setOnAction(e->{
				
				name = tfInput.getText();
				highScore = name + ":"+score;
				
				FileWriter writeFile = null;
				BufferedWriter writer = null;
				try
				{
					File scoreFile = new File("highscore.dat");
					writeFile = new FileWriter(scoreFile.toString());
					writer = new BufferedWriter(writeFile);
					writer.write(highScore);
					nameInput.close();
					
				}
				catch (Exception ex)
				{
					//errors
				}
				finally
				{
					try{
						if(writer != null)	
							writer.close();
					}
					catch (Exception ex){}
					}
				
				menuStage.show();
				score = 0;
			});
			// creates the high score enter stage
			inputVBox.getChildren().addAll(inputLabel, tfInput, inputButton);
			nameInput.setScene(inputScene);
			nameInput.show();
		}
	}
	
	/**
	 * Timer Class used to create a visibly changing Timer.
	 * <p>
	 * Timer extends Labels to make displaying it easier.
	 * The Timer class is used to create a countdown timer for the game.
	 * When the timer hits 0, it causes all of the pieces to no longer be able to be clicked. 
	 * </p>
	 * 
	 * @author James Pierce
	 */
	class Timer extends Label {
		//This is the amount of time before the game ends
		int sec = 60;
		  public Timer() {
			  
			  Timeline timeline = new Timeline(
				      new KeyFrame(Duration.seconds(0),
				        new EventHandler<ActionEvent>() {
				          @Override public void handle(ActionEvent actionEvent) {
				        	  // decrements the timer by 1
				        	  sec--;
				        	  setText(sec + "");
				        	  if(sec <= 0){
				        		  CheckScore();
				        		  gameStage.close();
				        		  if(score<Integer.parseInt((highScore.split(":")[1]))){
				        			  menuStage.show();
				        			  score = 0;
				        		  }
				        		  for(int i = 0; i < 7; i++){
				        			  for(int j = 0; j < 7; j++){
				        				  pieceArray[i][j].setOnMouseClicked(e ->{
				        					  
				        				  });
				        			  }
				        		  }

				        		  message.setText("Game is Over!");
				        		  
				        	  }
				          }
				        }
				      ),
				      new KeyFrame(Duration.millis(1000))	// This is the amount of time before it reiterates
				    );
				    timeline.setCycleCount(60);		// number of cycles to repeat
				    timeline.play();
		  }
	}
	
}