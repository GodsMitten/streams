package javarush.streams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static javarush.streams.Selector.initData;

public class Main{
 
  public static void main(String[] args) {
     initData();

     List<String> findNames = new ArrayList<>();
     List<Cat> findCats = new ArrayList<>();
     List<Owner> owners = Selector.getOwners();

     for (Owner owner : owners) {
        for (Animal pet : owner.getPets()) {
           if (Cat.class.equals(pet.getClass()) && Color.FOXY == pet.getColor()) {
              findCats.add((Cat) pet);
           }
        }
     }

     Collections.sort(findCats, new Comparator<Cat>() {
        public int compare(Cat o1, Cat o2) {
           return o2.getAge() - o1.getAge();
        }
     });

     for (Cat cat : findCats) {
        findNames.add(cat.getName());
     }

     findNames.forEach(System.out::println);
   
   //another_way
   final List<String> findNamesStream = owners.stream()
           .flatMap(owner -> owner.getPets().stream())       //переход от Stream<Owner> к Stream<Pet>
           .filter(pet -> Cat.class.equals(pet.getClass()))  //в потоке данных оставляем только котов
           .filter(cat -> Color.FOXY == cat.getColor())      //в потоке данных оставляем только рыжих
           .sorted((o1, o2) -> o2.getAge() - o1.getAge())    //сортируем по возрасту в убывающем порядке
           .map(Animal::getName)                             //берем имена
           .collect(Collectors.toList());                    //результат складываем в список

   findNamesStream.forEach(System.out::println);
  } 
}
