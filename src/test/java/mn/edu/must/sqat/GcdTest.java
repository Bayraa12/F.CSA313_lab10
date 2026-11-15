package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GcdTest {

    @Test
    @DisplayName("G01: x(13->20); y(13->15F)")
    void gcd_zeroSecond_coversEntryToExitDu() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    @Test
    @DisplayName("G02: x(13->16,17->16,17->20); "
            + "y(13->15T,13->16,13->17,18->15T,18->15F,18->16,18->17); tmp(16->18)")
    void gcd_twoIterations_coversLoopCarriedDu() {
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }

    @Test
    @DisplayName("G03: x(13->16,17->20); y(13->15T,13->16,13->17,18->15F); tmp(16->18)")
    void gcd_zeroFirst_returnsSecond() {
        assertEquals(5, DataFlowTargets.gcd(0, 5));
    }
}
