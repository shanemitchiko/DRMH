package learn.drmh.data;

import learn.drmh.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository {

    public final static Host YEARNES = makeYearnes();
    public final static Host RHODES = makeRhodes();

    private final ArrayList<Host> hosts = new ArrayList<>();

    public HostRepositoryDouble() {
        hosts.add(YEARNES);
        hosts.add(RHODES);
    }

    @Override
    public List<Host> findAll() {
        return hosts;
    }

    @Override
    public Host findById(String id) {
        return findAll().stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Host findByEmail(String email) {
        return findAll().stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    private static Host makeYearnes() {
        Host host = new Host();
        host.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        host.setLastName("Yearnes");
        host.setEmail("eyearnes0@sfgate.com");
        host.setPhone("(806) 1783815");
        host.setAddress("3 Nova Trail");
        host.setCity("Amarillo");
        host.setState("TX");
        host.setPostal_code("79182");
        host.setStandard_rate(BigDecimal.valueOf(340));
        host.setWeekend_rate(BigDecimal.valueOf(425));
        return host;
    }

    private static Host makeRhodes() {
        Host host = new Host();
        host.setId("a0d911e7-4fde-4e4a-bdb7-f047f15615e8");
        host.setLastName("Rhodes");
        host.setEmail("krhodes1@posterous.com");
        host.setPhone("(478) 7475991");
        host.setAddress("7262 Morning Avenue");
        host.setCity("Macon");
        host.setState("GA");
        host.setPostal_code("31296");
        host.setStandard_rate(BigDecimal.valueOf(295));
        host.setWeekend_rate(BigDecimal.valueOf(368.75));
        return host;
    }
}
