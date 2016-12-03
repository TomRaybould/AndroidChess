package com.example.miketomkowich.chess72android;

/**
* Player is the class that creates player objects (black and white) and initially sets the board as well
* as checking whether or not a players current state is in check, checkmate, or stalemate
* 
* @author  Tom Raybould & Mike Tomkowich
*/

public class Player {
	
		private char color;
		private Player opponent;
		
    /**
     * Player constructor that populates the board with the players pieces at the beginning of the
     * game. 
     * @param b
     * @param color
     * @return void
     * @see Piece
     */
	public Player(Board b, char color) {
		Piece[][] board = b.getBoard();
		this.color=color;
		if(this.color == 'w'){
			Piece wLRook = new Rook("wR", 0, 0,'w');
			board[0][0] = wLRook;
			Piece wRRook = new Rook("wR", 0, 7,'w');
			board[0][7] = wRRook;
			Piece wLKnight = new Knight("wN", 0, 1,'w');
			board[0][1] = wLKnight;
			Piece wRKnight = new Knight("wN", 0, 6, 'w');
			board[0][6] = wRKnight;
			Piece wLBishop= new Bishop("wB", 0, 2, 'w');
			board[0][2] = wLBishop;
			Piece wRBishop = new Bishop("wB", 0, 5, 'w');
			board[0][5] = wRBishop;
			Piece wKing= new Queen("wQ", 0, 3,'w');
			board[0][3] = wKing;
			Piece wQueen = new King("wK", 0, 4,'w');
			board[0][4] = wQueen;
			Piece wPawn0 = new Pawn("wP", 1, 0,'w');
			board[1][0] = wPawn0;
			Piece wPawn1 = new Pawn("wP", 1, 1,'w');
			board[1][1] = wPawn1;
			Piece wPawn2 = new Pawn("wP", 1, 2,'w');
			board[1][2] = wPawn2;
			Piece wPawn3 = new Pawn("wP", 1, 3,'w');
			board[1][3] = wPawn3;
			Piece wPawn4 = new Pawn("wP", 1, 4,'w');
			board[1][4] = wPawn4;
			Piece wPawn5 = new Pawn("wP", 1, 5,'w');
			board[1][5] = wPawn5;
			Piece wPawn6 = new Pawn("wP", 1, 6,'w');
			board[1][6] = wPawn6;
			Piece wPawn7 = new Pawn("wP", 1, 7,'w'); 
			board[1][7] = wPawn7;
		}
		else if (this.color == 'b'){
			Piece bLRook = new Rook("bR", 7, 0, 'b');
			board[7][0] = bLRook;
			Piece bRRook = new Rook("bR", 7, 7, 'b');
			board[7][7] = bRRook;
			Piece bLKnight = new Knight("bN", 7, 1, 'b');
			board[7][1] = bLKnight;
			Piece bRKnight = new Knight("bN", 7, 6, 'b');
			board[7][6] = bRKnight;
			Piece bLBishop= new Bishop("bB", 7, 2, 'b');
			board[7][2] = bLBishop;
			Piece bRBishop = new Bishop("bB", 7, 5, 'b');
			board[7][5] = bRBishop;
			Piece bKing= new Queen("bQ", 7, 3, 'b');
			board[7][3] = bKing;
			Piece bQueen = new King("bK", 7, 4, 'b');
			board[7][4] = bQueen;
			Piece bPawn0 = new Pawn("bP", 6, 0, 'b');
			board[6][0] = bPawn0;
			Piece bPawn1 = new Pawn("bP", 6, 1, 'b');
			board[6][1] = bPawn1;
			Piece bPawn2 = new Pawn("bP", 6, 2, 'b');
			board[6][2] = bPawn2;
			Piece bPawn3 = new Pawn("bP", 6, 3, 'b');
			board[6][3] = bPawn3;
			Piece bPawn4 = new Pawn("bP", 6, 4, 'b');
			board[6][4] = bPawn4;
			Piece bPawn5 = new Pawn("bP", 6, 5, 'b');
			board[6][5] = bPawn5;
			Piece bPawn6 = new Pawn("bP", 6, 6, 'b');
			board[6][6] = bPawn6;
			Piece bPawn7 = new Pawn("bP", 6, 7, 'b'); 
			board[6][7] = bPawn7;
		}
	}
	
	public char getColor(){
		return this.color;
	}
	
	public void setOpponent(Player opp){
		this.opponent=opp;
	}
	/**
	   * This method takes in the current board and checks to see if a player is in check.
	   * If one of your opponents pieces can attack the king it will return true. If the 
	   * player is not in check it will return false.
	   * 
	   * @param b The current state of the board for both players
	   * @return boolean true only if the player that it is called on is in check
	*/	
	
	public boolean isInCheck(Board b){
		//find the king players king
		Piece myKing = null;
		for (int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(b.getBoard()[i][j]!=null){
					Piece p = b.getBoard()[i][j];
					if (p.getPieceName().equals(this.color +"K")){
						myKing=p;
					}
				}
			}
		}
		
		for (int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(b.getBoard()[i][j]!=null){
					Piece attacker= b.getBoard()[i][j];
					if(attacker.getColor()==this.opponent.color){
						if(attacker.isValidMove(b, myKing.getRow(), myKing.getCol()) != null){
							//something can attack my king
							return true;
						}
					}
				}
			}
		}
		return false;//if none of the opponents pieces could attack your king
	}
	/**
	   * This method takes in the current board and checks to see if a player is in checkMate.
	   * If the player is not in check it will return false. If the player is in check but a move
	   * by the player can end check it will return false. If the player is in check and no move by
	   * the player can end check then it will return true. This is done by taking every one the 
	   * players pieces and trying to move it to every space on the board and see if it ends your
	   * check, using the piece.isValidMove and move.moveEndsCheck method. The method returns true
	   * if you have at least one valid move that does not put you in check. 
	   * 
	   * @param b The current state of the board for both players
	   * @return boolean true only if the player that it is called on is in checkMate
	   * @see Move.moveEndsCheck Piece.isValidMove
	*/
	public boolean isInCheckMate(Board b){
		//System.out.println("in checkMate");
		if(!this.isInCheck(b)){
			return false; //cant be in checkmate if not in check
		}
		Piece[][] board = b.getBoard();
		
		for(int i=0; i<8;i++){//for every row on board
			for(int j=0; j<8;j++){// for every col in row , searchs for all of your pieces on the board
				if (board[i][j]==null||board[i][j].getColor()==this.opponent.color){
					continue;
				}
				
				else{	
					Piece hero = board[i][j];// one of your pieces that could end check
					for(int x=0; x<8; x++){
						for(int y=0; y<8; y++){// trying to move it to every spot of the board
							Move potentMove = hero.isValidMove(b, x, y);
							if (potentMove==null){
								continue;
							}
							else{
								if(potentMove.moveEndsCheck(b, this)){// if moving the piece there ends your check you are not in checkmate
									return false;
								}
							}
						}
					}		
				}
			}
		}//end of nested loops
		return true;
	}//end of checkmate
	/**
	   * This method takes in the current board and checks to see if the players are in a stalemate.
	   * This is done by first seeing if you are in check because if you are in check it cannot be
	   * stalemate.This is done by taking every one the players pieces and trying to move it to 
	   * every space on the board and see if it ends your check, using the piece.isValidMove and
	   * move.moveEndsCheck method. The method returns false if you have at least one valid move 
	   * does not put you in check. 

	   * @param b The current state of the board for both players
	   * @return boolean true only if the player that it is called on is in stalemate
	   * @see Move.moveEndsCheck Piece.isValidMove
	*/
	public boolean isInStaleMate(Board b){
		//System.out.println("Stale mate method");	
		Piece[][] board = b.getBoard();
	
		if(this.isInCheck(b)){
			return (false); //it would be checkmate if there was check.
		}
				
		for(int i=0; i<8;i++){//for every row on board
			for(int j=0; j<8;j++){// for every col in row
				if (board[i][j]==null||board[i][j].getColor() == this.opponent.color){
					continue;
				}
				else{	
					Piece hero = board[i][j];
					for(int x=0; x<8; x++){
						for(int y=0; y<8; y++){
							Move potentMove = hero.isValidMove(b, x, y);
							if (potentMove==null){
								//cant make that move
								continue;
							}
							else{
								if(potentMove.moveEndsCheck(b, this)){
									//has a move
									return (false);
								}
							}
						}
					}		
				}
			}
		}//end of nested loops
		return true;
	}
	
	public boolean resign(Board b){
		return true;
	}
}