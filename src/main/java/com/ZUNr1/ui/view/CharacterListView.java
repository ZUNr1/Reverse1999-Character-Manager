package com.ZUNr1.ui.view;

import com.ZUNr1.model.Characters;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.List;
import java.util.function.Consumer;

public class CharacterListView {
    private BorderPane root;
    private FlowPane cardsContainer;
    //FlowPane将其子节点（按钮、标签、图片等）以“流”的形式进行排列。当一行（或一列）的空间不足以容纳下一个子节点时，它会自动“换行”到下一行（或下一列）
    private TextField searchField;
    private Label countLabel;
    private Label resultLabel;
    private List<Characters> allCharacters; // 保存所有角色数据
    private Consumer<String> onSearchRequest;
    // 搜索回调,是一种回调函数，在搜索动作发生时被自动调用，用来处理搜索业务逻辑。
    //回调是一种编程模式：定义"当某件事发生时，调用这个函数"
    //Consumer<String>接收参数不返回值，函数式接口，accept()可以接收值的传入，我们可以对这个接口写方法来实现(在另一个类实现)
    private Consumer<Characters> onCharacterClick;
    public CharacterListView() {
        initializeUI();
        setupSearchListener();
    }
    private void initializeUI() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        // 1. 顶部搜索栏
        setupTopBar();

        // 2. 中间卡片容器
        setupCardsContainer();

        // 3. 底部信息栏
        setupBottomBar();
    }
    private void setupTopBar() {
        HBox topBar = new HBox(15);
        topBar.setStyle("-fx-background-color: white; -fx-padding: 15px; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1px 0;");
        topBar.setAlignment(Pos.CENTER_LEFT);

        // 搜索框
        searchField = new TextField();
        searchField.setPromptText("搜索角色名称、英文名");
        searchField.setPrefWidth(300);

        // 统计信息
        countLabel = new Label("共 0 个角色");
        countLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");

        // 搜索结果信息
        resultLabel = new Label("");
        resultLabel.setStyle("-fx-text-fill: #2196F3; -fx-font-size: 14px;");

        HBox.setHgrow(searchField, Priority.ALWAYS);
        topBar.getChildren().addAll(searchField, countLabel, resultLabel);
        root.setTop(topBar);
    }
    private void setupCardsContainer() {
        cardsContainer = new FlowPane();
        cardsContainer.setPadding(new Insets(20));
        cardsContainer.setHgap(20);
        cardsContainer.setVgap(20);
        cardsContainer.setStyle("-fx-background-color: #f5f5f5;");
        ScrollPane scrollPane = new ScrollPane(cardsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: #f5f5f5; -fx-border-color: transparent;");

        root.setCenter(scrollPane);
    }
    private void setupBottomBar() {
        HBox bottomBar = new HBox();
        bottomBar.setStyle("-fx-background-color: white; -fx-padding: 10px; -fx-border-color: #e0e0e0; -fx-border-width: 1px 0 0 0;");
        bottomBar.setAlignment(Pos.CENTER);

        Label hintLabel = new Label("点击角色卡片查看详情");
        hintLabel.setStyle("-fx-text-fill: #999; -fx-font-size: 12px;");

        bottomBar.getChildren().add(hintLabel);
        root.setBottom(bottomBar);
    }
    private void setupSearchListener(){
        searchField.textProperty().addListener((observable,oldValue,newValue) -> {
            if (onSearchRequest != null){
                onSearchRequest.accept(newValue.trim());
                //接收String，不返回值，就是Consumer<String>函数式接口的效果
                //解耦：事件源（ListView）不知道也不关心具体的处理逻辑，只需要在适当的时候调用注册的方法即可。
                //用户输入时立即触发搜索，无需点击搜索按钮
                //主UI线程不会被阻塞，界面保持流畅
                //实时反馈搜索结果，提升用户体验
                //方便后续改为异步的优化处理，而且可以添加多个搜索回调
            }
        });
        searchField.setOnAction(actionEvent -> {
            if (onSearchRequest != null){
                onSearchRequest.accept(searchField.getText().trim());
            }
        });
    }
    public void displayCharacters(List<Characters> characters, String searchKeyword) {
        this.allCharacters = characters; // 保存原始数据
        cardsContainer.getChildren().clear();

        if (characters.isEmpty()) {
            showEmptyState(searchKeyword);
        } else {
            showCharactersGrid(characters);
        }

        updateStatistics(characters, searchKeyword);
    }
    private void showEmptyState(String searchKeyword) {
        VBox emptyState = new VBox(10);
        emptyState.setAlignment(Pos.CENTER);
        emptyState.setStyle("-fx-padding: 60px;");

        Label iconLabel = new Label("🔍");
        iconLabel.setStyle("-fx-font-size: 48px;");

        Label textLabel;
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            textLabel = new Label("没有找到包含 \"" + searchKeyword + "\" 的角色");
        } else {
            textLabel = new Label("暂无角色数据");
        }
        textLabel.setStyle("-fx-text-fill: #999; -fx-font-size: 16px;");

        emptyState.getChildren().addAll(iconLabel, textLabel);
        cardsContainer.getChildren().add(emptyState);
    }
    private void showCharactersGrid(List<Characters> characters) {
        for (Characters character : characters) {
            CharacterCard card = new CharacterCard(character);

            // 添加点击事件（后续可以跳转到详情）
            card.setOnMouseClicked(event -> {
                System.out.println("点击角色: " + character.getName());
                if (onCharacterClick != null){
                    onCharacterClick.accept(character);
                }
            });

            cardsContainer.getChildren().add(card);
        }
    }
    private void updateStatistics(List<Characters> characters, String searchKeyword) {
        int totalCount = characters.size();
        countLabel.setText("共 " + totalCount + " 个角色");

        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            resultLabel.setText("搜索 \"" + searchKeyword + "\" 的结果");
        } else {
            resultLabel.setText("");
        }
    }
    public void clearSearch() {
        searchField.clear();
        resultLabel.setText("");
    }
    // 设置搜索回调
    public void setOnSearchRequest(Consumer<String> onSearchRequest) {
        this.onSearchRequest = onSearchRequest;
        //这里我们接收外部的回调函数，就是说这个回调函数的具体实现在其他类，实现后传过来使用
    }
    public void setOnCharacterClick(Consumer<Characters> onCharacterClick){
        this.onCharacterClick = onCharacterClick;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public BorderPane getRoot() {
        return root;
    }
}
