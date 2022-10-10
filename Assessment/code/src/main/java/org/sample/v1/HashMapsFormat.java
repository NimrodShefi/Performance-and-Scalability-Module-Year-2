package org.sample.v1;

import org.openjdk.jmh.annotations.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)

@State(Scope.Benchmark)
public class HashMapsFormat {

    @Param({"1", "5", "10", "15", "20", "30", "40", "50", "60", "70", "80", "90", "100", "150", "200", "250", "300", "351", "400"})
    public int arg;
    @Param({"Randall Best", "Gale Stringer", "Lettie Hodson", "Earl Strickland"})
    public String names;
    private Map<String, List<String>> map;

    @Setup
    public void getFile() {
        try {
            map = new HashMap<>();
            File file = new File("c1989618_data.csv");
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
    }

    //    Your application will provide a method to return details of an order chosen via the ID
    @Benchmark
    public String getOrder() {
        //        your code
        List<String> values = map.get(String.valueOf(arg));
        return "ID: " + arg + ", ITEM: " + values.get(0) + ", COST: " + values.get(1) + ", CUSTOMER: " + values.get(2) + ", DATE: " + values.get(3);
    }

    //    Your application will provide a method to return the total cost of orders made by a particular person
    @Benchmark
    public double getTotalCostOfOrdersBy() {
        DecimalFormat df = new DecimalFormat("0.00"); // https://mkyong.com/java/how-to-round-double-float-value-to-2-decimal-points-in-java/
        double value = 0;
        //        your code
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            List<String> values = entry.getValue();
            if (values.get(2).equalsIgnoreCase(names)) { // checks the customer
                value += Double.parseDouble(values.get(1)); // gets the price
            }
        }
        return Double.parseDouble(df.format(value));
    }

    //    Your application will provide a method to get the most popular items based on number of times ordered
    @Benchmark
    public String[] getTopItemsOrdered() {
        String[] items = new String[arg];
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

        List<String> topNItems = sortHashMap(itemCounter, arg);
        for (int i = 0; i < arg; i++) {
            items[i] = topNItems.get(i);
        }

        return items;
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
