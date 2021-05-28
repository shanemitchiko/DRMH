package learn.drmh.data;

import learn.drmh.models.Guest;
import learn.drmh.models.Host;
import learn.drmh.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/reservations-seed-3edda6bc-ab95-49a8-8962.csv";
    static final String TEST_FILE_PATH = "./data/reservations_test/3edda6bc-ab95-49a8-8962.csv";
    static final String TEST_DIR_PATH = "./data/reservations_test";
    static final int RESERVATION_COUNT = 13;

    final String hostId = "3edda6bc-ab95-49a8-8962";
    final int guestId = 230;
    final LocalDate start = LocalDate.of(2021, 06, 21);
    final LocalDate end = LocalDate.of(2021, 06, 28);
    final BigDecimal total = BigDecimal.valueOf(3500);


    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindByHostId() {
        List<Reservation> reservations = repository.findByHostId(hostId);
        assertEquals(RESERVATION_COUNT, reservations.size());
    }

    @Test
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(14);
        reservation.setStart(LocalDate.of(2021,6,20));
        reservation.setEnd(LocalDate.of(2021, 6, 24));
        reservation.setTotal(BigDecimal.valueOf(3500));

        Guest guest = new Guest();
        guest.setId(200);
        reservation.setGuest(guest);

        Host host = new Host();
        host.setId(hostId);
        reservation.setHost(host);

        reservation = repository.add(reservation);

        assertEquals(14, reservation.getId());
    }

    @Test
    void shouldUpdateExistingTwelve() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(12);
        reservation.setStart(start);
        reservation.setEnd(end);
        reservation.setTotal(total);

        Guest guest = new Guest();
        guest.setId(200);
        reservation.setGuest(guest);

        Host host = new Host();
        host.setId(hostId);
        reservation.setHost(host);

        boolean success = repository.update(reservation);
        assertTrue(success);

        Reservation actual = repository.findById(12, hostId);
        assertNotNull(actual);
        assertEquals(start, actual.getStart());
        assertEquals(end, actual.getEnd());
        assertEquals(total, actual.getTotal());
    }

    @Test
    void shouldNotUpdateExisting() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(10000);
        reservation.setStart(start);
        reservation.setEnd(end);
        reservation.setTotal(total);

        Guest guest = new Guest();
        guest.setId(200);
        reservation.setGuest(guest);

        Host host = new Host();
        host.setId(hostId);
        reservation.setHost(host);

        boolean actual = repository.update(reservation);
        assertFalse(actual);

    }


    @Test
    void shouldNotUpdateTwelveByMissingHostId() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(12);
        reservation.setStart(start);
        reservation.setEnd(end);
        reservation.setTotal(total);

        Guest guest = new Guest();
        guest.setId(200);
        reservation.setGuest(guest);

        Host host = new Host();
        host.setId("1jd9dhl0-kdnjw8-1298hsd.csv");
        reservation.setHost(host);

        boolean actual = repository.update(reservation);
        assertFalse(actual);
    }

    @Test
    void shouldNotUpdateMissingByMissingHostId() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(12);
        reservation.setStart(start);
        reservation.setEnd(end);
        reservation.setTotal(total);

        Guest guest = new Guest();
        guest.setId(200);
        reservation.setGuest(guest);

        Host host = new Host();
        host.setId("1jd9dhl0-kdnjw8-1298hsd.csv");
        reservation.setHost(host);

        boolean actual = repository.update(reservation);
        assertFalse(actual);
    }

    @Test
    void shouldDeleteExistingTwelve() throws DataException {
        boolean actual = repository.deleteById(12,hostId);
        assertTrue(actual);

        Reservation r = repository.findById(12, hostId);
        assertNull(r);
    }

    @Test
    void shouldNotDeleteNotExisting() throws DataException {
        boolean actual = repository.deleteById(1000, hostId);
        assertFalse(actual);
    }

    @Test
    void shouldNotDeleteTwelveByMissingHostId() throws DataException {
        boolean actual = repository.deleteById(12, "1jd9dhl0-kdnjw8-1298hsd.csv");
        assertFalse(actual);
    }

    @Test
    void ShouldNotDeleteNotExistingByMissingHostId() throws DataException {
        boolean actual = repository.deleteById(10000, "1jd9dhl0-kdnjw8-1298hsd.csv");
        assertFalse(actual);
    }



}