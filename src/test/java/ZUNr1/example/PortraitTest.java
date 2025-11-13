package ZUNr1.example;


import com.ZUNr1.model.Portrait;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PortraitTest {

    @Test
    @DisplayName("应该正确创建肖像对象")
    void testPortraitCreation() {
        Portrait portrait = new Portrait.PortraitBuilder("肖像描述")
                .firstPortrait("第一肖像")
                .secondPortrait("第二肖像")
                .thirdPortrait("第三肖像")
                .fourthPortrait("第四肖像")
                .fifthPortrait("第五肖像")
                .build();

        assertEquals("肖像描述", portrait.getPortraitDescribe());
        assertEquals("第一肖像", portrait.getFirstPortrait());
        assertEquals("第二肖像", portrait.getSecondPortrait());
        assertEquals("第三肖像", portrait.getThirdPortrait());
        assertEquals("第四肖像", portrait.getFourthPortrait());
        assertEquals("第五肖像", portrait.getFifthPortrait());
    }

    @Test
    @DisplayName("应该处理空的肖像信息")
    void testPortraitWithEmptyValues() {
        Portrait portrait = new Portrait.PortraitBuilder("基础描述")
                .firstPortrait(null)
                .secondPortrait("")
                .build();

        assertEquals("基础描述", portrait.getPortraitDescribe());
        assertEquals("", portrait.getFirstPortrait());
        assertEquals("", portrait.getSecondPortrait());
        assertEquals("", portrait.getThirdPortrait());
        assertEquals("", portrait.getFourthPortrait());
        assertEquals("", portrait.getFifthPortrait());
    }

    @Test
    @DisplayName("应该验证必需的肖像描述")
    void testPortraitDescribeValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Portrait.PortraitBuilder(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Portrait.PortraitBuilder("");
        });
    }
}
