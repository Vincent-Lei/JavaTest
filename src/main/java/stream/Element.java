package stream;

import java.util.*;

public class Element {
    private int id;
    private String type;
    private int sortIndex;
    private static Random random = new Random();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    public Element() {
    }

    public Element(int id, String type) {
        this.id = id;
        this.type = type;
        this.sortIndex = random.nextInt(1000000);
    }

    @Override
    public String toString() {
        return "{id = " + id + ";type = " + type + ";sortIndex = " + sortIndex + "}";
    }

    private static int ID_INDEX = 0;

    private synchronized static int createId() {
        ID_INDEX++;
        return ID_INDEX;
    }

    public static List<Element> createElementTypeList(int count, String type) {
        List<Element> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(new Element(createId(), type));
        }
        return list;
    }

    public static Map<String, List<Element>> createElementTypeMap(String[] types, int eachCount) {
        Map<String, List<Element>> map = new HashMap<>();
        List<Element> list;
        for (String type : types) {
            list = new ArrayList<>(eachCount);
            map.put(type, list);
            for (int i = 0; i < eachCount; i++) {
                list.add(new Element(createId(), type));
            }
        }
        return map;
    }

    public static class ElementStub{
        private int id;
        private int sortIndex;

        public ElementStub(int id, int sortIndex) {
            this.id = id;
            this.sortIndex = sortIndex;
        }

        @Override
        public String toString() {
            return "{id = " + id + ";sortIndex = " + sortIndex + "}";
        }
    }
}
