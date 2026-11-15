package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    @DisplayName("B03 DU: present(35->50T); best(37->53F)")
    void bonus_emptyAtZeroThreshold_returns5() {
        assertEquals(5, DataFlowTargets.bonus(new int[]{}, 0));
    }

    @Test
    @DisplayName("B04 DU: present(35->50F); score(49->56)")
    void bonus_emptyBelowThreshold_returns0() {
        assertEquals(0, DataFlowTargets.bonus(new int[]{}, 1));
    }

    @Test
    @DisplayName("B05 DU: present(35->50F); best(37->53F); repeated streak def 46")
    void bonus_allAbsent_returns0() {
        assertEquals(0, DataFlowTargets.bonus(new int[]{0, 0}, 1));
    }

    @Test
    @DisplayName("B06 DU: streak(41->42F); best(43->42F,43->42T)")
    void bonus_interruptedStreak_returns0() {
        assertEquals(0, DataFlowTargets.bonus(new int[]{1, 0, 1, 1}, 4));
    }

    @Test
    @DisplayName("B07 DU: present(40->40); streak(41->41); score(51->54)")
    void bonus_longStreakAtThreshold_returns8() {
        assertEquals(8, DataFlowTargets.bonus(new int[]{1, 1, 1, 1, 1, 1}, 6));
    }

    @Test
    @DisplayName("B08 DU: attendance(31->32T)")
    void bonus_nullAttendance_throws() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(null, 1));
    }

    @Test
    @DisplayName("B09 DU: threshold(31->32T)")
    void bonus_negativeThreshold_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> DataFlowTargets.bonus(new int[]{1}, -1));
    }

    @Test
    @DisplayName("C01 M2: separated runs keep best below 5")
    void bonus_splitAttendanceWithShortStreak_returns5() {
        assertEquals(5, DataFlowTargets.bonus(new int[]{1, 1, 0, 1, 1, 1}, 5));
    }
}
