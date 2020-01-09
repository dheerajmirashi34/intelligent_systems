import java.util.Random;
import java.util.Scanner;

/**
 *   Author : Dheeraj Mirashi (801151308), Snehal Kulkarni (801147615), Adwait More (801134139)
 *   File Name : Main.java
 *   Description : This is the starting point of the application N-Queen using Hill Climbing.
 *                   Here the input Number of Queens is taken from user and then the choice of algorithm is asked.
 *                   Chosen algorithm executes and displays the required analysis.
 */

public class Main {

    /*
        This function prints the menu for choosing the type of hill climbing algorithm to take inputs from the user
    */
    private static void printMenuDrivenChoices() {
        System.out.println("Select Varient of Hill Climbing Algorithm:");
        System.out.println("1. Steepest Ascent Without Sideways Move");
        System.out.println("2. Steepest Ascent with Sideways Move");
        System.out.println("3. Random Restart Without Sideways Move");
        System.out.println("4. Random Restart With Sideways Move");
        System.out.print("Select choice:");
    }

    /*
        This function prints the analysis output based on the type of hill climbing algorithm chosen by the user
    */
    public static void printAnalysis(String algo,int queen, int noOfItr, float succRate)
    {
        System.out.println("\n****************************************************");
        System.out.println("Hill Climbing "+ algo );
        System.out.println("No. of Queens: " + queen);
        System.out.println("No. of Iterations: " + noOfItr);
        System.out.println("Success and Fail Rates:---------");
        System.out.println("Success Rate: "+succRate+ " %");
    }

    public static void main(String[] args){

        Scanner numScanner = new Scanner(System.in);
        Scanner txtScanner = new Scanner(System.in);

        do
        {
            int queen = 0;
            int noOfItr = new Random().nextInt(500) + 100;
            int[] storeResults = new int[4];

            while (queen <= 3)
            {
                System.out.print("Input no. of queens (greater than 3): ");
                queen = numScanner.nextInt();
            }
            int choice = -1;
            printMenuDrivenChoices();
            choice = numScanner.nextInt();
            switch (choice)
            {
                case 1:
                    for (int i = 0; i < noOfItr; i++)
                    {
                        HillClimbingSteepestAscent game = new HillClimbingSteepestAscent(queen);
                        int[] results = game.executeHillClimbAlgo();
                        storeResults[0] += results[0];
                        storeResults[1] += results[1];
                        storeResults[2] += results[2];
                        storeResults[3] += results[3];
                    }
                    printAnalysis("Steepest-Ascent Algorithm",queen,noOfItr,(storeResults[1] * 100) / (float)noOfItr);
                    System.out.println("Failure Rate: "+((storeResults[3] * 100) / (float)noOfItr) + " %");

                    if (storeResults[1] != 0)
                        System.out.println("Average no. of Steps When It Succeeds: " + (storeResults[0] / storeResults[1]));
                    if (storeResults[3] != 0)
                        System.out.println("Average no. of Steps When It Fails: " + (storeResults[2] / storeResults[3]));
                    break;

                case 2:
                    for (int i = 0; i < noOfItr; i++)
                    {
                        HillClimbingSidewaysMove game = new HillClimbingSidewaysMove(queen);
                        int[] results = game.executeHillClimbAlgo();
                        storeResults[0] += results[0];
                        storeResults[1] += results[1];
                        storeResults[2] += results[2];
                        storeResults[3] += results[3];
                    }

                    printAnalysis("Sideways Moves Algorithm",queen,noOfItr,(storeResults[1] * 100) / (float)noOfItr);
                    System.out.println("Failure Rate: "+((storeResults[3] * 100) / (float)noOfItr) + " %");

                    if (storeResults[1] != 0)
                        System.out.println("Average no. of Steps When It Succeeds: " + (storeResults[0] / storeResults[1]));
                    if (storeResults[3] != 0)
                        System.out.println("Average no. of Steps When It Fails: " + (storeResults[2] / storeResults[3]));
                    break;

                case 3:
                    for (int i = 0; i < noOfItr; i++)
                    {
                        HillClimbingRandomRestart game = new HillClimbingRandomRestart(queen);
                        int[] results = game.runWithoutSideways();
                        storeResults[0] += results[0];
                        storeResults[1] += results[1];
                        storeResults[2] += results[2];
                    }

                    printAnalysis("Random Restart Algorithm",queen, noOfItr,(storeResults[1] * 100) / (float)noOfItr );
                    System.out.println("Average no. of Steps When It Succeeds: " + (storeResults[0] / noOfItr));
                    System.out.println("Average no. of Restarts When It Succeeds: " + (storeResults[2] / noOfItr));
                    break;

                case 4:
                    for (int i = 0; i < noOfItr; i++)
                    {
                        HillClimbingRandomRestart game = new HillClimbingRandomRestart(queen);
                        int[] results = game.runWithSideways();
                        storeResults[0] += results[0];
                        storeResults[1] += results[1];
                        storeResults[2] += results[2];
                    }

                    printAnalysis("Random Restart Algorithm with sideways move",queen,noOfItr,((storeResults[1] * 100) / (float)noOfItr));
                    System.out.println("Average no. of steps when it succeeds: " + (storeResults[0] / noOfItr));

                    if ((storeResults[2] / (float)noOfItr) > 0 && (storeResults[2] / (float)noOfItr) < 1)
                        System.out.println("Average no. of Restarts when it succeeds: ~ 1");
                    else
                        System.out.println("Average no. of Restarts when it succeeds: " + (storeResults[2] / (float)noOfItr));
                    break;
            }
            System.out.println("Continue algorithm?  Y or N");
        } while (!txtScanner.nextLine().toUpperCase().equals("N"));
    }


}

