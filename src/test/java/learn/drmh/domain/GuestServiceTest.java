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
        assertEquals(6, guests.size());
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
        assertEquals("Tom", guest.getFirstName());
        assertEquals("Sullivan", guest.getLastName());
        assertEquals("tomsullivan123@gmail.com", guest.getEmail());
        assertEquals("(718) 3456789", guest.getPhoneNum());
        assertEquals("MD", guest.getState());
    }

    @Test
    void shouldNotFindNotExistingGuestById() {
        Guest guest = service.findById(7);
        assertNull(guest);
    }

    @Test
    void shouldFindSullivanByEmail() {
        Guest guest = service.findByEmail("tomsullivan123@gmail.com");
        assertNotNull(guest);
        assertEquals(1, guest.getId());
        assertEquals("Tom", guest.getFirstName());
        assertEquals("Sullivan", guest.getLastName());
        assertEquals("(718) 3456789", guest.getPhoneNum());
        assertEquals("MD", guest.getState());
    }

    @Test
    void shouldNotFindNotExistingGuestByEmail() {
        Guest guest = service.findByEmail("idontexist123@gmail.com");
        assertNull(guest);
    }

}