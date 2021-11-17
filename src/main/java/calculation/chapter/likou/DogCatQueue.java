package calculation.chapter.likou;

import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 猫狗队列
 * @author: xuxianbei
 * Date: 2021/11/15
 * Time: 14:32
 * Version:V1.0
 */
public class DogCatQueue {

    @Data
    static class Pet {
        private String type;

        public Pet(String type) {
            this.type = type;
        }
    }

    static class Dog extends Pet {
        public Dog() {
            super("dog");
        }
    }

    static class Cat extends Pet {
        public Cat() {
            super("cat");
        }
    }

    @Data
    static class PetItem {
        private Pet pet;
        private Integer count;

    }

    static class PetQueue {
        private Queue<PetItem> dogQueue = new LinkedList<>();
        private Queue<PetItem> catQueue = new LinkedList<>();

        private Integer timeCount = 0;

        public Integer add(Pet pet) {
            PetItem petItem = new PetItem();
            petItem.pet = pet;
            petItem.count = timeCount++;
            if (pet instanceof Dog) {
                dogQueue.add(petItem);
            }
            if (pet instanceof Cat) {
                catQueue.add(petItem);
            }
            return timeCount;
        }

        public Pet poll() {
            PetItem petItemDog = dogQueue.peek();
            PetItem petItemCat = catQueue.peek();
            if (petItemCat.count > petItemDog.count) {
                return dogQueue.poll().getPet();
            } else {
                return catQueue.poll().getPet();
            }
        }

        public Pet pollDog() {
            return dogQueue.poll().getPet();
        }

        public Pet pollCat() {
            return catQueue.poll().getPet();
        }

    }
}
