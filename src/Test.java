import java.util.TreeMap;

public class Test {
    private int date;
    private int year;
    private int mooth;

    public int getDate() {
        return date;
    }

    public int getYear() {
        return year;
    }

    public int getMooth() {
        return mooth;
    }

    @Override
    public String toString() {
        return "Test{" +
                "date=" + date +
                ", year=" + year +
                ", mooth=" + mooth +
                '}';
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMooth(int mooth) {
        this.mooth = mooth;
    }

    public static void main(String[] args) {

    }

    
}