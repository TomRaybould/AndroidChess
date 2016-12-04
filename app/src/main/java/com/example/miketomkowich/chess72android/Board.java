package com.example.miketomkowich.chess72android;

import java.util.ArrayList;

/**
* Board is a class that holds the game board. Boardis responsible for printing the chess board 
* array for the boards current state on each turn. It also has a method that makes sure that a 
* possible move's coordinates are within the boards array (Example: (10, 10) would fail because 
* it is off of the board while (1,3) would work because its on the board).
* 
* @author  Tom Raybould & Mike Tomkowich
*/

public class Board {
		
	private Piece [][] board= new Piece [8][8];
		
	public Board(){
		
	}
	
	public Piece[][] getBoard(){
		return this.board;  
	}


	/**
	   * This method returns whether a space is a possible location on the board
	   * if the row or column is greater then 7 or less then 0 it returns false
	   * @param row the row of the space
	   * @param col the column of the space
	   * @return boolean true if the space is contained on the board, otherwise false.
	*/
	
	public static boolean isSpaceOnBoard(int row, int col){
		if(row>7|row<0||col>7||col<0){
			return false;
		}
		else{
			return true; 
		}
	}
	
	/**
	   * This method prints the current state of the game board to the console
	   * @return void
	*/

	/*
		important notation!
		white space =ws
		black space =bs

		example white rook on black space = wRbs

	*/
	
	public ArrayList<String> printBoard(){

		ArrayList<String> pieceCodes= new ArrayList<String>();

		for(int i= 7; i >= 0; i--){
			for(int j=0; j < 8; j++){
				
				if(board[i][j]==null){
					if(i%2==0&&j%2==0){
						System.out.print("## ");
						pieceCodes.add("bs");
					}
					else if(i%2==0&&j%2!=0){
						System.out.print("   ");
						pieceCodes.add("ws");
					}
					else if(i%2!=0&&j%2==0){
						System.out.print("   ");
						pieceCodes.add("ws");
					}
					else if(i%2!=0&&j%2!=0){
						System.out.print("## ");
						pieceCodes.add("bs");
					}
				}
				else{

					System.out.print(board[i][j].toString()+" ");
					if(i%2==0&&j%2==0){
						pieceCodes.add(board[i][j].toString()+"bs");
					}
					else if(i%2==0&&j%2!=0){
						pieceCodes.add(board[i][j].toString()+"ws");
					}
					else if(i%2!=0&&j%2==0){
						pieceCodes.add(board[i][j].toString()+"ws");
					}
					else if(i%2!=0&&j%2!=0){
						pieceCodes.add(board[i][j].toString()+"bs");
					}
				}
			}//end of col in row
			System.out.println((i+1));
		}//end of rows
			System.out.print(" ");
		for(char c='a';c<'i';c++){
			System.out.print(c+"  ");
		}
		System.out.println();
		System.out.println();
		System.out.println(pieceCodes.size());
		return pieceCodes;

	}//end of print board


}//end of board