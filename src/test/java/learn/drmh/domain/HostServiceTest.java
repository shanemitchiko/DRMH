package learn.drmh.domain;

import learn.drmh.data.DataException;
import learn.drmh.data.HostRepositoryDouble;
import learn.drmh.models.Host;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {

    HostService service = new HostService(new HostRepositoryDouble());

    @Test
    void shouldFindSix() throws DataException {
        List<Host> hosts = service.findAll();
        assertEquals(2, hosts.size());
    }

    @Test
    void shouldNotFindSeven() throws DataException {
        List<Host> hosts = service.findAll();
        assertNotEquals(7, hosts.size());
    }

    @Test
    void shouldFindYearnesById() throws DataException {
        Host host = service.findById("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        assertNotNull(host);
        assertEquals("Yearnes", host.getLastName());
        assertEquals("eyearnes0@sfgate.com", host.getEmail());
        assertEquals("(806) 1783815", host.getPhone());
        assertEquals("3 Nova Trail", host.getAddress());
        assertEquals("Amarillo", host.getCity());
        assertEquals("TX", host.getState());
        assertEquals("79182", host.getPostal_code());
        assertEquals(BigDecimal.valueOf(340), host.getStandard_rate());
        assertEquals(BigDecimal.valueOf(425), host.getWeekend_rate());

    }

    @Test
    void shouldNotFindNotExistingGuestById() throws DataException {
        Host host = service.findById("9jdhskdsand-hd3287-872hd-38dnh93-98h098e");
        assertNull(host);
    }

    @Test
    void shouldFindYearnesByEmail() {
        Host host = service.findByEmail("eyearnes0@sfgate.com");
        assertNotNull(host);
        assertEquals("3edda6bc-ab95-49a8-8962-d50b53f84b15", host.getId());
        assertEquals("Yearnes", host.getLastName());
        assertEquals("(806) 1783815", host.getPhone());
        assertEquals("3 Nova Trail", host.getAddress());
        assertEquals("Amarillo", host.getCity());
        assertEquals("TX", host.getState());
        assertEquals("79182",host.getPostal_code());
        assertEquals(BigDecimal.valueOf(340), host.getStandard_rate());
        assertEquals(BigDecimal.valueOf(425), host.getWeekend_rate());
    }

    @Test
    void shouldNotFindNotExistingGuestByEmail() {
        Host host = service.findByEmail("idontexisthost123@gmail.com");
        assertNull(host);
    }

}