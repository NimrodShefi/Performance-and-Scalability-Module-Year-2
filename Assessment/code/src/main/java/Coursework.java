/*
idea for how to do this assessment:
  during @Setup, convert the csv file to a Map.
  This way you only need to look through the file once and then have the ability
  to look through much faster than by looking just at the csv file every time

  https://www.baeldung.com/java-treemap-vs-hashmap --> HashMap provides expected constant-time performance O(1) for most operations like add(), remove() and contains().
  https://stackoverflow.com/questions/11050539/using-hashmap-in-multithreaded-environment/11050613

  Once the HashMap is made, threads can be used to search through it, according to what is wanted. E.g.:
    Thread 1 will count all of the total cost of a single customer
    Thread 2 will count how many customers there are
    Thread 3 will check what was the most ordered item
*/


import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;

public class Coursework {

    private final String filename;
    private final int orderId;
    private final String personName;
    private final int topNItems;

    // Please do not hardcode the filename into the program! It's ok to change the program to prompt the user
    // for the filename, but when I download assignments from LC the filenames get changed so hardcoding can cause problems.
    public Coursework(String filename, int orderId, String personName, int topNItems) {
        // your code here if needed.
        this.filename = filename;
        this.orderId = orderId;
        this.personName = personName;
        this.topNItems = topNItems;
    }

    //A main method that will take command line arguments to call the given methods.
    //args [0] = data file filename
    //args [1] = order ID
    //args [2] = person p - as a string e.g. "Randall Best"
    //args [3] = n for the top n most-ordered items
    public static void main(String[] args) {
        // add validation of args -- please do not change the orderings of these
        Coursework cw = new Coursework(args[0], Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]));
        Map<String, List<String>> map = cw.getFile(cw.filename);
        System.out.println(cw.getOrder(cw.orderId, map));
        System.out.println();
        System.out.println(cw.getTotalCostOfOrdersBy(cw.personName, map));
        System.out.println();
        String[] topItems = cw.getTopItemsOrdered(cw.topNItems, map);
        for (int i = 0; i < cw.topNItems; i++) {
            if (topItems[i] == null){
                break;
            }
            System.out.println(topItems[i] + ",");
        }
    }

    //    Your application will provide a method to return details of an order chosen via the ID
    public String getOrder(int id, Map<String, List<String>> map) {
        //        your code
        List<String> values = map.get(String.valueOf(id));
        return String.format("ID: %s, ITEM: %s, COST: %s, CUSTOMER: %s, DATE: %s", id, values.get(0), values.get(1), values.get(2), values.get(3));
    }

    //    Your application will provide a method to return the total cost of orders made by a particular person
    public double getTotalCostOfOrdersBy(String p, Map<String, List<String>> map) {
        DecimalFormat df = new DecimalFormat("0.00"); // https://mkyong.com/java/how-to-round-double-float-value-to-2-decimal-points-in-java/
        double value = 0;
        //        your code
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            List<String> values = entry.getValue();
            if (values.get(2).equalsIgnoreCase(p)) { // checks the customer
                value += Double.parseDouble(values.get(1)); // gets the price
            }
        }
        return Double.parseDouble(df.format(value));
    }

    //    Your application will provide a method to get the most popular items based on number of times ordered
    public String[] getTopItemsOrdered(int n, Map<String, List<String>> map) {
        String[] items = new String[n];
        //        your code
        Map<String, Integer> itemCounter = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String item = entry.getValue().get(0); // gives the item
            if (itemCounter.containsKey(item)) {
                // if item exists in map, add 1 to the value
                itemCounter.put(item, itemCounter.get(item) + 1); // this uses auto-boxing -- https://stackoverflow.com/questions/4157972/how-to-update-a-value-given-a-key-in-a-hashmap
            } else {
                itemCounter.put(item, 1); // if item doesn't exist, create an entry in the map with value of 1
            }
        }
        if (n > itemCounter.size()){ // this is to ensure that if a user is looking for a top N Items and there aren't enough items in the data, it will give a different number that will work
            n = itemCounter.size();
        }

        List<String> topNItems = sortHashMap(itemCounter, n);
        for (int i = 0; i < n; i++) {
            items[i] = topNItems.get(i);
        }

        return items;
    }

    public Map<String, List<String>> getFile(String filename) {
        Map<String, List<String>> map = null;
        try {
            map = new HashMap<>();
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                List<String> values = new ArrayList<>();
                String[] data = scanner.nextLine().split(",");
                values.add(data[1]);
                values.add(data[2]);
                values.add(data[3]);
                values.add(data[4]);
                map.put(data[0], values);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static List<String> sortHashMap(final Map<String, Integer> map, int n) {
        // https://stackoverflow.com/questions/18971849/best-way-to-get-top-n-keyssorted-by-values-in-a-hashmap/27872027
        Set<String> set = map.keySet();
        List<String> keys = new ArrayList<String>(set);

        keys.sort(new Comparator<String>() {

            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(map.get(s2), map.get(s1));
            }
        });

        return keys.subList(0, n);
    }
}
