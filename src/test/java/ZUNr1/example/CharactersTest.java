package ZUNr1.example;

import com.ZUNr1.enums.Afflatus;
import com.ZUNr1.enums.DamageType;
import com.ZUNr1.enums.Gender;
import com.ZUNr1.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CharactersTest {

    @Test
    @DisplayName("应该正确创建角色对象")
    void testCharactersCreation() {
        Attributes attributes = new Attributes(1000, 150, 80, 60, 120);
        Skills skills = new Skills.SkillsBuilder()
                .arcaneSkill1("火焰冲击")
                .arcaneSkill2("寒冰护盾")
                .arcaneSkill3("雷霆万钧")
                .build();

        Inheritance inheritance = new Inheritance.InheritanceBuilder("英雄传承")
                .basicInheritance("基础能力")
                .firstInheritance("第一传承")
                .build();

        Portrait portrait = new Portrait.PortraitBuilder("英雄肖像")
                .firstPortrait("初始立绘")
                .build();

        List<String> usedTerm = Arrays.asList("术语1", "术语2");

        Characters character = new Characters.CharactersBuilder("char01", "测试角色", Gender.MAN, 50)
                .afflatus(Afflatus.STAR)
                .damageType(DamageType.REALITY)
                .attributes(attributes)
                .skills(skills)
                .inheritance(inheritance)
                .portrait(portrait)
                .usedTerm(usedTerm)
                .rarity(3)
                .build();

        assertEquals("char01", character.getId());
        assertEquals("测试角色", character.getName());
        assertEquals(Gender.MAN, character.getGender());
        assertEquals(50, character.getLevel());
        assertEquals(Afflatus.STAR, character.getAfflatus());
        assertEquals(DamageType.REALITY, character.getDamageType());
        assertEquals(3, character.getRarity());
        assertEquals(attributes, character.getAttributes());
        assertEquals(skills, character.getSkills());
        assertEquals(inheritance, character.getInheritance());  // 现在应该不为null了
        assertEquals(portrait, character.getPortrait());
        assertEquals(2, character.getUsedTerm().size());
    }

    @Test
    @DisplayName("应该处理空的集合字段")
    void testCharactersWithEmptyCollections() {
        Characters character = new Characters.CharactersBuilder("char01", "测试角色", Gender.MAN, 50)
                .afflatus(Afflatus.STAR)
                .damageType(DamageType.REALITY)
                .rarity(3)
                .usedTerm(null)
                .build();

        assertNotNull(character.getUsedTerm());
        assertTrue(character.getUsedTerm().isEmpty());
    }

    @Test
    @DisplayName("应该验证稀有度范围")
    void testRarityValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Characters.CharactersBuilder("char01", "测试角色", Gender.MAN, 50)
                    .rarity(1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Characters.CharactersBuilder("char01", "测试角色", Gender.MAN, 50)
                    .rarity(7);
        });
    }

    @Test
    @DisplayName("应该验证必需的字段")
    void testRequiredFieldsValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Characters.CharactersBuilder("char01", "测试角色", Gender.MAN, 50)
                    .afflatus(null)
                    .build();
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Characters.CharactersBuilder("char01", "测试角色", Gender.MAN, 50)
                    .attributes(null)
                    .build();
        });
    }

    @Test
    @DisplayName("应该正确实现防御性拷贝")
    void testDefensiveCopy() {
        List<String> originalTerms = new ArrayList<>(Arrays.asList("术语1", "术语2"));

        Characters character = new Characters.CharactersBuilder("char01", "测试角色", Gender.MAN, 50)
                .afflatus(Afflatus.STAR)
                .damageType(DamageType.REALITY)
                .rarity(3)
                .usedTerm(originalTerms)
                .build();

        // 修改原始列表不应该影响角色对象
        originalTerms.add("术语3");

        List<String> characterTerms = character.getUsedTerm();
        assertEquals(2, characterTerms.size());
        assertFalse(characterTerms.contains("术语3"));
    }

    @Test
    @DisplayName("应该处理null的optional字段")
    void testCharactersWithNullOptionalFields() {
        Characters character = new Characters.CharactersBuilder("char01", "测试角色", Gender.MAN, 50)
                .afflatus(Afflatus.STAR)
                .damageType(DamageType.REALITY)
                .rarity(3)
                .inheritance(null)  // 测试null继承
                .portrait(null)     // 测试null肖像
                .otherInformation(null) // 测试null其他信息
                .build();

        assertNotNull(character);
        assertEquals("char01", character.getId());
        assertNull(character.getInheritance());  // 应该允许为null
        assertNull(character.getPortrait());     // 应该允许为null
        assertNull(character.getOtherInformation()); // 应该允许为null
    }
}