package com.ZUNr1.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TermDescription {
    private Map<String, String> termDictionary;

    private TermDescription(TermDescriptionBuilder termDescriptionBuilder) {
        this.termDictionary = Collections.unmodifiableMap(
                new HashMap<>(termDescriptionBuilder.termDictionary)
        );
        //this.termDictionary = new HashMap<>(termDescriptionBuilder.termDictionary);
        //注意，这两者，前者是不可变Map，注释掉的代码是可变Map，其他类的代码可以get到字典后直接使用put修改值
        //虽然这样修改的是get到的副本，但是会导致封装性泄漏，我们要保证得到的字典在任何情况下一定是确定的，不可改变的
        //Collections里面的unmodifiableMap方法，可以获得不可变的Map
        //我们还是要new一个Map存Builder里面的副本，这是防御性拷贝，分离Builder的引用
    }

    public Map<String, String> getTermDictionary() {
        return termDictionary;
    }

    public int getCount() {
        return termDictionary.size();
    }

    public boolean isContainsKey(String key) {
        return termDictionary.containsKey(key);
    }

    public String getDescription(String term) {
        if (term == null) {
            return "术语值不能为null";
        }
        if (!isContainsKey(term)) {
            return "未找到对应描述";
        }
        return termDictionary.get(term).trim();
    }

    //public String removeTerm(String term)
    //专有名词字典是不可变的对象，我们不应该提供修改的选择
    //如果需要修改，就通过创建新对象实现，通过再次创建Builder实现
    public Set<String> getAllTerm() {
        return Collections.unmodifiableSet(termDictionary.keySet());
        //这里注意，直接返回keySet方法的结果是不行的
        // keySet方法依然是原始Map的keySet视图，我们依然可以对这个set进行add之类的可变行为
        //同样，Collections.unmodifiableSet创建不可变set，因为keySet返回的本身是新副本，不用new了
    }

    public static class TermDescriptionBuilder {
        private Map<String, String> termDictionary;

        public TermDescriptionBuilder() {
            this.termDictionary = new HashMap<>();
        }

        public TermDescriptionBuilder addTerm(String term, String description) {
            if (term != null && !term.trim().isEmpty() && description != null && !description.trim().isEmpty()) {
                termDictionary.put(term.trim(), description.trim());
            }
            return this;
        }

        public TermDescription build() {
            if (termDictionary.isEmpty()) {
                throw new IllegalStateException("无法创建空的术语词典，请至少添加一个术语");
            }
            return new TermDescription(this);
        }
    }
}
