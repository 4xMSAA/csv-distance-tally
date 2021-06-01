import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.Set;
import java.util.TreeSet;

public class App {
    private static final String SPLIT_TOKEN = ",";

    private static final String CSV_PATH = "./data_files/";

    public static void main(String[] args) throws Exception {
        Set<String> paths = new TreeSet<>(Collator.getInstance());
        Tally tally = null;

        Files.walk(Path.of(CSV_PATH)).forEach(path -> {
            if (!Files.isDirectory(path))
                paths.add(path.toString());
        });

        for (String pathString : paths) {
            try {

                BufferedReader br = Files.newBufferedReader(Path.of(pathString));
                String line;

                while ((line = br.readLine()) != null) {

                    String[] contents = line.split(SPLIT_TOKEN);
                    String date = contents[1];
                    float kmph = Float.parseFloat(contents[contents.length - 1]);

                    if (tally == null) {
                        tally = new Tally(date);
                    } else if (!tally.getDate().equals(date)) {
                        exportResults(tally);
                        tally = new Tally(date);
                    }

                    tally.addTally(kmph);
                }

            } catch (IOException e) {
                System.err.println("Faili " + pathString + " lugemisel tekkis viga");
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Fail " + pathString + " on vigane - reas peaks olema 11 veergu");
            }
        }
        exportResults(tally);
    }

    public static void exportResults(Tally tally) {
        System.out.println("======== ANALÜÜS =========");
        System.out.println("Kuupäev: " + tally.getDate());
        System.out.println("Distants läbitud: " + tally.getTally() + "km");
    }
}
