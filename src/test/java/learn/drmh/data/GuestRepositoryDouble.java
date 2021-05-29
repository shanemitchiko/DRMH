package learn.drmh.data;

import learn.drmh.models.Guest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {

    public final static Guest LOMAS = makeLomas();
    public final static Guest GECKS = makeGecks();
    public final static Guest CARNCROSS = makeCarncross();

    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble() {
        guests.add(LOMAS);
        guests.add(GECKS);
        guests.add(CARNCROSS);
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
        guest.setId(1);
        guest.setFirstName("Sullivan");
        guest.setLastName("Lomas");
        guest.setEmail("slomas0@mediafire.com");
        guest.setPhoneNum("(702) 7768761");
        guest.setState("NV");
        return guest;
    }

    private static Guest makeGecks() {
        Guest guest = new Guest();
        guest.setId(2);
        guest.setFirstName("Olympie");
        guest.setLastName("Gecks");
        guest.setEmail("ogecks1@dagondesign.com");
        guest.setPhoneNum("(202) 2528316");
        guest.setState("MD");
        return guest;
    }

    private static Guest makeCarncross() {
        Guest guest = new Guest();
        guest.setId(3);
        guest.setFirstName("Tremain");
        guest.setLastName("Carncross");
        guest.setEmail("tcarncross2@japanpost.jp");
        guest.setPhoneNum("(313) 2245034");
        guest.setState("MI");
        return guest;
    }
}
