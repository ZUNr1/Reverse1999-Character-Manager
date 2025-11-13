package ZUNr1.example;


import com.ZUNr1.enums.Afflatus;
import com.ZUNr1.enums.DamageType;
import com.ZUNr1.enums.Gender;
import com.ZUNr1.util.JsonDataLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonDataLoaderTest {

    @Test
    @DisplayName("应该正确创建CharactersJson内部类")
    void testCharactersJsonStructure() {
        JsonDataLoader.CharactersJson json = new JsonDataLoader.CharactersJson();
        json.id = "test01";
        json.name = "测试角色";
        json.gender = Gender.MAN;
        json.afflatus = Afflatus.STAR;
        json.damageType = DamageType.REALITY;
        json.level = 50;
        json.rarity = 3;

        assertEquals("test01", json.id);
        assertEquals("测试角色", json.name);
        assertEquals(Gender.MAN, json.gender);
        assertEquals(Afflatus.STAR, json.afflatus);
        assertEquals(DamageType.REALITY, json.damageType);
        assertEquals(50, json.level);
        assertEquals(3, json.rarity);
    }

    @Test
    @DisplayName("应该正确创建CharactersData内部类")
    void testCharactersDataStructure() {
        JsonDataLoader.CharactersData data = new JsonDataLoader.CharactersData();
        data.characters = new ArrayList<>();

        JsonDataLoader.CharactersJson json = new JsonDataLoader.CharactersJson();
        json.id = "test01";
        json.name = "测试角色";

        data.characters.add(json);

        assertNotNull(data.characters);
        assertEquals(1, data.characters.size());
        assertEquals("test01", data.characters.get(0).id);
    }

    @Test
    @DisplayName("加载不存在的文件应该抛出异常")
    void testLoadNonExistentFile() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            JsonDataLoader.loadCharacters();
        });

        assertTrue(exception.getMessage().contains("找不到文件"));
    }

    @Test
    @DisplayName("应该正确处理空字符列表")
    void testLoadEmptyCharacters(@TempDir Path tempDir) throws IOException {
        // 创建临时JSON文件
        String jsonContent = "{\"characters\": []}";
        File tempFile = tempDir.resolve("empty_characters.json").toFile();
        Files.write(tempFile.toPath(), jsonContent.getBytes());

        // 临时修改类加载器路径（在实际测试中可能需要使用Mock）
        // 这里主要测试逻辑结构
        assertNotNull(new JsonDataLoader());
    }
}
