package learn.drmh.data;

import learn.drmh.models.Guest;

import java.util.List;

public interface GuestRepository {
    List<Guest> findAll();

    Guest findByEmail(String email);

    Guest findById(int id);
}
