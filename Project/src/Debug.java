import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Debug extends Game{

	private Boolean status;
	
	public Debug(Boolean status){
		this.status = status;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	/**
	 * manualGrid() lets you manully place pieces
	 * <p>
	 * The fillArray array is used to manually fill the grid with any pieces the user would like. This is
	 * useful for testing certain situations that would be difficult to test for with a randomly generated board
	 * </p>
	 * @author Joey Guenette
	 * 
	 */
	
	public void manualGrid(){
		
		//Use this to manually fill the grid with your own pieces to re-create random situations that cause errors
		//must set both Debug object and ManualBoardGen variables to true in Game.java
		
		int[] fillArray = {
				
				1,1,4,1,1,1,1,
				1,1,6,1,1,1,3,
				1,1,4,1,3,3,5,
				1,1,6,3,1,3,1,
				1,1,5,3,1,1,1,
				1,1,3,1,1,1,1,
				1,1,3,1,1,1,1,
				
				/* 
				In case you a need quick reset
				
				1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,
				
				*/
				
		};
		
		for(int i = 0; i < 7 ; i++){
			for(int j = 0; j < 7; j++){
				System.out.println((j*7) + i);
				
				switch(fillArray[(j*7) + i]){
					case 0:
						pieceArray[i][j] = new GamePiece("red", 0, 1000, new Image("red.png"));
					break;
					case 1:
						pieceArray[i][j] = new GamePiece("green", 1, 1000, new Image("green.png"));
					break;
					case 2:
						pieceArray[i][j] = new GamePiece("blue" , 2, 1000, new Image("blue.png"));
					break;
					case 3:
						pieceArray[i][j] = new GamePiece("purple", 3, 1000, new Image("purple.png"));
					break;
					case 4:
						pieceArray[i][j] = new GamePiece("gray", 4, 1000, new Image("gray.png"));
					break;
					case 5:
						pieceArray[i][j] = new GamePiece("orange", 5, 1000, new Image("orange.png"));
					break;
					case 6:
						pieceArray[i][j] = new GamePiece("pink", 6, 1000, new Image("pink.png"));
					break;
					default:
						pieceArray[i][j] = new GamePiece("red", 0, 1000, new Image("red.png"));
					break;
				}
				
				pieceArray[i][j].setiIndex(i);
				pieceArray[i][j].setjIndex(j);
				
				FadeTransition fade = new FadeTransition(Duration.millis(1000), pieceArray[i][j]);
				fade.setFromValue(0.1);
				fade.setToValue(1);
				fade.setCycleCount(1);
				gPane.add(pieceArray[i][j], i, j);
				fade.play();
				
			}
		}
		
	}
	
}
