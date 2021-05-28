package learn.drmh.data;

import learn.drmh.models.Guest;
import learn.drmh.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ReservationRepositoryDouble implements ReservationRepository {

    private ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() {
        Reservation reservation1 = new Reservation();
        reservation1.setId(1);
        reservation1.setStart(LocalDate.of(2021,7,31));
        reservation1.setEnd(LocalDate.of(2021,8,7));
        reservation1.setGuest(GuestRepositoryDouble.GUEST);
        reservation1.setTotal(BigDecimal.valueOf(2550));
        reservations.add(reservation1);
    }

    @Override
    public List<Reservation> findByHostId(String hostId) {
        return reservations.stream()
                .filter(i -> i.getHost().getId().equals(hostId))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation add(Reservation reservation) {
        List<Reservation> all = findByHostId(reservation.getHost().getId());

        int nextId = all.stream()
                .mapToInt(Reservation::getId)
                .max()
                .orElse(0) + 1;

        reservation.setId(nextId);

        all.add(reservation);
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        return true;
    }

    @Override
    public boolean deleteById(int id, String hostId) {
        return true;
    }
}
