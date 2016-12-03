package com.example.miketomkowich.chess72android;
/**
* Piece is the abstract class that is used for all piece objects that the board
* array contains. It is extended by Rook, Knight, Bishop, King, Queen, and Pawn
* 
* @author  Tom Raybould & Mike Tomkowich
*/
public abstract class Piece {
		
		private String pieceName;//2 char name of piece "wp"
		private char color;
		private int moveCounter;//count of times the piece have moved 
		private int row, col; //x and y coordinates of piece
	public Piece(String pieceName, int row, int col, char color) {
		this.row = row;
		this.col = col;
		this.pieceName = pieceName;
		this.color = color;
		this.moveCounter=0;
	}
	
	/**
	   * This method is used to check if a piece can move to a certain space on the board
	   * it checks to see if there are pieces that block this move or if the space they are 
	   * trying to move to contains a piece of there own color. This method does not care about 
	   * check.
	   * 
	   * @param b The current game board
	   * @param destRow The destination row in the game board that the player is trying to move
	   * the piece to.
	   * @param destCol The destination column in the game board that the player is trying to move
	   * the piece to.
	   * @return Move Return a new instance of Move only if the move is valid, otherwise returns
	   * null for invalid moves.
	   * @see Move class
	*/	
	
	public abstract Move isValidMove(Board b, int destRow, int destCol);//returns null if move is invalid, or move Object if it is valid
	
	
	
	public String toString(){
		return this.pieceName;
	}

	public String getPieceName() {
		return pieceName;
	}

	public char getColor() {
		return color;
	}

	public int getMoveCounter() {
		return moveCounter;
	}

	public void setMoveCounter(int moveCounter) {
		this.moveCounter = moveCounter;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	

}
