package com.ZUNr1.ui.viewtab;

import com.ZUNr1.model.Characters;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AttributesInformationViewTab {
    private Characters character;
    public AttributesInformationViewTab(Characters character){
        this.character = character;
    }
    public GridPane createViewTab(){
        GridPane content = new GridPane();
        content.setHgap(15);
        content.setVgap(15);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #f8f9fa;");

        Label titleLabel = new Label("角色属性信息（满级）");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        content.add(titleLabel, 0, 0, 3, 1);

        int currentRow = 1;
        // 生命值
        VBox healthCard = createAttributeCard("生命值", String.valueOf(character.getAttributes().getHealth()), "#e74c3c");
        content.add(healthCard, 0, currentRow);
        // 攻击力
        VBox attackCard = createAttributeCard("攻击力", String.valueOf(character.getAttributes().getAttack()), "#3498db");
        content.add(attackCard, 1, currentRow);

        // 现实防御
        VBox realityDefenseCard = createAttributeCard("现实防御", String.valueOf(character.getAttributes().getRealityDefense()), "#2ecc71");
        content.add(realityDefenseCard, 2, currentRow++);

        // 精神防御
        VBox mentalDefenseCard = createAttributeCard("精神防御", String.valueOf(character.getAttributes().getMentalDefense()), "#9b59b6");
        content.add(mentalDefenseCard, 0, currentRow);

        // 暴击技巧
        VBox techniqueCard = createAttributeCard("暴击技巧", String.valueOf(character.getAttributes().getTechnique()), "#f39c12");
        content.add(techniqueCard, 1, currentRow);
        return content;
    }
    private VBox createAttributeCard(String attributeName,String value,String color){
        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 8px; -fx-background-radius: 8px;");
        //我打算属性名称通过使用HBox横向布局，加一个颜色方块根据颜色指引，然后用到了Rectangle
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        //设置了HBox容器的字节点的布局方式：垂直居中、水平靠左的对齐方式。
        Rectangle colorIndicator = new Rectangle(12,12);
        //Rectangle可以创建一个矩形，参数是指定矩形的长宽
        colorIndicator.setFill(Color.web(color));
        //web格式，支持16进制格式的颜色代码,这里设置矩形颜色
        colorIndicator.setArcWidth(6);
        colorIndicator.setArcHeight(6);

        Label nameLabel = new Label(attributeName);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        header.getChildren().addAll(colorIndicator, nameLabel);

        card.getChildren().addAll(header, valueLabel);
        return card;
    }
}
