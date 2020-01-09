import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    private Node parent;
    private int[][] state;
    private int pathCost;
    private int fOfNManhatten;
    private int fOfNTiles;

    Node(Node parent,int[][] state,int pathCost)
    {
            this.parent = parent;
            this.state = state;
            this.pathCost = pathCost;
            fOfNManhatten = this.heuristicManhatten()+pathCost;
            fOfNTiles = this.heuristicTiles()+pathCost;
    }

    // returns manhatten distance heuristic value
    public int heuristicManhatten(){
        int manhattenDist = 0;
        if(Astar.goalState!=null)
        {
           for(int i = 1; i<9;i++){
               int[] goalCo = getCoOrdinates(i,Astar.goalState);
               int[] curCo = getCoOrdinates(i,this);
               manhattenDist +=Math.abs(goalCo[0]-curCo[0])+Math.abs(goalCo[1]-curCo[1]);
           }
        }
        return manhattenDist;
        //sum of all x co-ordinate goal vs actual + y-coordinate goal vs actual
    }

    // returns the co-ordinates of a number in the matrix
    public static int[] getCoOrdinates(int num,Node n)
    {
        int[] coOrdinates ={0,0};
        for(int i=0;i<3;i++){
            for(int j =0; j<3;j++){
                if(num==n.getState()[i][j]){
                    coOrdinates[0] = i;
                    coOrdinates[1] = j;
                }
            }
        }
        return coOrdinates;
    }

    // returns misplaced tiles heuristic value
    public int heuristicTiles(){
        int misplacedTilesCount = 0;
        if(Astar.goalState!=null)
        {
            for(int i=0;i<3;i++){
                for(int j =0; j<3;j++){
                    if((this.state[i][j]!=Astar.goalState.state[i][j]) && this.state[i][j]!=0)
                        misplacedTilesCount++;
                }
            }
        }
        return misplacedTilesCount;
    }


    // returns list of all the possible children
    public List<Node> getChildren()
    {
        List<Node> childrenNodes = new ArrayList<Node>();

        int a = getCoOrdinates(0,this)[0];
        int b = getCoOrdinates(0,this)[1];

        Node child1 = getNode(a,b+1,a,b);
        Node child2 = getNode(a+1,b,a,b);
        Node child3 = getNode(a,b-1,a,b);
        Node child4 = getNode(a-1,b,a,b);

        if(child1!=null)
            childrenNodes.add(child1);
        if(child2!=null)
            childrenNodes.add(child2);
        if(child3!=null)
            childrenNodes.add(child3);
        if(child4!=null)
            childrenNodes.add(child4);
        return childrenNodes;
    }

    // returns node with switching the location of empty tile from old to new
    private Node getNode(int newLocaionx, int newLocaiony,int oldLocationx, int oldLocationy) {
        if(newLocaionx>2||newLocaiony>2||newLocaionx<0||newLocaiony<0)
            return null;

        int[][] stateNew = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                stateNew[i][j]=this.state[i][j];
            }
        }

        Node node = new Node(this,stateNew,this.pathCost+1);
        int temp = node.state[oldLocationx][oldLocationy];
        node.state[oldLocationx][oldLocationy] = node.state[newLocaionx][newLocaiony];
        node.state[newLocaionx][newLocaiony] = temp;
        return node;
    }

    public Node getParent() {
        return parent;
    }

    public int[][] getState() {
        return state;
    }

    public int getPathCost() {
        return pathCost;
    }
    public int getfOfNManhatten() {
        return fOfNManhatten;
    }

    public int getfOfNTiles() {
        return fOfNTiles;
    }

    public void setfOfNManhatten(int fOfNManhatten) {
        this.fOfNManhatten = fOfNManhatten;
    }

    public void setfOfNTiles(int fOfNTiles) {
        this.fOfNTiles = fOfNTiles;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }


    public void setState(int[][] state) {
        this.state = state;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<3;i++)
        {
           for(int j =0;j<3;j++)
           {
               sb.append(state[i][j]+" ");
           }
           sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        for (int i = 0; i <3; i++) {
            for (int j = 0; j < 3; j++) {
                if(node.state[i][j]!=this.getState()[i][j])
                    return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getState());
    }
}
