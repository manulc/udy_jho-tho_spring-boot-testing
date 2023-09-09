import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith(MockitoExtension.class)
public class AnnotationMockTest {

    @Mock
    Map<String, Object> mapMock;

    @BeforeEach
    void setUp() {
        // Para poder usar anotaciones de Mockito
        // Otra forma equivalente es usar la anotación @ExtendWith y la clase MockitoExtension a nivel de clase
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testMock() {
        mapMock.put("kayValue", "foo");
        // Se espera que el tamaño sea 0 porque las acciones sobre Mocks realmente no se llevan a cabo ya que no son
        // objetos reales
        assertEquals(0, mapMock.size());
    }
}
