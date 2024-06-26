package Part2;

import java.util.*;
import java.io.*;

public class Main {

    public static GardenTree[] createTreesArr() {
        GardenTree[] treesArr = new GardenTree[7];
        treesArr[0] = new CherryTree("Sweet", 12, 49.9);
        treesArr[1] = new CherryTree("Tasty", 23, 64.7);
        treesArr[2] = new PearTree("Delicious", 4, 11);
        treesArr[3] = new PearTree("Yummy", 18, 52.7);
        treesArr[4] = new AppleTree("Superb", 3, 88.6);
        treesArr[5] = new AppleTree("Outstanding", 34, 95.1);
        treesArr[6] = new AppleTree("Refined", 26, 48.2);

        return treesArr;
    }

    static Locale createLocale(String[] args) {
        if (args.length == 2) {
            return new Locale(args[0], args[1]);
        } else if (args.length == 4) {
            return new Locale(args[2], args[3]);
        }
        return null;
    }

    static void setupConsole(String[] args) {
        if (args.length >= 2) {
            if (args[0].equals("-encoding")) {
                try {
                    System.setOut(new PrintStream(System.out, true, args[1]));
                } catch (UnsupportedEncodingException ex) {
                    System.err.println("Unsupported encoding: " + args[1]);
                    System.exit(1);
                }
            }
        }
    }

    public static void main(String[] args) {

        try {
            setupConsole(args);
            Locale loc = createLocale(args);
            if (loc == null) {
                System.err.println("Invalid argument(s)\n"
                        + "Syntax: [-encoding ENCODING_ID] language country\n"
                        + "Example: -encoding UTF-8 be BY");
                System.exit(1);
            }
            AppLocale.set(loc);
            Connector con = new Connector(new File("trees_pt2.txt"));
            con.write(createTreesArr());
            GardenTree[] trees = con.read();
            System.out.println(AppLocale.getString(AppLocale.allTrees));
            for (GardenTree n : trees) {
                System.out.println(n);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
