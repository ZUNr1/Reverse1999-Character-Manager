package com.ZUNr1.ui.controller;

import com.ZUNr1.manager.CharacterManage;
import com.ZUNr1.model.Characters;
import com.ZUNr1.service.CharacterStorageService;
import com.ZUNr1.ui.view.CharacterDetailView;
import com.ZUNr1.ui.view.CharacterListView;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CharacterViewController {
    private final CharacterStorageService storageService;
    private final CharacterManage characterManage;
    private final CharacterListView listView;
    private CharacterDetailView characterDetailView;
    public CharacterViewController(){
        this.storageService = new CharacterStorageService();
        this.characterManage = storageService.getCharacterManage();
        this.listView = new CharacterListView();
        setupEventHandlers();
        loadAndDisplayAllCharacters();
    }
    private void setupEventHandlers(){
        listView.setOnSearchRequest(this::handleSearch);
        //两个冒号表示方法引用，类名冒号静态方法名和实例名冒号方法名，并不是调用，这里就没有调用，而是把方法的引用传给搜索回调（如果直接写方法名就会运行一次这个方法）
        //这里这个方法的引用使用了Consumer函数式接口
        //这是设置搜索回调，因为listView里面用到了，必须初始化的时候就设置
        listView.setOnCharacterClick(this::handleCharacterClick);
    }
    private void loadAndDisplayAllCharacters(){
        List<Characters> charactersList = characterManage.findAllCharacters();
        listView.displayCharacters(charactersList,"");
    }
    private void handleSearch(String keyword){
        System.out.println("处理搜索请求，关键词: '" + keyword + "'");
        List<Characters> charactersList;
        if (keyword == null || keyword.trim().isEmpty()){
            charactersList = characterManage.findAllCharacters();
        }else {
            charactersList = searchCharacters(keyword);
        }
        listView.displayCharacters(charactersList,keyword);
    }
    private List<Characters> searchCharacters(String keyword) {
        List<Characters> list1 = characterManage.findByName(keyword);
        List<Characters> list2 = characterManage.findByEnName(keyword);

        Set<String> idSet = new HashSet<>();
        List<Characters> resultList = new ArrayList<>();
        //把两个list合并去重，我选择使用HashSet存储已经存入的id来检查
        // 合并两个列表并去重
        addUniqueCharacters(list1, idSet, resultList);
        addUniqueCharacters(list2, idSet, resultList);
        return resultList;
    }
    private void addUniqueCharacters(List<Characters> sourceList, Set<String> idSet, List<Characters> targetList) {
        for (Characters character : sourceList) {
            String characterId = character.getId();
            if (idSet.add(characterId)) {
                //HashSet的add方法返回一个boolean，如果为true表示正常添加（Set里面没有）
                targetList.add(character);
            }
        }
    }
    private void handleCharacterClick(Characters character){
        System.out.println("跳转到角色详情页: " + character.getName());
        characterDetailView = new CharacterDetailView(character);
        characterDetailView.showDetailInformation();
        Scene currentScene = listView.getRoot().getScene();
        //哎呀想半天没有注意到，直接getRoot再getScene就可以获得场景了
        if (currentScene != null){
            currentScene.setRoot(characterDetailView.getRoot());
        }

    }
    public CharacterListView getListView() {
        return listView;
    }
}

