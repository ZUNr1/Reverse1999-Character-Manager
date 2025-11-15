package com.ZUNr1.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class CharacterMainController {
    private BorderPane root;

    public CharacterMainController(){
        createInterface();
    }
    private void createInterface(){
        root = new BorderPane();

        //中心标题
        Label titleLabel = new Label("角色信息录入系统");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 20px;");
        root.setTop(titleLabel);

        //选项卡，包含很多Tab标签选项
        TabPane tabPane = new TabPane();

        //角色基本信息
        Tab basicInformationTab = new Tab("角色基本信息");
        basicInformationTab.setContent(createBasicInformationTab());
        //设置内容
        basicInformationTab.setClosable(false);
        //这个可以把Tab标签页设为不可关闭，去掉x，防止不小心关了
        //包含id，name，稀有度，灵感类型，创伤类型，性别，
        Tab skillInformationTab = new Tab("神秘术信息");
        skillInformationTab.setContent(createSkillInformationTab());
        skillInformationTab.setClosable(false);
        //包含技能
        Tab attributesInformationTab = new Tab("属性信息（默认满级）");
        attributesInformationTab.setContent(createAttributesInformationTab());
        attributesInformationTab.setClosable(false);
        //包含属性
        Tab otherInformationTab = new Tab("塑造与传承");
        otherInformationTab.setContent(createOtherInformationTab());
        otherInformationTab.setClosable(false);
        //包含Portrait和Inheritance
        Tab usedTermInformationTab = new Tab("专有名词");
        usedTermInformationTab.setContent(createUsedTermInformationTab());
        usedTermInformationTab.setClosable(false);
        //包含usedTerm
        tabPane.getTabs().addAll
                (basicInformationTab,skillInformationTab,attributesInformationTab,
                        otherInformationTab,usedTermInformationTab);
        //这一行获得所有标签然后添加所有我们要加的标签
        root.setCenter(tabPane);
    }
    private GridPane createBasicInformationTab(){
        GridPane content = new GridPane();//GridPane布局可以像表格一样划分
        content.setHgap(10);//设置水平间距
        content.setVgap(15);//设置垂直间距
        content.setPadding(new Insets(20));
        //为 GridPane 布局容器设置内边距
        //内边距 (Padding) 会在 GridPane 的内容区域和边框之间创建空白空间。
        content.setStyle("-fx-padding: 20px;");

        Label titleLabel = new Label("角色基本信息");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label idLabel = new Label("角色id");
        TextField idField = new TextField();
        idField.setPromptText("请输入角色id（最多10个字符）");
        //下一行代码，使用了属性变化监听器，textProperty获得这个文本，
        // 设置addListener监听，里面一个lambda表达式，监听文本的变化做出行动
        // observable: 被观察的属性对象（就是textProperty）
        // oldValue: 变化前的旧文本
        // newValue: 变化后的新文本
        //当文本发生变化（如删除添加，就会触发监听器）
        //当文本长度超出数额，我们截断（用户可能会粘贴很长一段过来，表现出来就是只有前面一截）
        //这里我们不用trim处理空格，在Manage类里面就处理了这些
        idField.textProperty().addListener
                ((observable,oldValue,newValue) -> {
                    if (newValue.length() > 10){
                        idField.setText(newValue.substring(0,10));
                    }
                });

        Label nameLabel = new Label("角色姓名");
        TextField nameField = new TextField();
        nameField.setPromptText("请输入角色姓名（最多20个字符）");
        nameField.textProperty().addListener
                ((observable,oldValue,newValue ) ->{
                    if (newValue.length() > 20){
                        nameField.setText(newValue.substring(0,20));
                    }
                });

        Label rarityLabel = new Label("稀有度");
        //下一行代码是数字选择器 (Spinner)
        //new Spinner<>(最小值, 最大值, 初始值)
        //可以设置输入的数字的最大值最小值还有初始值
        Spinner raritySpinner = new Spinner<>(2,6,6);
        raritySpinner.setEditable(true);
        //允许用户直接在 Spinner 的文本框中输入数值，而不仅仅是通过上下箭头按钮来调整。

        Label genderLabel = new Label("角色性别");
        //下一行代码是下拉选择框 (ComboBox) ，产生下拉选择框选，getItems().setAll可以设置选项的名字
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().setAll("男","女","其他");
        genderComboBox.setPromptText("请选择角色性别");

        Label afflatusLabel = new Label("灵感类型");
        ComboBox<String> afflatusComboBox = new ComboBox<>();
        afflatusComboBox.getItems().setAll("星系","岩系","兽系","木系","灵系","智系");
        afflatusComboBox.setPromptText("请选择角色灵感类型");

        Label damageTypeLabel = new Label("角色创伤类型");
        ComboBox<String> damageTypeComboBox = new ComboBox<>();
        damageTypeComboBox.getItems().setAll("现实创伤","精神创伤","本源创伤");
        damageTypeComboBox.setPromptText("请选择角色创伤类型");

        content.add(titleLabel,0,0,2,1);
        //将 titleLabel 添加到 GridPane 中，从第 0 列第 0 行开始，横跨 2 列，占据 1 行。
        content.add(idLabel,0,1);
        //不跨行就两个参数，列和行
        content.add(idField,1,1);
        content.add(nameLabel,0,2);
        content.add(nameField,1,2);
        content.add(rarityLabel,0,3);
        content.add(raritySpinner,1,3);
        content.add(genderLabel, 0, 4);
        content.add(genderComboBox, 1, 4);
        content.add(afflatusLabel,0,5);
        content.add(afflatusComboBox,1,5);
        content.add(damageTypeLabel,0,6);
        content.add(damageTypeComboBox,1,6);
        return content;
    }
    private GridPane createSkillInformationTab(){
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(15);
        content.setPadding(new Insets(20));
        //我们要解决每一列我们的布局是不一样的，放输入框的应该长一点，接收长段文字
        //使用ColumnConstraints可以控制页面的布局，每一列（竖列）的布局设置
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        col1.setPrefWidth(100);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.NEVER);
        //限制不扩展，优先级低的操作不能改变布局
        //Always是尽可能扩展到最大
        col2.setPrefWidth(200);
        //设置首选的宽度（长度）
        //布局优先级顺序：
        //1.  ColumnConstraints/RowConstraints (最高优先级)
        //2.  父容器的布局策略 (GridPane、VBox等)
        //3.  组件自身的setPrefSize() (最低优先级)
        content.getColumnConstraints().addAll(col1, col2);
        //为GridPane的第0列设置col1规则，第1列设置col2规则

        int currentRow = 0;

        VBox skillsContainer = new VBox(15);
        skillsContainer.setStyle("-fx-padding: 10px;");
        skillsContainer.getChildren().addAll(createDetailedSkillPanel("神秘术I"),
                createDetailedSkillPanel("神秘术II"),
                createDetailedSkillPanel("至终的仪式"));
        //创建一个垂直排列的容器，存放3个详细的技能面板，间距15像素。

        //额外技能区域
        Label extraSkillsNameLabel = new Label("额外神秘术");
        content.add(extraSkillsNameLabel,0,currentRow,2,1);
        //涵盖两列

        Button extraSkillsAdd = new Button("+ 添加额外技能");
        content.add(extraSkillsAdd,1,currentRow,2,1);
        currentRow++;

        VBox extraSkillsContainer = new VBox(10);//间距10像素
        //将其所有子节点（控件）在垂直方向（Vertical）上一个接一个地排列。
        //这个布局接收所有可能的额外技能，添加额外技能就在这个布局上修改
        extraSkillsContainer.setStyle("-fx-padding: 10px; -fx-border-color: #bdc3c7; -fx-border-width: 1;");
        content.add(extraSkillsContainer,0,currentRow,2,1);

        extraSkillsAdd.setOnAction(actionEvent -> addExtraSkills(extraSkillsContainer));

        skillsContainer.getChildren().addAll(extraSkillsNameLabel,extraSkillsAdd,extraSkillsContainer);

        ScrollPane scrollPane = new ScrollPane(skillsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(350);
        //ScrollPane：让内容可以上下滚动,每个技能内容很多，可能会超限，我们使用滚动布局
        //setFitToWidth(true)：内容自动适应宽度
        //setPrefViewportHeight(350)：设置可见区域高度为350像素
        scrollPane.setStyle("-fx-background: white; -fx-border-color: #bdc3c7; -fx-border-width: 1;");
        content.add(scrollPane,0,currentRow,2,1);
        currentRow++;




        return content;
    }
    private GridPane createDetailedSkillPanel(String skillInformation){
        GridPane skillPane = new GridPane();
        skillPane.setHgap(10);
        skillPane.setVgap(12);
        skillPane.setPadding(new Insets(15));
        skillPane.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 1; -fx-background-color: #ecf0f1;");
        int row = 0;

        Label titleLabel = new Label(skillInformation);
        if ("至终的仪式".equals(skillInformation)) {
            titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
        } else {
            titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        }
        skillPane.add(titleLabel,0,row,2,1);
        row++;

        Label nameLabel = new Label("神秘术名称");
        TextField nameField = new TextField();
        nameField.setPromptText("请输入" + skillInformation + "名称");
        skillPane.add(nameLabel,0,row);
        skillPane.add(nameField,1,row);
        row++;

        skillPane.add(createSkillLevelSection("一星牌"),0,row,2,1);
        row += 4;
        skillPane.add(createSkillLevelSection("二星牌"),0,row,2,1);
        row += 4;
        skillPane.add(createSkillLevelSection("三星牌"),0,row,2,1);
        return skillPane;
    }
    private GridPane createSkillLevelSection(String skillLevel){
        GridPane levelPane = new GridPane();
        levelPane.setHgap(10);
        levelPane.setVgap(8);
        levelPane.setPadding(new Insets(10));
        levelPane.setStyle("-fx-border-color: #d5dbdb; -fx-border-width: 1; -fx-background-color: #f4f6f6;");

        int levelRow = 0;

        Label levelLabel = new Label(skillLevel);
        levelLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #7f8c8d;");
        levelPane.add(levelLabel,0,levelRow,2,1);
        levelRow++;

        Label describeLabel = new Label("神秘术描述");
        TextArea describeArea = new TextArea();
        //与 TextField（单行文本框）不同，TextArea 可以容纳和显示多行文本。
        //当文本超出显示区域时，它会自动出现滚动条。
        describeArea.setPromptText("输入" + skillLevel + "技能效果");
        describeArea.setPrefRowCount(4);
        //设置初始行数row为4行
        describeArea.setWrapText(true);
        //设置自动换行，当输入不能一行显示，就换行
        levelPane.add(describeLabel,0,levelRow);
        levelPane.add(describeArea,1,levelRow);
        levelRow++;

        Label storyLabel = new Label("神秘术故事");
        TextArea storyArea = new TextArea();
        storyArea.setPromptText("输入" + skillLevel + "背景故事");
        storyArea.setPrefRowCount(2);
        storyArea.setWrapText(true);
        levelPane.add(storyLabel,0,levelRow);
        levelPane.add(storyArea,1,levelRow);
        levelRow++;

        Label typeLabel = new Label("神秘术类型");
        ComboBox<String> skillTypeComBox = new ComboBox<>();
        skillTypeComBox.getItems().addAll("攻击","增益","减益","治疗","吟诵","特殊","即兴咒语");
        levelPane.add(typeLabel,0,levelRow);
        levelPane.add(skillTypeComBox,1,levelRow);

        return levelPane;
    }
    private void addExtraSkills(VBox container){
        GridPane extraSkillPane = createDetailedSkillPanel("额外神秘术");

        Button removeExtraSkill = new Button("删除");
        removeExtraSkill.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

        VBox skillSelection = new VBox(15);
        skillSelection.getChildren().addAll(extraSkillPane,removeExtraSkill);
        //把组件加入VBox，就会垂直排序
        container.getChildren().add(skillSelection);
        //注意，这行代码是在button按下前执行的，先关联container，然后再处理
        removeExtraSkill.setOnAction
                (actionEvent -> {
                    container.getChildren().remove(skillSelection);
                    //删除这个组件，skillSelection不再被container引用
                    //所以后续代码还会执行，但是不再关联container
                    //注意，与container直接管联的是skillSelection而不是extraSkillPane
                });
    }

    private GridPane createAttributesInformationTab(){
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(15);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-padding: 20px;");

        Label titleLabel = new Label("角色属性（默认满级）");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label healthLabel = new Label("生命值");
        TextField healthField = attributesInput(30000,0,"10000");

        Label attackLabel = new Label("攻击力");
        TextField attackField = attributesInput(2000,0,"1000");

        Label realityDefenseLabel = new Label("现实防御");
        TextField realityDefenseField = attributesInput(2000,0,"500");

        Label mentalDefenseLabel = new Label("精神防御");
        TextField mentalDefenseField = attributesInput(2000,0,"500");

        Label techniqueLabel = new Label("暴击技巧");
        TextField techniqueField = attributesInput(2000,0,"500");

        content.add(titleLabel, 0, 0, 2, 1);
        content.add(healthLabel, 0, 1);
        content.add(healthField, 1, 1);
        content.add(attackLabel, 0, 2);
        content.add(attackField, 1, 2);
        content.add(realityDefenseLabel, 0, 3);
        content.add(realityDefenseField, 1, 3);
        content.add(mentalDefenseLabel, 0, 4);
        content.add(mentalDefenseField, 1, 4);
        content.add(techniqueLabel, 0, 5);
        content.add(techniqueField, 1, 5);

        return content;
    }
    private TextField attributesInput(int maxValue,int minValue,String defaultValue){
        if (minValue > maxValue){
            throw new IllegalArgumentException("最大限制小于最小限制");
        }
        TextField field = new TextField(defaultValue);
        field.setPromptText(minValue + "~" + maxValue + "之间");
        field.textProperty().addListener
                ((observable,oldValue,newValue) -> {
                    if (newValue == null || newValue.trim().isEmpty()){
                        return;
                        //为什么要return，因为如果我们不return结束这次的监听器，就会执行监听器的下一步操作（下一行的代码）
                    }
                    if (!newValue.matches("\\d*")){
                        //String类的match()方法用于检查字符串是否与给定的正则表达式匹配
                        //\d 表示匹配一个且仅一个数字字符（0-9）。  \d* 表示匹配零个或多个数字字符。
                        //这里进行输入验证，新newValue如果没有完全数字，就去除（设为空）
                        field.setText(newValue.replaceAll("[^\\d]",""));
                        //[^ ] 表示否定字符类（匹配不在方括号内的字符）
                        return;
                    }
                    if (!newValue.isEmpty()){
                        //这里可以保证传来的数据一定是纯数字，当然不包含空格（空格也会被正则表达式检测到）
                        int value = Integer.parseInt(newValue);
                        if (value > maxValue){
                            field.setText(String.valueOf(maxValue));
                        }else if (value < minValue){
                            field.setText(String.valueOf(minValue));
                        }
                    }
                });
        return field;
    }
    private VBox createOtherInformationTab(){
        return new VBox(new Label("开发中"));
    }
    private VBox createUsedTermInformationTab(){
        return new VBox(new Label("开发中"));
    }

    public BorderPane getRoot() {
        return root;
    }

}
