package ZUNr1.example;


import com.ZUNr1.model.TermDescription;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TermDescriptionTest {

    @Test
    @DisplayName("应该正确创建术语字典对象")
    void testTermDescriptionCreation() {
        TermDescription termDesc = new TermDescription.TermDescriptionBuilder()
                .addTerm("术语1", "描述1")
                .addTerm("术语2", "描述2")
                .build();

        assertEquals(2, termDesc.getCount());
        assertEquals("描述1", termDesc.getDescription("术语1"));
        assertEquals("描述2", termDesc.getDescription("术语2"));
    }

    @Test
    @DisplayName("应该忽略空的术语和描述")
    void testTermDescriptionWithEmptyValues() {
        TermDescription termDesc = new TermDescription.TermDescriptionBuilder()
                .addTerm(null, "描述")
                .addTerm("术语", null)
                .addTerm("", "描述")
                .addTerm("术语", "")
                .addTerm("有效术语", "有效描述")
                .build();

        assertEquals(1, termDesc.getCount());
        assertTrue(termDesc.isContainsKey("有效术语"));
    }

    @Test
    @DisplayName("应该验证至少需要一个术语")
    void testEmptyTermDictionaryValidation() {
        assertThrows(IllegalStateException.class, () -> {
            new TermDescription.TermDescriptionBuilder().build();
        });
    }

    @Test
    @DisplayName("应该返回不可变的字典")
    void testImmutableDictionary() {
        TermDescription termDesc = new TermDescription.TermDescriptionBuilder()
                .addTerm("术语1", "描述1")
                .build();

        Map<String, String> dictionary = termDesc.getTermDictionary();

        assertThrows(UnsupportedOperationException.class, () -> {
            dictionary.put("新术语", "新描述");
        });
    }

    @Test
    @DisplayName("应该正确处理不存在的术语查询")
    void testNonExistentTerm() {
        TermDescription termDesc = new TermDescription.TermDescriptionBuilder()
                .addTerm("现有术语", "描述")
                .build();

        assertEquals("未找到对应描述", termDesc.getDescription("不存在的术语"));
        assertEquals("术语值不能为null", termDesc.getDescription(null));
    }

    @Test
    @DisplayName("应该返回不可变的术语集合")
    void testGetAllTerm() {
        TermDescription termDesc = new TermDescription.TermDescriptionBuilder()
                .addTerm("术语1", "描述1")
                .addTerm("术语2", "描述2")
                .build();

        Set<String> allTerms = termDesc.getAllTerm();
        assertEquals(2, allTerms.size());

        assertThrows(UnsupportedOperationException.class, () -> {
            allTerms.add("新术语");
        });
    }
}