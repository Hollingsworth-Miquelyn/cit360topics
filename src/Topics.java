import java.util.*;

public class Topics {
    public static void main(String[] args){

        //The Collection Interface
        //ArrayList
        List a1 = new ArrayList();
        a1.add("Miquelyn");
        a1.add("Dave");
        a1.add("Lincoln");
        a1.add("McKinley");
        System.out.println(" ArrayList Elements");
        System.out.println("\t" + a1);

        //LinkedList
        List l1 = new LinkedList();
        l1.add("Miquelyn");
        l1.add("Dave");
        l1.add("Lincoln");
        l1.add("McKinley");
        System.out.println();
        System.out.println(" LinkedList Elements");
        System.out.println("\t" + l1);

        //HashSet
        Set s1 = new HashSet();
        s1.add("Miquelyn");
        s1.add("Dave");
        s1.add("Lincoln");
        s1.add("McKinley");
        System.out.println();
        System.out.println(" Set Elements");
        System.out.println("\t" + s1);

        //HashMap
        Map m1 = new HashMap();
        m1.put("Miquelyn", "29");
        m1.put("Dave", "36");
        m1.put("Lincoln", "6");
        m1.put("McKinley", "3");
        System.out.println();
        System.out.println(" Map Elements");
        System.out.println("\t" + m1);

        //SetInterface
        int count[] = {34, 22, 55, 16, 7};
        Set<Integer> set = new HashSet<Integer>();
        try {
            for (int i=0; i < 5; i++) {
                set.add(count[i]);
            }
            System.out.println(set);

            TreeSet sortedSet = new TreeSet<Integer>(set);
            System.out.println("The sorted list is:");
            System.out.println(sortedSet);

            System.out.println("The first element of the set is: " + (Integer)sortedSet.first());
            System.out.println("The last element of the set is: " + (Integer)sortedSet.last());
        }
        catch (Exception e){}

        //SortedMap Interface
        //Create a hashmap
        TreeMap tm = new TreeMap();

        //Put elements in to the map
        tm.put("Miquelyn", new Double(1234.52));
        tm.put("Dave", new Double(4332.31));
        tm.put("Lincoln", new Double(5690.21));
        tm.put("McKinley", new Double(587.34));

        //Get a set of the entries
        Set entry = tm.entrySet();

        //Get an iterator
        Iterator i = entry.iterator();

        //Display elements
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            System.out.println(me.getKey() + ": ");
            System.out.println(me.getValue());
        }
        System.out.println();

        //Deposit 1000 into Lincoln's account
        double balance = ((Double)tm.get("Lincoln")).doubleValue();
        tm.put("Lincoln", new Double(balance + 1000));
        System.out.println("Lincolns's new balance: " + tm.get("Lincoln"));


    }
}
