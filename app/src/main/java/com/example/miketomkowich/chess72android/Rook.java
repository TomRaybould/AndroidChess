package com.example.miketomkowich.chess72android;

/** 
* Rook class that extends the Piece class
* 
* @author  Tom Raybould & Mike Tomkowich
*/
public class Rook extends Piece {
	
	public Rook (String pieceName, int row, int col, char color){
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
		Piece [][] curr = b.getBoard() ; // to get board for coordinates
		Piece testColor = curr [destRow][destCol];
		int cRow=this.getRow();
		int cCol=this.getCol();
		
		if (curr[destRow][destCol] == null || this.getColor() != testColor.getColor()){
			//System.out.println("In dest null");
			// no piece will be captured, see if it is valid
			if(this.getRow() != destRow && this.getCol() != destCol){
				return null;
			}
			
			if(this.getCol() < destCol && this.getRow() == destRow){ ///move right
				while(cCol != destCol){
					cCol++;
					if(curr[cRow][cCol] != null){
						//something is on the diagonal path, so invalid
						if(curr[cRow][cCol].getColor() != this.getColor() && cRow == destRow && cCol == destCol){
							Move k = new Move(this, destRow, destCol, curr[destRow][destCol], false, false, false);
							return k;
						}
						return null;
					}
				}
				// return move object
				Move k = new Move(this, destRow, destCol, curr[destRow][destCol], false, false, false);
				return k;
			}
			
			else if(this.getCol() > destCol && this.getRow() == destRow){ ///move left
				while(cCol != destCol){
					cCol--;
					if(curr[cRow][cCol] != null){
						//something is on the diagonal path, so invalid
						if(curr[cRow][cCol].getColor() != this.getColor() && cRow == destRow && cCol == destCol){
							Move k = new Move(this, destRow, destCol, curr[destRow][destCol], false, false, false);
							return k;
						}
						return null;
					}
				}
				// return move object
				Move k = new Move(this, destRow, destCol, curr[destRow][destCol], false, false, false);
				return k;
			}
			
			else if(this.getCol() == destCol && this.getRow() > destRow){ ///move down
				while(cRow != destRow){
					cRow--;
					if(curr[cRow][cCol] != null){
						//something is on the diagonal path, so invalid
						if(curr[cRow][cCol].getColor() != this.getColor() && cRow == destRow && cCol == destCol){
							Move k = new Move(this, destRow, destCol, curr[destRow][destCol], false, false, false);
							return k;
						}
						return null;
					}
				}
				// return move object
				Move k = new Move(this, destRow, destCol, curr[destRow][destCol], false, false, false);
				return k;
			}
			
			else if(this.getCol() == destCol && this.getRow() < destRow){ ///move up
				while(cRow != destRow){
					cRow++;
					if(curr[cRow][cCol] != null){
						//something is on the diagonal path, so invalid
						if(curr[cRow][cCol].getColor() != this.getColor() && cRow == destRow && cCol == destCol){
							Move k = new Move(this, destRow, destCol, curr[destRow][destCol], false, false, false);
							return k;
						}
						return null;
					}
				}
				// return move object
				Move k = new Move(this, destRow, destCol, curr[destRow][destCol], false, false, false);
				return k;
			}
			else{
				return null; 
			}
			
		}// end of in null if statement	
		return null;
	}// end of is  valid move
}
