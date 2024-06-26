package PT7;

import java.io.*;
import java.time.YearMonth;
import java.util.*;

public class ConversData implements Serializable {
    // class release version:
    @Serial
    private static final long serialVersionUID = 1L;
    // areas with prompts:
    String personFullName;
    static final String P_personFullName = "Full name";
    String telNumber;
    static final String P_telNumber = "Telephone number";
    String date;
    static final String P_date = "Date";
    String tariff;
    static final String P_tariff = "Tariff";
    String sale;
    static final String P_sale = "Sale";
    String beginTime;
    static final String P_beginTime = "Conversation begin time";
    String endTime;
    static final String P_endTime = "Conversation end time";

    // validation methods:
    static Boolean validSale(String str) {
        double value = Double.parseDouble(str.substring(0, str.indexOf("%")));
        return value >= 0 && value <= 100;
    }

    private static final GregorianCalendar curCalendar = new GregorianCalendar();

    static Boolean validDate(String date) {
        String[] vals = date.split("/");
        int day = Integer.parseInt(vals[0]);
        int month = Integer.parseInt(vals[1]);
        int year = Integer.parseInt(vals[2]);
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();
        return year > 0 && year <= curCalendar.get(Calendar.YEAR) &&
                month > 0 && month <= 12 && day > 0 && day <= daysInMonth;
    }

    static Boolean validTime(String time) {
        String[] time_ = time.split(":");

        int hours = Integer.parseInt(time_[0]);
        int minutes = Integer.parseInt(time_[1]);
        return hours >= 0 && hours < 24 &&
                minutes >= 0 && minutes < 60;
    }

    public static boolean nextRead(Scanner fin, PrintStream out) {
        return nextRead(P_personFullName, fin, out);
    }

    static boolean nextRead(final String prompt, Scanner fin, PrintStream out) {
        out.print(prompt);
        out.print(": ");
        return fin.hasNextLine();
    }

    public static ConversData read(Scanner fin, PrintStream out) throws IOException,
            NumberFormatException {
        ConversData conversData = new ConversData();
        conversData.personFullName = fin.nextLine().trim();

        if (!nextRead(P_telNumber, fin, out)) {
            return null;
        }
        conversData.telNumber = fin.nextLine();

        if (!nextRead(P_date, fin, out)) {
            return null;
        }
        conversData.date = fin.nextLine();
        if (!ConversData.validDate(conversData.date)) {
            throw new IOException("Invalid ConversData.date value");
        }

        if (!nextRead(P_tariff, fin, out)) {
            return null;
        }
        conversData.tariff = fin.nextLine();

        if (!nextRead(P_sale, fin, out)) {
            return null;
        }
        conversData.sale = fin.nextLine();
        if (!ConversData.validSale(conversData.sale)) {
            throw new IOException("Invalid ConversData.sale value");
        }

        if (!nextRead(P_beginTime, fin, out)) {
            return null;
        }
        conversData.beginTime = fin.nextLine();
        if (!ConversData.validTime(conversData.beginTime)) {
            throw new IOException("Invalid ConversData.beginTime value");
        }

        if (!nextRead(P_endTime, fin, out)) {
            return null;
        }
        conversData.endTime = fin.nextLine();
        if (!ConversData.validTime(conversData.endTime)) {
            throw new IOException("Invalid ConversData.endTime value");
        }

        return conversData;
    }

    public ConversData() {
    }

    public static final String areaDel = "\n";

    public String toString() {
        return personFullName + areaDel +
                telNumber + areaDel +
                date + areaDel +
                tariff + areaDel +
                sale + areaDel +
                beginTime + areaDel +
                endTime;
    }
}
