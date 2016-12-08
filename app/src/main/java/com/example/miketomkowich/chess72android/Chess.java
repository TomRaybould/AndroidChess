package com.example.miketomkowich.chess72android;

import java.util.Scanner;
/**
* Chess is a two player version of chess using a printed board 
* and requiring text input from the users
* 
* @author  Tom Raybould & Mike Tomkowich
*/
public class Chess {
		protected Player playerWhite;
		protected Player playerBlack;
		protected Board board;
		protected boolean isGameOver; //set to false when game is ready
		protected int globalCount;
		protected int draw;
		protected boolean endGameWithDraw;
		protected boolean endGameWithResign;
		
	public Chess(Player white, Player black, Board b) {
		this.playerWhite = white;
		this.playerBlack = black;
		this.board = b;
		this.isGameOver = false;
		this.globalCount = 0;
		this.draw=0;
		this.endGameWithDraw=false;
		this.endGameWithResign=false;
	}
	public Board getBoard(){
		return this.board;
	}
	
	public static void main(String[] args) {
		
		Board b= new Board();
	    Player player1= new Player(b,'w');
		Player player2= new Player(b,'b');
		player1.setOpponent(player2);
		player2.setOpponent(player1);
		Chess game= new Chess(player1 , player2, b );
		
		b.printBoard();
		// main game loop
		Scanner sc= new Scanner(System.in);
		while(!game.isGameOver){
			//Check for check mate or stale mate in player class set game over if check mate is true
			if(game.playerWhite.isInCheckMate(game.board)){
				game.isGameOver=true;
				System.out.println("Checkmate");
				System.out.println("Black wins");
				break;
			}
			if(game.playerWhite.isInStaleMate(game.board)){
				game.isGameOver=true;
				System.out.println("Stalemate");
				break;
			}
			
			if(game.playerWhite.isInCheck(game.board)){
				System.out.println("Check");
			}
			boolean validInput=false;
				
			while(!validInput){
				//asks for input and returns true if the move and input are valid
				System.out.print("White's move: ");
				String entry = sc.nextLine();
				validInput = game.handleTurn(game.playerWhite, entry);
				System.out.println();
				b.printBoard();
				
			}
			if(game.endGameWithResign){
				System.out.println("White has Resigned");
				game.isGameOver=true;
				break;
			}
			if(game.endGameWithDraw){
				System.out.println("White has accepted Draw: Game over");
				game.isGameOver=true;
				break;
			}
			game.globalCount++;
			
			
			if(game.isGameOver){break;}//beginning of blacks turn
			
			
			//Check for check mate or stale mate in player class set game over if check mate is true
			if(game.playerBlack.isInCheckMate(game.board)){
				System.out.println("Checkmate");
				System.out.println("White wins");
				game.isGameOver=true;
				break;
			}
			if(game.playerBlack.isInStaleMate(game.board)){
				System.out.println("Stalemate");
				game.isGameOver=true;
				break;
			}
			/// comment out for now
			//checks if player is in check prints to screen
			if(game.playerBlack.isInCheck(game.board)){
				System.out.println("Check");
			};
			
			validInput=false;
				
			while(!validInput){
				//asks for input and returns true if the move and input are valid
				System.out.print("Black's move: ");
				String entry = sc.nextLine();
				validInput = game.handleTurn(game.playerBlack, entry);
				System.out.println();
				b.printBoard();
			}
			if(game.endGameWithResign){
				System.out.println("Black has Resigned");
				game.isGameOver=true;
				break;
			}
			if(game.endGameWithDraw){
				System.out.println("Black has accepted Draw: Gameover");
				game.isGameOver=true;
				break;
			}
			game.globalCount++;
			
		}//end of main game loop
		sc.close();//close scanner when game is over
	
	}
	
	/**
	   * This method takes in user input, prints an error if the input or move is invalid
	   * and returns true, only if the move and input were valid. This method passes the board
	   * and the destination coordinates to the method isMoveValid of the piece you are trying to
	   * move. If isMoveValid returns a move, .moveEndsCheck() is called and return returns false 
	   * if the move puts you in check, or does not end your check, this cause handle turn to return
	   * false immediately. If the return is true the move is made and the piece is promoted if the 
	   * isPromote boolean in the move instance is true. The method then returns true.
	   * 
	   *
	   * @param p The player whose turn it is in the game
	   * @return boolean true only if the move and input were valid other wise returned false
	*/	
	public boolean handleTurn(Player p, String entry){
		 
		 Piece promotion = null;
		 if(entry.length() != 5 || !Character.isAlphabetic(entry.charAt(0)) || !Character.isDigit(entry.charAt(1)) ||
				 !Character.isAlphabetic(entry.charAt(3)) || !Character.isDigit(entry.charAt(4))){
			 	if(entry.toLowerCase().equals("resign")){
			 		this.endGameWithResign = true;
			 		return true;
			 	}
			 	else if (entry.length() == 7 && Character.isAlphabetic(entry.charAt(0)) && Character.isDigit(entry.charAt(1)) &&
						 Character.isAlphabetic(entry.charAt(3)) && Character.isDigit(entry.charAt(4)) && 
						 Character.isLetter(entry.charAt(6))){
			 		//promotion with input Put in invalid values because code should go somewhere else or should be overwrriten later
			 		//Promotion can also occur without an input, in that case make a new queen
			 		// this could be done in isPromote below
			 		char promoted = entry.charAt(6);
			 		if(promoted == 'q' || promoted == 'Q'){
			 			char pC = p.getColor();
			 			String newPieceName = pC + "" +'Q';
			 			promotion = new Queen(newPieceName, -1, -1, pC);
			 		}
			 		else if(promoted == 'n' || promoted == 'N'){
			 			char pC = p.getColor();
			 			String newPieceName = pC + "" +'N';
			 			promotion = new Knight(newPieceName, -1, -1, pC);
			 		}
					else if(promoted == 'b' || promoted == 'B'){
						char pC = p.getColor();
			 			String newPieceName = pC + "" +'B';
						promotion = new Bishop(newPieceName, -1, -1, pC);
						}
					else if(promoted == 'r' || promoted == 'R'){
						char pC = p.getColor();
			 			String newPieceName = pC + "" +'R';
						promotion = new Rook(newPieceName, -1, -1, pC);	
						}
					else {
						System.out.println("Invalid Input, Try again");
						return false;
					}
			 	}
			 	else if((entry.length() == 11||entry.length() == 13) && 
			 			(entry.contains("draw?")||entry.contains("DRAW?")||entry.contains("Draw?"))){
			 			draw = globalCount;
			 			
			 	}
			 	else if(entry.equalsIgnoreCase("draw")){
			 			
			 			if(globalCount-1 == draw){
			 				endGameWithDraw = true;
			 				return true;
			 			}
			 			else{
			 				System.out.println("Invalid input: You cannot accept a draw when not offered one");
			 				return false;
			 			}
			 				
			 	}
			 	else{
			 		System.out.println("Invalid Input, Try again");
			 		return false;
			 	}
		}
		
		 char letX = entry.charAt(0);
		 int ogX;
		 if(letX == 'a' || letX == 'A'){
			 ogX = 0;
		 }
		 else if(letX == 'b'|| letX == 'B'){
			 ogX = 1;
		 }
		else if(letX == 'c'|| letX == 'C'){
			ogX = 2;	 
				 }
		else if(letX == 'd'|| letX == 'D'){
			ogX = 3;
		}
		else if(letX == 'e'|| letX == 'E'){
			ogX = 4;
		}
		else if(letX == 'f'|| letX == 'F'){
			ogX = 5;
		}
		else if(letX == 'g'|| letX == 'G'){
			ogX = 6;
		}
		else if(letX == 'h'|| letX == 'H'){
			ogX = 7;
		}
		else{
			System.out.println("Invalid Input, Try again");
			return false;
		}
		 
		 char c = entry.charAt(1);
		 int ogY = Character.getNumericValue(c) - 1;
		 if(ogY < 0 || ogY >7){
			 System.out.println("Invalid Input, Try again");
			return false;
		 }

		 letX = entry.charAt(3);
		 int destX;
		 if(letX == 'a' || letX == 'A'){
			 destX = 0;
		 }
		 else if(letX == 'b' || letX == 'B'){
			 destX = 1;
		 }
		else if(letX == 'c' || letX == 'C'){
			destX = 2;	 
				 }
		else if(letX == 'd' || letX == 'D'){
			destX = 3;
		}
		else if(letX == 'e' || letX == 'E'){
			destX = 4;
		}
		else if(letX == 'f' || letX == 'F'){
			destX = 5;
		}
		else if(letX == 'g' || letX == 'G'){
			destX = 6;
		}
		else if (letX == 'h' || letX == 'H'){
			destX = 7;
		}
		else{
			System.out.println("Invalid Input, Try again");
			return false;
		}
		 c = entry.charAt(4);
		 int destY = Character.getNumericValue(c) - 1;
		 if(destY < 0 || destY >7){
			 System.out.println("Invalid Input, Try again");
			return false;
		 }
		 
		 Piece piece;
		 Piece[][] temp = this.board.getBoard();
		 
		 if(temp[ogY][ogX]==null){
			 System.out.println("Illegal Move: There is no piece there. Try again");
			 return false;
		 }
		 if(temp[ogY][ogX].getColor()!=p.getColor()){
			 System.out.println("Illegal Move, Try again");
			 return false;
		 }

		 piece = temp[ogY][ogX]; 
		 
		 
		 if(piece == null){
			 System.out.println("Invalid Input, Try again");
			 return false; // means that piece doesnt exist for the given coordinates
		 }
		  // save for later incase the move is a promotion
		 
		 Move m = piece.isValidMove(this.board, destY, destX);

		
		 if (m==null){
			 System.out.println("Illegal Move, Try again");
			 return false;
		 }
		 
		 if(m != null){//there was a move returned from isMoveValid
			 
			 if(!m.moveEndsCheck(this.board, p)){//if the move ends will you in/still in check return false
				 System.out.println("Illegal Move: This move causes/or doesn't end check. Try again");
				 return false;
			 }
			 else{//the move is now complete valid
				 
				 if(promotion != null && !m.getIsPromote()){
					 System.out.println("Illegal Move: This is not a valid promotion");
					 return false;
				 }
				 
				 m.makeMove(this.board);//move the piece
				 //don't return until you have checked for promotion
				 if (m.getIsPromote()){
					 if(promotion!=null){//if promotion was specified 
						 this.board.getBoard()[destY][destX]= promotion;
						 promotion.setRow(destY);
						 promotion.setCol(destX);
						 return true;
					 }
					 else{// if promotion was not specified default to queen
						 char pC = p.getColor();
				 		 String newPieceName = pC + ""+'Q';
						 promotion= new Queen(newPieceName, destY, destX, pC);
						 this.board.getBoard()[destY][destX]= promotion;
						 return true;
					 }
				 }
				 return true;
			 }
		 }
		return false;
	}//end of handle player
	

}//end of chess class
