/*
 * CrossWordPuzzle.java
 * 
 * @version 1
 * 
 * @revision 1
 * 
 */

import java.io.*;
import java.util.Scanner;


/**
 *
 *This class defines the board puzzle and solves the board using
 *the dictionary provided
 *
 * @author          Uday Vilas Wadhone
 * 
 * 
 * 
 * 
 * 
 */


class Crossword {
	
	/**
	 * Main method calls the function to calculate the answer and print the 
	 * solution
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]){
    	
        try {
			compute(0,0);
		} catch (Exception e) {
			e.printStackTrace();
		}
        print();
        
	}
	
	 //initialise array to store usage of each letter
    
    static int letterUse[][]=new int[13][13];
	
	/*
	 * declare the board board by defining hardcoded word "FUN" and "0" for
	 * valid spaces and '*' for invalid spaces.
	 */
	
    static char board[][]={
        {'*','*','*','*','*','*','0','*','*','*','*','*','*'},
        {'*','*','*','*','*','F','U','N','*','*','*','*','*'},
        {'*','*','*','*','0','0','0','0','0','*','*','*','*'},
        {'*','*','*','0','0','0','0','0','0','0','*','*','*'},
        {'*','*','0','0','0','0','*','0','0','0','0','*','*'},
        {'*','0','0','0','0','*','*','*','0','0','0','0','*'},
        {'0','0','0','0','*','*','*','*','*','0','0','0','0'},
        {'*','0','0','0','0','*','*','*','0','0','0','0','*'},
        {'*','*','0','0','0','0','*','0','0','0','0','*','*'},
        {'*','*','*','0','0','0','0','0','0','0','*','*','*'},
        {'*','*','*','*','0','0','0','0','0','*','*','*','*'},
        {'*','*','*','*','*','0','0','0','*','*','*','*','*'},
        {'*','*','*','*','*','*','0','*','*','*','*','*','*'}
    };
    
    
   
    
    
  
    
    /**
     * method to check if we can place word horizontally
     * 
     * @param 	currRow 		current row position
     * @param 	currCol 		current column position
     * @return  true or false
     */
    
    static boolean scanHorizontalSpace(int currRow,int currCol){
    	
        /*
         * condition to check from the left and the right as board is split in
         * between
         * 
         */
    	
    	for(int col=currCol;col>=0;col--){
    		
            if(board[currRow][col]=='0')
                return true;
            if(board[currRow][col]=='*')
                break;
        }
        
    	for(int col=currCol;col<13;col++){
    		
            if(board[currRow][col]=='0')
                return true;
            if(board[currRow][col]=='*')
                break;
        }
        
        return false;
    }
    
    /**
     * method to check if we can place word vertically
     * 
     * @param 		currRow		current row position
     * @param 		currCol      current column position
     * @return      true or false
     */
    static boolean scanVerticalSpace(int currRow,int currCol){
    	
    	/*
    	 * condition to check vertically from up and from down (from first row
    	 * and from the last row) as the board is split in between
    	 */
        
    	for(int row=currRow;row>=0;row--){
    		
            if(board[row][currCol]=='0')
                return true;
            if(board[row][currCol]=='*')
                break;
        }
       
    	for(int row=currRow;row<13;row++){
    		
            if(board[row][currCol]=='0')
                return true;
            if(board[row][currCol]=='*')
                break;
        }
        return false;
    }
    
    /**
     * method to find starting position for horizontal word
     * 
     * @param	 currRow		current row position
     * @param	 currCol		current column position
     * @return   
     */
    
    static int searchHorizontalWord(int currRow,int currCol){
    	
        /*
         * traverse through the column and look for the starting character
         * which will be used to find the legal word from the dictionary
         *  
         */
    	
    	for(int col=currCol;col>=0;col--){
         
    		if(board[currRow][col]=='*'){
    			
    			//move to next column if invalid space
                return col+1;
            }
        }
        
    	return 0;
    }
    
    /**
     * method to find the starting position for horizontal word
     * 
     * @param	 currRow		current row position
     * @param	 currCol		current column position
     * @return
     */
     
    static int searchVerticalWord(int currRow,int currCol){
       
    	for(int row=currRow;row>=0;row--){
    		
            if(board[row][currCol]=='*'){
            	
            	//move to next row if invalid space
                return row+1;
            }
        }
        return 0;
    }
    
    /**
     * method to find the length of the word that is required from the 
     * dictionary
     * 
     * @param	 currRow		current row position
     * @param	 currCol		current column position
     * @return	 length of the word required 
     */
    
    static int calcRowLength(int currRow,int currCol){
    	
    	/*
    	 *traverses through the row until invalid space is found 
    	 */
    	
    	for(int col=currCol;col<13;col++){
    		
    		//return the length of the word required
            if(board[currRow][col]=='*'){
            	
                return(col-currCol);
            }
        }
        return (13-currCol);
    }
    
    /**
     * method to find the length of the word that is required from the
     * dictionary
     * 
     * @param	 currRow		current row position
     * @param	 currCol		current column position
     * @return	 length of the word required
     */
    
    static int calcColLength(int currRow,int currCol){   
       
    	for(int row=currRow;row<13;row++){
    		
    		//return the length of the word required
            if(board[row][currCol]=='*'){
            	
                return(row-currRow);
            }
        }
        return (13-currRow);
    }
    
    /**
     * method to check if a word matches from the dictionary using the length 
     * and position found above
     * 
     * @param 	row					current row
     * @param 	startingLetter		first letter of the word
     * @param 	length				length of the word required
     * @param 	wordFromDict		initialize variable for word from dictionary
     * @return	true or false
     * 
     */
    static boolean rowCharMatch(int row,int startingLetter,int length,
    		String wordFromDict){
    	
        /*
         * find a word from the dictionary that meets the constraints passed 
         * as parameters
         * 
         */
    	
    	for(int index=startingLetter;index<(startingLetter+length);index++){
    		
            if(board[row][index]=='0')
                continue;
            if(board[row][index]!=wordFromDict.charAt(index-startingLetter))
                return false;
        }
        return true;
    }
    
    /**
     * method to check if a word matches from the dictionary using the length
     * and position found above
     * 
     * @param	 wordStart			starting letter for required word	
     * @param	 col				current column
     * @param	 length				length of the word required
     * @param	 wordFromDict		initialize variable for word from dictionary
     * @return	 true or false
     * 	
     */
    
    static boolean colCharMatch(int wordStart,int col,int length,String wordFromDict){
    	
        /*
         * find a word from the dictionary that meets the constraints passed
         * as parameters
         * 
         */
    	
    	for(int index=wordStart;index<(wordStart+length);index++){
    		
            if(board[index][col]=='0')
                continue;
            if(board[index][col]!=wordFromDict.charAt(index-wordStart))
                return false;
        }
        return true;
    }
    
    /**
     * method to place word horizontally using the values obtained in above
     * methods
     * 
     * @param	 row			current row	
     * @param	 wordStart		starting letter of the word
     * @param	 length			length of the word taken
     * @param	 wordFromDict	word matched from the dictionary
     * 
     */
    
    static void putHorWord(int row,int wordStart,int length,String wordFromDict)
    {
    	
       /*
        * place the matching word from dictionary in board puzzle and update
        * number of uses of each character
        * 
        */
    	
    	for(int col=wordStart; col<(wordStart+length);col++){
    		
            board[row][col]=wordFromDict.charAt(col-wordStart);
            letterUse[row][col]++;
        }
    	
        //print out the board puzzle
        print();
        
    }
    
  
    
    /**
     * method to place word vertically using the values obtained in above
     * methods
     * 
     * @param	 col				current column	
     * @param	 startingLetter		starting letter of the word
     * @param	 length				length of the word taken
     * @param	 wordFromDict		word matched from the dictionary
     * 
     */
    
    static void putVerticalWord(int startingLetter,int col,int length,
    		String wordFromDict){
    	
       /*
        * place the matching word from dictionary in board puzzle and update
        * number of uses of each character
        * 
        */
    	
    	for(int row=startingLetter; row<(startingLetter+length); row++){
    		
            board[row][col]=wordFromDict.charAt(row-startingLetter);
            letterUse[row][col]++;
        }
        
        print();
    }
    
    /**
     * this method deletes a word in process of backtracking. It removes the
     * previous word if no match for the current word is found
     * 
     * @param	 row			row from which word is to be deleted
     * @param	 wordStart		starting position of the word
     * @param	 length			length of the word being deleted
     * 
     */
    
    static void deleteHorizWord(int row,int wordStart,int length){
    	
       /*
        * using the letter use array, delete the characters at 1 and put '0'
        * (revert it back to blank),decrement letteruse count by 1.
        */
    	
    	for(int col=wordStart;col<(wordStart+length);col++){
    		
            if(letterUse[row][col]==1)
            {
                board[row][col]='0';
            }
            letterUse[row][col]--;
        }
        print();
    }
    
    /**
     * this method deletes a word in process of backtracking. It removes the
     * previous word if no match for the current word is found
     * 
     * @param	 wordStart		starting letter of word to be deleted
     * @param	 col			column from which word is to be deleted
     * @param	 length			length of word to be deleted
     */
    
    static void deleteVerWord(int wordStart,int col,int length){
    	
    	/*
         * using the letter use array, delete the characters at 1 and put '0'
         * (revert it back to blank),decrement letteruse count by 1.
         */
    	
    	for(int row=wordStart;row<(wordStart+length);row++){
    		
            if(letterUse[row][col]==1){
            	
                board[row][col]='0';
            }
            letterUse[row][col]--;
        }
        print();
    }
    
    
    
    /**
     * this method integrates all the required operations of using recursion,
     * backtracking and completing the solution to the board
     * 
     * @param	 row			starting from first row
     * @param	 col			starting from first column
     * @throws	 Exception
     */
    
    @SuppressWarnings("resource")
	static void compute(int row,int col)throws Exception{
    	
    	/*
    	 * traverse through columns and if invalid space is found, move to the 
    	 * next.
    	 */
    	
        for(int currCol=col;currCol<13;currCol++){
            
            if(board[row][currCol]=='*')
            	
                continue;
             
            /*
             * check if word can be placed horizontally, find length & first 
             * letter of the word required. Place a word matched from the 
             * dictionary
             * 
             */
            
            if(scanHorizontalSpace(row,currCol) && row!=1){
            	
                //scanner object to scan through given dictionary
            	Scanner sc=new Scanner(new File
            			("dict1.txt"));
                
            	/*
            	 * traverse through each line of the dictionary and search for
            	 * valid words that can be placed in the board
            	 */
            	
            	while (sc.hasNext()){
                    
            		String line = sc.nextLine();
            		
            		//calculate length of the line taken from dictionary
                    int lineLength=line.length();
                    
                    //variable to store the first letter of the required word
                    
                    int horizWordStartPos=searchHorizontalWord(row,currCol);
                    
                    //variable to hold length of row that is length of word
                    int rowLength=calcRowLength(row,horizWordStartPos);
                    
                    //implements recursion and backtracking
                    if(lineLength==rowLength && 
                    		rowCharMatch(row,horizWordStartPos,rowLength,line)){
                    	
                        putHorWord(row,horizWordStartPos,rowLength,line);
                        compute(row,currCol);
                        deleteHorizWord(row,horizWordStartPos,rowLength);
                        
                    }
                }
            	
                if(row>0)return;
            }
           
            /*
             * check if word can be placed vertically, find length & first word
             * of the word required. Place a word matched from the dictionary
             * in the board
             * 
             */
            
            if(scanVerticalSpace(row,currCol)){
            	
                //scanner object to traverse through the given dictionary
            	Scanner sc=new Scanner(new File
            			("dict1.txt"));
               
            	/*
            	 * traverse through each line of dictionary to find and search
            	 * for valid words that can be placed in the board
            	 * 
            	 */
            	
            	while (sc.hasNext()){
                   
            		String line = sc.nextLine();
            		//calculate length of line in use in dictionary
                    int lineLength=line.length();
                    
                    //variable to store start position of the required word
                    int verWordStartPos=searchVerticalWord(row,currCol);
                    
                    /*
                     * variable to calculate length of column that is length of
                     * required word
                     * 
                     */
                    int colLength=calcColLength(verWordStartPos,currCol);
                   
                    //implements recursion and backtracking
                    if(lineLength==colLength && colCharMatch
                    		(verWordStartPos,currCol,colLength,line)){
                        
                    	putVerticalWord
                    	(verWordStartPos,currCol,colLength,line);
                        
                    	if(currCol<12){
                    		
                            compute(row,currCol+1);
                        }
                        
                    	if(row<12){
                    		
                            compute(row+1,0);                            
                        }
                        deleteVerWord(verWordStartPos,currCol,colLength);
                        
                    }
                }
                
            	if(currCol>0 && currCol<13)return;
            
            }
        }
    }
    
    /**
     * method to print out the board as and when a word is placed or 
     * deleted from the board
     * 
     */
    
    static void print(){
    	
       //print the board
    	for(int row=0;row<13;row++){
    		
            for(int col=0;col<13;col++){
            	
                if(board[row][col]=='*')
                    System.out.print("  \t");
                else
                    System.out.print(board[row][col]+"\t");
            }
            System.out.println();
            
        }
        
    	//try loop to stop at every output
    	try
        {
            DataInputStream dis=new DataInputStream(System.in);
            String s=dis.readLine();
        }catch(Exception e)
        {
            System.out.println(e);
        }
    	

    	
    	
    }
}

