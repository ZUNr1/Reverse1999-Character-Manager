package com.ZUNr1.ui.viewtab;

import com.ZUNr1.model.Characters;
import com.ZUNr1.model.Euphoria;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class EuphoriaInformationViewTab {
    private Characters character;

    public EuphoriaInformationViewTab(Characters character) {
        this.character = character;
    }
    public GridPane createViewTab(){
        GridPane content = new GridPane();
        content.setHgap(15);
        content.setVgap(15);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #f8f9fa;");

        // 标题
        Label titleLabel = new Label("角色狂想信息");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        content.add(titleLabel, 0, 0);
        // 狂想信息折叠面板
        Accordion euphoriaAccordion = new Accordion();
        euphoriaAccordion.setPadding(new Insets(10, 0, 0, 0));
        List<Euphoria> euphoriaList = character.getEuphoria();
        if (euphoriaList != null && !euphoriaList.isEmpty()) {
            for (int i = 0; i < euphoriaList.size(); i++) {
                Euphoria euphoria = euphoriaList.get(i);
                addEuphoriaToAccordion(euphoriaAccordion, "狂想 " + (i + 1), euphoria);
            }
        }else {
            VBox emptyBox = new VBox(10);
            emptyBox.setPadding(new Insets(15));
            Label emptyLabel = new Label("该角色暂无狂想信息");
            emptyLabel.setStyle("-fx-text-fill: #999;");
            emptyBox.getChildren().add(emptyLabel);

            TitledPane emptyPane = new TitledPane("狂想信息", emptyBox);
            emptyPane.setAnimated(false);
            euphoriaAccordion.getPanes().add(emptyPane);
        }
        content.add(euphoriaAccordion,0,1);
        return content;
    }
    private void addEuphoriaToAccordion(Accordion accordion, String euphoriaName, Euphoria euphoria) {
        if (euphoria == null) return;

        VBox euphoriaContent = new VBox(10);
        euphoriaContent.setPadding(new Insets(15));
        euphoriaContent.setStyle("-fx-background-color: #ffffff;");

        // 狂想名称
        Label nameLabel = new Label("狂想名称: " + (euphoria.getEuphoriaName() != null ? euphoria.getEuphoriaName() : "未命名"));
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // 属性加成部分
        VBox attributesBox = createAttributesPanel(euphoria);

        // 各阶狂想描述部分 - 横向排列
        HBox levelsBox = createEuphoriaLevelsPanel(euphoria);

        euphoriaContent.getChildren().addAll(nameLabel, attributesBox, levelsBox);

        TitledPane titledPane = new TitledPane(euphoriaName, euphoriaContent);
        titledPane.setAnimated(false);
        accordion.getPanes().add(titledPane);
    }
    private VBox createAttributesPanel(Euphoria euphoria) {
        VBox attributesBox = new VBox(8);
        attributesBox.setPadding(new Insets(10));
        attributesBox.setStyle("-fx-background-color: #f5f5f5; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        Label attrTitle = new Label("属性加成");
        attrTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // 创建属性网格
        GridPane attrGrid = new GridPane();
        attrGrid.setHgap(15);
        attrGrid.setVgap(5);

        int row = 0;
        if (euphoria.getEuphoriaAttributesAddition() != null) {
            if (euphoria.getEuphoriaAttributesAddition().getHealth() != 0) {
                attrGrid.add(new Label("生命值:"), 0, row);
                attrGrid.add(new Label("+" + euphoria.getEuphoriaAttributesAddition().getHealth()), 1, row++);
            }
            if (euphoria.getEuphoriaAttributesAddition().getAttack() != 0) {
                attrGrid.add(new Label("攻击力:"), 0, row);
                attrGrid.add(new Label("+" + euphoria.getEuphoriaAttributesAddition().getAttack()), 1, row++);
            }
            if (euphoria.getEuphoriaAttributesAddition().getRealityDefense() != 0) {
                attrGrid.add(new Label("现实防御:"), 0, row);
                attrGrid.add(new Label("+" + euphoria.getEuphoriaAttributesAddition().getRealityDefense()), 1, row++);
            }
            if (euphoria.getEuphoriaAttributesAddition().getMentalDefense() != 0) {
                attrGrid.add(new Label("精神防御:"), 0, row);
                attrGrid.add(new Label("+" + euphoria.getEuphoriaAttributesAddition().getMentalDefense()), 1, row++);
            }
            if (euphoria.getEuphoriaAttributesAddition().getTechnique() != 0) {
                attrGrid.add(new Label("暴击技巧:"), 0, row);
                attrGrid.add(new Label("+" + euphoria.getEuphoriaAttributesAddition().getTechnique()), 1, row);
            }
        }
        attributesBox.getChildren().addAll(attrTitle, attrGrid);
        return attributesBox;
    }
    private HBox createEuphoriaLevelsPanel(Euphoria euphoria) {
        HBox levelsBox = new HBox(10);
        levelsBox.setPadding(new Insets(10, 0, 0, 0));

        // 一阶狂想
        if (euphoria.getFirstEuphoria() != null && !euphoria.getFirstEuphoria().isEmpty()) {
            VBox firstStageBox = createLevelDescriptionPanel("一阶狂想", euphoria.getFirstEuphoria());
            levelsBox.getChildren().add(firstStageBox);
        }

        // 二阶狂想
        if (euphoria.getSecondEuphoria() != null && !euphoria.getSecondEuphoria().isEmpty()) {
            VBox secondStageBox = createLevelDescriptionPanel("二阶狂想", euphoria.getSecondEuphoria());
            levelsBox.getChildren().add(secondStageBox);
        }

        // 三阶狂想
        if (euphoria.getThirdEuphoria() != null && !euphoria.getThirdEuphoria().isEmpty()) {
            VBox thirdStageBox = createLevelDescriptionPanel("三阶狂想", euphoria.getThirdEuphoria());
            levelsBox.getChildren().add(thirdStageBox);
        }

        // 四阶狂想
        if (euphoria.getFourthEuphoria() != null && !euphoria.getFourthEuphoria().isEmpty()) {
            VBox fourthStageBox = createLevelDescriptionPanel("四阶狂想", euphoria.getFourthEuphoria());
            levelsBox.getChildren().add(fourthStageBox);
        }

        return levelsBox;
    }
    private VBox createLevelDescriptionPanel(String levelName, String description) {
        VBox levelBox = new VBox(5);
        levelBox.setPadding(new Insets(8));
        levelBox.setStyle("-fx-background-color: #e3f2fd; -fx-border-radius: 4px; -fx-background-radius: 4px;");
        levelBox.setPrefWidth(150);

        Label levelLabel = new Label(levelName);
        levelLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1976d2;");

        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-font-size: 12px; -fx-wrap-text: true;");
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(140);

        levelBox.getChildren().addAll(levelLabel, descLabel);
        return levelBox;
    }
}
