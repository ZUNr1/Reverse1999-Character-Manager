package com.ZUNr1.ui.view;

import com.ZUNr1.model.Characters;
import com.ZUNr1.model.OtherInformation;
import com.ZUNr1.ui.viewtab.*;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class CharacterDetailView {
    private BorderPane root;
    private final Characters character;
    public CharacterDetailView(Characters character){
        this.root = new BorderPane();
        this.character = character;
    }
    public void showDetailInformation(){
        initializeUi();
    }
    private void initializeUi(){
        Label titleLabel = new Label("角色信息查询系统");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 20px;");
        root.setTop(titleLabel);
        TabPane tabPane = new TabPane();
        Tab basicTab = new Tab("基本信息");
        BasicInformationViewTab basicInformationViewTab  = new BasicInformationViewTab(character);
        basicTab.setContent(basicInformationViewTab.createViewTab());
        basicTab.setClosable(false);
        Tab attributeTab =  new Tab("属性信息");
        AttributesInformationViewTab attributesInformationViewTab = new AttributesInformationViewTab(character);
        attributeTab.setContent(attributesInformationViewTab.createViewTab());
        attributeTab.setClosable(false);
        Tab skillsTab = new Tab("技能信息");
        SkillInformationViewTab skillInformationViewTab = new SkillInformationViewTab(character);
        skillsTab.setContent(skillInformationViewTab.createViewTab());
        skillsTab.setClosable(false);
        Tab euphoriaTab = new Tab("狂想信息");
        EuphoriaInformationViewTab euphoriaInformationViewTab = new EuphoriaInformationViewTab(character);
        euphoriaTab.setContent(euphoriaInformationViewTab.createViewTab());
        euphoriaTab.setClosable(false);
        Tab progressionTab = new Tab("传承与塑造信息");
        ProgressionInformationViewTab progressionInformationViewTab = new ProgressionInformationViewTab(character);
        progressionTab.setContent(progressionInformationViewTab.createViewTab());
        progressionTab.setClosable(false);
        Tab otherTab = new Tab("其他信息");
        OtherInformationViewTab otherInformationViewTab = new OtherInformationViewTab(character);
        otherTab.setContent(otherInformationViewTab.createViewTab());
        otherTab.setClosable(false);
        tabPane.getTabs().addAll(basicTab,attributeTab,skillsTab,euphoriaTab,progressionTab,otherTab);
        root.setCenter(tabPane);
    }

    public BorderPane getRoot() {
        return root;
    }
}
