package Part1;

import java.io.Serializable;

public class CherryTree extends GardenTree implements Serializable {
    private final int uniqueId = id;

    public CherryTree(String name, int age, double fruiting) {
        super(name, Type.CHERRY_TREE, age, fruiting);
    }

    public CherryTree() {
        this("", 0, 0);
    }

    @Override
    public String toString() {
        outputColor = isReplaceNeeded() ? "\n\u001b[31m" : "\n\u001b[32m";

        return "-=-=-=-=-=-=-\nTree sort: " +
                sort + "\nType: " + type.toString() + "\nID: " + uniqueId + "\nAge: " +
                age + "\nFruiting: " + fruiting + "%" + outputColor + (isReplaceNeeded() ? "Replace IS needed" : "Replace IS NOT needed") + whiteColor +
                "-=-=-=-=-=-=-\n";
    }
}
