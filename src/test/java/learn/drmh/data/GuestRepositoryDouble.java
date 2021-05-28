package learn.drmh.data;

import learn.drmh.models.Guest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {

    public final static Guest LOMAS = makeLomas();
    public final static Guest GECKS = makeGecks();

    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble() {
        guests.add(new Guest(1, "Sullivan", "Lomas", "slomas0@mediafire.com", "(702) 7768761", "NV"));
        guests.add(new Guest(2, "Olympie", "Gecks", "ogecks1@dagondesign.com", "(202) 2528316", "DC"));
        guests.add(new Guest(3, "Tremain", "Carncross", "tcarncross2@japanpost.jp", "(313) 2245034", "MI"));
        guests.add(new Guest(4, "Leonidas", "Gueny", "lgueny3@example.com", "(412) 6493981", "PA"));
        guests.add(new Guest(5, "Berta", "Seppey", "bseppey4@yahoo.com", "(202) 2668098", "DC"));
        guests.add(new Guest(6, "Kenn", "Curson", "kcurson5@youku.com", "(941) 9618942", "FL"));
    }

    @Override
    public List<Guest> findAll() {
        return guests;
    }

    @Override
    public Guest findByEmail(String email) {
        return findAll().stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest findById(int id) {
        return findAll().stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private static Guest makeLomas()  {
        Guest guest = new Guest();
        guest.setId(2);
        guest.setFirstName("Sullivan");
        guest.setLastName("Lomas");
        guest.setEmail("slomas0@mediafire.com");
        guest.setPhoneNum("(702) 7768761");
        guest.setState("NV");
        return guest;
    }

    private static Guest makeGecks() {
        Guest guest = new Guest();
        guest.setId(1);
        guest.setFirstName("Olympie");
        guest.setLastName("Gecks");
        guest.setEmail("ogecks1@dagondesign.com");
        guest.setPhoneNum("(202) 2528316");
        guest.setState("MD");
        return guest;
    }
}
