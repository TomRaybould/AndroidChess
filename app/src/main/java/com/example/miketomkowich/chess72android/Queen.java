package com.example.miketomkowich.chess72android;

/**
* Queen class that extends the Piece class
* 
* @author  Tom Raybould & Mike Tomkowich
*/
public class Queen extends Piece {
	
	public Queen (String pieceName, int row, int col, char color){
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
		Piece testColor = curr [destRow][destCol];
		int cRow = this.getRow();
		int cCol = this.getCol();
		
		if (curr[destRow][destCol] != null){
			if(this.getColor() == testColor.getColor()){
				return null;
			}
		}
		
		if(Math.abs(cRow-destRow)==Math.abs(cCol-destCol)){//dia moves copied from bishop
			
			if(cCol < destCol && cRow < destRow){ ///move is up and to the right
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
			
			else if(cCol > destCol && cRow < destRow){ //move is up and to left
				while(cRow != destRow && cCol != destCol){
					cCol--;
					cRow++;
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
			
			
			else if(cRow > destRow && cCol < destCol){ // down and to the right
				while(cRow != destRow && cCol != destCol){
					cCol++;
					cRow--;
					if(curr[cRow][cCol] != null){
						//something is on the diagonal path, so invalid
						if(curr[cRow][cCol].getColor()!= this.getColor() && cRow == destRow && cCol == destCol){
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
			
			
			else if(cRow > destRow && cCol > destCol){//down and to the left
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
			
			return null;
		}//end of dia moves if statement
		
		else if(cRow ==destRow||cCol==destCol){// hor or vert moves copied from rook
			
			if(cCol < destCol && cRow == destRow){ ///move right
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
			
			
			else if(cCol > destCol && cRow == destRow){ ///move left
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
			
			else if(cCol == destCol && cRow > destRow){ ///move down
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
			
			else if(cCol == destCol && cRow < destRow){ ///move up
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
			return null;
		}
		return null;
	}
}
