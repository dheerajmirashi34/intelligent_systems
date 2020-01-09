import java.util.ArrayList;
import java.util.Random;

/**
 *   Author : Dheeraj Mirashi (801151308), Snehal Kulkarni (801147615), Adwait More (801134139)
 *   File Name : HillClimbingSteepestAscent.java
 *   Description : This class solves the N-Queen problem using the steepest ascent hill climbing algorithm
 */

public class HillClimbingSteepestAscent extends Board {

    private int noOfSuccessSteps = 0;
    private int noSuccessIterations = 0;
    private int noOfFailSteps = 0;
    private int noFailIterations = 0;
    private boolean changesInBoard = true;

    public HillClimbingSteepestAscent(int numberOfQueens) {
        super(numberOfQueens);
    }

    /*
       Description : This function is called untill the heiuristic value is zero and neighbours with a lower heuristic value is found
                     This is failed if heuristic value equal to zero is not found
       Returns : Success / Failure Analysis
     */

    public int[] executeHillClimbAlgo()
    {
        this.startNewGameBoard();
        this.changesInBoard = true;
        int currentStateHeuristic = this.heuristicCalculationOfBoard(this.board);
        ArrayList<int[][]> possibleMoves = new ArrayList<int[][]>();
        this.resetRegularMoves();

        while (this.heuristicCalculationOfBoard(this.board) != 0 && (this.changesInBoard == true))
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
            this.changesInBoard = false;

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
                    this.changesInBoard = true;

                    this.regularMoves += 1;
                    System.out.println("\nStep No." + regularMoves);
                    this.displayBoard();
                    possibleMoves.clear();
                }
            }
            else
            {
                if (possibleMoves.size() == 0)
                {
                    this.changesInBoard = false;
                    possibleMoves.clear();
                }
            }
        }

        if (this.heuristicCalculationOfBoard(this.board) == 0)
        {
            System.out.println("\nSuccess!");
            this.noOfSuccessSteps += this.regularMoves;
            this.noSuccessIterations += 1;
             System.out.println("Total Steps Taken: " + this.regularMoves);
        }
        else
        {
             System.out.println("\nFailure!");
            this.displayBoard();
            this.noOfFailSteps += this.regularMoves;
            this.noFailIterations += 1;
             System.out.println("Total Steps Taken: " + this.regularMoves);
        }

        return new int[] {noOfSuccessSteps, noSuccessIterations, noOfFailSteps,
                noFailIterations};
    }
}
