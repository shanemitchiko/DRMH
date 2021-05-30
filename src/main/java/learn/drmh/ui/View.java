package learn.drmh.ui;

import learn.drmh.models.Host;
import learn.drmh.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class View {

    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            if (!option.isHidden()) {
                io.printf("%s. %s%n", option.getValue(), option.getMessage());
            }
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }

    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }

    public String getHostEmail() {
        return io.readRequiredString("Host email: ");
    }

    public String getGuestEmail() {
        return io.readRequiredString("Guest email: ");
    }

    public LocalDate getStartDate() {
        return io.readLocalDate("Select Start date [MM/dd/yyyy]: ");
    }

    public LocalDate getEndDate() {
        return io.readLocalDate("Select End date [MM/dd/yyyy]: ");
    }

    public LocalDate getEditedStartDate()  {
        return io.readEditedDate("Select Start date [MM/dd/yyyy]: ");
    }

    public LocalDate getEditedEndDate()  {
        return io.readEditedDate("Select End date [MM/dd/yyyy]: ");
    }





    public void displayHost(Host host) {
        String message = String.format("%s: %s, %s",
                host.getLastName(),
                host.getCity(),
                host.getState());
        displayHeader(message);
    }

    public void displayReservations(List<Reservation> sortedReservations) {
        if (sortedReservations == null || sortedReservations.isEmpty()) {
            io.println("No reservations found.");
            return;
        }
        for (Reservation reservation : sortedReservations) {
            io.printf("ID: %s,  %s - %s, Guest: %s, %s, Email: %s%n ",
                    reservation.getId(),
                    reservation.getStart(),
                    reservation.getEnd(),
                    reservation.getGuest().getFirstName(),
                    reservation.getGuest().getLastName(),
                    reservation.getGuest().getEmail());
        }
    }


    public Reservation displaySummary(Reservation reservation, BigDecimal total) {
        String message = String.format("Summary");
        displayHeader(message);
        io.printf("Start: %s%n End: %s%n Total: $%s%n",
                reservation.getStart(),
                reservation.getEnd(),
                total);
        io.readBoolean("Is this okay? [y/n]: ");
        if (false) {
            System.out.println("Reservation cancelled");
            enterToContinue();
        }
        return reservation;
    }

    public Reservation chooseReservation(List<Reservation> reservations) {
        displayReservations(reservations);
        Reservation result = null;
        if (reservations.size() > 0) {
            int id = io.readInt("Choose Reservation ID: ");
            for (Reservation r : reservations) {
                if (r.getId() == id) {
                    result = r;
                    break;
                }
            }
        }
        return result;
    }

    public void findReservation(Reservation reservation) {
        io.printf("ID: %s,  %s - %s, Guest: %s, %s, Email: %s%n ",
                reservation.getId(),
                reservation.getStart(),
                reservation.getEnd(),
                reservation.getGuest().getFirstName(),
                reservation.getGuest().getLastName(),
                reservation.getGuest().getEmail());
    }

    public Reservation editReservation(Reservation reservation) {
        displayHeader("Editing Reservation " + reservation.getId());
        findReservation(reservation);
        LocalDate startDate = getEditedStartDate();
        if (startDate == null) startDate = reservation.getStart();
        LocalDate endDate = getEditedEndDate();
        if (endDate == null) endDate = reservation.getEnd();
        reservation.setStart(startDate);
        reservation.setEnd(endDate);
        return reservation;
    }

}
