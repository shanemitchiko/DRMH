package learn.drmh.data;

import learn.drmh.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {

    static final String SEED_PATH = "./data/guests-seed.csv";
    static final String TEST_PATH = "./data/guests-test.csv";
    static final int NEXT_ID = 1001;

    private GuestFileRepository repository = new GuestFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Files.copy(
                Paths.get(SEED_PATH),
                Paths.get(TEST_PATH),
                StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        List<Guest> actual = repository.findAll();
        assertNotNull(actual);

        assertEquals(1000, actual.size());
    }

    @Test
    void shouldFindLomasByEmail() {
        Guest lomas = repository.findByEmail("slomas0@mediafire.com");
        assertNotNull(lomas);
        assertEquals("Lomas", lomas.getLastName());
    }

    @Test
    void shouldFindLomasById() {
        Guest lomas = repository.findById(1);
        assertNotNull(lomas);
        assertEquals("Lomas", lomas.getLastName());
    }

}