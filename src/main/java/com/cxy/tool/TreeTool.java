package com.cxy.tool;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CXY
 * @className TreeTool
 * @description 树结构处理
 * @date 2025/01/02 14:43
 */
public class TreeTool {

    private String childrenKey = "id";
    private String parentKey = "pId";
    private String childrenListKey = "children";
    private String masterParentValue;
    private List<Map<String, Object>> childrenList = new ArrayList<>();
    private List<Map<String, Object>> treeList = new ArrayList<>();
    private List<Map<String, Object>> sourceList = new ArrayList<>();

    public TreeTool(List<Map<String, Object>> sourceList, String childrenKey, String parentKey, String masterParentValue, String childrenListKey) {
        checkList(sourceList);
        this.childrenKey = childrenKey;
        this.parentKey = parentKey;
        this.sourceList = sourceList;
        this.childrenListKey = childrenListKey;
        this.masterParentValue = masterParentValue;
        categoryList(sourceList);
    }

    public TreeTool(List<Map<String, Object>> sourceList, String masterParentValue) {
        checkList(sourceList);
        this.sourceList = sourceList;
        this.masterParentValue = masterParentValue;
        categoryList(sourceList);
    }

    private void categoryList(List<Map<String, Object>> sourceList) {
        checkList(sourceList);
        for (Map<String, Object> map : sourceList) {
            String value = formatObjectToString(map.get(this.parentKey));
            if (masterParentValue.equals(value)) {
                this.treeList.add(map);
                continue;
            }
            this.childrenList.add(map);
        }
    }

    public List<Map<String,Object>> initTreeList() {
        checkList(childrenList, treeList);
        for (Map<String, Object> map : treeList) {
            map.put(childrenListKey, setChildren(map));
        }
        return this.treeList;
    }

    private List<Map<String, Object>> setChildren(Map<String, Object> parent) {
        List<Map<String, Object>> res = new ArrayList<>();
        for (Map<String, Object> map : childrenList) {
            Object parentValue = map.get(parentKey);
            Object value = parent.get(childrenKey);
            if (formatObjectToString(parentValue).equals(formatObjectToString(value))) {
                res.add(map);
            }
        }
        if (!res.isEmpty()) {
            for (Map<String, Object> re : res) {
                re.put(childrenListKey, setChildren(re));
            }
        }
        return res;
    }

    @SafeVarargs
    private final void checkList(List<Map<String, Object>>... lists) {
        for (List<Map<String, Object>> list : lists) {
            if (list == null || list.isEmpty()) {
                throw new RuntimeException("参数不能为空");
            }
        }
    }

    private String formatObjectToString(Object o) {
        if (o == null) {
            return "";
        }
        return String.valueOf(o);
    }
}
