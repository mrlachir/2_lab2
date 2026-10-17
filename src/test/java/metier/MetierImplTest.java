package metier;
import org.junit.Test;
import static org.junit.Assert.*;

public class MetierImplTest {
    @Test public void testCalcul() {
        MetierImpl metier = new MetierImpl();
        metier.setDao(() -> 10.0);
        assertEquals(20.0, metier.calcul(), 0.001);
    }
    @Test public void missingInjectionFailsClearly() {
        assertThrows(IllegalStateException.class, () -> new MetierImpl().calcul());
    }
    @Test public void nullDependencyRejected() {
        assertThrows(NullPointerException.class, () -> new MetierImpl().setDao(null));
    }
}
