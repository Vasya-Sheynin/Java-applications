package Part2;

import java.io.Serial;
import java.io.Serializable;

public class AppleTree extends GardenTree implements Serializable {
    private final int uniqueId = id;

    public String getId() {
        return String.valueOf(uniqueId);
    }

    @Serial
    private static final long serialVersionUID = 3636278649039857364L;

    public AppleTree(String name, int age, double fruiting) {
        super(name, Type.APPLE_TREE, age, fruiting);
    }

    public AppleTree() {
        this("", 0, 0);
    }

    @Override
    public String toString() {
        outputColor = isReplaceNeeded() ? "\n\u001b[31m" : "\n\u001b[32m";

        return "-=-=-=-=-=-=-" +
                AppLocale.getString(AppLocale.id) + getId() +
                AppLocale.getString(AppLocale.creationDate) + getCreationDate() +
                AppLocale.getString(AppLocale.type) + getType() +
                AppLocale.getString(AppLocale.treeSort) + getSort() +
                AppLocale.getString(AppLocale.age) + getAge() +
                AppLocale.getString(AppLocale.fruiting) + getFruiting() + "%" +
                outputColor + (isReplaceNeeded() ? AppLocale.getString(AppLocale.needReplace) : AppLocale.getString(AppLocale.noNeedReplace)) + whiteColor +
                "-=-=-=-=-=-=-\n";
    }
}
