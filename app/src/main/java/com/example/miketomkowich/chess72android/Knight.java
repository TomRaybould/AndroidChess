package com.example.miketomkowich.chess72android;

/**
* Knight class that extends the Piece class
* 
* @author  Tom Raybould & Mike Tomkowich
*/
public class Knight extends Piece {
	
	public Knight (String pieceName, int row, int col, char color){
		super(pieceName, row, col, color);
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


	@Override
	public Move isValidMove(Board b, int destRow, int destCol) {
		
		if(!Board.isSpaceOnBoard(destRow, destCol)){
			return null;
		}
		
		Piece [][] curr = b.getBoard() ; // to get board for coordinates
		int distanceY = Math.abs(this.getRow()-destRow);
		int distanceX = Math.abs(this.getCol()-destCol);
		
		if (curr[destRow][destCol] == null || curr[destRow][destCol].getColor() != this.getColor()){
			
			if((distanceY==2 && distanceX==1)||(distanceY==1 && distanceX==2)){
				Move k = new Move(this, destRow, destCol, curr[destRow][destCol],false, false, false);
				return k;
			}
			else{
				return null;
			}
		}
		return null;
	}

}
