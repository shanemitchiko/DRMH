package learn.drmh.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private int id;
    private LocalDate start;
    private LocalDate end;
    private Guest guest;
    private Host host;
    private BigDecimal total;

    public Reservation() {
    }

    public Reservation(int id, LocalDate start, LocalDate end, Guest guest, Host host, BigDecimal total) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.guest = guest;
        this.host = host;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && start.equals(that.start) && end.equals(that.end) && guest.equals(that.guest) && host.equals(that.host) && total.equals(that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end, guest, host, total);
    }
}
