import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * GamePiece extends ImageView to allow the pieces to be displayed directly to the screen.
 * <p>
 * The pieces are able to set their own images using the ImageView's method to setImage(), by calling
 * the image stored within the piece itself.
 * </p> 
 * @author Joey Guenette
 *
 */
public class GamePiece extends ImageView{

	private int type;
	private int value;
	private String name;
	private int iIndex;
	private int jIndex;
	
	/**
	 * This GamePiece() exists as the base constructor, in the case that a piece is created with no values.
	 */
	public GamePiece() {
		type = 0;
		value = 1000;
		name = "Red";	
		this.setImage(new Image("red.png"));
		this.setOnMouseClicked(m -> {	
			Pieces.swap(this.getiIndex(), this.getjIndex());
		});
	}

	/**
	 * This GamePiece() is used to generate normal pieces when given values.
	 *  
	 * @param name is the name of the piece.
	 * @param type is the type of this piece.
	 * @param value is the score value this piece will give.
	 * @param image is the picture the piece will use when displayed on the board.
	 */
	public GamePiece(String name, int type, int value, Image image){
		this.type = type;
		this.value = value;
		this.name = name;
		this.setImage(image);
		this.setOnMouseClicked(m -> {
			// this tests if the pieces clicked is already part of a match. if it is, it will clear the match, otherwise it will perform the swap.
			if(Pieces.detect(this.iIndex, this.jIndex).size() >= 3){
				Game.delete(this.iIndex, this.jIndex);
				Game.swap2 = null;
			} else {
				Pieces.swap(this.getiIndex(), this.getjIndex());
			}
			
		});
	}
	
	//Getters and setters
	/**
	 * getType() is used to get the type of the piece.
	 * 
	 * @return type is the type ofthe piece.
	 */
	public int getType() {
		return type;
	}

	/**
	 * getValue() is used to get the value of the piece.
	 * 
	 * @return value is the value of the piece.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * setValue() is used to set the piece's value.
	 * 
	 * @param value is the piece's value.
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * getName() is used to get the piece's name.
	 * 
	 * @return name is the piece's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * setiIndex() is used to set the i index of the piece when required.
	 * 
	 * @param iIndex the i index of the piece.
	 */
	public void setiIndex(int iIndex){
		this.iIndex = iIndex;
	}
	
	/**
	 * getiIndex() is used to get the i index of the piece when required.
	 * 
	 * @return iIndex the i index of the piece.
	 */
	public int getiIndex(){
		return iIndex;
	}
	
	/**
	 * setjIndex() is used to set the j index of the piece when required.
	 * 
	 * @param jIndex the j index of the piece.
	 */
	public void setjIndex(int jIndex){
		this.jIndex = jIndex;
	}
	
	/**
	 * getiIndex() is used to get the i index of the piece when required.
	 * 
	 * @return jIndex the j index of the piece.
	 */
	public int getjIndex(){
		return jIndex;
	}

}
