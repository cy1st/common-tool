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

    public static void main(String[] args) {
        List<Map<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> m1 = new HashMap<>();
        m1.put("id", 0);
        m1.put("pId", 0);
        m1.put("value", "test");
        HashMap<String, Object> m2 = new HashMap<>();
        m2.put("id", 1);
        m2.put("pId", 0);
        m2.put("value", "test");
        HashMap<String, Object> m3 = new HashMap<>();
        m3.put("id", 2);
        m3.put("pId", 1);
        m3.put("value", "test");
        HashMap<String, Object> m4 = new HashMap<>();
        m4.put("id", 3);
        m4.put("pId", 2);
        m4.put("value", "test");
        HashMap<String, Object> m5 = new HashMap<>();
        m5.put("id", 4);
        m5.put("pId", 1);
        m5.put("value", "test");
        HashMap<String, Object> m6 = new HashMap<>();
        m6.put("id", 5);
        m6.put("pId", 4);
        m6.put("value", "test");
        HashMap<String, Object> m7 = new HashMap<>();
        m7.put("id", 6);
        m7.put("pId", 1);
        m7.put("value", "test");
        HashMap<String, Object> m8 = new HashMap<>();
        m8.put("id", 8);
        m8.put("pId", 0);
        m8.put("value", "test");
        HashMap<String, Object> m9 = new HashMap<>();
        m9.put("id", 9);
        m9.put("pId", 8);
        m9.put("value", "test");
        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m4);
        list.add(m5);
        list.add(m6);
        list.add(m7);
        list.add(m8);
        list.add(m9);
        TreeTool treeTool = new TreeTool(list, "0");
        List<Map<String, Object>> trees = treeTool.initTreeList();
        System.out.println(JSONArray.toJSONString(trees));

    }

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
