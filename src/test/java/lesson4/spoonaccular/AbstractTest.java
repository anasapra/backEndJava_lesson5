package lesson4.spoonaccular;

import net.javacrumbs.jsonunit.JsonAssert;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;

public class AbstractTest {
    public void assertJson(Object expected, Object actually) {
        JsonAssert.assertJsonEquals(
                expected,
                actually,
                JsonAssert.when(IGNORING_ARRAY_ORDER)
        );
    }

    public String getResource(String name) throws Exception {
        String resource = getClass().getSimpleName() + "/" + name;
        byte[] bytes;
        try (InputStream inputStream = getClass().getResourceAsStream(resource)) {
            assert inputStream != null;
            bytes = inputStream.readAllBytes();
        }
        return null;
    }
    public File getFile(String name) {
        String resource = getClass().getSimpleName() + "/" + name;
        String file = Objects.requireNonNull(getClass().getResource(resource)).getFile();
        return new File(file);
    }
}
