package ZUNr1.example;


import com.ZUNr1.model.Skills;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkillsTest {

    @Test
    @DisplayName("应该正确创建技能对象")
    void testSkillsCreation() {
        Skills skills = new Skills.SkillsBuilder()
                .arcaneSkill1("一技能")
                .arcaneSkill2("二技能")
                .arcaneSkill3("大招")
                .addExtraSkills("额外技能1")
                .addExtraSkills("额外技能2")
                .build();

        assertEquals("一技能", skills.getArcaneSkill1());
        assertEquals("二技能", skills.getArcaneSkill2());
        assertEquals("大招", skills.getArcaneSkill3());

        List<String> extraSkills = skills.getExtraSkills();
        assertEquals(2, extraSkills.size());
        assertTrue(extraSkills.contains("额外技能1"));
        assertTrue(extraSkills.contains("额外技能2"));
    }

    @Test
    @DisplayName("应该处理空的技能信息")
    void testSkillsWithEmptyValues() {
        Skills skills = new Skills.SkillsBuilder()
                .arcaneSkill1(null)
                .arcaneSkill2("")
                .addExtraSkills(null)
                .addExtraSkills("")
                .build();

        assertEquals("", skills.getArcaneSkill1());
        assertEquals("", skills.getArcaneSkill2());
        assertEquals("", skills.getArcaneSkill3());
        assertTrue(skills.getExtraSkills().isEmpty());
    }

    @Test
    @DisplayName("应该正确实现防御性拷贝")
    void testDefensiveCopy() {
        Skills.SkillsBuilder builder = new Skills.SkillsBuilder();
        builder.addExtraSkills("技能1");
        builder.addExtraSkills("技能2");

        Skills skills = builder.build();
        List<String> extraSkills = skills.getExtraSkills();

        // 尝试修改返回的列表不应该影响原始数据
        extraSkills.add("技能3");

        List<String> originalSkills = skills.getExtraSkills();
        assertEquals(2, originalSkills.size());
        assertFalse(originalSkills.contains("技能3"));
    }

    @Test
    @DisplayName("toString方法应该正确格式化")
    void testToString() {
        Skills skills = new Skills.SkillsBuilder()
                .arcaneSkill1("火焰冲击")
                .arcaneSkill2("寒冰护盾")
                .arcaneSkill3("雷霆万钧")
                .addExtraSkills("治疗术")
                .build();

        String result = skills.toString();
        assertTrue(result.contains("一技能：火焰冲击"));
        assertTrue(result.contains("二技能：寒冰护盾"));
        assertTrue(result.contains("大招：雷霆万钧"));
        assertTrue(result.contains("额外技能"));
        assertTrue(result.contains("治疗术"));
    }
}