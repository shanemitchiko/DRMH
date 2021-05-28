package learn.drmh.domain;

import learn.drmh.data.DataException;
import learn.drmh.data.GuestRepository;
import learn.drmh.data.ReservationRepository;
import learn.drmh.models.Guest;

import java.util.List;

public class GuestService {

    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public Guest findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<Guest> findAll() throws DataException {
        return repository.findAll();
    }

    public Guest findById(int id){
        return repository.findById(id);
    }

    private Result<Guest> validate(Guest guest) {

        Result<Guest> result = validateNulls(guest);
        if (!result.isSuccess()) {
            return result;
        }

        validateDuplicate(guest, result);
        return result;
    }

    private Result<Guest> validateNulls(Guest guest) {
        Result<Guest> result = new Result<>();

        if (guest == null) {
            result.addErrorMessage("Nothing to save");
            return result;
        }

        if (guest.getFirstName() == null || guest.getLastName() == null) {
            result.addErrorMessage("Name is required");
        }

        if (guest.getEmail() == null) {
            result.addErrorMessage("Email is required");
        }

        if (guest.getPhoneNum() == null) {
            result.addErrorMessage("Phone number is required");
        }

        if (guest.getState() == null) {
            result.addErrorMessage("State is required");
        }

        return result;
    }

    private void validateDuplicate(Guest guest, Result<Guest> result) {
        List<Guest> guests = repository.findAll();
        for (Guest g : guests) {
            if (guest.getFirstName().equals(g.getFirstName())){
                if (guest.getLastName().equals(g.getLastName())) {
                    if (guest.getEmail().equals(g.getEmail())) {
                        if (guest.getPhoneNum().equals(g.getPhoneNum())) {
                            if (guest.getState().equals(g.getState())) {
                                result.addErrorMessage("Duplicate Guest is not allowed");
                            }
                        }
                    }
                }
            }
        }
    }
}
