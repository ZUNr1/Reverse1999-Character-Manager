package ZUNr1.example;


import com.ZUNr1.enums.Afflatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AfflatusTest {

    private static Stream<Arguments> provideValidTypes() {
        return Stream.of(
                Arguments.of(1, Afflatus.STAR),
                Arguments.of(2, Afflatus.MINERAL),
                Arguments.of(3, Afflatus.BEAST),
                Arguments.of(4, Afflatus.PLANT),
                Arguments.of(-1, Afflatus.SPIRIT),
                Arguments.of(-2, Afflatus.INTELLECT)
        );
    }

    @Test
    void testEnumValues() {
        assertEquals(6, Afflatus.values().length);
        assertEquals("星系", Afflatus.STAR.getChineseName());
        assertEquals(1, Afflatus.STAR.getType());
    }

    @ParameterizedTest
    @MethodSource("provideValidTypes")
    void testFromType_ValidTypes(int type, Afflatus expected) {
        Afflatus result = Afflatus.fromType(type);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {-3, 0, 5, 100})
    void testFromType_InvalidTypes(int invalidType) {
        assertThrows(IllegalArgumentException.class, () -> {
            Afflatus.fromType(invalidType);
        });
    }

    @Test
    void testIsNatureAfflatus() {
        assertTrue(Afflatus.STAR.isNatureAfflatus());
        assertTrue(Afflatus.MINERAL.isNatureAfflatus());
        assertTrue(Afflatus.BEAST.isNatureAfflatus());
        assertTrue(Afflatus.PLANT.isNatureAfflatus());
        assertFalse(Afflatus.SPIRIT.isNatureAfflatus());
        assertFalse(Afflatus.INTELLECT.isNatureAfflatus());
    }

    @Test
    void testIsNativeAfflatus() {
        assertFalse(Afflatus.STAR.isNativeAfflatus());
        assertFalse(Afflatus.MINERAL.isNativeAfflatus());
        assertFalse(Afflatus.BEAST.isNativeAfflatus());
        assertFalse(Afflatus.PLANT.isNativeAfflatus());
        assertTrue(Afflatus.SPIRIT.isNativeAfflatus());
        assertTrue(Afflatus.INTELLECT.isNativeAfflatus());
    }
}