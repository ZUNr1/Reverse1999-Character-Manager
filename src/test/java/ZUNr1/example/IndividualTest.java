package ZUNr1.example;


import com.ZUNr1.enums.Afflatus;
import com.ZUNr1.enums.DamageType;
import com.ZUNr1.enums.Gender;
import com.ZUNr1.model.Characters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndividualTest {

    @Test
    @DisplayName("Individual.Builder应该正确验证输入")
    void testBuilderValidation() {
        // 测试ID验证
        assertThrows(IllegalArgumentException.class, () -> {
            new Characters.CharactersBuilder(null, "测试角色", Gender.MAN, 50);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Characters.CharactersBuilder("", "测试角色", Gender.MAN, 50);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Characters.CharactersBuilder("12345678901", "测试角色", Gender.MAN, 50);
        });

        // 测试姓名验证
        assertThrows(IllegalArgumentException.class, () -> {
            new Characters.CharactersBuilder("test01", null, Gender.MAN, 50);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Characters.CharactersBuilder("test01", "", Gender.MAN, 50);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Characters.CharactersBuilder("test01", "这是一个很长的角色名hhhhhhhhh", Gender.MAN, 50);
        });

        // 测试等级验证
        assertThrows(IllegalArgumentException.class, () -> {
            new Characters.CharactersBuilder("test01", "测试角色", Gender.MAN, 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Characters.CharactersBuilder("test01", "测试角色", Gender.MAN, 201);
        });
    }

    @Test
    @DisplayName("Individual应该正确实现equals和hashCode")
    void testEqualsAndHashCode() {
        Characters char1 = new Characters.CharactersBuilder("char01", "角色1", Gender.MAN, 50)
                .afflatus(Afflatus.STAR)
                .damageType(DamageType.REALITY)
                .rarity(3)
                .build();

        Characters char2 = new Characters.CharactersBuilder("char01", "角色2", Gender.WOMAN, 60)
                .afflatus(Afflatus.MINERAL)
                .damageType(DamageType.MENTAL)
                .rarity(4)
                .build();

        Characters char3 = new Characters.CharactersBuilder("char02", "角色3", Gender.MAN, 50)
                .afflatus(Afflatus.STAR)
                .damageType(DamageType.REALITY)
                .rarity(3)
                .build();

        // 相同ID应该相等
        assertEquals(char1, char2);
        assertEquals(char1.hashCode(), char2.hashCode());

        // 不同ID应该不相等
        assertNotEquals(char1, char3);
        assertNotEquals(char1, null);
        assertNotEquals(char1, new Object());
    }
}