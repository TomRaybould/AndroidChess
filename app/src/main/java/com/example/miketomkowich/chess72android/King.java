package com.example.miketomkowich.chess72android;

/**
* King class that extends the Piece class
* 
* @author  Tom Raybould & Mike Tomkowich
*/
public class King extends Piece{
	
	public King (String pieceName, int row, int col, char color){
		super(pieceName, row, col, color);
	}
	
	/**
	   * This method is used to check if a piece can move to a certain space on the board
	   * it checks to see if there are pieces that block this move or if the space they are 
	   * trying to move to contains a piece of there own color. This method does not care about 
	   * check.
	   * 
	   * If the move is a castle is castle boolean of move is set to true in the constructor call 
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
		
		int moreThanOneSpaceX = Math.abs(this.getCol()-destCol);
		int moreThanOneSpaceY = Math.abs(this.getRow()-destRow);
		
		//this covers all regular moves of the king
		if(moreThanOneSpaceX <= 1 && moreThanOneSpaceY <= 1){
			//checks if the piece changes at least x or y
			if(moreThanOneSpaceX == 1 ||  moreThanOneSpaceY == 1){
				//valid, king is moving left or right 1 space
				if (curr[destRow][destCol] == null || curr[destRow][destCol].getColor() != this.getColor()){
					// return move object
					Move k = new Move(this, destRow, destCol, curr[destRow][destCol], false, false, false);
					return k;
				}
			}
		}
		//castle if the king has already moved you can't castle
		if(this.getMoveCounter()>0){
			return null;
		}
		
		//first separate conditions by color
		
		if (this.getColor()=='w'){
			if(destRow==0 && destCol== 6){ //king side castle
				if(curr[0][7]==null){
					return null;// the rook is not there
				}
				if(curr[0][7].getMoveCounter()>0){
					return null;// you can can't castle if the rook has already moved
				}
				
				for(int i=0; i<8;i++){
					for(int j=0;j<8;j++){
						if(curr[i][j]!=null){
							if(curr[i][j].getColor()=='b'){
								if(curr[i][j].isValidMove(b, 0 , 5)!=null||curr[i][j].isValidMove(b, 0 , 6)!=null){
									System.out.println("You cannot castle through check");
									return null;
								}
							}
						}
					}
				}//end of nested for loops
				
				if (curr[0][5]==null&& curr[0][6]==null){// there can not be pieces in the way
					Move k= new Move(this, destRow, destCol, curr[0][7], false, false, true); // return piece where the rook is the taken piece, and is caste is true
					return k;
				}
				else{
					return null;
				}
			}
			else if(destRow==0 && destCol== 2){ //queen side castle
				if(curr[0][0]==null){
					return null;// the rook is not there you have to check so you don't get null pointer 
				}
				if(curr[0][0].getMoveCounter()>0){
					return null;// you can can't castle if the rook has already moved
				}
				
				for(int i=0; i<8;i++){
					for(int j=0;j<8;j++){
						if(curr[i][j]!=null){
							if(curr[i][j].getColor()=='b'){
								if(curr[i][j].isValidMove(b, 0 , 3)!=null||curr[i][j].isValidMove(b, 0 , 2)!=null||curr[i][j].isValidMove(b, 0 , 1)!=null){
									System.out.print("You cannot castle through check");
									return null;
								}
							}
						}
					}
				}//end of nested for loops
				
				if (curr[0][3]==null && curr[0][2]==null && curr[0][1]==null){// no piece in the way
					Move k= new Move(this, destRow, destCol, curr[0][0], false, false, true); // return piece where the rook is the taken piece, and is caste is true
					return k;
				}
			}
		}//end of white castle check
		
		else if(this.getColor()=='b'){
			
			if(destRow==7 && destCol== 6){ //king side castle
				if(curr[7][7]==null){
					return null;// the rook is not there
				}
				if(curr[7][7].getMoveCounter()>0){
					return null;// you can can't castle if the rook has already moved
				}
				
				for(int i=0; i<8;i++){
					for(int j=0;j<8;j++){
						if(curr[i][j]!=null){
							if(curr[i][j].getColor()=='w'){
								if(curr[i][j].isValidMove(b, 7 , 5)!=null||curr[i][j].isValidMove(b, 7 , 6)!=null){
									System.out.print("You cannot castle through check");
									return null;
								}
							}
						}
					}
				}//end of nested for loops
				
				if (curr[7][5]==null&& curr[7][6]==null){// there can not be pieces in the way
					Move k= new Move(this, destRow, destCol, curr[7][7], false, false, true); // return piece where the rook is the taken piece, and is caste is true
					return k;
				}
				else{
					return null;
				}
			}
			
			else if(destRow==7 && destCol== 2){ //queen side castle
				if(curr[7][0]==null){
					return null;// the rook is not there you have to check so you don't get null pointer 
				}
				if(curr[7][0].getMoveCounter()>0){
					return null;// you can can't castle if the rook has already moved
				}
				
				for(int i=0; i<8;i++){
					for(int j=0;j<8;j++){
						if(curr[i][j]!=null){
							if(curr[i][j].getColor()=='w'){
								if(curr[i][j].isValidMove(b, 7 , 3)!=null||curr[i][j].isValidMove(b, 7 , 2)!=null||curr[i][j].isValidMove(b, 7 , 1)!=null){
									System.out.println("You cannot castle through check");
									return null;
								}
							}
						}
					}
				}//end of nested for loops
				
				if (curr[7][3]==null && curr[7][2]==null && curr[7][1]==null){// no piece in the way
					Move k= new Move(this, destRow, destCol, curr[7][7], false, false, true); // return piece where the rook is the taken piece, and is caste is true
					return k;
				}
				else{
					return null; 
				}
			}
		}//end of black castle check
		
		else{
			return null;
		}
		
		return null;
	}//end of is move valid

}//end of class
