package learn.drmh.domain;

import learn.drmh.data.DataException;
import learn.drmh.data.GuestRepository;
import learn.drmh.data.HostRepository;
import learn.drmh.data.ReservationRepository;
import learn.drmh.models.Guest;
import learn.drmh.models.Host;
import learn.drmh.models.Reservation;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationService {

    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(GuestRepository guestRepository, HostRepository hostRepository, ReservationRepository reservationRepository) {
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findByHostId(String hostId) {
        Map<String, Host> hostMap = hostRepository.findAll().stream()
                .collect(Collectors.toMap(h -> h.getId(), h -> h));
        Map<Integer, Guest> guestMap = guestRepository.findAll().stream()
                .collect(Collectors.toMap(g -> g.getId(), g -> g));

        List<Reservation> result = reservationRepository.findByHostId(hostId);
        for (Reservation reservation : result) {
            reservation.setHost(hostMap.get(reservation.getHost().getId()));
            reservation.setGuest(guestMap.get(reservation.getGuest().getId()));
        }
        return result;
    }

    public Result<Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(reservationRepository.add(reservation));

        return result;
    }

    public BigDecimal calculateTotal(Reservation reservation, Host host) {
        BigDecimal total = BigDecimal.ZERO;
        LocalDate index = reservation.getStart();

        List<LocalDate> stay = new ArrayList<>();
        int days = Period.between(reservation.getStart(),reservation.getEnd()).getDays();


        for(int count = 0; count < days; count++, index = index.plusDays(1)) {
            stay.add(index);
        }

        return stay.stream()
                .map(localDate -> {
                    if (localDate.getDayOfWeek() == DayOfWeek.FRIDAY ||
                            localDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
                        return host.getWeekend_rate();
                    } else {
                        return host.getStandard_rate();
                    }
                }
                )
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public Result<Reservation> update(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if(!result.isSuccess()) {
            return result;
        }

        if(!reservationRepository.update(reservation)) {
            result.addErrorMessage("Reservation" + reservation.getId() + "was not updated.");
        }
        return result;
    }

    public Result<Reservation> delete(Reservation reservation) throws DataException {
        Result<Reservation> result = new Result<>();

        if(!reservationRepository.deleteById(reservation.getId(), reservation.getHost().getId())) {
            result.addErrorMessage("Reservation" + reservation.getId() + "was not successfully cancelled");
        }

        return result;
    }

    private Result<Reservation> validate(Reservation reservation) {

        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        validateChildrenExist(reservation, result);
        if (!result.isSuccess()) {
            return result;

        }

        validateFields(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }

        return result;
    }

    private Result<Reservation> validateNulls(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        if (reservation == null) {
            result.addErrorMessage("Nothing to save");
            return result;
        }

        if (reservation.getStart() == null) {
            result.addErrorMessage("Start date is required");
        }

        if (reservation.getEnd() == null) {
            result.addErrorMessage("End date is required");
        }

        if (reservation.getGuest() == null) {
            result.addErrorMessage("Guest is required");
        }

        if (reservation.getTotal() == null) {
            result.addErrorMessage("Total sum is required");
        }

        return result;
    }

    private void validateChildrenExist(Reservation reservation, Result<Reservation> result) {

        if (reservation.getHost().getId() == null
                || hostRepository.findById(reservation.getHost().getId()) == null) {
            result.addErrorMessage("Host does not exist.");
        }
        if (reservation.getGuest().getId() == 0
                || guestRepository.findById(reservation.getGuest().getId()) == null) {
            result.addErrorMessage("Guest does not exist.");
        }
    }

    private void validateFields(Reservation reservation, Result<Reservation> result) {
        if (reservation.getStart().isBefore(LocalDate.now())) {
            result.addErrorMessage("Reservation date cannot be in the past.");
        }

        if (reservation.getStart().isAfter(reservation.getEnd())) {
            result.addErrorMessage("Start date of reservation must be before the end date.");
        }

        List<Reservation> reservations = findByHostId(reservation.getHost().getId());
        LocalDate startDate = reservation.getStart();
        LocalDate endDate = reservation.getEnd();

        if (reservations != null && !reservations.isEmpty()) {
            for (Reservation r : reservations) {
                if (r.getId() == reservation.getId()) {
                    continue;
                }
                LocalDate rStartDate = r.getStart();
                LocalDate rEndDate = r.getEnd();
                if ((rStartDate.isAfter(startDate) && rStartDate.isBefore(endDate))
                        || (rEndDate.isAfter(startDate) && rEndDate.isBefore(endDate))
                        || (startDate.isAfter(rStartDate) && startDate.isBefore(rEndDate))
                        || (endDate.isAfter(rStartDate) && endDate.isBefore(rEndDate))) {
                    result.addErrorMessage("Reservation dates overlap with an existing reservation");
                    break;
                }
            }

        }


//    private void validateDuplicate(Reservation reservation, Result<Reservation> result) {
//        List<Reservation> reservations = repository.findAll();
//        for (Reservation r : reservations) {
//            if (guest.getFirstName().equals(g.getFirstName())){
//                if (guest.getLastName().equals(g.getLastName())) {
//                    if (guest.getEmail().equals(g.getEmail())) {
//                        if (guest.getPhoneNum().equals(g.getPhoneNum())) {
//                            if (guest.getState().equals(g.getState())) {
//                                result.addErrorMessage("Duplicate Guest is not allowed");
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }


    }
}
