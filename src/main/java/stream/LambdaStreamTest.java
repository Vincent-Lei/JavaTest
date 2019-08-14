package stream;

import java.util.*;
import java.util.stream.Collectors;

public class LambdaStreamTest {
    private static final String[] types = {"A", "B", "C", "D", "E"};

    public static void main(String[] args) {
        List<Element> elementList = new ArrayList<>();
        for (String type : types) {
            elementList.addAll(Element.createElementTypeList(20, type));
        }

        Map<String, List<Element>> mapList = elementList.stream().collect(Collectors.groupingBy(Element::getType, Collectors.toList()));
        System.out.println(mapList);

        mapList.values().stream().forEach(LambdaStreamTest::sortList);
        System.out.println(mapList);

        List<Element> filterList = elementList.stream().filter(element -> element.getSortIndex() > 100000).collect(Collectors.toList());
        System.out.println(filterList);

        List< Element.ElementStub> integerList = elementList.stream().filter(element -> element.getSortIndex() > 100000).map(e -> new Element.ElementStub(e.getId(), e.getSortIndex())).collect(Collectors.toList());
        System.out.println(integerList);
    }

    public static void sortList(List<Element> list) {
        Collections.sort(list, Comparator.comparingInt(Element::getSortIndex));
    }
}
