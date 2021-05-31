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

    public LocalDate getEditedStartDate() {
        return io.readEditedDate("Select Start date [MM/dd/yyyy]: ");
    }

    public LocalDate getEditedEndDate() {
        return io.readEditedDate("Select End date [MM/dd/yyyy]: ");
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
        io.printf("ID: %s%nStart Date: %s%nEnd Date: %s%nGuest: %s %s%nEmail: %s%n",
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
        System.out.println("Reservations");
        System.out.println("=".repeat(92));
        String rowFormat = "| %-2s |  %-10s | %-10s | %-10s | %-10s | %-30s | %n";
        System.out.printf(rowFormat, "ID", "Start Date", "End Date", "First Name", "Last Name", "Email");
        System.out.println("=".repeat(92));
        for (Reservation reservation : sortedReservations) {
            io.printf(rowFormat,
                    reservation.getId(),
                    reservation.getStart(),
                    reservation.getEnd(),
                    reservation.getGuest().getFirstName(),
                    reservation.getGuest().getLastName(),
                    reservation.getGuest().getEmail());
        }
        System.out.println("=".repeat(92));
    }

    public Reservation displaySummary(Reservation reservation, BigDecimal total) {
        String message = String.format("Summary");
        displayHeader(message);
        io.printf("Start: %s%nEnd: %s%nTotal: $%s%n",
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

    public void displayNoHostFound() {
        System.out.println();
        io.println("Host not found. ");
    }

    public void displayNoGuestFound() {
        System.out.println();
        io.println("Guest not found.");
    }

    public void displayReservationsNotFound() {
        System.out.println();
        io.println("No reservations found.");
    }

    public void displayTotalNotFound() {
        System.out.println();
        io.println("Total not found.");
    }

    public void displayReservationNotFound() {
        System.out.println();
        io.println("Reservation not found.");
    }

}
