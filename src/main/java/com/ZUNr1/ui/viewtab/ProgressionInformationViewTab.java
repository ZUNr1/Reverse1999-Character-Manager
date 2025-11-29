package com.ZUNr1.ui.viewtab;

import com.ZUNr1.model.Characters;
import com.ZUNr1.model.Portrait;
import com.ZUNr1.model.Inheritance;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class ProgressionInformationViewTab {
    private Characters character;

    public ProgressionInformationViewTab(Characters character) {
        this.character = character;
    }

    public GridPane createViewTab() {
        GridPane content = new GridPane();
        content.setHgap(15);
        content.setVgap(15);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #f8f9fa;");

        Label titleLabel = new Label("塑造与传承");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        content.add(titleLabel, 0, 0);

        Accordion progressionAccordion = new Accordion();
        progressionAccordion.setPadding(new Insets(10, 0, 0, 0));

        Portrait portrait = character.getPortrait();
        if (portrait != null) {
            addPortraitToAccordion(progressionAccordion, portrait);
        }

        Inheritance inheritance = character.getInheritance();
        if (inheritance != null) {
            addInheritanceToAccordion(progressionAccordion, inheritance);
        }

        if (progressionAccordion.getPanes().isEmpty()) {
            VBox emptyBox = new VBox(10);
            emptyBox.setPadding(new Insets(15));
            Label emptyLabel = new Label("该角色暂无塑造与传承信息");
            emptyLabel.setStyle("-fx-text-fill: #999;");
            emptyBox.getChildren().add(emptyLabel);

            TitledPane emptyPane = new TitledPane("塑造与传承", emptyBox);
            emptyPane.setAnimated(false);
            progressionAccordion.getPanes().add(emptyPane);
        }

        content.add(progressionAccordion, 0, 1);
        return content;
    }

    private void addPortraitToAccordion(Accordion accordion, Portrait portrait) {
        VBox content = new VBox(10);
        content.setPadding(new Insets(15));
        content.setStyle("-fx-background-color: #ffffff;");

        if (portrait.getPortraitDescribe() != null && !portrait.getPortraitDescribe().isEmpty()) {
            Label descLabel = new Label("塑造描述: " + portrait.getPortraitDescribe());
            descLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #e74c3c;");
            content.getChildren().add(descLabel);
        }

        HBox stagesBox = new HBox(10);
        stagesBox.setPadding(new Insets(10, 0, 0, 0));

        if (portrait.getFirstPortrait() != null && !portrait.getFirstPortrait().isEmpty()) {
            VBox stageBox = createStagePanel("一阶", portrait.getFirstPortrait());
            stagesBox.getChildren().add(stageBox);
        }

        if (portrait.getSecondPortrait() != null && !portrait.getSecondPortrait().isEmpty()) {
            VBox stageBox = createStagePanel("二阶", portrait.getSecondPortrait());
            stagesBox.getChildren().add(stageBox);
        }

        if (portrait.getThirdPortrait() != null && !portrait.getThirdPortrait().isEmpty()) {
            VBox stageBox = createStagePanel("三阶", portrait.getThirdPortrait());
            stagesBox.getChildren().add(stageBox);
        }

        if (portrait.getFourthPortrait() != null && !portrait.getFourthPortrait().isEmpty()) {
            VBox stageBox = createStagePanel("四阶", portrait.getFourthPortrait());
            stagesBox.getChildren().add(stageBox);
        }

        if (portrait.getFifthPortrait() != null && !portrait.getFifthPortrait().isEmpty()) {
            VBox stageBox = createStagePanel("五阶", portrait.getFifthPortrait());
            stagesBox.getChildren().add(stageBox);
        }

        content.getChildren().add(stagesBox);

        TitledPane titledPane = new TitledPane("角色塑造", content);
        titledPane.setAnimated(false);
        accordion.getPanes().add(titledPane);
    }

    private void addInheritanceToAccordion(Accordion accordion, Inheritance inheritance) {
        VBox content = new VBox(10);
        content.setPadding(new Insets(15));
        content.setStyle("-fx-background-color: #ffffff;");

        if (inheritance.getInheritanceName() != null && !inheritance.getInheritanceName().isEmpty()) {
            Label nameLabel = new Label("传承名称: " + inheritance.getInheritanceName());
            nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #3498db;");
            content.getChildren().add(nameLabel);
        }

        VBox stagesBox = new VBox(8);
        stagesBox.setPadding(new Insets(10, 0, 0, 0));

        if (inheritance.getBasicInheritance() != null && !inheritance.getBasicInheritance().isEmpty()) {
            stagesBox.getChildren().add(createInheritanceStagePanel("基础", inheritance.getBasicInheritance()));
        }

        if (inheritance.getFirstInheritance() != null && !inheritance.getFirstInheritance().isEmpty()) {
            stagesBox.getChildren().add(createInheritanceStagePanel("一阶", inheritance.getFirstInheritance()));
        }

        if (inheritance.getSecondInheritance() != null && !inheritance.getSecondInheritance().isEmpty()) {
            stagesBox.getChildren().add(createInheritanceStagePanel("二阶", inheritance.getSecondInheritance()));
        }

        if (inheritance.getThirdInheritance() != null && !inheritance.getThirdInheritance().isEmpty()) {
            stagesBox.getChildren().add(createInheritanceStagePanel("三阶", inheritance.getThirdInheritance()));
        }

        content.getChildren().add(stagesBox);

        TitledPane titledPane = new TitledPane("角色传承", content);
        titledPane.setAnimated(false);
        accordion.getPanes().add(titledPane);
    }

    private VBox createStagePanel(String stageName, String description) {
        VBox stageBox = new VBox(5);
        stageBox.setPadding(new Insets(8));
        stageBox.setStyle("-fx-background-color: #e3f2fd; -fx-border-radius: 4px; -fx-background-radius: 4px;");
        stageBox.setPrefWidth(150);

        Label stageLabel = new Label(stageName);
        stageLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1976d2;");

        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-font-size: 12px; -fx-wrap-text: true;");
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(140);

        stageBox.getChildren().addAll(stageLabel, descLabel);
        return stageBox;
    }

    private VBox createInheritanceStagePanel(String stageName, String description) {
        VBox stageBox = new VBox(3);

        Label stageLabel = new Label(stageName + ":");
        stageLabel.setStyle("-fx-font-weight: bold;");

        Label descLabel = new Label(description);
        descLabel.setStyle("-fx-wrap-text: true; -fx-padding: 0 0 0 10;");
        descLabel.setWrapText(true);

        stageBox.getChildren().addAll(stageLabel, descLabel);
        return stageBox;
    }
}
