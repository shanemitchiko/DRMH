package learn.drmh.data;

import learn.drmh.models.Guest;
import learn.drmh.models.Host;
import learn.drmh.models.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.accessibility.AccessibleRole.HEADER;

public class ReservationFileRepository implements ReservationRepository {


    private final String directory;

    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    public List<Reservation> findByHostId(String hostId) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(hostId)))) {

            reader.readLine(); // read header - throws it

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields, hostId));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    public Reservation findById(int id, String hostId) {
       return findByHostId(hostId).stream()
                .filter(i -> i.getId() == id)
               .findFirst()
               .orElse(null);

    }

    public Reservation add(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostId(reservation.getHost().getId());

        int nextId = all.stream()
                .mapToInt(Reservation::getId)
                .max()
                .orElse(0) + 1;

        reservation.setId(nextId);

        all.add(reservation);
        writeAll(all, reservation.getHost().getId());
        return reservation;
    }

    public boolean update(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostId(reservation.getHost().getId());
        for(int i = 0; i < all.size(); i++) {
            if(reservation.getId() == all.get(i).getId()) {
                all.set(i, reservation);
                writeAll(all, reservation.getHost().getId());
                return true;
            }
        }
        return false;
    }

    public boolean deleteById(int id, String hostId) throws DataException {
       List<Reservation> all = findByHostId(hostId);
       for (int i = 0; i < all.size(); i++) {
           if(all.get(i).getId() == id) {
               all.remove(i);
               writeAll(all, hostId);
               return true;
           }
       }
       return false;
    }


    protected void writeAll(List<Reservation> reservations, String hostId) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(hostId))) {

            writer.println(HEADER);

            for (Reservation booking : reservations) {
                writer.println(serialize(booking));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    private String getFilePath(String hostId) {
        return Paths.get(directory, hostId + ".csv").toString();
    }

    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getId(),
                reservation.getStart(),
                reservation.getEnd(),
                reservation.getGuest().getId(),
                reservation.getTotal());
    }

    private Reservation deserialize(String[] fields, String hostId) {
        Reservation result = new Reservation();
        result.setId(Integer.parseInt(fields[0]));
        result.setStart(LocalDate.parse(fields[1]));
        result.setEnd(LocalDate.parse(fields[2]));
        result.setTotal(new BigDecimal(fields[4]));

        Guest guest = new Guest();
        guest.setId(Integer.parseInt(fields[3]));
        result.setGuest(guest);

        Host host = new Host();
        host.setId(hostId);
        result.setHost(host);
        return result;
    }

}
