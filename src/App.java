import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

        String lastKnownPathString = "";
        for (String pathString : paths) {
            try {

                BufferedReader br = Files.newBufferedReader(Path.of(pathString));
                String line;
                PolarCoordinates lastCoords = null;

                while ((line = br.readLine()) != null) {

                    String[] contents = line.split(SPLIT_TOKEN);
                    String date = contents[1];

                    if (tally == null) {
                        tally = new Tally(date);
                    } else if (!tally.getDate().equals(date)) {
                        exportResults(lastKnownPathString, tally);
                        tally = new Tally(date);
                    }

                    PolarCoordinates coords = new PolarCoordinates(Double.parseDouble(contents[5]), Double.parseDouble(contents[7]));

                    double km = lastCoords != null ? calcDistanceFromPolarCoords(lastCoords, coords) : 0;
                    tally.addTally(km);

                    lastCoords = coords;
                }


            } catch (IOException e) {
                System.err.println("Faili " + pathString + " lugemisel tekkis viga");
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Fail " + pathString + " on vigane - reas peaks olema 11 veergu");
            }
            lastKnownPathString = pathString;
        }
        exportResults(lastKnownPathString, tally);

    }

    // will most likely fail in extreme cases where one switches from N to S or E to W and vice versa
    public static double calcDistanceFromPolarCoords(PolarCoordinates polarCoords1, PolarCoordinates polarCoords2) {
        double earthRadiusKm = 6371;

        double deltaLat = Math.toRadians(polarCoords2.getLatitude() - polarCoords1.getLatitude());
        double deltaLon = Math.toRadians(polarCoords2.getLongitude() - polarCoords1.getLongitude());

        double lat1 = polarCoords1.getLatitude();
        double lat2 = polarCoords2.getLatitude();

        double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) + Math.sin(deltaLon/2) * Math.sin(deltaLon/2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadiusKm * c;
    }

    public static void exportResults(String fileName, Tally tally) {
        System.out.println("======== ANALÜÜS =========");
        System.out.println("Fail: " + fileName);
        System.out.println("Kuupäev: " + tally.getDate());
        System.out.println("Distants läbitud: " + tally.getTally() + "km");
    }
}
