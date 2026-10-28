import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class SearchInventoryMap {
    // the key is the product id, the value is a Product object
    // note: keys must be objects, so use Integer instead of int
    static HashMap<String, Product> inventory = new HashMap<String, Product>();

    public static void main(String[] args) {
        loadInventory();
        Scanner read = new Scanner(System.in);

        String command = "yes";

        while (command.equalsIgnoreCase("yes") ) {

            System.out.print("What item are you interested in? ");
            String search = read.nextLine();

            Product matchedProduct = inventory.get(search);
            if (matchedProduct == null) {
                System.out.println("We don't carry that product");
                continue;
            }
            System.out.printf(Locale.US, "We carry %s and the price is $%.2f%n",
                    matchedProduct.getName(), matchedProduct.getPrice());
            System.out.println("\nDo you want to search again?");
            command = read.nextLine().trim();
        }

        read.close();
    }

    public static void loadInventory() {
        try {
            // create a FileReader object
            FileReader filereader = new FileReader("inventory.csv");

            // create a BufferedReader to manage input stream
            BufferedReader bufReader = new BufferedReader(filereader);

            String input;
            String[] productInfo;

            // product information variables
            int id;
            String name;
            float price;

            // read until there is no more data
            while ((input = bufReader.readLine()) != null) {
                productInfo = input.split("\\|");
                id = Integer.parseInt(productInfo[0]);
                name = productInfo[1];
                price = Float.parseFloat(productInfo[2]);
                inventory.put(name, new Product(id, name, price));
            }
        }
        catch (IOException e) {
            System.out.println("ERROR: An unexpected error occurred");
        }
    }
}
