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
        hosts.add(new Host("3edda6bc-ab95-49a8-8962-d50b53f84b15","Yearnes","eyearnes0@sfgate.com","(806) 1783815","3 Nova Trail","Amarillo","TX","79182", BigDecimal.valueOf(340),BigDecimal.valueOf(425)));
        hosts.add(new Host("a0d911e7-4fde-4e4a-bdb7-f047f15615e8","Rhodes","krhodes1@posterous.com","(478) 7475991","7262 Morning Avenue","Macon","GA","31296",BigDecimal.valueOf(295),BigDecimal.valueOf(368.75)));
        hosts.add(new Host("a0d911e7-4fde-4e4a-bdb7-f047f15615e8","Rhodes","krhodes1@posterous.com","(478) 7475991","7262 Morning Avenue","Macon","GA","31296",BigDecimal.valueOf(451),BigDecimal.valueOf(563.75)));
        hosts.add(new Host("b4f38829-c663-48fc-8bf3-7fca47a7ae70","Fader","mfader2@amazon.co.jp","(501) 2490895","99208 Morning Parkway","North Little Rock","AR","72118",BigDecimal.valueOf(433),BigDecimal.valueOf(541.25)));
        hosts.add(new Host("9f2578e7-6723-482b-97c1-f9be0b7c96dd","Spellesy","rspellesy3@google.co.jp","(214) 5201692","78765 Lotheville Drive","Garland","TX","75044",BigDecimal.valueOf(176),BigDecimal.valueOf(220)));
        hosts.add(new Host("b6ddb844-b990-471a-8c0a-519d0777eb9b","Harley","charley4@apple.com","(954) 7895760","1 Maple Wood Terrace","Orlando","FL","32825",BigDecimal.valueOf(387),BigDecimal.valueOf(483.75)));
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
