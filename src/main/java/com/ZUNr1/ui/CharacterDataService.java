package com.ZUNr1.ui;

import com.ZUNr1.enums.Afflatus;
import com.ZUNr1.enums.DamageType;
import com.ZUNr1.enums.Gender;
import com.ZUNr1.enums.SkillType;
import com.ZUNr1.ui.tab.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class CharacterDataService {
    public void populateFormDataFromUI(CharacterFormData formData,
                                       BasicInformationTab basicInformationTab,
                                       AttributesInformationTab attributesInformationTab,
                                       SkillInformationTab skillInformationTab,
                                       ProgressionInformationTab progressionInformationTab,
                                       UsedTermInformationTab usedTermInformationTab,
                                       EuphoriaInformationTab euphoriaInformationTab,
                                       OtherInformationTab otherInformationTab) {

    }
    private void populateBasicInformation(CharacterFormData formData,BasicInformationTab basicInformationTab){
        formData.setId(basicInformationTab.getIdField().getText());
        formData.setName(basicInformationTab.getNameField().getText());
        formData.setEnName(basicInformationTab.getEnNameField().getText());
        formData.setCreator(basicInformationTab.getCreatorField().getText());
        formData.setAfflatus(convertToAfflatus(basicInformationTab.getAfflatusComboBox().getValue()));
        formData.setGender(convertToGender(basicInformationTab.getGenderComboBox().getValue()));
        formData.setDamageType(convertToDamageType(basicInformationTab.getDamageTypeComboBox().getValue()));
        formData.setRarity(basicInformationTab.getRaritySpinner().getValue());

    }
    private void populateAttributesInformation(CharacterFormData formData, AttributesInformationTab attributesInformationtab) {
        formData.setHealth(parseInt(attributesInformationtab.getHealthField().getText()));
        formData.setAttack(parseInt(attributesInformationtab.getAttackField().getText()));
        formData.setRealityDefense(parseInt(attributesInformationtab.getRealityDefenseField().getText()));
        formData.setMentalDefense(parseInt(attributesInformationtab.getMentalDefenseField().getText()));
        formData.setTechnique(parseInt(attributesInformationtab.getTechniqueField().getText()));
    }
    private void populateSkillsInformation(CharacterFormData formData,SkillInformationTab skillInformationTab){
        Map<String,String> skillNames = new HashMap<>();
        for (Map.Entry<String, TextField> entry : skillInformationTab.getSkillNameFields().entrySet()){
            //entry.getKey() = 技能键名（如："神秘术I"）
            skillNames.put(entry.getKey(), entry.getValue().getText());
        }
        formData.setSkillNames(skillNames);

        Map<String,Map<String,String>> skillDescribes = new HashMap<>();
        for (Map.Entry<String,Map<String, TextArea>> skillEntry : skillInformationTab.getSkillDescribeFields().entrySet()){
            // 这里应该是 Map<String, String>，因为要存储文本
            Map<String,String> levelDescribes = new HashMap<>();
            for (Map.Entry<String,TextArea> levelEntry : skillEntry.getValue().entrySet()){
                //skillEntry是外层Map的键值对,不是Map,skillEntry.getValue()返回的是内层Map
                levelDescribes.put(levelEntry.getKey(), levelEntry.getValue().getText());
            }
            skillDescribes.put(skillEntry.getKey(),levelDescribes);
        }

        Map<String,Map<String,String>> skillStories = new HashMap<>();
        for (Map.Entry<String,Map<String,TextArea>> skillEntry : skillInformationTab.getSkillStoryFields().entrySet()){
            Map<String,String> levelStory = new HashMap<>();
            for (Map.Entry<String,TextArea> levelEntry : skillEntry.getValue().entrySet()){
                levelStory.put(levelEntry.getKey(),levelEntry.getValue().getText());
            }
            skillStories.put(skillEntry.getKey(),levelStory);
        }
        Map<String, Map<String, SkillType>> skillTypes = new HashMap<>();
        for (Map.Entry<String, Map<String, ComboBox<String>>> skillEntry : skillInformationTab.getSkillTypeFields().entrySet()) {
            Map<String, SkillType> levelTypes = new HashMap<>();
            for (Map.Entry<String, ComboBox<String>> levelEntry : skillEntry.getValue().entrySet()) {
                String selectedValue = levelEntry.getValue().getValue();
                SkillType skillType = convertToSkillType(selectedValue);  // 需要实现这个转换方法
                levelTypes.put(levelEntry.getKey(), skillType);
            }
            skillTypes.put(skillEntry.getKey(), levelTypes);
        }
        formData.setSkillTypes(skillTypes);
    }
    private void populateUsedTermInformation(CharacterFormData formData, UsedTermInformationTab usedTermInformationTab) {
        Map<String, String> usedTerms = new HashMap<>();
        for (Map.Entry<String, TextField> nameEntry : usedTermInformationTab.getUsedTermNameFields().entrySet()) {
            String termId = nameEntry.getKey();
            String termName = nameEntry.getValue().getText();

            TextArea describeArea = usedTermInformationTab.getUsedTermDescribeFields().get(termId);
            String termDescribe = describeArea != null ? describeArea.getText() : "";

            // 只有当名称和描述都不为空时才保存
            if (!termName.trim().isEmpty() && !termDescribe.trim().isEmpty()) {
                usedTerms.put(termName, termDescribe);
            }
        }
        formData.setUsedTerms(usedTerms);
    }
    private void populateEuphoriaInformation(CharacterFormData formData,EuphoriaInformationTab euphoriaInformationTab){
        Map<String, Map<String, String>> euphoriaDescribes = new HashMap<>();
        Map<String, Map<String, Integer>> euphoriaAttributes = new HashMap<>();
        Map<String,String> euphoriaNames = new HashMap<>();
        for (Map.Entry<String,Map<String,TextArea>> describeEntry : euphoriaInformationTab.getEuphoriaDescribeFields().entrySet()){
            String euphoriaId = describeEntry.getKey();
            Map<String, TextArea> describeMap = describeEntry.getValue();
            Map<String, String> describeTexts = new HashMap<>();
            for (Map.Entry<String, TextArea> levelEntry : describeMap.entrySet()) {
                describeTexts.put(levelEntry.getKey(), levelEntry.getValue().getText());
            }
            euphoriaDescribes.put(euphoriaId, describeTexts);
            Map<String,TextField> attributesMap = euphoriaInformationTab.getEuphoriaAttributesFields().get(euphoriaId);
            if (attributesMap != null) {
                Map<String, Integer> attributeValues = new HashMap<>();
                // 狂想名称单独处理
                TextField nameField = attributesMap.get("name");
                String euphoriaName = nameField != null ? nameField.getText().trim() : "";
                euphoriaNames.put(euphoriaId, euphoriaName);
                attributeValues.put("health", parseInt(attributesMap.get("health").getText()));
                attributeValues.put("attack", parseInt(attributesMap.get("attack").getText()));
                attributeValues.put("realityDefense", parseInt(attributesMap.get("realityDefense").getText()));
                attributeValues.put("mentalDefense", parseInt(attributesMap.get("mentalDefense").getText()));
                attributeValues.put("technique", parseInt(attributesMap.get("technique").getText()));
                euphoriaAttributes.put(euphoriaId,attributeValues);
            }
        }
        formData.setEuphoriaNames(euphoriaNames);
        formData.setEuphoriaDescribes(euphoriaDescribes);
        formData.setEuphoriaAttributes(euphoriaAttributes);
    }
/* todo   private void populateProgressionInformation(CharacterFormData formData,ProgressionInformationTab progressionInformationTab){

    }
  todo  private void populateOtherInformation(CharacterFormData formData,OtherInformationTab otherInformationTab){

    }

 */
    private Gender convertToGender(String value) {
        if (value == null) return null;
        switch (value) {
            case "男": return Gender.MAN;
            case "女": return Gender.WOMAN;
            case "其他": return Gender.OTHER;
            default: return null;
        }
    }

    private Afflatus convertToAfflatus(String value) {
        if (value == null) return null;
        switch (value) {
            case "星系": return Afflatus.STAR;
            case "岩系": return Afflatus.MINERAL;
            case "兽系": return Afflatus.BEAST;
            case "木系": return Afflatus.PLANT;
            case "灵系": return Afflatus.SPIRIT;
            case "智系": return Afflatus.INTELLECT;
            default: return null;
        }
    }

    private DamageType convertToDamageType(String value) {
        if (value == null) return null;
        switch (value) {
            case "现实创伤": return DamageType.REALITY;
            case "精神创伤": return DamageType.MENTAL;
            case "本源创伤": return DamageType.GENESIS;
            default: return null;
        }
    }

    private SkillType convertToSkillType(String value) {
        if (value == null) return null;
        switch (value){
            case "攻击": return SkillType.ATTACK;
            case "增益": return SkillType.BUFF;
            case "减益": return SkillType.DEBUFF;
            case "治疗": return SkillType.HEALTH;
            case "吟诵": return SkillType.CHANNEL;
            case "特殊": return SkillType.VERSATILE;
            case "即兴咒语": return SkillType.IMPROMPTU;
            default: return null;
        }
    }
    private int parseInt(String text) {
        if (text == null || text.trim().isEmpty()) return 0;
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
