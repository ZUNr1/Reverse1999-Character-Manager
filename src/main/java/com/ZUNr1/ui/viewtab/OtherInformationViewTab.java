package com.ZUNr1.ui.viewtab;

import com.ZUNr1.model.Characters;
import com.ZUNr1.model.CharacterCoverInformation;
import com.ZUNr1.model.CharacterItems;
import com.ZUNr1.model.CharacterStory;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class OtherInformationViewTab {
    private Characters character;

    public OtherInformationViewTab(Characters character) {
        this.character = character;
    }

    public GridPane createViewTab() {
        GridPane content = new GridPane();
        content.setHgap(15);
        content.setVgap(15);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #f8f9fa;");

        Label titleLabel = new Label("其他信息");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        content.add(titleLabel, 0, 0);

        Accordion otherAccordion = new Accordion();
        otherAccordion.setPadding(new Insets(10, 0, 0, 0));

        CharacterCoverInformation coverInfo = character.getOtherInformation().getCharacterCoverInformation();
        if (coverInfo != null) {
            addCharacterCoverInfo(otherAccordion, coverInfo);
        }

        List<CharacterItems> characterItems = character.getOtherInformation().getCharacterItems();
        if (characterItems != null && !characterItems.isEmpty()) {
            addCharacterItemsInfo(otherAccordion, characterItems);
        }

        CharacterStory characterStory = character.getOtherInformation().getCharacterStory();
        if (characterStory != null) {
            addCharacterStoryInfo(otherAccordion, characterStory);
        }

        content.add(otherAccordion, 0, 1);
        return content;
    }

    private void addCharacterCoverInfo(Accordion accordion, CharacterCoverInformation coverInfo) {
        VBox content = new VBox(8);
        content.setPadding(new Insets(15));
        content.setStyle("-fx-background-color: #ffffff;");

        // 添加封面信息的各个字段
        if (coverInfo.getCharacterIntroduction() != null && !coverInfo.getCharacterIntroduction().isEmpty()) {
            VBox fieldBox = createFieldPanel("角色介绍", coverInfo.getCharacterIntroduction());
            content.getChildren().add(fieldBox);
        }

        if (coverInfo.getCharacterSize() != null && !coverInfo.getCharacterSize().isEmpty()) {
            VBox fieldBox = createFieldPanel("角色尺寸", coverInfo.getCharacterSize());
            content.getChildren().add(fieldBox);
        }

        if (coverInfo.getCharacterFragrance() != null && !coverInfo.getCharacterFragrance().isEmpty()) {
            VBox fieldBox = createFieldPanel("角色香调", coverInfo.getCharacterFragrance());
            content.getChildren().add(fieldBox);
        }

        if (coverInfo.getCharacterDetailedAfflatus() != null && !coverInfo.getCharacterDetailedAfflatus().isEmpty()) {
            VBox fieldBox = createFieldPanel("详细灵感", coverInfo.getCharacterDetailedAfflatus());
            content.getChildren().add(fieldBox);
        }

        TitledPane titledPane = new TitledPane("角色封面信息", content);
        titledPane.setAnimated(false);
        accordion.getPanes().add(titledPane);
    }

    private void addCharacterItemsInfo(Accordion accordion, List<CharacterItems> itemsList) {
        VBox content = new VBox(10);
        content.setPadding(new Insets(15));
        content.setStyle("-fx-background-color: #ffffff;");

        for (CharacterItems items : itemsList) {
            if (items != null) {
                VBox dressBox = new VBox(8);
                dressBox.setPadding(new Insets(5));
                dressBox.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 4px; -fx-background-radius: 4px;");

                if (items.getDressName() != null && !items.getDressName().isEmpty()) {
                    Label dressLabel = new Label("装扮: " + items.getDressName());
                    dressLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
                    dressBox.getChildren().add(dressLabel);
                }

                if (items.getFirstItemName() != null && !items.getFirstItemName().isEmpty()) {
                    VBox firstItemBox = createItemPanel(
                            items.getFirstItemName(),
                            items.getFirstItemDescribe(),
                            items.getFirstItemPrice()
                    );
                    dressBox.getChildren().add(firstItemBox);
                }

                if (items.getSecondItemName() != null && !items.getSecondItemName().isEmpty()) {
                    VBox secondItemBox = createItemPanel(
                            items.getSecondItemName(),
                            items.getSecondItemDescribe(),
                            items.getSecondItemPrice()
                    );
                    dressBox.getChildren().add(secondItemBox);
                }

                if (items.getThirdItemName() != null && !items.getThirdItemName().isEmpty()) {
                    VBox thirdItemBox = createItemPanel(
                            items.getThirdItemName(),
                            items.getThirdItemDescribe(),
                            items.getThirdItemPrice()
                    );
                    dressBox.getChildren().add(thirdItemBox);
                }

                content.getChildren().add(dressBox);
            }
        }

        TitledPane titledPane = new TitledPane("装扮物品信息", content);
        titledPane.setAnimated(false);
        accordion.getPanes().add(titledPane);
    }

    private VBox createItemPanel(String name, String description, String price) {
        VBox itemBox = new VBox(3);
        itemBox.setPadding(new Insets(3, 0, 3, 15));

        if (name != null && !name.isEmpty()) {
            Label nameLabel = new Label("• " + name);
            nameLabel.setStyle("-fx-font-weight: bold;");
            itemBox.getChildren().add(nameLabel);
        }

        if (description != null && !description.isEmpty()) {
            Label descLabel = new Label("  描述: " + description);
            descLabel.setWrapText(true);
            descLabel.setStyle("-fx-font-size: 12px;");
            itemBox.getChildren().add(descLabel);
        }

        if (price != null && !price.isEmpty()) {
            Label priceLabel = new Label("  价格: " + price);
            priceLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #e74c3c;");
            itemBox.getChildren().add(priceLabel);
        }

        return itemBox;
    }

    private void addCharacterStoryInfo(Accordion accordion, CharacterStory story) {
        VBox content = new VBox(10);
        content.setPadding(new Insets(15));
        content.setStyle("-fx-background-color: #ffffff;");

        if (story.getFirstStoryName() != null && !story.getFirstStoryName().isEmpty()) {
            VBox firstStoryBox = createStoryPanel(
                    story.getFirstStoryName(),
                    story.getFirstStoryDescribe()
            );
            content.getChildren().add(firstStoryBox);
        }

        if (story.getSecondStoryName() != null && !story.getSecondStoryName().isEmpty()) {
            VBox secondStoryBox = createStoryPanel(
                    story.getSecondStoryName(),
                    story.getSecondStoryDescribe()
            );
            content.getChildren().add(secondStoryBox);
        }

        if (story.getThirdStoryName() != null && !story.getThirdStoryName().isEmpty()) {
            VBox thirdStoryBox = createStoryPanel(
                    story.getThirdStoryName(),
                    story.getThirdStoryDescribe()
            );
            content.getChildren().add(thirdStoryBox);
        }

        TitledPane titledPane = new TitledPane("角色故事信息", content);
        titledPane.setAnimated(false);
        accordion.getPanes().add(titledPane);
    }

    private VBox createStoryPanel(String name, String description) {
        VBox storyBox = new VBox(5);

        if (name != null && !name.isEmpty()) {
            Label nameLabel = new Label(name);
            nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
            storyBox.getChildren().add(nameLabel);
        }

        if (description != null && !description.isEmpty()) {
            Label descLabel = new Label(description);
            descLabel.setWrapText(true);
            descLabel.setStyle("-fx-padding: 0 0 0 10;");
            storyBox.getChildren().add(descLabel);
        }

        return storyBox;
    }

    private VBox createFieldPanel(String fieldName, String fieldValue) {
        VBox fieldBox = new VBox(3);

        Label nameLabel = new Label(fieldName + ":");
        nameLabel.setStyle("-fx-font-weight: bold;");

        Label valueLabel = new Label(fieldValue);
        valueLabel.setWrapText(true);
        valueLabel.setStyle("-fx-padding: 0 0 0 10;");

        fieldBox.getChildren().addAll(nameLabel, valueLabel);
        return fieldBox;
    }
}
