package PT7;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length >= 1) {
                switch (args[0]) {
                    case "-?", "-h" -> System.out.println(
                            """
                                    Syntax:
                                    \t-a  [file [encoding]] - append data (*)
                                    \t-az [file [encoding]] - append data (*), compress every record
                                    \t-d                    - clear all data
                                    \t-dk  {f|t|d} key      - clear data by key
                                    \t-p                    - print data unsorted
                                    \t-ps  {f|t|d}          - print data sorted by full name/telephone number/date
                                    \t-psr {f|t|d}          - print data reverse sorted by full name/telephone number/date
                                    \t-f   {f|t|d} key      - find record by key
                                    \t-fg  {f|t|d} key      - find records > key
                                    \t-fl  {f|t|d} key      - find records < key
                                    \t-?, -h                - command line syntax
                                       (*) if not specified, encoding for file is utf8
                                    """
                    );
                    case "-a" ->
                        // Append file with new object from System.in
                        // -a [file [encoding]]
                            appendFile(args, false);
                    case "-az" ->
                        // Append file with compressed new object from System.in
                        // -az [file [encoding]]
                            appendFile(args, true);
                    case "-p" ->
                        // Prints data file
                            printFile();
                    case "-ps" -> {
                        // Prints data file sorted by key
                        if (!printFile(args, false)) {
                            System.exit(1);
                        }
                    }
                    case "-psr" -> {
                        // Prints data file reverse-sorted by key
                        if (!printFile(args, true)) {
                            System.exit(1);
                        }
                    }
                    case "-d" -> {
                        // delete files
                        if (args.length != 1) {
                            System.err.println("Invalid number of arguments");
                            System.exit(1);
                        }
                        deleteFile();
                    }
                    case "-dk" -> {
                        // Delete records by key
                        if (!deleteFile(args)) {
                            System.exit(1);
                        }
                    }
                    case "-f" -> {
                        // Find record(s) by key
                        if (!findByKey(args)) {
                            System.exit(1);
                        }
                    }
                    case "-fg" -> {
                        // Find record(s) by key greater then key
                        if (!findByKey(args, new KeyCompReverse())) {
                            System.exit(1);
                        }
                    }
                    case "-fl" -> {
                        // Find record(s) by key less then key
                        if (!findByKey(args, new KeyComp())) {
                            System.exit(1);
                        }
                    }
                    default -> {
                        System.err.println("Option is not realised: " + args[0]);
                        System.exit(1);
                    }
                }
            }
            else {
                System.err.println("ConversationData: Nothing to do! Enter -? for options");
            }
        }
        catch (Exception e) {
            System.err.println("Run/time error: " + e);
            System.exit(1);
        }
        System.out.println("ConversationData finished...");
        System.exit(0);
    }

    static final String filename    = "ConversData.dat";
    static final String filenameBak = "ConversData.~dat";
    static final String idxname     = "ConversData.idx";
    static final String idxnameBak  = "ConversData.~idx";

    // input file encoding:
    private static String encoding = "utf8";
    private static PrintStream ConversDataOut = System.out;

    static ConversData readConversData(Scanner fin) throws IOException {
        return ConversData.nextRead(fin, ConversDataOut)
                ? ConversData.read(fin, ConversDataOut) : null;
    }

    private static void deleteBackup() {
        new File(filenameBak).delete();
        new File(idxnameBak).delete();
    }

    static void deleteFile() {
        deleteBackup();
        new File(filename).delete();
        new File(idxname).delete();
    }

    private static void backup() {
        deleteBackup();
        new File(filename).renameTo(new File(filenameBak));
        new File(idxname).renameTo(new File(idxnameBak));
    }

    static boolean deleteFile(String[] args)
            throws ClassNotFoundException, IOException {
        if (args.length != 3) {
            System.err.println("Invalid number of arguments");
            return false;
        }
        Long[] poss = null;
        try (Index idx = Index.load(idxname)) {
            IndexBase pidx = indexByArg(args[1], idx);
            if (pidx == null) {
                return false;
            }
            if (!pidx.contains(args[2])) {
                System.err.println("Key not found: " + args[2]);
                return false;
            }
            poss = pidx.get(args[2]);
        }
        backup();
        Arrays.sort(poss);
        try (Index idx = Index.load(idxname);
              RandomAccessFile fileBak= new RandomAccessFile(filenameBak, "rw");
              RandomAccessFile file = new RandomAccessFile(filename, "rw")) {
            boolean[] wasZipped = new boolean[] {false};
            long pos;
            while ((pos = fileBak.getFilePointer()) < fileBak.length()) {
                ConversData conversData = (ConversData)
                        Buffer.readObject(fileBak, pos, wasZipped);
                if (Arrays.binarySearch(poss, pos) < 0) { // if not found in deleted
                    long ptr = Buffer.writeObject(file, conversData, wasZipped[0]);
                    idx.put(conversData, ptr);
                }
            }
        }
        return true;
    }

    static void appendFile(String[] args, Boolean zipped)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        if (args.length >= 2) {
            FileInputStream stdin = new FileInputStream(args[1]);
            System.setIn(stdin);
            if (args.length == 3) {
                encoding = args[2];
            }
            // hide output:
            ConversDataOut = new PrintStream("nul");
        }
        appendFile(zipped);
    }

    static void appendFile(Boolean zipped)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        Scanner fin = new Scanner(System.in, encoding);
        ConversDataOut.println("Enter conversation data: ");
        try (Index idx = Index.load(idxname);
              RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            for(;;) {
                ConversData conversData = readConversData(fin);
                if (conversData == null)
                    break;
                long pos = Buffer.writeObject(raf, conversData, zipped);
                idx.put(conversData, pos);
            }
        }
    }

    private static void printRecord(RandomAccessFile raf, long pos)
            throws ClassNotFoundException, IOException {
        boolean[] wasZipped = new boolean[] {false};
        ConversData conversData = (ConversData) Buffer.readObject(raf, pos, wasZipped);
        if (wasZipped[0]) {
            System.out.print(" compressed");
        }
        System.out.println(" record at position "+ pos + ": \n" + conversData);
        System.out.println();
    }

    private static void printRecord(RandomAccessFile raf, String key,
                                     IndexBase pidx) throws ClassNotFoundException, IOException {
        Long[] poss = pidx.get(key);
        for (long pos : poss) {
            System.out.print("*** Key: " +  key + " points to");
            printRecord(raf, pos);
        }
    }

    static void printFile()
            throws FileNotFoundException, IOException, ClassNotFoundException {
        long pos;
        int rec = 0;
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            while ((pos = raf.getFilePointer()) < raf.length()) {
                System.out.print("#" + (++rec));
                printRecord(raf, pos);
            }
            System.out.flush();
        }
    }

    private static IndexBase indexByArg(String arg, Index idx) {
        IndexBase pidx = null;
        switch (arg) {
            case "f" -> pidx = idx.fullNames;
            case "t" -> pidx = idx.telNumbers;
            case "d" -> pidx = idx.dates;
            default -> System.err.println("Invalid index specified: " + arg);
        }
        return pidx;
    }

    static boolean printFile(String[] args, boolean reverse)
            throws ClassNotFoundException, IOException {
        if (args.length != 2) {
            System.err.println("Invalid number of arguments");
            return false;
        }
        try (Index idx = Index.load(idxname);
              RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            IndexBase pidx = indexByArg(args[1], idx);
            if (pidx == null) {
                return false;
            }
            String[] keys =
                    pidx.getKeys(reverse ? new KeyCompReverse() : new KeyComp());
            for (String key : keys) {
                printRecord(raf, key, pidx);
            }
        }
        return true;
    }

    static boolean findByKey(String[] args)
            throws ClassNotFoundException, IOException {
        if (args.length != 3) {
            System.err.println("Invalid number of arguments");
            return false;
        }
        try (Index idx = Index.load(idxname);
              RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            IndexBase pidx = indexByArg(args[1], idx);
            if (!pidx.contains(args[2])) {
                System.err.println("Key not found: " + args[2]);
                return false;
            }
            printRecord(raf, args[2], pidx);
        }
        return true;
    }

    static boolean findByKey(String[] args, Comparator<String> comp)
            throws ClassNotFoundException, IOException {
        if (args.length != 3) {
            System.err.println("Invalid number of arguments");
            return false;
        }
        try (Index idx = Index.load(idxname);
              RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            IndexBase pidx = indexByArg(args[1], idx);
            if (!pidx.contains(args[2])) {
                System.err.println("Key not found: " + args[2]);
                return false;
            }
            String[] keys = pidx.getKeys(comp);
            for (String key : keys) {
                if (key.equals(args[2])) {
                    break;
                }
                printRecord(raf, key, pidx);
            }
        }
        return true;
    }
}
