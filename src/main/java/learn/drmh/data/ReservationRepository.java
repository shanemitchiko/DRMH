package learn.drmh.data;

import learn.drmh.models.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByHostId(String hostId);

    Reservation findById(int id, String hostId) throws DataException;

    Reservation add(Reservation reservation) throws DataException;

    boolean update(Reservation reservation) throws DataException;

    boolean deleteById(int id, String hostId) throws DataException;
}

