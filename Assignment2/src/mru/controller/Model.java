package mru.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import mru.model.*;
import mru.view.AppMenu;

public class Model {
    private static ArrayList<String> storeList;
    private ArrayList<Toy> toys = new ArrayList<Toy>();
    private final String FILE_PATH = "Assignment2/res/toys.txt";
    private AppMenu appMenu;

    /**
     * Default constructor initializes the wordList, appMenu, and launches the application
     * @throws Exception 
     */
    public Model() throws Exception {
        Model.storeList = new ArrayList<>();
        this.appMenu = new AppMenu(this); // Instantiate AppMenu
        initializeStoreList();
        launchApplication();
    }

    private void initializeStoreList() throws Exception {
    	File db = new File(FILE_PATH);
		String currentLine;
		String[] splittedLine;
		String[] splitPlayers;
		
		
		/**
		 * takes the text from the txt file into an arraylist
		 */
		if(db.exists()) {
			Scanner fileReader = new Scanner(db);
			
			while (fileReader.hasNextLine()) {
				
				currentLine = fileReader.nextLine();
				splittedLine = currentLine.split(";");
				if (splittedLine[0].charAt(0) == '1' || splittedLine[0].charAt(0) == '0') {
					//figures SN, name, brand, price, count, age, class
					Toy toy = new Figures(Long.parseLong(splittedLine[0]), splittedLine[1], splittedLine[2], Double.parseDouble(splittedLine[3]), Integer.parseInt(splittedLine[4]), Integer.parseInt(splittedLine[5]), splittedLine[6].charAt(0));
					toys.add(toy);
				}
				else if (splittedLine[0].charAt(0) == '2' || splittedLine[0].charAt(0) == '3') {
					//Animals mat size
					Toy toy = new Animals(Long.parseLong(splittedLine[0]), splittedLine[1], splittedLine[2], Double.parseDouble(splittedLine[3]), Integer.parseInt(splittedLine[4]), Integer.parseInt(splittedLine[5]), splittedLine[6], splittedLine[7].charAt(0));
					toys.add(toy);
				}
				else if (splittedLine[0].charAt(0) == '4' || splittedLine[0].charAt(0) == '5' || splittedLine[0].charAt(0) == '6') {
					//puzzles type
					Toy toy = new Puzzles(Long.parseLong(splittedLine[0]), splittedLine[1], splittedLine[2], Double.parseDouble(splittedLine[3]), Integer.parseInt(splittedLine[4]), Integer.parseInt(splittedLine[5]), splittedLine[6].charAt(0));
					toys.add(toy);
				}
				else {
					//Board games min, max, designers
					splitPlayers = splittedLine[6].split("-");
					Toy toy = new BoardGames(Long.parseLong(splittedLine[0]), splittedLine[1], splittedLine[2], Double.parseDouble(splittedLine[3]), Integer.parseInt(splittedLine[4]), Integer.parseInt(splittedLine[5]), Integer.parseInt(splitPlayers[0]), Integer.parseInt(splitPlayers[1]), splittedLine[7]);
					toys.add(toy);
				}
			}
			fileReader.close();
		}
    }

    private void launchApplication() throws IOException {
        boolean flag = true;
        int option;

        try (Scanner scanner = new Scanner(System.in)) {
            while (flag) {
                option = appMenu.showMainMenu(); // Call showMainMenu from AppMenu

                switch (option) {
                    case 1:
                        search();
                        break;
                    case 2:
                        addToy();
                        break;
                    case 3:
                        removeToy(storeList);
                        break;
                    case 4:
                        save(storeList, "toys.txt");
                        flag = false; // Exit the loop when the user chooses to save and exit
                        break;
                    default:
                        System.out.println("Invalid option. Please choose again.");
                }
            }
        }
    }


    private void save(ArrayList<String> storeList, String fileName) {
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String toy : storeList) {
                writer.write(toy);
                writer.newLine();
            }
            System.out.println("Toys have been successfully saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving toys to file: " + e.getMessage());
        }
    }
    

    private void addToy() {
        Scanner scanner = new Scanner(System.in);
        //add validation for Serial number to check if valid and if unique
        
        long SN;
        while (true) {
            System.out.print("\nEnter Serial Number (10 digits): ");
            String inputSN = scanner.next();
            if (inputSN.matches("\\d{10}")) {
                SN = Long.parseLong(inputSN);

                // Check if SN is unique
                if (isSerialNumberUnique(SN)) {
                    break;
                } else {
                    System.out.println("Serial Number is not unique. Please enter a different Serial Number.");
                }
            } else {
                System.out.println("Invalid input. Serial Number must be 10 digits long and contain only digits.");
            }
        }
        
        System.out.print("\nEnter Toy Name: ");
        String name = scanner.next();
        
        System.out.print("\nEnter Toy Brand: ");
        String brand = scanner.next();
        
        double price;
        while (true) {
            System.out.print("\nEnter Toy Price: ");
            price = scanner.nextDouble();
            if (price >= 0) {
                break;
            } else {
                System.out.println("Invalid input. Price cannot be negative.");
            }
        }
        
        System.out.print("\nEnter Available Counts: ");
        int count = scanner.nextInt();
        
        System.out.print("\nEnter Appropriate Age: ");
        int age = scanner.nextInt();

        String type = Long.toString(SN);
        if (type.startsWith("0") || type.startsWith("1")) {
        	System.out.println("\nEnter Toy Classification: ");
            char classification = scanner.next().charAt(0);
            Toy toy = new Figures(SN, name, brand, price, count, age, classification);
            toys.add(toy);
        }
        else if (type.startsWith("2") || type.startsWith("3")) {
        	System.out.println("\nEnter Toy Material: ");
            String material = scanner.next();
            System.out.println("\nEnter Toy Size: ");
            char size = scanner.nextLine().charAt(0);
            Toy toy = new Animals(SN, name, brand, price, count, age, material, size);
            toys.add(toy);
        }
        else if (type.startsWith("4") || type.startsWith("5") || type.startsWith("6")) {
        	System.out.println("\nEnter Puzzle Type: ");
            char puzzleType = scanner.next().charAt(0);
            Toy toy = new Puzzles(SN, name, brand, price, count, age, puzzleType);
            toys.add(toy);
        }
        else {
        	int minPlayers, maxPlayers;
            do {
                System.out.print("\nEnter Minimum Number of Players: ");
                minPlayers = scanner.nextInt();
                System.out.print("\nEnter Maximum Number of Players: ");
                maxPlayers = scanner.nextInt();
                if (minPlayers > maxPlayers) {
                    System.out.println("Invalid input. Minimum number of players cannot be greater than maximum.");
                }
            } while (minPlayers > maxPlayers);
            
            System.out.println("\nEnter Designer Names(Use ',' to separate the names if there are more than one name): ");
            String designers = scanner.next();
            Toy toy = new BoardGames(SN, name, brand, price, count, age, minPlayers, maxPlayers, designers);
            toys.add(toy);
        }
    }
    
 // Helper method to check if Serial Number is unique in the toys ArrayList
    private boolean isSerialNumberUnique(long newSN) {
        for (Toy existingToy : toys) {
            if (existingToy.getSN() == newSN) {
                return false; // Serial Number already exists, not unique
            }
        }
        return true; // Serial Number is unique
    }

    private void removeToy(ArrayList<String> itemList) {
        Scanner scanner = new Scanner(System.in);

        // Validate user input for Serial Number (SN)
        String keyword;
        while (true) {
            System.out.println("Enter Serial Number (SN) to search: ");
            try {
                keyword = scanner.nextLine();
                Integer.parseInt(keyword); // Try parsing the input as an integer
                break; // If successful, exit the loop
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid SN containing numbers only.");
            }
        }

        ArrayList<String> searchResults = new ArrayList<>();

        // Search for items containing the keyword
        for (String item : itemList) {
            if (item.toLowerCase().contains(keyword.toLowerCase())) {
                searchResults.add(item);
            }
        }

        // Display search results
        if (searchResults.isEmpty()) {
            System.out.println("No matching items found.\n");
        } else {
            System.out.println("Matching item(s):\n");
            for (int i = 0; i < searchResults.size(); i++) {
                System.out.println((i + 1) + ". " + searchResults.get(i));
            }

            // Ask user for confirmation to remove the toy
            System.out.println("Do you want to remove the toy? (Y/N)");
            String confirmation = scanner.nextLine().toLowerCase();

            if (confirmation.equalsIgnoreCase("Y")) {
                // Remove the toy using the removeItem() method
            	 removeItem2(itemList, searchResults);
                System.out.println("Toy removed successfully.");
            } else {
                System.out.println("Toy removal canceled.");
            }
        }
    }

    private void search() {
        int searchOption;

        do {
            System.out.println("Find toys with:\n");
            System.out.println("(1) Serial Number(SN)");
            System.out.println("(2) Toy Name");
            System.out.println("(3) Type");
            System.out.println("(4) Back to Main Menu");

            searchOption = appMenu.input.nextInt();
            appMenu.input.nextLine(); // Consume newline character

            switch (searchOption) {
                case 1:
                    serialNumSearch(storeList);
                    break;
                case 2:
                    toyNameSearch(storeList);
                    break;
                case 3:
                	typeSearch();
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }

        } while (searchOption != 4);
    }

        
        
    

    private static void typeSearch() {
        System.out.println("Select the type of toy you're looking for:\n");
        System.out.println("(1) Figures");
        System.out.println("(2) Animals");
        System.out.println("(3) Puzzles");
        System.out.println("(4) Board Games");
        System.out.println("(5) Back to search menu");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("You selected Figures");
                typeFigureSearch();
                break;
            case 2:
                System.out.println("You selected Animals");
                typeAnimalSearch();
                break;
            case 3:
                System.out.println("You selected Puzzles");
                typePuzzleSearch();
                break;
            case 4:
                System.out.println("You selected Board Games");
                typeBgSearch();
                break;
            case 5:
                System.out.println("Back to search menu");
                // Add code for returning to the search menu
                break;
            default:
                System.out.println("Invalid choice");
                // Add code for handling invalid choices
        }
    }
    private static void typeBgSearch() {
        // Display the items starting with 7, 8, or 9
        List<String> matchedKeywords = new ArrayList<>();
        for (String keyword : storeList) {
            if (keyword.startsWith("7") || keyword.startsWith("8") || keyword.startsWith("9")) {
                matchedKeywords.add(keyword);
                System.out.println(matchedKeywords.size() + ". " + keyword);
            }
        }

        // Ask the user to select an item for purchase
        if (matchedKeywords.isEmpty()) {
            System.out.println("No items found with keywords starting with 7, 8, or 9.");
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the number of the item you want to purchase (or 0 to exit): ");
            int choice = scanner.nextInt();

            if (choice >= 1 && choice <= matchedKeywords.size()) {
                String selectedKeyword = matchedKeywords.get(choice - 1);
                System.out.println("You have selected: " + selectedKeyword);
                // Add your purchase logic here

                // Remove the purchased item from the list
                removeItem(storeList, selectedKeyword);

                System.out.println(selectedKeyword + " has been purchased and removed from the list.");
            } else if (choice == 0) {
                System.out.println("Exiting the purchase process.");
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

private static void typePuzzleSearch() {
    // Display the items starting with 4, 5, or 6
    List<String> matchedKeywords = new ArrayList<>();
    for (String keyword : storeList) {
        if (keyword.startsWith("4") || keyword.startsWith("5") || keyword.startsWith("6")) {
            matchedKeywords.add(keyword);
            System.out.println(matchedKeywords.size() + ". " + keyword);
        }
    }

    // Ask the user to select an item for purchase
    if (matchedKeywords.isEmpty()) {
        System.out.println("No items found with keywords starting with 4, 5, or 6.");
    } else {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the item you want to purchase (or 0 to exit): ");
        int choice = scanner.nextInt();

        if (choice >= 1 && choice <= matchedKeywords.size()) {
            String selectedKeyword = matchedKeywords.get(choice - 1);
            System.out.println("You have selected: " + selectedKeyword);
            // Add your purchase logic here

            // Remove the purchased item from the list
            removeItem(storeList, selectedKeyword);

            System.out.println(selectedKeyword + " has been purchased and removed from the list.");
        } else if (choice == 0) {
            System.out.println("Exiting the purchase process.");
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }
}

private static void typeAnimalSearch() {
    // Display the items starting with 2 or 3
    List<String> matchedKeywords = new ArrayList<>();
    for (String keyword : storeList) {
        if (keyword.startsWith("2") || keyword.startsWith("3")) {
            matchedKeywords.add(keyword);
            System.out.println(matchedKeywords.size() + ". " + keyword);
        }
    }

    // Ask the user to select an item for purchase
    if (matchedKeywords.isEmpty()) {
        System.out.println("No items found with keywords starting with 2 or 3.");
    } else {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the item you want to purchase (or 0 to exit): ");
        int choice = scanner.nextInt();

        if (choice >= 1 && choice <= matchedKeywords.size()) {
            String selectedKeyword = matchedKeywords.get(choice - 1);
            System.out.println("You have selected: " + selectedKeyword);
            // Add your purchase logic here

            // Remove the purchased item from the list
            removeItem(storeList, selectedKeyword);

            System.out.println(selectedKeyword + " has been purchased and removed from the list.");
        } else if (choice == 0) {
            System.out.println("Exiting the purchase process.");
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }
}

private static void typeFigureSearch() {
    // Display the items starting with 0 or 1
    List<String> matchedKeywords = new ArrayList<>();
    for (String keyword : storeList) {
        if (keyword.startsWith("0") || keyword.startsWith("1")) {
            matchedKeywords.add(keyword);
            System.out.println(matchedKeywords.size() + ". " + keyword);
        }
    }

    // Ask the user to select an item for purchase
    if (matchedKeywords.isEmpty()) {
        System.out.println("No items found with keywords starting with 0 or 1.");
    } else {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the item you want to purchase (or 0 to exit): ");
        int choice = scanner.nextInt();

        if (choice >= 1 && choice <= matchedKeywords.size()) {
            String selectedKeyword = matchedKeywords.get(choice - 1);
            System.out.println("You have selected: " + selectedKeyword);
            // Add your purchase logic here

            // Remove the purchased item from the list
            removeItem(storeList, selectedKeyword);

            System.out.println(selectedKeyword + " has been purchased and removed from the list.");
        } else if (choice == 0) {
            System.out.println("Exiting the purchase process.");
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }
}


	public static void toyNameSearch(ArrayList<String> itemList) {
        Scanner scanner = new Scanner(System.in);

        // Validate user input for Serial Number (SN)
        String keyword;
        while (true) {
            System.out.println("Enter a Toy Name to search: ");
            keyword = scanner.nextLine();

            // Check if the input contains only characters
            if (keyword.matches("[a-zA-Z]+")) {
                break; // If successful, exit the loop
            } else {
                System.out.println("Error: Please enter characters only no numbers.");
            }
        }

        ArrayList<String> searchResults = new ArrayList<>();

        // Search for items containing the keyword
        for (String item : itemList) {
            if (item.toLowerCase().contains(keyword.toLowerCase())) {
                searchResults.add(item);
            }
        }

        // Display search results
        if (searchResults.isEmpty()) {
            System.out.println("No matching items found.\n");
        } else {
            System.out.println("Matching items:\n");
            for (int i = 0; i < searchResults.size(); i++) {
                System.out.println((i + 1) + ". " + searchResults.get(i));
            }

            // Allow the user to select and remove an item
            System.out.println("Enter the number of the item you would like to purchase (or 0 to go back to menu): ");
            int choice;
            while (true) {
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    break; // If successful, exit the loop
                } catch (NumberFormatException e) {
                    System.out.println("Error: Please enter a valid number.");
                }
            }

            if (choice >= 1 && choice <= searchResults.size()) {
                String itemToRemove = searchResults.get(choice - 1);
                removeItem(itemList, itemToRemove);
                System.out.println(itemToRemove + " has been purchased.\n");
            } else {
                System.out.println("No item purchased.\n");
            }
        }

        // Do not close the scanner here (close it where it's no longer needed)
    }

    

    public static void serialNumSearch(ArrayList<String> itemList) {
        Scanner scanner = new Scanner(System.in);

        // Validate user input for Serial Number (SN)
        String keyword;
        while (true) {
            System.out.println("Enter Serial Number (SN) to search: ");
            try {
                keyword = scanner.nextLine();
                Integer.parseInt(keyword); // Try parsing the input as an integer
                break; // If successful, exit the loop
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid SN containing numbers only.");
            }
        }

        ArrayList<String> searchResults = new ArrayList<>();

        // Search for items containing the keyword
        for (String item : itemList) {
            if (item.toLowerCase().contains(keyword.toLowerCase())) {
                searchResults.add(item);
            }
        }

        // Display search results
        if (searchResults.isEmpty()) {
            System.out.println("No matching items found.\n");
        } else {
            System.out.println("Matching items:\n");
            for (int i = 0; i < searchResults.size(); i++) {
                System.out.println((i + 1) + ". " + searchResults.get(i));
            }

            // Allow user to select and remove an item
            System.out.println("Enter the number of the item you would like to purchase (or 0 to go back to menu): ");
            int choice;
            while (true) {
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    break; // If successful, exit the loop
                } catch (NumberFormatException e) {
                    System.out.println("Error: Please enter a valid number.");
                }
            }

            if (choice >= 1 && choice <= searchResults.size()) {
                String itemToRemove = searchResults.get(choice - 1);
                removeItem(itemList, itemToRemove);
                System.out.println(itemToRemove + " has been purchased.\n");
            } else {
                System.out.println("No item purchased.\n");
            }
        }

        // Do not close the scanner here (close it where it's no longer needed)
    }



    public static void removeItem2(ArrayList<String> itemList, ArrayList<String> searchResults) {
        Iterator<String> iterator = itemList.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (item.equals(searchResults)) {
                iterator.remove();
                break; // Stop after removing the first occurrence
            }
        }
    }

    
    public static void removeItem(ArrayList<String> itemList, String itemToRemove) {
        Iterator<String> iterator = itemList.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (item.equals(itemToRemove)) {
                iterator.remove();
                break; // Stop after removing the first occurrence
            }
        }
    }
}



