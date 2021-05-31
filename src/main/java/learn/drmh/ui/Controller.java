package learn.drmh.ui;

import learn.drmh.data.DataException;
import learn.drmh.domain.GuestService;
import learn.drmh.domain.HostService;
import learn.drmh.domain.ReservationService;
import learn.drmh.domain.Result;
import learn.drmh.models.Guest;
import learn.drmh.models.Host;
import learn.drmh.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Controller {

    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(GuestService guestService, HostService hostService, ReservationService reservationService, View view) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }


    public void run() {
        view.displayHeader("Welcome to Don't Wreck My House");
        try {
            runAppLoop();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runAppLoop() throws DataException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS_FOR_HOST:
                    view();
                    break;
                case MAKE_RESERVATION:
                    make();
                    break;
                case EDIT_RESERVATION:
                    edit();
                    break;
                case CANCEL_RESERVATION:
                    cancel();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

    private void view() {
        view.displayHeader(MainMenuOption.VIEW_RESERVATIONS_FOR_HOST.getMessage());
        String email = view.getHostEmail();
        Host host = hostService.findByEmail(email);
        if (host != null) {
            List<Reservation> reservations = reservationService.findByHostId(host.getId());
            if (reservations != null) {
                List<Reservation> allReservations = reservationService.findAllReservations(reservations);
                view.displayHost(host);
                view.displayReservations(allReservations);
            } else view.displayReservationsNotFound();
        } else view.displayNoHostFound();
    }

    private void make() throws DataException {
        view.displayHeader(MainMenuOption.MAKE_RESERVATION.getMessage());
        String guestEmail = view.getGuestEmail();
        Guest guest = guestService.findByEmail(guestEmail);
        if(guest != null) {
            String hostEmail = view.getHostEmail();
            Host host = hostService.findByEmail(hostEmail);
            if (host != null) {
                view.displayHost(host);
                List<Reservation> reservations = reservationService.findByHostId(host.getId());
                if (reservations != null) {
                    List<Reservation> sortedReservations = reservationService.sortReservations(reservations);
                    view.displayReservations(sortedReservations);
                    LocalDate start = view.getStartDate();
                    LocalDate end = view.getEndDate();
                    Reservation reservation = new Reservation(start, end, guest, host);
                    BigDecimal total = reservationService.calculateTotal(reservation, host);
                    if (total != null) {
                        view.displaySummary(reservation, total);
                        reservation.setTotal(total);
                        Result<Reservation> result = reservationService.add(reservation);
                        if(result.isSuccess()) {
                            view.displayHeader("Reservation " + reservation.getId() + " created");
                        } else {
                            List<String> messages = result.getErrorMessages();
                            for(String s : messages) {
                                System.out.println(s);
                            }
                        }
                    } else view.displayTotalNotFound();
                } else view.displayReservationsNotFound();
            } else view.displayNoHostFound();
        } else view.displayNoGuestFound();
    }

    private void edit() throws DataException {
        view.displayHeader(MainMenuOption.EDIT_RESERVATION.getMessage());
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        if (host != null) {
            view.displayHost(host);
            List<Reservation> reservations = reservationService.findByHostId(host.getId());
            if(reservations != null && !reservations.isEmpty()) {
                List<Reservation> sortedReservations = reservationService.sortReservations(reservations);
                Reservation reservation = view.chooseReservation(sortedReservations);
                if (reservation != null) {
                    view.editReservation(reservation);
                    BigDecimal total = reservationService.calculateTotal(reservation, host);
                    if (total != null) {
                        view.displaySummary(reservation, total);
                        reservation.setTotal(total);
                        Result<Reservation> result = reservationService.update(reservation);
                        if (result.isSuccess()) {
                            view.displayHeader("Success");
                            System.out.println("Reservation " + reservation.getId() + " updated");
                        } else {
                            List<String> messages = result.getErrorMessages();
                            for (String s : messages) {
                                System.out.println(s);
                            }
                        }
                    } else view.displayTotalNotFound();
                } else view.displayReservationNotFound();
            } else view.displayReservationsNotFound();
        } else view.displayNoHostFound();
    }

    //g- slomas0@mediafire.com
    //h- eyearnes0@sfgate.com

    private void cancel() throws DataException {
        view.displayHeader(MainMenuOption.CANCEL_RESERVATION.getMessage());
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        if (host != null) {
            view.displayHost(host);
            List<Reservation> reservations = reservationService.findByHostId(host.getId());
            if (reservations != null) {
                List<Reservation> sortedReservations = reservationService.sortReservations(reservations);
                Reservation reservation = view.chooseReservation(sortedReservations);
                if (reservation != null) {
                    Result<Reservation> result = reservationService.delete(reservation);
                    if(result.isSuccess()) {
                        view.displayHeader("Success");
                        System.out.println("Reservation " + reservation.getId() + " cancelled");
                    } else {
                        List<String> messages = result.getErrorMessages();
                        for(String s : messages) {
                            System.out.println(s);
                        }
                    }
                } else view.displayReservationNotFound();
            } else view.displayReservationsNotFound();
        } else view.displayNoHostFound();
    }


}
