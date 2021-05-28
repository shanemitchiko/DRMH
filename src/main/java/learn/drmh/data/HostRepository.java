package learn.drmh.data;

import learn.drmh.models.Host;

import java.util.List;

public interface HostRepository {
    List<Host> findAll();

    Host findById(String id);

    Host findByEmail(String email);

}
