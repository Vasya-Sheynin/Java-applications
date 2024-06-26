package Part1;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        GardenTree[] treesArr = new GardenTree[7];
        treesArr[0] = new CherryTree("Sweet", 12, 49.9);
        treesArr[1] = new CherryTree("Tasty", 23, 64.7);
        treesArr[2] = new PearTree("Delicious", 4, 11);
        treesArr[3] = new PearTree("Yummy", 18, 52.7);
        treesArr[4] = new AppleTree("Superb", 3, 88.6);
        treesArr[5] = new AppleTree("Outstanding", 34, 95.1);
        treesArr[6] = new AppleTree("Refined", 26, 48.2);

        try {
            Connector con = new Connector(new File("trees_pt1.txt"));

            con.write(treesArr);

            GardenTree[] readTreesArr = con.read();

            System.out.println("-----All trees-----");
            for (GardenTree tree : readTreesArr) {
                System.out.println(tree);
            }

        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}