package Part2;

import java.io.*;

public class Connector {

    private final File file;

    public File getFile() {
        return file;
    }

    public Connector(String filename) {
        this.file = new File(filename);
    }

    public Connector(File file) {
        this.file = file;
    }

    public void write(GardenTree[] treesArr) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);

        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeInt(treesArr.length);
            for (GardenTree gardenTree : treesArr) {
                oos.writeObject(gardenTree);
            }

            oos.flush();
        }
    }

    public GardenTree[] read() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);

        try (ObjectInputStream ois = new ObjectInputStream(fis)) {
            int length = ois.readInt();
            GardenTree[] result = new GardenTree[length];

            for (int i = 0; i < length; i++) {
                result[i] = (GardenTree) ois.readObject();
            }

            return result;
        }
    }
}
