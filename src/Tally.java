public class Tally {
    private float tally;
    private String date;

    public Tally(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void addTally(float tally) {
        this.tally += tally;
    }

    public float getTally() {
        return tally;
    }
    public void setTally(float tally) {
        this.tally = tally;
    }

}
