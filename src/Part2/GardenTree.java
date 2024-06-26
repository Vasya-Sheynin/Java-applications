package Part2;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.text.DateFormat;

public class GardenTree implements Serializable {
    protected static int id = 0;

    @Serial
    private static final long serialVersionUID = 2937587316086537465L;
    protected final Date creationDate = new Date();
    protected final String whiteColor = "\n\u001b[0m";

    protected transient String outputColor;

    protected int age;

    protected double fruiting;

    protected String sort;

    protected Type type;

    public static enum Type {
        APPLE_TREE, CHERRY_TREE, PEAR_TREE
    }

    public String getSort() {
        return sort;
    }

    public String getAge() {
        return String.valueOf(age);
    }

    public String getFruiting() {
        return String.valueOf(fruiting);
    }

    public Type getType() {
        return type;
    }

    public String getCreationDate() {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(
                DateFormat.LONG, DateFormat.DEFAULT, AppLocale.getLocale());
        return dateFormat.format(creationDate);
    }

    public boolean isReplaceNeeded() {
        if (fruiting < 50.0 && age > 5) return true;

        switch(type) {
            case CHERRY_TREE -> {
                return age > 20;
            }
            case APPLE_TREE, PEAR_TREE -> {
                return age > 15;
            }
            default -> throw new RuntimeException("Invalid tree type");
        }
    }

    protected GardenTree(String sort, Type type, int age, double fruiting) {
        id++;
        this.sort = sort;
        this.type = type;

        if (age < 0) throw new IllegalArgumentException("Tree age is negative");
        this.age = age;

        if (fruiting < 0.0 || fruiting > 100.0) throw new IllegalArgumentException("Tree fruiting is out of range");
        this.fruiting = fruiting;
    }
}
