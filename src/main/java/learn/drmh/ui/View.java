package learn.drmh.ui;

import learn.drmh.models.Host;
import learn.drmh.models.Reservation;

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
        return io.readLocalDate("Select a date [MM/dd/yyyy]: ");
    }

    public LocalDate getEndDate() {
        return io.readLocalDate("Select a date [MM/dd/yyyy]: ");
    }

    public void displaySummary() {

    }

    public void displayHost(Host host) {
        io.printf("%s: %s, %s%n",
                host.getLastName(),
                host.getCity(),
                host.getState());
    }

    public void displayReservations(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            return;
        }
        for (Reservation reservation : reservations) {
            io.printf("%s %s - %s:%s - Value: $%.2f%n",
                    reservation.getId(),
                    reservation.getStart(),
                    reservation.getEnd(),
                    reservation.getGuest(),
                    reservation.getTotal()
            );
        }
    }



}
