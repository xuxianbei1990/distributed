package calculation.chapter.likou;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: xuxianbei
 * Date: 2021/11/24
 * Time: 14:57
 * Version:V1.0
 */
public class CategoryDemo {
    @Data
    static class CategoryItem {
        private Integer id;
        private Integer parentId;

        CategoryItem(Integer id, Integer parentId) {
            this.id = id;
            this.parentId = parentId;
        }
    }

    /**
     * 格式要求： List的下标代表层级， Map的key存的就是parentID
     *
     * @param categoryItems
     * @return
     */
    public List<Map<Integer, List<CategoryItem>>> execute(List<CategoryItem> categoryItems) {
        //O(N)
        Map<Integer, List<CategoryItem>> listMap = categoryItems.stream().collect(Collectors.groupingBy(inventoryCategoryNew -> inventoryCategoryNew.getParentId() == null ? 0 : inventoryCategoryNew.getParentId()));

        List<CategoryItem> oneList = listMap.get(0);
        List<Map<Integer, List<CategoryItem>>> result = new ArrayList<>();
        Map<Integer, List<CategoryItem>> oneMap = new HashMap();
        result.add(oneMap);
        // <= O(N)
        for (CategoryItem inventoryCategoryNew : oneList) {
            oneMap.put(inventoryCategoryNew.getId(), Arrays.asList(inventoryCategoryNew));
            process(inventoryCategoryNew.getId(), listMap, result, 1, 0);
        }

        return result;
    }

    private void process(Integer id, Map<Integer, List<CategoryItem>> listMap, List<Map<Integer, List<CategoryItem>>> result,
                         int storey, int index) {
        List<CategoryItem> list1 = listMap.get(id);
        if (list1 == null) {
            return;
        }
        if (storey >= result.size()) {
            result.add(new HashMap<>(8));
        }
        result.get(storey).put(id, list1);
        if (index >= list1.size()) {
            return;
        }
        process(list1.get(index).getId(), listMap, result, ++storey, index);
        index++;
    }

    public static void main(String[] args) {
        CategoryDemo categoryDemo = new CategoryDemo();
        List<CategoryItem> categoryItems = new ArrayList<>();
        categoryItems.add(new CategoryItem(1, null));
        categoryItems.add(new CategoryItem(2, null));
        categoryItems.add(new CategoryItem(3, null));
        categoryItems.add(new CategoryItem(4, null));
        categoryItems.add(new CategoryItem(5, 1));
        categoryItems.add(new CategoryItem(6, 1));
        categoryItems.add(new CategoryItem(7, 2));
        categoryItems.add(new CategoryItem(8, 2));
        categoryItems.add(new CategoryItem(9, 5));
        categoryItems.add(new CategoryItem(10, 5));
        categoryItems.add(new CategoryItem(11, 9));
        categoryItems.add(new CategoryItem(12, 9));
        List<Map<Integer, List<CategoryItem>>> maps = categoryDemo.execute(categoryItems);
        System.out.println(maps);
    }

}
