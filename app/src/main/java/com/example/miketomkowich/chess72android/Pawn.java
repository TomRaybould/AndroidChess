package com.example.miketomkowich.chess72android;

/**
* Pawn class that extends the Piece class
* 
* @author  Tom Raybould & Mike Tomkowich
*/
public class Pawn extends Piece {
	
	public Pawn (String pieceName, int row, int col, char color){
		super(pieceName, row, col, color);
	}
	
	/**
	   * This method is used to check if a piece can move to a certain space on the board
	   * it checks to see if there are pieces that block this move or if the space they are 
	   * trying to move to contains a piece of there own color. This method does not care about 
	   * check.
	   * 
	   * If the move is a promotion the isPromotion boolean of move is set to true in the constructor call 
	   * of the move that is being made.
	   * 
	   * If the move is a en passent the isEmpass boolean of move is set to true in the constructor call 
	   * of the move that is being made.
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
		
		Piece [][] curr = b.getBoard(); // to get board for coordinates
		int moreThanOneSpaceY = Math.abs(this.getRow()-destRow);
		int moreThanOneSpaceX = Math.abs(this.getCol()-destCol); // this will be used for when capturing an apposing piece
		
		boolean isPromote=false;
		
		if (this.getColor() == 'w'){
			
			if(destRow<= this.getRow()){
				return null;
			}
			
			if(moreThanOneSpaceY == 2 && this.getCol() == destCol && this.getMoveCounter() == 0){
				if (curr[destRow][destCol] == null && curr[(destRow-1)][destCol]==null){
					Move P = new Move(this, destRow, destCol, null, false, false, false);
					return P;
				}
				else{
					return null;
				}
			}
			
			else if(moreThanOneSpaceY == 1 && this.getCol() == destCol){
				if (curr[destRow][destCol] == null){
					if(destRow==7){
						isPromote = true;
					}
					// return move object
					Move P = new Move(this, destRow, destCol, null, false, isPromote, false);
					return P;
				}
			}
			
			else if(moreThanOneSpaceY == 1 && moreThanOneSpaceX == 1 && curr[destRow][destCol] != null){
				if(curr[destRow][destCol].getColor() != this.getColor()){
					if(destRow==7){
						isPromote = true;
					}
					Move P = new Move(this, destRow, destCol, curr[destRow][destCol], false, isPromote, false);
					return P;
				}
				else{
					return null;
				}
			}
			
			else if(curr[(destRow-1)][destCol]!=null){
					if(moreThanOneSpaceY == 1 && moreThanOneSpaceX == 1&&destRow == 5 && curr[(destRow-1)][destCol].getPieceName()=="bP" && curr[(destRow-1)][destCol].getMoveCounter()==1 && Move.getLastMove().getMovedPiece()==curr[(destRow-1)][destCol]){//empassant conditions for white
						Move P = new Move(this, destRow, destCol, curr[(destRow-1)][destCol], true, false, false);
						return P;
					}
			}
		
	}
		else if(this.getColor() == 'b'){
			
			if(destRow>=this.getRow()){
				return null;
			}
			
			if(moreThanOneSpaceY == 2 && this.getCol() == destCol && this.getMoveCounter() == 0){
				if (curr[destRow][destCol] == null && curr[(destRow+1)][destCol] == null){
					// return move object
					Move P = new Move(this, destRow, destCol, null , false, false, false);
					return P;
				}
				else{
					return null;
				}
			}
			
			else if(moreThanOneSpaceY == 1 && this.getCol() == destCol){
				if(destRow==0){
					isPromote = true;
				}
				if (curr[destRow][destCol] == null){
					// return move object
					Move P = new Move(this, destRow, destCol, null, false, isPromote, false);
					return P;
				}
			}
			
			else if(moreThanOneSpaceY == 1 && moreThanOneSpaceX == 1 && curr[destRow][destCol] != null){
				if(curr[destRow][destCol].getColor() != this.getColor()){
					if(destRow==0){
						isPromote = true;
					}
					Move P = new Move(this, destRow, destCol, curr[destRow][destCol], false, isPromote, false);
					return P;
				}
			}
			
			else if(curr[(destRow+1)][destCol]!=null){
				if(moreThanOneSpaceY == 1 && moreThanOneSpaceX == 1 && destRow == 2 && curr[(destRow+1)][destCol].getPieceName()=="wP" && curr[(destRow+1)][destCol].getMoveCounter() == 1 && Move.getLastMove().getMovedPiece() == curr[(destRow+1)][destCol]){//empassant conditions for white
					Move P = new Move(this, destRow, destCol, curr[(destRow+1)][destCol], true, false, false);
					return P;
				}
			}
		}
		return null;
}
}