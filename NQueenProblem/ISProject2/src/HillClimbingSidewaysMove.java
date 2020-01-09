import java.util.ArrayList;
import java.util.Random;

/**
 *   Author : Dheeraj Mirashi (801151308), Snehal Kulkarni (801147615), Adwait More (801134139)
 *   File Name : HillClimbingSidewaysMove.java
 *   Description : This class solves the N-Queen problem using the Hill Climbing algorithm with sideways move.
 */

public class HillClimbingSidewaysMove extends Board {
    private int noOfSuccessSteps = 0;
    private int noSuccessIterations = 0;
    private int noOfFailSteps = 0;
    private int noFailIterations = 0;
    private boolean changesInBoard = true;
    private int sidewaysMovesConsecutive = 0;
    private final int LIMIT_OF_SIDEWAYS_MOVES = 200;

    public HillClimbingSidewaysMove(int numberOfQueens) {
        super(numberOfQueens);
    }

    /*
       Description : This function is called until the heuristic value is zero and neighbours with a lower heuristic value is found
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
            Boolean moveMade = false;
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
                    if (this.heuristicCalculationOfBoard(this.board) >= this.heuristicCalculationOfBoard(newMove) && this.equalsBoards(this.board, newMove) == false)
                        possibleMoves.add(newMove);
                    possibleMove[i][columnNumber] = 0;
                }

                possibleMove[currentColumnQueenPosition][columnNumber] = 1;

                columnNumber += 1;
            }

            Random rand = new Random();
            int minHeuristic = currentStateHeuristic;

            if (possibleMoves.size() != 0)
            {
                int pick = rand.nextInt(possibleMoves.size());

                if (minHeuristic > this.heuristicCalculationOfBoard(possibleMoves.get(pick)))
                {
                    minHeuristic = this.heuristicCalculationOfBoard(possibleMoves.get(pick));
                    this.board = this.createCopy(possibleMoves.get(pick));
                    this.changesInBoard = true;
                    this.regularMoves += 1;
                    this.sidewaysMovesConsecutive = 0;
                    System.out.println("\nStep No." + regularMoves);
                    this.displayBoard();
                    possibleMoves.clear();
                }
                else if (minHeuristic == this.heuristicCalculationOfBoard(possibleMoves.get(pick)) &&
                        this.sidewaysMovesConsecutive < 200)    //restriction to number of sideways move
                {
                    minHeuristic = this.heuristicCalculationOfBoard(possibleMoves.get(pick));
                    this.board = this.createCopy(possibleMoves.get(pick));
                    this.changesInBoard = true;
                    this.sidewaysMovesConsecutive += 1;
                    this.regularMoves += 1;
                    System.out.println("\nStep No." + regularMoves);
                    this.displayBoard();
                    System.out.println("Sideways Move!");
                    possibleMoves.clear();
                }
            }
            else
            {
                this.changesInBoard = false;
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
