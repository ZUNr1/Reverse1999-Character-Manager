package ZUNr1.example;


import com.ZUNr1.enums.Afflatus;
import com.ZUNr1.enums.DamageType;
import com.ZUNr1.enums.Gender;
import com.ZUNr1.manager.CharacterManage;
import com.ZUNr1.model.Attributes;
import com.ZUNr1.model.Characters;
import com.ZUNr1.model.Skills;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CharacterManageTest {

    private CharacterManage manage;
    private Characters character1;
    private Characters character2;
    private Characters character3;

    @BeforeEach
    void setUp() {
        manage = new CharacterManage();

        Attributes attributes = new Attributes(1000, 150, 80, 60, 120);
        Skills skills = new Skills.SkillsBuilder()
                .arcaneSkill1("技能1")
                .build();
        List<String> usedTerm = Arrays.asList("攻击", "防御");

        character1 = new Characters.CharactersBuilder("char01", "阿尔法", Gender.MAN, 50)
                .afflatus(Afflatus.STAR)
                .damageType(DamageType.REALITY)
                .attributes(attributes)
                .skills(skills)
                .usedTerm(usedTerm)
                .rarity(3)
                .build();

        character2 = new Characters.CharactersBuilder("char02", "贝塔", Gender.WOMAN, 60)
                .afflatus(Afflatus.MINERAL)
                .damageType(DamageType.MENTAL)
                .attributes(attributes)
                .skills(skills)
                .usedTerm(usedTerm)
                .rarity(4)
                .build();

        character3 = new Characters.CharactersBuilder("char03", "伽玛", Gender.OTHER, 70)
                .afflatus(Afflatus.STAR)
                .damageType(DamageType.REALITY)
                .attributes(attributes)
                .skills(skills)
                .usedTerm(Arrays.asList("治疗", "辅助"))
                .rarity(5)
                .build();
    }

    @Test
    @DisplayName("应该正确添加角色")
    void testAddCharacters() {
        manage.addCharacters(character1);
        manage.addCharacters(character2);

        assertEquals(2, manage.getTotalNumber());
        assertEquals(character1, manage.findById("char01"));
        assertEquals(character2, manage.findById("char02"));
    }

    @Test
    @DisplayName("添加空角色应该抛出异常")
    void testAddNullCharacter() {
        assertThrows(IllegalArgumentException.class, () -> {
            manage.addCharacters(null);
        });
    }

    @Test
    @DisplayName("应该正确删除角色")
    void testRemoveCharacters() {
        manage.addCharacters(character1);
        manage.addCharacters(character2);

        assertTrue(manage.removeCharacters("char01"));
        assertFalse(manage.removeCharacters("char99")); // 不存在的ID
        assertEquals(1, manage.getTotalNumber());
        assertNull(manage.findById("char01"));
    }

    @Test
    @DisplayName("应该正确按ID查找角色")
    void testFindById() {
        manage.addCharacters(character1);

        assertEquals(character1, manage.findById("char01"));
        assertNull(manage.findById("nonexistent"));
    }

    @Test
    @DisplayName("应该正确按名称模糊搜索")
    void testFindByName() {
        manage.addCharacters(character1);
        manage.addCharacters(character2);
        manage.addCharacters(character3);

        List<Characters> results = manage.findByName("尔");
        assertEquals(1, results.size());
        assertEquals("阿尔法", results.get(0).getName());

        // 测试空查询
        assertTrue(manage.findByName(null).isEmpty());
        assertTrue(manage.findByName("").isEmpty());
        assertTrue(manage.findByName("不存在的名字").isEmpty());
    }

    @Test
    @DisplayName("应该正确按灵感类型查找")
    void testFindByAfflatus() {
        manage.addCharacters(character1); // STAR
        manage.addCharacters(character2); // MINERAL
        manage.addCharacters(character3); // STAR

        List<Characters> starCharacters = manage.findByAfflatus(Afflatus.STAR);
        assertEquals(2, starCharacters.size());

        List<Characters> mineralCharacters = manage.findByAfflatus(Afflatus.MINERAL);
        assertEquals(1, mineralCharacters.size());

        // 测试不存在的灵感类型
        assertTrue(manage.findByAfflatus(Afflatus.BEAST).isEmpty());
    }

    @Test
    @DisplayName("应该正确按伤害类型查找")
    void testFindByDamageType() {
        manage.addCharacters(character1); // REALITY
        manage.addCharacters(character2); // MENTAL
        manage.addCharacters(character3); // REALITY

        List<Characters> realityCharacters = manage.findByDamageType(DamageType.REALITY);
        assertEquals(2, realityCharacters.size());
    }

    @Test
    @DisplayName("应该正确按性别查找")
    void testFindByGender() {
        manage.addCharacters(character1); // MAN
        manage.addCharacters(character2); // WOMAN
        manage.addCharacters(character3); // OTHER

        assertEquals(1, manage.findByGender(Gender.MAN).size());
        assertEquals(1, manage.findByGender(Gender.WOMAN).size());
        assertEquals(1, manage.findByGender(Gender.OTHER).size());
    }

    @Test
    @DisplayName("应该正确按稀有度查找")
    void testFindByRarity() {
        manage.addCharacters(character1); // 3星
        manage.addCharacters(character2); // 4星
        manage.addCharacters(character3); // 5星

        assertEquals(1, manage.findByRarity(3).size());
        assertEquals(1, manage.findByRarity(4).size());
        assertEquals(1, manage.findByRarity(5).size());
        assertTrue(manage.findByRarity(6).isEmpty());
    }

    @Test
    @DisplayName("应该正确按术语查找")
    void testFindByTerm() {
        manage.addCharacters(character1); // 包含"攻击", "防御"
        manage.addCharacters(character3); // 包含"治疗", "辅助"

        List<Characters> attackCharacters = manage.findByTerm("攻击");
        assertEquals(1, attackCharacters.size());

        List<Characters> healCharacters = manage.findByTerm("治疗");
        assertEquals(1, healCharacters.size());

        // 测试空查询
        assertTrue(manage.findByTerm(null).isEmpty());
        assertTrue(manage.findByTerm("").isEmpty());
        assertTrue(manage.findByTerm("不存在的术语").isEmpty());
    }

    @Test
    @DisplayName("应该正确获取所有角色")
    void testFindAllCharacters() {
        manage.addCharacters(character1);
        manage.addCharacters(character2);

        List<Characters> allCharacters = manage.findAllCharacters();
        assertEquals(2, allCharacters.size());
        assertTrue(allCharacters.contains(character1));
        assertTrue(allCharacters.contains(character2));
    }

    @Test
    @DisplayName("应该正确清空所有数据")
    void testClearAll() {
        manage.addCharacters(character1);
        manage.addCharacters(character2);

        assertEquals(2, manage.getTotalNumber());

        manage.clearAll();

        assertEquals(0, manage.getTotalNumber());
        assertTrue(manage.findAllCharacters().isEmpty());
    }

    @Test
    @DisplayName("应该正确生成统计信息")
    void testStatistics() {
        manage.addCharacters(character1); // STAR, REALITY, MAN, 3星
        manage.addCharacters(character2); // MINERAL, MENTAL, WOMAN, 4星
        manage.addCharacters(character3); // STAR, REALITY, OTHER, 5星

        // 稀有度统计
        Map<Integer, Integer> rarityStats = manage.getRarityStatistics();
        assertEquals(1, rarityStats.get(3));
        assertEquals(1, rarityStats.get(4));
        assertEquals(1, rarityStats.get(5));

        // 灵感类型统计
        Map<Afflatus, Integer> afflatusStats = manage.getAfflatusStatistics();
        assertEquals(2, afflatusStats.get(Afflatus.STAR));
        assertEquals(1, afflatusStats.get(Afflatus.MINERAL));

        // 伤害类型统计
        Map<DamageType, Integer> damageTypeStats = manage.getDamageTypeStatistics();
        assertEquals(2, damageTypeStats.get(DamageType.REALITY));
        assertEquals(1, damageTypeStats.get(DamageType.MENTAL));

        // 性别统计
        Map<Gender, Integer> genderStats = manage.getGenderStatistics();
        assertEquals(1, genderStats.get(Gender.MAN));
        assertEquals(1, genderStats.get(Gender.WOMAN));
        assertEquals(1, genderStats.get(Gender.OTHER));
    }

    @Test
    @DisplayName("应该正确获取各类数量")
    void testGetNumbers() {
        manage.addCharacters(character1); // STAR, REALITY, MAN, 3星, 包含"攻击"
        manage.addCharacters(character2); // MINERAL, MENTAL, WOMAN, 4星, 包含"攻击"

        assertEquals(2, manage.getTotalNumber());
        assertEquals(1, manage.getRarityNumber(3));
        assertEquals(1, manage.getRarityNumber(4));
        assertEquals(1, manage.getAfflatusNumber(Afflatus.STAR));
        assertEquals(1, manage.getAfflatusNumber(Afflatus.MINERAL));
        assertEquals(1, manage.getGenderNumber(Gender.MAN));
        assertEquals(1, manage.getGenderNumber(Gender.WOMAN));
        assertEquals(1, manage.getDamageTypeNumber(DamageType.REALITY));
        assertEquals(1, manage.getDamageTypeNumber(DamageType.MENTAL));
        assertEquals(2, manage.getTermCharactersNumber("攻击"));
    }

    @Test
    @DisplayName("应该验证稀有度范围")
    void testRarityNumberValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            manage.getRarityNumber(1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            manage.getRarityNumber(7);
        });
    }
}
