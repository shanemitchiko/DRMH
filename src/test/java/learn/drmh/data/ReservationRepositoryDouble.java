package learn.drmh.data;

import learn.drmh.models.Guest;
import learn.drmh.models.Host;
import learn.drmh.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class ReservationRepositoryDouble implements ReservationRepository {



    public static Host YEARNES = HostRepositoryDouble.YEARNES;
    public static Host RHODES = HostRepositoryDouble.RHODES;
    public static Guest LOMAS = GuestRepositoryDouble.LOMAS;
    public static Guest GECKS = GuestRepositoryDouble.GECKS;


    private ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() {
        reservations.add(makeYearnesLomas());
        reservations.add(makeYearnesGecks());
        reservations.add(makeRhodesLomas());
        reservations.add(makeRhodesGecks());
    }

    @Override
    public List<Reservation> findByHostId(String hostId) {
        return reservations.stream()
                .filter(i -> i.getHost().getId().equals(hostId))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation findById(int id, String hostId) {
        return findByHostId(hostId).stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Reservation add(Reservation reservation) {
        List<Reservation> all = findByHostId(reservation.getHost().getId());

        int nextId = all.stream()
                .mapToInt(Reservation::getId)
                .max()
                .orElse(0) + 1;

        reservation.setId(nextId);

        reservations.add(reservation);
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostId(reservation.getHost().getId());
        for (int i = 0; i < all.size(); i++) {
            if (reservation.getId() == all.get(i).getId()) {
                reservations.set(i, reservation);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int id, String hostId) throws DataException {
        List<Reservation> all = findByHostId(hostId);
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == id) {
                reservations.remove(i);
                return true;
            }
        }
        return false;
    }

    private static Reservation makeYearnesLomas() {
        LocalDate start = LocalDate.of(2021, 7, 31);
        LocalDate end = LocalDate.of(2021, 8, 7);
        return new Reservation(1, start, end, LOMAS, YEARNES, new BigDecimal(2550));
    }

    private static Reservation makeYearnesGecks() {
        LocalDate start = LocalDate.of(2021, 3, 23);
        LocalDate end = LocalDate.of(2021, 3, 26);
        return new Reservation(2, start, end, GECKS, YEARNES, new BigDecimal(1020));
    }

    private static Reservation makeRhodesLomas() {
        LocalDate start = LocalDate.of(2021, 4, 23);
        LocalDate end = LocalDate.of(2021, 4, 26);
        return new Reservation(1, start, end, LOMAS, RHODES, new BigDecimal(1190));
    }

    private static Reservation makeRhodesGecks() {
        LocalDate start = LocalDate.of(2021, 3, 17);
        LocalDate end = LocalDate.of(2021, 3, 22);
        return new Reservation(2, start, end, GECKS, RHODES, new BigDecimal(1870));
    }


}
