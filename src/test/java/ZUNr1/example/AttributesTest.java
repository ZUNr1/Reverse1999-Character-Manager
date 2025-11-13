package ZUNr1.example;

import com.ZUNr1.model.Attributes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AttributesTest {

    private Attributes attributes;

    @BeforeEach
    void setUp() {
        attributes = new Attributes(1000, 150, 80, 60, 120);
    }

    @Test
    @DisplayName("应该正确创建属性对象")
    void testAttributesCreation() {
        assertEquals(1000, attributes.getHealth());
        assertEquals(150, attributes.getAttack());
        assertEquals(80, attributes.getRealityDefense());
        assertEquals(60, attributes.getMentalDefense());
        assertEquals(120, attributes.getTechnique());
    }

    @Test
    @DisplayName("应该正确设置和获取属性值")
    void testSettersAndGetters() {
        attributes.setHealth(1200);
        attributes.setAttack(180);
        attributes.setRealityDefense(100);
        attributes.setMentalDefense(80);
        attributes.setTechnique(150);

        assertEquals(1200, attributes.getHealth());
        assertEquals(180, attributes.getAttack());
        assertEquals(100, attributes.getRealityDefense());
        assertEquals(80, attributes.getMentalDefense());
        assertEquals(150, attributes.getTechnique());
    }
}