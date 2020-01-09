
/**
 *   Author : Dheeraj Mirashi (801151308), Snehal Kulkarni (801147615), Adwait More (801134139)
 *   File Name : Board.java
 *   Description : This is the base class consisting of all the Board related operations and variables used accross the algorithms
 */

import java.util.Random;

public class Board {

    Random rand = new Random();
    private int queens;
    private int[][] boardOne;
    private int[][] boardTwo;
    private int[][] boardThree;
    public int[][] board;
    public int numberOfResets = -1;
    public int regularMoves = 0;
    public int sidewaysMoves = 0;

    public Board(int numberOfQueens) {
        this.queens = numberOfQueens;
        boardOne = new int[numberOfQueens][numberOfQueens];
        boardTwo = new int[numberOfQueens][numberOfQueens];
        boardThree = new int[numberOfQueens][numberOfQueens];
        this.resetBoards();
        this.setBoard();
    }

    public int getQueens() {
        return queens;
    }

    public int[][] getBoardOne() {
        return boardOne;
    }

    public int[][] getBoardTwo() {
        return boardTwo;
    }

    public int[][] getBoardThree() {
        return boardThree;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getNumberOfResets() {
        return numberOfResets;
    }

    public int getRegularMoves() {
        return regularMoves;
    }

    public int getSidewaysMoves() {
        return sidewaysMoves;
    }

    public void resetRegularMoves()
    {
        this.regularMoves = 0;
    }

    public void sidewaysMovesReset()
    {
        this.sidewaysMoves = 0;
    }

    /*
       Description : Resets the boards and then generates a new board by randomly placing the queen
    */
    public void resetBoards()
    {
        boardOne = resetOneBoard();
        boardTwo = resetOneBoard();
        boardThree = resetOneBoard();
        this.numberOfResets += 1;
    }

    public void setBoard()
    {
        int n = rand.nextInt(2);
        if (n == 0)
            this.board = this.createCopy(boardOne);
        else if (n == 1)
            this.board = this.createCopy(boardTwo);
        else
            this.board = this.createCopy(boardThree);
    }

    public int[][] createCopy(int[][] oldBoard)
    {
        int[][] newBoard = new int[this.getQueens()][this.getQueens()];

        for (int i = 0; i < oldBoard.length; i++)
        {
            for (int j = 0; j < oldBoard.length; j++)
            {
                newBoard[i][j] = oldBoard[i][j];
            }
        }
        return newBoard;
    }

    /*
          Description : This function is starts the game boards
       */
    public void startNewGameBoard()
    {
        System.out.println("\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("Initial Board");
        System.out.println("State's Heuristic value: " + this.heuristicCalculationOfBoard(this.board));

        for (int x = 0; x < this.board.length; x++)
        {
            for (int y = 0; y < this.board.length; y++)
            {
                System.out.print(this.board[x][y] + " ");
            }
            System.out.println();
        }
    }


    /*
       Description : This function is prints the game board
    */

    public void displayBoard()
    {
        System.out.println("State's Heuristic value: " + this.heuristicCalculationOfBoard(this.board));

        for (int x = 0; x < this.board.length; x++)
        {
            for (int y = 0; y < this.board.length; y++)
            {
                System.out.print(this.board[x][y] + " ");
            }
            System.out.println();
        }
    }


    /*
       Description : This function checks whether the boards are equal or not
    */
    public boolean equalsBoards(int[][] boardOne, int[][] boardTwo)
    {
        Boolean areEqual = true;

        for (int i = 0; i < this.getQueens(); i++)
        {
            for (int j = 0; j < this.getQueens(); j++)
            {
                if (boardOne[i][j] != boardTwo[i][j])
                areEqual = false;
            }
        }
        return areEqual;
    }

    /*
      Description : Resets board by randomly placing the queens and then returns it
   */
    public int[][] resetOneBoard(){
        int[][] board1 = new int[queens][queens];
        for (int i = 0; i < this.queens; i++)
        {
            int queenPositionY = rand.nextInt(this.queens - 1);

            for (int j = 0; j < this.queens; j++)
            {
                if (j == queenPositionY){
                    board1[j][i] = 1; //means queen is placed;
                }
                else{
                    board1[j][i] = 0; //means queen is not placed;
                }
            }
        }
        return board1;
    }

     /*
       Parameters : queenBoard
       Description : This function is calculates the heuristic of the board sent as parameter.
                     The heuristic here is the number of pairs of queens attacking each other
       Returns : Heuristic value (Integer)
     */

    public int heuristicCalculationOfBoard(int[][] queenBoard)
    {
        int[][] board = queenBoard;
        int heuristic = 0;

        for (int i = 0; i < this.queens; i++)
        {
            int noQueens = 0;
            for (int j = 0; j < this.queens; j++)
            {
                if (board[i][j] == 1)
                    noQueens += 1;
            }

            if (noQueens > 1)
            {
                for (int queens = noQueens; queens > 1; --queens)
                {
                    heuristic += queens - 1;
                }
            }
        }


        for (int i = 0; i < this.queens; i++)
        {
            int noQueens = 0;
            for (int j = 0; j < this.queens; j++)
            {
                if (board[j][i] == 1)
                    noQueens += 1;
            }

            if (noQueens > 1)
            {
                for (int queens = noQueens; queens > 1; --queens)
                {
                    heuristic += queens - 1;
                }
            }
        }


        int numOfQueens = 0;
        int i1 = 0;
        while (i1 < (this.queens - 1))
        {
            numOfQueens = 0;
            int xValue = 0;
            int yValue = i1;

            while (xValue <= i1)
            {
                if (board[xValue][yValue] == 1)
                    numOfQueens += 1;

                yValue--;
                xValue++;
            }
            if (numOfQueens > 1)
            {
                for (int queens = numOfQueens; queens > 1; --queens)
                {
                    heuristic += queens - 1;
                }
            }
            i1++;
        }


        numOfQueens = 0;
        int diagonal = this.queens - 1;
        for (int i = 0; i < this.queens; i++)
        {
            if (board[i][diagonal] == 1)
                numOfQueens++;
            diagonal--;
        }

        if (numOfQueens > 1)
        {
            for (int queens = numOfQueens; queens > 1; --queens)
            {
                heuristic += queens - 1;
            }
        }


        numOfQueens = 0;
        i1 = 1;
        while (i1 < (this.queens - 1))
        {
            numOfQueens = 0;
            int xValue = i1;
            int yValue = this.queens - 1;

            while (xValue < this.queens)
            {
                if (board[xValue][yValue] == 1)
                    numOfQueens += 1;

                yValue--;
                xValue++;
            }
            if (numOfQueens > 1)
            {
                for (int queens = numOfQueens; queens > 1; --queens)
                {
                    heuristic += queens - 1;
                }
            }

            i1++;
        }


        numOfQueens = 0;
        i1 = this.queens - 2;
        while (i1 > 0)
        {
            numOfQueens = 0;
            int xValue = 0;
            int yValue = i1;

            while (yValue < this.queens)
            {
                if (board[xValue][yValue] == 1)
                    numOfQueens += 1;

                yValue++;
                xValue++;
            }

            if (numOfQueens > 1)
            {
                for (int queens = numOfQueens; queens > 1; --queens)
                {
                    heuristic += queens - 1;
                }
            }

            i1--;
        }


        numOfQueens = 0;
        diagonal = 0;
        for (int i = 0; i < this.queens; i++)
        {
            if (board[i][diagonal] == 1)
                numOfQueens++;
            diagonal++;
        }

        if (numOfQueens > 1)
        {
            for (int queens = numOfQueens; queens > 1; --queens)
            {
                heuristic += queens - 1;
            }
        }


        numOfQueens = 0;
        i1 = 1;
        while (i1 < this.queens)
        {
            numOfQueens = 0;
            int xValue = i1;
            int yValue = 0;

            while (xValue < this.queens)
            {
                if (board[xValue][yValue] == 1)
                    numOfQueens += 1;

                yValue++;
                xValue++;
            }
            if (numOfQueens > 1)
            {
                for (int queens = numOfQueens; queens > 1; --queens)
                {
                    heuristic += queens - 1;
                }
            }

            i1++;
        }

        return heuristic;
    }
}
