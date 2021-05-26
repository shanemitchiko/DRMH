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
        reservation.setStart(LocalDate.parse("2021-05-20"));
        reservation.setEnd(LocalDate.parse("2021-05-24"));
        reservation.setTotal(new BigDecimal("3000"));

        Guest guest = new Guest();
        guest.setId(200);
        reservation.setGuest(guest);

        Host host = new Host();
        host.setId(hostId);
        reservation.setHost(host);

        reservation = repository.add(reservation);

        assertEquals(14, reservation.getId());
    }

//    @Test
//    void shouldUpdate() throws DataException {
//        Reservation reservation = repository.findByHostId(hostId);
//        reservation.setStart(LocalDate.parse("2020-04-20"));
//        reservation.setEnd(LocalDate.parse("2020-04-24"));
//        reservation.setTotal(new BigDecimal("2500"));
//        assertTrue(repository.update(reservation));
//    }



}