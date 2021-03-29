import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        MyCollection myCollection = new MyCollection();
        Integer[] mass = {0, 2, null, 5, 2};
        myCollection = MyCollection.getCollection(mass);
        Iterator<Integer> it = myCollection.iterator();
        System.out.println(Arrays.toString(myCollection.toArray((new Integer[]{0, 0, 0, 0, 0, 0, 0}))));
//        while (it.hasNext()){
//            System.out.print(it.next() + " ");
//        }
    }
}
