package learn.drmh.domain;

import learn.drmh.data.GuestRepositoryDouble;
import learn.drmh.data.HostRepositoryDouble;
import learn.drmh.data.ReservationRepositoryDouble;
import learn.drmh.models.Reservation;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service = new ReservationService(
            new ReservationRepositoryDouble(),
            new HostRepositoryDouble(),
            new GuestRepositoryDouble());
    )


}