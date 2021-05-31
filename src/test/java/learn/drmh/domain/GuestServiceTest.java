package learn.drmh.domain;

import learn.drmh.data.DataException;
import learn.drmh.data.GuestRepositoryDouble;
import learn.drmh.models.Guest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {

    GuestService service = new GuestService(new GuestRepositoryDouble());

    @Test
    void shouldFindSix() throws DataException {
        List<Guest> guests = service.findAll();
        assertEquals(3, guests.size());
    }

    @Test
    void shouldNotFindSeven() throws DataException {
        List<Guest> guests = service.findAll();
        assertNotEquals(7, guests.size());
    }

    @Test
    void shouldFindSullivanById() {
        Guest guest = service.findById(1);
        assertNotNull(guest);
        assertEquals("Sullivan", guest.getFirstName());
        assertEquals("Lomas", guest.getLastName());
        assertEquals("slomas0@mediafire.com", guest.getEmail());
        assertEquals("(702) 7768761", guest.getPhoneNum());
        assertEquals("NV", guest.getState());
    }

    @Test
    void shouldNotFindNotExistingGuestById() {
        Guest guest = service.findById(7);
        assertNull(guest);
    }

    @Test
    void shouldFindSullivanByEmail() {
        Guest guest = service.findByEmail("slomas0@mediafire.com");
        assertNotNull(guest);
        assertEquals(1, guest.getId());
        assertEquals("Sullivan", guest.getFirstName());
        assertEquals("Lomas", guest.getLastName());
        assertEquals("(702) 7768761", guest.getPhoneNum());
        assertEquals("NV", guest.getState());
    }

    @Test
    void shouldNotFindNotExistingGuestByEmail() {
        Guest guest = service.findByEmail("idontexist123@gmail.com");
        assertNull(guest);
    }

}