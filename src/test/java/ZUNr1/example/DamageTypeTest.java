package ZUNr1.example;


import com.ZUNr1.enums.DamageType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DamageTypeTest {

    private static Stream<Arguments> provideValidTypes() {
        return Stream.of(
                Arguments.of(1, DamageType.REALITY),
                Arguments.of(-1, DamageType.MENTAL),
                Arguments.of(0, DamageType.GENESIS)
        );
    }

    @Test
    void testEnumValues() {
        assertEquals(3, DamageType.values().length);
        assertEquals("现实创伤", DamageType.REALITY.getChineseName());
        assertEquals(1, DamageType.REALITY.getType());
    }

    @ParameterizedTest
    @MethodSource("provideValidTypes")
    void testFromType_ValidTypes(int type, DamageType expected) {
        DamageType result = DamageType.fromType(type);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {-2, 2, 100})
    void testFromType_InvalidTypes(int invalidType) {
        assertThrows(IllegalArgumentException.class, () -> {
            DamageType.fromType(invalidType);
        });
    }
}