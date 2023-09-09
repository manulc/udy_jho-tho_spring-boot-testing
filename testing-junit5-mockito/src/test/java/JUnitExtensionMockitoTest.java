import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Para poder usar anotaciones de Mockito
// Otra forma equivalente es usar la expresión "MockitoAnnotations.initMocks(this);"
@ExtendWith(MockitoExtension.class)
public class JUnitExtensionMockitoTest {

    @Mock
    Map<String, Object> mapMock;

    @Test
    void testMock() {
        mapMock.put("kayValue", "foo");
        // Se espera que el tamaño sea 0 porque las acciones sobre Mocks realmente no se llevan a cabo ya que no son
        // objetos reales
        assertEquals(0, mapMock.size());
    }
}
