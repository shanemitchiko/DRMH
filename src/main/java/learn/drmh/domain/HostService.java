package learn.drmh.domain;

import learn.drmh.data.DataException;
import learn.drmh.data.HostRepository;
import learn.drmh.models.Host;

import java.util.List;

public class HostService {

    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public Host findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<Host> findAll() throws DataException {
        return repository.findAll();
    }

    public Host findById(String id) throws DataException {
        return repository.findById(id);
    }

}
