import java.util.ArrayList;
import java.util.Random;

/**
 *  Author : Dheeraj Mirashi (801151308), Snehal Kulkarni (801147615), Adwait More (801134139)
 *  File Name : HillClimbingRandomRestart.java
 *  Description : This class solves the N-Queen problem using the random restart hill climbing algorithm with and without sideways
*/


public class HillClimbingRandomRestart extends Board {
    private int noOfSuccessSteps = 0;
    private int noSuccessIterations = 0;
    private boolean boardChanged = true;
    private int sidewaysMovesCompleted = 0;

    public HillClimbingRandomRestart(int numberOfQueens) {
        super(numberOfQueens);
    }

    /*
       Description : Resetboard from Board.java is called, and game board is restarted with side ways move
                     whenever board heuristic is stuck at h > 0
       Returns : Success / Failure Analysis
     */

    public int[] runWithSideways()
    {
        this.startNewGameBoard();
        boolean boardChanged = true;
        int currStateHeuristic = this.heuristicCalculationOfBoard(this.board);
        ArrayList<int[][]> listOfPossibleMoves = new ArrayList<int[][]>();
        this.resetRegularMoves();

        while (this.heuristicCalculationOfBoard(this.board) != 0 && boardChanged == true)
        {
            int columnNumber = 0;
            int[][] possibleMove = new int[this.getQueens()][this.getQueens()];
            for (int i = 0; i < this.getQueens(); i++)
            {
                for (int j = 0; j < this.getQueens(); j++)
                {
                    possibleMove[i][j] = this.board[i][j];
                }
            }

            currStateHeuristic = this.heuristicCalculationOfBoard(this.board);
            this.boardChanged = false;

            while (columnNumber < this.getQueens())
            {
                int currentQueenPos = -1;

                for (int i = 0; i < this.getQueens(); i++)
                {
                    if (possibleMove[i][columnNumber] == 1)
                        currentQueenPos = i;
                    possibleMove[i][columnNumber] = 0;
                }

                for (int i = 0; i < this.getQueens(); i++)
                {
                    possibleMove[i][columnNumber] = 1;

                    int[][] newMoveOfQueen = new int[this.getQueens()][this.getQueens()];
                    for (int k = 0; k < this.getQueens(); k++)
                    {
                        for (int j = 0; j < this.getQueens(); j++)
                        {
                            newMoveOfQueen[k][j] = possibleMove[k][j];
                        }
                    }
                    if (this.heuristicCalculationOfBoard(this.board) >= this.heuristicCalculationOfBoard(newMoveOfQueen) && this.equalsBoards(this.board, newMoveOfQueen) == false)
                        listOfPossibleMoves.add(newMoveOfQueen);
                    possibleMove[i][columnNumber] = 0;
                }

                possibleMove[currentQueenPos][columnNumber] = 1;

                columnNumber += 1;
            }

            Random rand = new Random();
            int minHeuristic = currStateHeuristic;

            if (listOfPossibleMoves.size() != 0)
            {
                int pick = rand.nextInt(listOfPossibleMoves.size());

                if (minHeuristic > this.heuristicCalculationOfBoard(listOfPossibleMoves.get(pick)))
                {
                    minHeuristic = this.heuristicCalculationOfBoard(listOfPossibleMoves.get(pick));
                    this.board = this.createCopy(listOfPossibleMoves.get(pick));
                    this.boardChanged = true;
                    this.sidewaysMovesCompleted = 0;
                    this.regularMoves += 1;
                    System.out.println("\nStep No." + regularMoves);
                    this.displayBoard();
                    listOfPossibleMoves.clear();
                }
                else if (minHeuristic == this.heuristicCalculationOfBoard(listOfPossibleMoves.get(pick)) &&
                        this.sidewaysMovesCompleted < 100) //restricting the sideways moves to 100
                {
                    minHeuristic = this.heuristicCalculationOfBoard(listOfPossibleMoves.get(pick));
                    this.board = this.createCopy(listOfPossibleMoves.get(pick));
                    this.boardChanged = true;
                    this.sidewaysMovesCompleted += 1;
                    this.regularMoves += 1;
                    System.out.println("\nStep No." + regularMoves);
                    this.displayBoard();
                    System.out.println("Sideways Move!");
                    listOfPossibleMoves.clear();
                }
                else
                {
                    this.resetBoards();
                    this.setBoard();
                    this.boardChanged = true;
                    listOfPossibleMoves.clear();
                    System.out.println("Random Restart!");
                    this.startNewGameBoard();
                }
            }
            else
            {
                this.resetBoards();
                this.setBoard();
                this.boardChanged = true;
                listOfPossibleMoves.clear();
                System.out.println("Random Restart!");
                this.startNewGameBoard();
            }
        }

        updateSuccessStatusBasedOnHeuristic();

        return new int[] {noOfSuccessSteps, noSuccessIterations, this.getNumberOfResets() };
    }

    /*
       Description : Resetboard from Board.java is called, and game board is restarted whenever board heuristic is stuck at h > 0
       Returns : Success / Failure Analysis
     */

    public int[] runWithoutSideways()
    {
        this.startNewGameBoard();
        boolean boardChanged = true;
        int currentStateHeuristic = this.heuristicCalculationOfBoard(this.board);
        ArrayList<int[][]> possibleMoves = new ArrayList<int[][]>();
        this.resetRegularMoves();

        while (this.heuristicCalculationOfBoard(this.board) != 0 && boardChanged == true)
        {
            int columnNumber = 0;
            int[][] possibleMove = new int[this.getQueens()][this.getQueens()];
            for (int i = 0; i < this.getQueens(); i++)
            {
                for (int j = 0; j < this.getQueens(); j++)
                {
                    possibleMove[i][j] = this.board[i][j];
                }
            }

            currentStateHeuristic = this.heuristicCalculationOfBoard(this.board);
            this.boardChanged = false;

            while (columnNumber < this.getQueens())
            {
                int currentColumnQueenPosition = -1;

                for (int i = 0; i < this.getQueens(); i++)
                {
                    if (possibleMove[i][columnNumber] == 1)
                    currentColumnQueenPosition = i;
                    possibleMove[i][columnNumber] = 0;
                }

                for (int i = 0; i < this.getQueens(); i++)
                {
                    possibleMove[i][columnNumber] = 1;

                    int[][] newMove = new int[this.getQueens()][this.getQueens()];
                    for (int k = 0; k < this.getQueens(); k++)
                    {
                        for (int j = 0; j < this.getQueens(); j++)
                        {
                            newMove[k][j] = possibleMove[k][j];
                        }
                    }
                    if (this.heuristicCalculationOfBoard(this.board) > this.heuristicCalculationOfBoard(newMove))
                        possibleMoves.add(newMove);
                    possibleMove[i][columnNumber] = 0;
                }

                possibleMove[currentColumnQueenPosition][columnNumber] = 1;

                columnNumber += 1;
            }

            Random rand = new Random();

            if (possibleMoves.size() != 0)
            {
                int pick = rand.nextInt(possibleMoves.size());

                int minHeuristic = currentStateHeuristic;

                if (minHeuristic > this.heuristicCalculationOfBoard(possibleMoves.get(pick)))
                {
                    minHeuristic = this.heuristicCalculationOfBoard(possibleMoves.get(pick));
                    this.board = this.createCopy(possibleMoves.get(pick));
                    this.boardChanged = true;

                    this.regularMoves += 1;
                    System.out.println("\nStep No." + regularMoves);
                    this.displayBoard();
                    possibleMoves.clear();
                }
            }
            else
            {
                this.resetBoards();
                this.setBoard();
                this.boardChanged = true;
                possibleMoves.clear();
                System.out.println("Random Restart!");
                this.startNewGameBoard();
            }
        }

        updateSuccessStatusBasedOnHeuristic();

        return new int[] {noOfSuccessSteps, noSuccessIterations, this.getNumberOfResets() };
    }

    /*
        Description : After calculation of the heuristic, this function displays the success status for both of the above methods
     */
    private void updateSuccessStatusBasedOnHeuristic(){
        if (this.heuristicCalculationOfBoard(this.board) == 0)
        {
            System.out.println("\nSuccess!");
            this.noOfSuccessSteps += this.regularMoves;
            this.noSuccessIterations += 1;
            System.out.println("Total Steps Taken: " + this.regularMoves);
        }
    }
}
