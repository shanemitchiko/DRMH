package learn.drmh.domain;

import learn.drmh.data.DataException;
import learn.drmh.data.GuestRepositoryDouble;
import learn.drmh.data.HostRepositoryDouble;
import learn.drmh.data.ReservationRepositoryDouble;
import learn.drmh.models.Guest;
import learn.drmh.models.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static learn.drmh.data.ReservationRepositoryDouble.*;
import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    public static Guest CARNCROSS = GuestRepositoryDouble.CARNCROSS;

    private final static LocalDate start = LocalDate.of(2021, 12, 20);
    private final static LocalDate end = LocalDate.of(2021, 12, 29);
    private final static BigDecimal total = new BigDecimal(3000);

    ReservationService service = new ReservationService(
            new GuestRepositoryDouble(),
            new HostRepositoryDouble(),
            new ReservationRepositoryDouble());

    @Test
    void shouldAddRhodes_Carncross() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStart(LocalDate.of(2021, 8, 10));
        reservation.setEnd(LocalDate.of(2021, 8, 13));
        reservation.setGuest(CARNCROSS);
        reservation.setHost(RHODES);
        reservation.setTotal(new BigDecimal(1300));

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(3, result.getPayload().getId());
    }


    @Test
    void shouldAddWhenReservationStartsAtExistingEndDate() throws DataException {
        LocalDate startRes = LocalDate.of(2021,8,7);
        LocalDate endRes = LocalDate.of(2021,8,15);
        Reservation reservation = new Reservation(3,startRes, endRes, CARNCROSS, YEARNES, total);

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(3, result.getPayload().getId());

    }

    @Test
    void shouldAddWhenReservationEndsAtExistingStartDate() throws DataException {
        LocalDate startRes = LocalDate.of(2021,7,21);
        LocalDate endRes = LocalDate.of(2021,7,31);
        Reservation reservation = new Reservation(3,startRes, endRes, CARNCROSS, YEARNES, total);

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(3, result.getPayload().getId());
    }

    @Test
    void shouldUpdateYearnes_Lomas() throws DataException {
        Reservation reservation = service.findById(1, HostRepositoryDouble.YEARNES.getId());
        reservation.setStart(start);
        reservation.setEnd(end);
        reservation.setTotal(total);
        Result<Reservation> result = service.update(reservation);
        assertTrue(result.isSuccess());
        assertEquals(LocalDate.of(2021, 12, 20), result.getPayload().getStart());
        assertEquals(LocalDate.of(2021, 12, 29), result.getPayload().getEnd());
    }


    @Test
    void shouldNotAddNullReservation() throws DataException {
        Reservation reservation = new Reservation(5, null, null, CARNCROSS, YEARNES, total);
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddReservationInThePast() throws DataException {
        LocalDate startRes = LocalDate.of(2020,7,21);
        LocalDate endRes = LocalDate.of(2020,7,31);

        Reservation reservation = new Reservation(3,startRes, endRes, CARNCROSS, YEARNES, total);
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotMakeReservationWithStartDateAfterEndDate() throws DataException {
        LocalDate startRes = LocalDate.of(2021,7,31);
        LocalDate endRes = LocalDate.of(2021,7,21);

        Reservation reservation = new Reservation(3,startRes, endRes, CARNCROSS, YEARNES, total);
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotMakeReservationWithStartDateBeforeExistingEndDate() throws DataException {
        LocalDate startRes = LocalDate.of(2021,8,2);
        LocalDate endRes = LocalDate.of(2021,8,12);

        Reservation reservation = new Reservation(3,startRes, endRes, CARNCROSS, YEARNES, total);
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotMakeReservationWithEndDateAfterExistingStartDate() throws DataException {
        LocalDate startRes = LocalDate.of(2021,7,26);
        LocalDate endRes = LocalDate.of(2021,8,6);

        Reservation reservation = new Reservation(3,startRes, endRes, CARNCROSS, YEARNES, total);
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotMakeReservationThatOverlapsWithExistingReservation() throws DataException {
        LocalDate startRes = LocalDate.of(2021,7,26);
        LocalDate endRes = LocalDate.of(2021,8,12);

        Reservation reservation = new Reservation(3,startRes, endRes, CARNCROSS, YEARNES, total);
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
    }

    //negative updates

    @Test
    void shouldNotUpdateReservationInThePast() throws DataException {
        LocalDate startRes = LocalDate.of(2020,7,21);
        LocalDate endRes = LocalDate.of(2020,7,31);

        Reservation reservation = new Reservation(3,startRes, endRes, CARNCROSS, YEARNES, total);
        Result<Reservation> result = service.update(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateNullReservation() throws DataException {
        Reservation reservation = new Reservation(5, null, null, CARNCROSS, YEARNES, total);
        Result<Reservation> result = service.update(reservation);
        assertFalse(result.isSuccess());
        System.out.println("Cannot update null Reservation");
    }


    @Test
    void shouldNotUpdateReservationWithStartDateAfterEndDate() throws DataException {
        LocalDate startRes = LocalDate.of(2021,7,31);
        LocalDate endRes = LocalDate.of(2021,7,21);

        Reservation reservation = new Reservation(3,startRes, endRes, CARNCROSS, YEARNES, total);
        Result<Reservation> result = service.update(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateReservationWithStartDateBeforeExistingEndDate() throws DataException {
        LocalDate startRes = LocalDate.of(2021,8,2);
        LocalDate endRes = LocalDate.of(2021,8,12);

        Reservation reservation = new Reservation(3,startRes, endRes, CARNCROSS, YEARNES, total);
        Result<Reservation> result = service.update(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateReservationWithEndDateAfterExistingStartDate() throws DataException {
        LocalDate startRes = LocalDate.of(2021,7,26);
        LocalDate endRes = LocalDate.of(2021,8,6);

        Reservation reservation = new Reservation(3,startRes, endRes, CARNCROSS, YEARNES, total);
        Result<Reservation> result = service.update(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateReservationThatOverlapsWithExistingReservation() throws DataException {
        LocalDate startRes = LocalDate.of(2021,7,26);
        LocalDate endRes = LocalDate.of(2021,8,12);

        Reservation reservation = new Reservation(3,startRes, endRes, CARNCROSS, YEARNES, total);
        Result<Reservation> result = service.update(reservation);
        assertFalse(result.isSuccess());
    }

    //negative delete

    @Test
    void shouldNotDeleteNotExistingReservation() throws DataException {
        Reservation reservation = new Reservation(5,start, end, CARNCROSS, YEARNES, total);
        Result<Reservation> result = service.update(reservation);
        assertFalse(result.isSuccess());

    }

    @Test
    void shouldCalculateTotalOfStay()  {
        Reservation reservation = new Reservation();
        reservation.setStart(LocalDate.of(2021, 8, 10));
        reservation.setEnd(LocalDate.of(2021, 8, 13));
        reservation.setGuest(CARNCROSS);
        reservation.setHost(RHODES);
        reservation.setTotal(service.calculateTotal(reservation, RHODES));

        assertEquals(BigDecimal.valueOf(1253.75), reservation.getTotal());
    }





}