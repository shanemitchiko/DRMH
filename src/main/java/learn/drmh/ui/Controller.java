package learn.drmh.ui;

import learn.drmh.data.DataException;
import learn.drmh.domain.GuestService;
import learn.drmh.domain.HostService;
import learn.drmh.domain.ReservationService;
import learn.drmh.models.Guest;
import learn.drmh.models.Host;
import learn.drmh.models.Reservation;

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
        view.displayHeader("Welcome to Sustainable Foraging");
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
                    viewReservationsForHost();
                    break;
                case MAKE_RESERVATION:
                    makeReservation();
                    break;
                case EDIT_RESERVATION:
                    break;
                case CANCEL_RESERVATION:
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

    private void viewReservationsForHost() {
        view.displayHeader(MainMenuOption.VIEW_RESERVATIONS_FOR_HOST.getMessage());
        String email = view.getHostEmail();
        Host host = hostService.findByEmail(email);
        List<Reservation> reservations = reservationService.findByHostId(host.getId());
        view.displayHost(host);
        view.displayReservations(reservations);
    }

    private void makeReservation() {
        view.displayHeader(MainMenuOption.MAKE_RESERVATION.getMessage());
        String guestEmail = view.getGuestEmail();
        Guest guest = guestService.findByEmail(guestEmail);
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        view.displayHost(host);
        List<Reservation> reservations = reservationService.findByHostId(host.getId());
        view.displayReservations(reservations);



    }





}
