package com.ZUNr1.ui.viewtab;

import com.ZUNr1.model.Characters;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class BasicInformationViewTab {
    private Characters character;
    public BasicInformationViewTab(Characters character){
        this.character = character;
    }
    public GridPane createViewTab(){
        GridPane content = new GridPane();
        content.setHgap(15);
        content.setVgap(15);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #f8f9fa; -fx-border-radius: 8px;");

        Label titleLabel = new Label("角色基本信息");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        content.add(titleLabel, 0, 0, 3, 1);

        int currentRow = 1;
        //我打算使用VBox卡片形式存储信息
        VBox idCard = createInformationCard("角色ID", character.getId());
        content.add(idCard, 0, currentRow);

        VBox nameCard = createInformationCard("角色姓名", character.getName());
        content.add(nameCard, 1, currentRow);

        VBox enNameCard = createInformationCard("英文姓名", character.getEnName());
        content.add(enNameCard, 2, currentRow++);

        VBox rarityCard = createInformationCard("稀有度", "★".repeat(character.getRarity()));
        content.add(rarityCard, 0, currentRow);

        VBox genderCard = createInformationCard("性别", character.getGender().getChineseName());
        content.add(genderCard, 1, currentRow);

        VBox afflatusCard = createInformationCard("灵感类型", character.getAfflatus().getChineseName());
        content.add(afflatusCard, 2, currentRow++);

        VBox damageTypeCard = createInformationCard("创伤类型", character.getDamageType().getChineseName());
        content.add(damageTypeCard, 0, currentRow);

        VBox creatorCard = createInformationCard("创作者", character.getCreator());
        content.add(creatorCard, 1, currentRow);

        return content;
    }
    private VBox createInformationCard(String label, String value){
        VBox card = new VBox(8);
        card.setPadding(new Insets(12));
        card.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 6px; -fx-background-radius: 6px;");

        Label fieldLabel = new Label(label);
        fieldLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #666;");

        Label fieldValue = new Label(value != null ? value : "未设置");
        fieldValue.setStyle("-fx-font-size: 14px;");
        card.getChildren().addAll(fieldLabel,fieldValue);
        return card;
    }
}
