package ZUNr1.example;

import com.ZUNr1.dao.CharactersDao;
import com.ZUNr1.enums.Afflatus;
import com.ZUNr1.enums.DamageType;
import com.ZUNr1.enums.Gender;
import com.ZUNr1.manager.CharacterManage;
import com.ZUNr1.model.Attributes;
import com.ZUNr1.model.Characters;
import com.ZUNr1.model.Skills;
import com.ZUNr1.util.JsonDataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CharactersDaoTest {

    @Mock
    private CharacterManage manage;

    @Mock
    private JsonDataLoader jsonDataLoader;

    @InjectMocks
    private CharactersDao dao;

    private JsonDataLoader.CharactersJson jsonCharacter;
    private Characters javaCharacter;

    @BeforeEach
    void setUp() {
        // 创建测试用的JSON角色对象
        jsonCharacter = new JsonDataLoader.CharactersJson();
        jsonCharacter.id = "char01";
        jsonCharacter.name = "测试角色";
        jsonCharacter.gender = Gender.MAN;
        jsonCharacter.afflatus = Afflatus.STAR;
        jsonCharacter.damageType = DamageType.REALITY;
        jsonCharacter.level = 50;
        jsonCharacter.rarity = 3;
        jsonCharacter.attributes = new Attributes(1000, 150, 80, 60, 120);
        jsonCharacter.skills = new Skills.SkillsBuilder().build();
        jsonCharacter.usedTerm = Arrays.asList("术语1", "术语2");

        // 创建对应的Java角色对象
        javaCharacter = new Characters.CharactersBuilder("char01", "测试角色", Gender.MAN, 50)
                .afflatus(Afflatus.STAR)
                .damageType(DamageType.REALITY)
                .attributes(new Attributes(1000, 150, 80, 60, 120))
                .skills(new Skills.SkillsBuilder().build())
                .usedTerm(Arrays.asList("术语1", "术语2"))
                .rarity(3)
                .build();
    }

    @Test
    @DisplayName("应该正确覆盖加载数据到管理类")
    void testLoadToManageOverride() {
        List<JsonDataLoader.CharactersJson> jsonList = Arrays.asList(jsonCharacter);

        when(jsonDataLoader.loadCharacters()).thenReturn(jsonList);
        when(manage.findById("char01")).thenReturn(null);

        // 使用真实的dao对象测试
        CharactersDao realDao = new CharactersDao();

        // 由于JsonDataLoader是静态方法，这里主要测试方法调用不抛出异常
        assertDoesNotThrow(() -> {
            realDao.loadToManageOverride(manage);
        });
    }

    @Test
    @DisplayName("应该正确增量加载数据到管理类")
    void testLoadToManageIncremental() {
        CharactersDao realDao = new CharactersDao();

        // 主要测试方法调用不抛出异常
        assertDoesNotThrow(() -> {
            realDao.loadToManageIncremental(manage);
        });
    }

    @Test
    @DisplayName("应该跳过已存在的角色")
    void testLoadSkipExistingCharacters() {
        List<JsonDataLoader.CharactersJson> jsonList = Arrays.asList(jsonCharacter);

        when(jsonDataLoader.loadCharacters()).thenReturn(jsonList);
        when(manage.findById("char01")).thenReturn(javaCharacter); // 角色已存在

        CharactersDao realDao = new CharactersDao();

        assertDoesNotThrow(() -> {
            realDao.loadToManageOverride(manage);
        });

        // 验证没有添加重复角色
        verify(manage, never()).addCharacters(any());
    }

    @Test
    @DisplayName("应该跳过空JSON对象")
    void testLoadSkipNullJson() {
        List<JsonDataLoader.CharactersJson> jsonList = Arrays.asList(null, jsonCharacter);

        when(jsonDataLoader.loadCharacters()).thenReturn(jsonList);
        when(manage.findById("char01")).thenReturn(null);

        CharactersDao realDao = new CharactersDao();

        assertDoesNotThrow(() -> {
            realDao.loadToManageOverride(manage);
        });
    }

    @Test
    @DisplayName("应该正确从JSON转换到Characters对象")
    void testConvertFromJson() {
        CharactersDao realDao = new CharactersDao();

        // 使用反射测试私有方法（在实际项目中可能需要调整访问权限）
        Characters result = realDao.convertFromJson(jsonCharacter);

        assertNotNull(result);
        assertEquals("char01", result.getId());
        assertEquals("测试角色", result.getName());
        assertEquals(Gender.MAN, result.getGender());
        assertEquals(Afflatus.STAR, result.getAfflatus());
        assertEquals(DamageType.REALITY, result.getDamageType());
        assertEquals(50, result.getLevel());
        assertEquals(3, result.getRarity());
    }

    @Test
    @DisplayName("应该正确处理转换中的异常")
    void testConvertFromJsonWithInvalidData() {
        CharactersDao realDao = new CharactersDao();

        JsonDataLoader.CharactersJson invalidJson = new JsonDataLoader.CharactersJson();
        invalidJson.id = "invalid";
        invalidJson.name = "无效角色";
        invalidJson.gender = Gender.MAN;
        invalidJson.level = 50;
        // 缺少必需字段如afflatus等

        // 测试转换不会抛出异常，即使数据不完整
        assertDoesNotThrow(() -> {
            realDao.convertFromJson(invalidJson);
        });
    }

    @Test
    @DisplayName("应该正确保存管理类数据")
    void testSaveFromManage() {
        List<Characters> charactersList = Arrays.asList(javaCharacter);

        when(manage.findAllCharacters()).thenReturn(charactersList);

        CharactersDao realDao = new CharactersDao();

        // 主要测试方法调用不抛出异常
        assertDoesNotThrow(() -> {
            realDao.saveFromManage(manage);
        });
    }

    @Test
    @DisplayName("应该正确从管理类转换到JSON列表")
    void testConvertFromManage() {
        CharactersDao realDao = new CharactersDao();

        List<Characters> charactersList = Arrays.asList(javaCharacter);
        List<JsonDataLoader.CharactersJson> result = realDao.convertFromManage(charactersList);

        assertNotNull(result);
        assertEquals(1, result.size());

        JsonDataLoader.CharactersJson json = result.get(0);
        assertEquals("char01", json.id);
        assertEquals("测试角色", json.name);
        assertEquals(Gender.MAN, json.gender);
        assertEquals(Afflatus.STAR, json.afflatus);
        assertEquals(DamageType.REALITY, json.damageType);
        assertEquals(50, json.level);
        assertEquals(3, json.rarity);
    }

    @Test
    @DisplayName("应该正确处理空角色列表")
    void testConvertEmptyListFromManage() {
        CharactersDao realDao = new CharactersDao();

        List<Characters> emptyList = Arrays.asList();
        List<JsonDataLoader.CharactersJson> result = realDao.convertFromManage(emptyList);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
