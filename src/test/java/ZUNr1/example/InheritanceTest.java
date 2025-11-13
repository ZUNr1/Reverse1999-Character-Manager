package ZUNr1.example;


import com.ZUNr1.model.Inheritance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InheritanceTest {

    @Test
    @DisplayName("应该正确创建传承对象")
    void testInheritanceCreation() {
        Inheritance inheritance = new Inheritance.InheritanceBuilder("测试传承")
                .basicInheritance("基础传承")
                .firstInheritance("第一传承")
                .secondInheritance("第二传承")
                .thirdInheritance("第三传承")
                .build();

        assertEquals("测试传承", inheritance.getInheritanceName());
        assertEquals("基础传承", inheritance.getBasicInheritance());
        assertEquals("第一传承", inheritance.getFirstInheritance());
        assertEquals("第二传承", inheritance.getSecondInheritance());
        assertEquals("第三传承", inheritance.getThirdInheritance());
    }

    @Test
    @DisplayName("应该处理空的传承信息")
    void testInheritanceWithEmptyValues() {
        Inheritance inheritance = new Inheritance.InheritanceBuilder("测试传承")
                .basicInheritance(null)
                .firstInheritance("")
                .build();

        assertEquals("测试传承", inheritance.getInheritanceName());
        assertEquals("", inheritance.getBasicInheritance());
        assertEquals("", inheritance.getFirstInheritance());
        assertEquals("", inheritance.getSecondInheritance());
        assertEquals("", inheritance.getThirdInheritance());
    }

    @Test
    @DisplayName("应该验证必需的传承名称")
    void testInheritanceNameValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Inheritance.InheritanceBuilder(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Inheritance.InheritanceBuilder("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Inheritance.InheritanceBuilder("   ");
        });
    }
}