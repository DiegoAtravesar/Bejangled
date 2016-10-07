import java.util.ArrayList;

import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

/**
 * Pieces is a class file that stores some methods required by the pieces that did not need to be directly in the game class file.
 * <p>
 * This file includes the detect() method and the swap() method.
 * </p>
 * @author James Pierce
 * @author Joey Guenette
 *
 */
public class Pieces extends Game{
	
	/**
	 * detect() is a method to check for matches that include the piece it receives.
	 * <p>
	 * This method compares the types of the pieces above, below, to the left and right of the piece it receives.
	 * It uses this information to return a list of pieces that have matching types in a row.
	 * </p>
	 * @param row is the row value of the piece being checked.
	 * @param col is the column value of the piece being checked.
	 * @return deleteList is an ArrayList< GamePiece> that contains the pieces to be deleted. 
	 * 
	 * @author Joey Guenette
	 */
	public static ArrayList<GamePiece> detect(int row, int col){
		int i;
		int type = pieceArray[row][col].getType();
		ArrayList<GamePiece> deleteList = new ArrayList<GamePiece>();
		deleteList.add(pieceArray[row][col]);
		//System.out.println(deleteList.size());
		
		//check if the match is horizontal or vertical
		if((col-1 > 0 && type == pieceArray[row][col-1].getType()) || (col+1 < 6 && type == pieceArray[row][col+1].getType())){
			//VERTICAL
			//System.out.println("vert");
			if(col < 6 && type == pieceArray[row][col+1].getType()){
				i=0;
				//System.out.println("down");
				while(col+(i+1) <= 6 && pieceArray[row][col+i].getType() == pieceArray[row][col+(i+1)].getType()){
					//System.out.println("matchBellow");
					deleteList.add(pieceArray[row][col+(i+1)]);
					i++;
					//System.out.println(i+1);
				}
			}
				
			if(col > 0 && type == pieceArray[row][col-1].getType()){
				i=0;
				//System.out.println("up");
				while((col-(i+1) >= 0) && pieceArray[row][col-i].getType() == pieceArray[row][col-(i+1)].getType()){
					//System.out.println("matchAbove");
					deleteList.add(pieceArray[row][col-(i+1)]);
					//System.out.println(deleteList.size());
					i++;
				}
			}
			
		}
		//System.out.println("not vert");
		if(deleteList.size() < 3 && (row-1 > 0 && type == pieceArray[row-1][col].getType()) || (row+1 < 6 && type == pieceArray[row+1][col].getType())){
			//HORIZONTAL
			deleteList.clear();
			deleteList.add(pieceArray[row][col]);
			//System.out.println("hor");
			if(row < 6 && type == pieceArray[row+1][col].getType()){
				i=0;
				//System.out.println("right");
				while((row+(i+1) <= 6) && pieceArray[row+i][col].getType() == pieceArray[row+(i+1)][col].getType()){
					//System.out.println("matchRight");
					deleteList.add(pieceArray[row+(i+1)][col]);
					//System.out.println(deleteList.size());
					i++;
				}
			}
				
			if(row > 0 && type == pieceArray[row-1][col].getType()){
				i=0;
				//System.out.println("left");
				while((row-(i+1) >= 0) && pieceArray[row-1][col].getType() == pieceArray[row-(i+1)][col].getType()){
					//System.out.println("matchLeft");
					deleteList.add(pieceArray[row-(i+1)][col]);
					//System.out.println(deleteList.size());
					i++;
				}
			}
		}
		
		return(deleteList);
	}
	
	
	/**
	 * swap() is used to move two pieces on the board.
	 * <p>
	 * This method is used twice in order to implement itself. It takes the users first selection and stores
	 * it in a variable called swap1. It then takes the user's second selection and stores it in swap2.
	 * It then stores both pieces i and j indices in separate variables to  reduce the risk of accidentally overwriting
	 * the values stored within the pieces until the time is right. It calls the detect method on both pieces
	 * to find out if a match was found on either side, and if so, animates the piece that was left on the board.
	 * In the case where no match is found for either piece, it swaps the pieces back into their original locations.
	 * It animates the original swap, and the swap back, for any pieces not being removed as a match.
	 * </p>
	 * @param row This is the row value of the piece. Used for both PieceArray and gPane.
	 * @param col
	 * 
	 * @author James Pierce
	 */
	public static void swap(int row, int col){
		if(swap1 == null){
			swap1 = pieceArray[row][col];
			Game.pieceArray[Game.swap1.getiIndex()][Game.swap1.getjIndex()].setStyle("-fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.8), 10, 0.2, 0, 0);");
			
		} else if(swap2 == null){
			swap2 = pieceArray[row][col];
			// sets piece styles
			Game.pieceArray[Game.swap1.getiIndex()][Game.swap1.getjIndex()].setStyle("-fx-effect: dropshadow(gaussian, rgb(0, 0, 0), 0, 0, 0, 0);");
			// sets variables to store information relevant to the first and second swap pieces
			int p1i = swap1.getiIndex();
			int p1j = swap1.getjIndex();
			int p2i = swap2.getiIndex();
			int p2j = swap2.getjIndex();
			
			// tests pieces for location and deals with them accordingly.
			if(swap1.equals(swap2)){
				//System.out.println("You clicked the same piece twice. Please click another piece.");
				message.setText("You clicked the same piece twice. \nPlease click another piece.");
				swap2 = null;
			} else if( ( (p1i == p2i + 1 && p1j == p2j) || (p1i == p2i - 1 && p1j == p2j) ) || ( (p1j == p2j + 1 && p1i == p2i) || (p1j == p2j - 1 && p1i == p2i) ) ){
				// makes appropriate changes to 'piece1' inside the array
				pieceArray[p1i][p1j] = swap2;
				pieceArray[p1i][p1j].setiIndex(p1i);
				pieceArray[p1i][p1j].setjIndex(p1j);
				// makes appropriate changes to 'piece2' inside the array
				pieceArray[p2i][p2j] = swap1;
				pieceArray[p2i][p2j].setiIndex(p2i);
				pieceArray[p2i][p2j].setjIndex(p2j);
				
				// animations for the two pieces being swapped
				TranslateTransition oneSlide = new TranslateTransition(Duration.millis(500), pieceArray[p1i][p1j]);
				TranslateTransition twoSlide = new TranslateTransition(Duration.millis(500), pieceArray[p2i][p2j]);
				TranslateTransition threeSlide = new TranslateTransition(Duration.millis(500), pieceArray[p1i][p1j]);
				TranslateTransition fourSlide = new TranslateTransition(Duration.millis(500), pieceArray[p2i][p2j]);
				PauseTransition pSlide = new PauseTransition(Duration.millis(1000));
				ParallelTransition slide;
				SequentialTransition move;
				if(p1i > p2i) {
					oneSlide.setByX(42);
					twoSlide.setByX(-42);
					slide = new ParallelTransition(oneSlide, twoSlide);
					slide.play();
				}else
					if(p1i < p2i){
						oneSlide.setByX(-42);
						twoSlide.setByX(42);
						slide = new ParallelTransition(oneSlide, twoSlide);
						slide.play();
					}
				if(p1j < p2j){
					oneSlide.setByY(-42);
					twoSlide.setByY(42);
					slide = new ParallelTransition(oneSlide, twoSlide);
					slide.play();
				}else
					if(p1j > p2j){
						oneSlide.setByY(42);
						twoSlide.setByY(-42);
						slide = new ParallelTransition(oneSlide, twoSlide);
						slide.play();
					}
				
				
				ArrayList<GamePiece> matches1 = Pieces.detect(p1i, p1j);
				ArrayList<GamePiece> matches2 = Pieces.detect(p2i, p2j);
				
				if(matches1.size() >= 3){
						delete(p1i, p1j);
					swap1 = null;
					swap2 = null;
				} 
				if(matches2.size() >= 3){
					delete(p2i, p2j);
					swap1 = null;
					swap2 = null;
				} 
				if( (matches1.size() < 3) && (matches2.size() < 3) ){
					// animates the switch back
					if(p1i > p2i) {
						threeSlide.setByX(-42);
						fourSlide.setByX(42);
						slide = new ParallelTransition(threeSlide, fourSlide);
						move = new SequentialTransition(pSlide, slide);
						move.play();
					}else
						if(p1i < p2i){
							threeSlide.setByX(42);
							fourSlide.setByX(-42);
							slide = new ParallelTransition(threeSlide, fourSlide);
							move = new SequentialTransition(pSlide, slide);
							move.play();
						}
					if(p1j < p2j){
						threeSlide.setByY(42);
						fourSlide.setByY(-42);
						slide = new ParallelTransition(threeSlide, fourSlide);
						move = new SequentialTransition(pSlide, slide);
						move.play();
					}else
						if(p1j > p2j){
							threeSlide.setByY(-42);
							fourSlide.setByY(42);
							slide = new ParallelTransition(threeSlide, fourSlide);
							move = new SequentialTransition(pSlide, slide);
							move.play();
						}
					// resets the pieceArray to the original values
					// also sets the i and j indices because apparently that needed to happen so it doesnt break
					pieceArray[p1i][p1j] = swap1;
					pieceArray[p1i][p1j].setiIndex(p1i);
					pieceArray[p1i][p1j].setjIndex(p1j);
					pieceArray[p2i][p2j] = swap2;
					pieceArray[p2i][p2j].setiIndex(p2i);
					pieceArray[p2i][p2j].setjIndex(p2j);
					
					swap1 = null;
					swap2 = null;
				}
			} else {
				Game.pieceArray[Game.swap1.getiIndex()][Game.swap1.getjIndex()].setStyle("-fx-effect: dropshadow(gaussian, rgb(0, 0, 0), 0, 0, 0, 0);");
				swap1 = swap2;
				Game.pieceArray[Game.swap1.getiIndex()][Game.swap1.getjIndex()].setStyle("-fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.8), 10, 0.2, 0, 0);");
				swap2 = null;
			}
		}
	}
	
}
