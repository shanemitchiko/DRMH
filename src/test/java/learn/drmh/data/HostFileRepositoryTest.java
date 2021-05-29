package learn.drmh.data;

import learn.drmh.models.Guest;
import learn.drmh.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {

    static final String SEED_PATH = "./data/hosts-seed.csv";
    static final String TEST_PATH = "./data/hosts-test.csv";

    private HostFileRepository repository = new HostFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Files.copy(
                Paths.get(SEED_PATH),
                Paths.get(TEST_PATH),
                StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        List<Host> actual = repository.findAll();
        assertNotNull(actual);

        assertEquals(1000, actual.size());
    }

    @Test
    void shouldFindYearnesByEmail() {
        Host yearnes = repository.findByEmail("eyearnes0@sfgate.com");
        assertNotNull(yearnes);
        assertEquals("Yearnes", yearnes.getLastName());
    }

}