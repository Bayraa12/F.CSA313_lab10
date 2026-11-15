package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BonusTest {

    @Test
    @DisplayName("B01 all-defs: score(49->54,54->56); streak(36->41)")
    void bonus_belowThresholdWithLongStreak_returns3() {
        assertEquals(3, DataFlowTargets.bonus(new int[]{1, 1, 1, 1, 1, 0}, 7));
    }

    @Test
    @DisplayName("B02 all-defs: score(51->56); streak(46->41)")
    void bonus_afterAbsenceAtThreshold_returns5() {
        assertEquals(5, DataFlowTargets.bonus(new int[]{0, 1}, 1));
    }
}
