public class Tally {
    private double tally;
    private String date;

    public Tally(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void addTally(double tally) {
        this.tally += tally;
    }

    public double getTally() {
        return tally;
    }
    public void setTally(double tally) {
        this.tally = tally;
    }

}
