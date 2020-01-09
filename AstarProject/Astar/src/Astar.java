import java.util.*;

import static java.lang.System.*;

public class Astar {

    static Node goalState;
    private final static String heuristic1 = "MANHATTEN_DIST";
    private final static String heuristic2 = "TILE_LOCTION";
    private static String selectedHeuristic = "";
    private static Scanner input = new Scanner(in);

    public static void main(String[] args){
        try {
            int[][] state = new int[3][3];
            List<Node> openNodes = new ArrayList<Node>();
            List<Node> closedNodes = new ArrayList<Node>();
            Node initailnode = null;

            out.println("Enter goal state:");
            do {
                state = Astar.acceptInputState(input);
                goalState = new Node(null, state, 0);
                goalState = new Node(null, state, 0);
            }
            while (!isValidNode(state));

            out.println("Enter initial state:");
            do {
                state = Astar.acceptInputState(input);
                initailnode = new Node(null, state, 0);
            }
            while (!isValidNode(state));


            out.println("Initial node is:\n" + initailnode.toString());
            out.println("Initial node f(n)=" + initailnode.getfOfNManhatten());
            out.println("Goal node is:\n" + goalState.toString());
            out.println("<-------------------------------------------------------------------------------->");

            while (selectedHeuristic.equals(""))
                selectedHeuristic = selectHeuristicFromOptions(input);

            out.println("<-------------------------------------------------------------------------------->");

            openNodes.add(initailnode);
            while (!openNodes.isEmpty()) {
                Node node = findNodeWithLeastGOfN(openNodes, selectedHeuristic);
                openNodes.remove(node);

                if (node.equals(Astar.goalState)) {
                    List<Node> finalPath = new ArrayList<Node>();
                    out.println("Goal found");
                    out.println("path cost:" + node.getPathCost());
                    out.println("path:");

                    do {
                        finalPath.add(node);
                        node = node.getParent();
                    }
                    while (node.getParent() != null);
                    finalPath.add(initailnode);
                    printAllPath(finalPath);
                    return;
                }

                for (Node n1 : node.getChildren()) {
                    if (openNodes.contains(n1) || closedNodes.contains(n1)) {
                        continue;
                    } else {
                        openNodes.add(n1);
                    }
                }
                closedNodes.add(node);
            }
            System.out.println("Goal not found. Exiting with error");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            input.close();
        }
    }

    // prints all the nodes in a list in reverse order
    private static void printAllPath(List<Node> finalPath) {
        for(int i=finalPath.size()-1;i>=0;i--)
        {
            Node node1 = finalPath.get(i);
            out.println("");
            out.print(node1);

            if(selectedHeuristic.equals(heuristic1))
                out.println("h(n) manhatten             :   "+node1.heuristicManhatten());
            else
                out.println("h(n) misplaced tiles       :   "+node1.heuristicTiles());

            out.println("g(n) from root             :   "+node1.getPathCost());

            if(selectedHeuristic.equals(heuristic1))
                out.println("f(n) by manhatten          :   "+(node1.heuristicManhatten()+ node1.getPathCost()));
            else
                out.println("f(n) by misplaced tiles    :   "+(node1.heuristicTiles()+node1.getPathCost()));
        }
    }

    // returns a string that holds the name of heuristic selected by user
    private static String selectHeuristicFromOptions(Scanner input)
    {
        out.println("Choose a heuristic value between following:");
        out.println("press 1 for 'Manhatten Distance'");
        out.println("press 2 for 'misplaced tiles'");
        String selecetdHeuristic="";
        switch(Integer.parseInt(input.next().trim()))
        {
            case 1:
                selecetdHeuristic = Astar.heuristic1;
                break;
            case 2:
                selecetdHeuristic = Astar.heuristic2;
                break;
            default:
                out.println("Enter Valid Value");
        }
        return selecetdHeuristic;
    }

    // returns the Node with least g(n) value from the supplied list
    private static Node findNodeWithLeastGOfN(List<Node> openNodes, String heuristic) {
        Node nodeWithLeastGOfN = openNodes.get(0);
        for(Node n:openNodes){
            if(heuristic.equals(heuristic1)){
                if((n.getfOfNManhatten()+n.getPathCost())<(nodeWithLeastGOfN.getfOfNManhatten()+nodeWithLeastGOfN.getPathCost()))
                    nodeWithLeastGOfN = n;
            }
            else if(heuristic==heuristic2){
                if((n.getfOfNTiles()+n.getPathCost())<(nodeWithLeastGOfN.getfOfNTiles()+nodeWithLeastGOfN.getPathCost()))
                    nodeWithLeastGOfN = n;
            }

        }
        return nodeWithLeastGOfN;
    }

    // returns true iff the passed state is valid for any node
    //i.e. single occurance of all digits from 0 to 8
    public static boolean isValidNode(int[][] stateCheck) {
        Set<Integer> inputs = new HashSet<Integer>();
        for(int i = 0 ; i < stateCheck.length * stateCheck[0].length ; i++)
            inputs.add(stateCheck[i % stateCheck.length][i / stateCheck.length]);
        if(inputs.size() < 9){
            out.println("Please retry");
            return false;
        }
        return true;
    }

    // accepts Node's state from user and returns it in form form of 2D array of int
    public static int[][] acceptInputState(Scanner input){

            int[][] state = new int[3][3];
            for(int i=0;i<3;i++)
            {
                for(int j =0;j<3;j++)
                {
                    try
                    {
                        state[i][j] = Integer.parseInt(input.next().trim());
                        if(state[i][j]>8 || state[i][j]<0){
                            out.println("Enter Valid numbers from 0 to 8");
                            state[i][j] = 0;
                        }
                    }
                    catch (Exception e)
                    {
                        out.println("Enter Valid numbers from 0 to 8");
                        state[i][j] = 0;
                    }
                }
                out.println("");
            }
            return state;
    }
}
