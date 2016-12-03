package com.example.miketomkowich.chess72android;

/**
* Bishop class that extends the Piece class
* 
* @author  Tom Raybould & Mike Tomkowich
*/
public class Bishop extends Piece {
	public Bishop (String pieceName, int row, int col, char color){
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
		
		Piece [][] curr = b.getBoard();
		Piece testColor = curr [destRow][destCol];
		if (curr[destRow][destCol] == null || this.getColor() != testColor.getColor()){
			int cCol = this.getCol();
			int cRow = this.getRow();
			int testCol = Math.abs(destCol-this.getCol());
			int testRow = Math.abs(destRow-this.getRow());
			if(testCol-testRow != 0){
				// means invalid move because for a diagnoal move to be valid,
				// x and y should increment the same amount of units
				return null;
			}
			
			if(this.getCol() != destCol && this.getRow() !=destRow){
				//possibly a diagonal move, so check that here
				//this will check if its a valid diagonal move, still working on how to make sure mathematically it is actually diagonal though
				
				if(this.getCol() < destCol && this.getRow() < destRow){ ///move is up and to the right
					//System.out.println("Bishop 1");
					while(cRow != destRow && cCol != destCol){
						cRow++;
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
				
				else if(this.getCol() > destCol && this.getRow() < destRow){ //move is up and to left
					//System.out.println("Bishop 2");
					while(cRow != destRow && cCol != destCol){
						cCol--;
						cRow++;
						if(curr[cRow][cCol] != null){
							//something is on the diagonal path, so invalid
							if(curr[cRow][cCol].getColor()!= this.getColor()  && cRow == destRow && cCol == destCol){
								Move k = new Move(this, destRow, destCol, curr[cRow][cCol], false, false, false);
								return k;
							}
							return null;
						}
					}
				// return move object
				Move k = new Move(this, destRow, destCol, curr[cRow][cCol], false, false, false);
				return k;
				}
				
				
				else if(this.getRow() > destRow && this.getCol() < destCol){ // down and to the right
					//System.out.println("Bishop 3");
					while(cRow != destRow && cCol != destCol){
						cCol++;
						cRow--;
						if(curr[cRow][cCol] != null){
							//something is on the diagonal path, so invalid
							if(curr[cRow][cCol].getColor() != this.getColor()  && cRow == destRow && cCol == destCol){
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
				
				
				else if(this.getRow() > destRow && this.getCol() > destCol){//down and to the left
					//System.out.println("Bishop 4");
					while(cRow != destRow && cCol != destCol){
						cCol--;
						cRow--;
						if(curr[cRow][cCol] != null){
							//something is on the diagonal path, so invalid
							if(curr[cRow][cCol].getColor() != this.getColor() && cRow == destRow && cCol == destCol){
								Move k = new Move(this, destRow, destCol, curr[cRow][cCol], false, false, false);
								return k;
							}
							return null;
						}
					}
					// return move object
					Move k = new Move(this, destRow, destCol, curr[cRow][cCol], false, false, false);
					return k;	
				}
				
			}
		}
		return null;
	}
}
