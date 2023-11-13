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
	
    private ArrayList<Toy> toys = new ArrayList<Toy>();
    private final String FILE_PATH = "src\\toys.txt";
    private AppMenu appMenu;

    /**
     * Default constructor initializes the wordList, appMenu, and launches the application
     * @throws Exception 
     */
    public Model() throws Exception {
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
					//figures SN, name, brand, price, count, age, mat
					Toy toy = new Figures(splittedLine[0], splittedLine[1], splittedLine[2], Double.parseDouble(splittedLine[3]), Integer.parseInt(splittedLine[4]), Integer.parseInt(splittedLine[5]), splittedLine[6].charAt(0));
					toys.add(toy);
				}
				else if (splittedLine[0].charAt(0) == '2' || splittedLine[0].charAt(0) == '3') {
					//Animals mat size
					Toy toy = new Animals(splittedLine[0], splittedLine[1], splittedLine[2], Double.parseDouble(splittedLine[3]), Integer.parseInt(splittedLine[4]), Integer.parseInt(splittedLine[5]), splittedLine[6], splittedLine[7].charAt(0));
					toys.add(toy);
				}
				else if (splittedLine[0].charAt(0) == '4' || splittedLine[0].charAt(0) == '5' || splittedLine[0].charAt(0) == '6') {
					//puzzles type
					Toy toy = new Puzzles(splittedLine[0], splittedLine[1], splittedLine[2], Double.parseDouble(splittedLine[3]), Integer.parseInt(splittedLine[4]), Integer.parseInt(splittedLine[5]), splittedLine[6].charAt(0));
					toys.add(toy);
				}
				else {
					//Board games min, max, designers
					splitPlayers = splittedLine[6].split("-");
					Toy toy = new BoardGames(splittedLine[0], splittedLine[1], splittedLine[2], Double.parseDouble(splittedLine[3]), Integer.parseInt(splittedLine[4]), Integer.parseInt(splittedLine[5]), Integer.parseInt(splitPlayers[0]), Integer.parseInt(splitPlayers[1]), splittedLine[7]);
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
                        removeToy(toys);
                        break;
                    case 4:
                        save(toys, "res\\toys.txt");
                        flag = false; // Exit the loop when the user chooses to save and exit
                        break;
                    default:
                        System.out.println("Invalid option. Please choose again.");
                }
            }
        }
    }


    private void save(ArrayList<Toy> toys, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Toy toy : toys) {
                writer.write(toy.formatForSave());
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
        
        String SN;
        while (true) {
            System.out.print("\nEnter Serial Number (10 digits): ");
            SN = scanner.next();
            if (SN.matches("\\d{10}")) {

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

        if (SN.startsWith("0") || SN.startsWith("1")) {
        	System.out.println("\nEnter Toy Classification: ");
            char classification = scanner.next().charAt(0);
            Toy toy = new Figures(SN, name, brand, price, count, age, classification);
            toys.add(toy);
        }
        else if (SN.startsWith("2") || SN.startsWith("3")) {
        	System.out.println("\nEnter Toy Material: ");
            String material = scanner.next();
            System.out.println("\nEnter Toy Size: ");
            char size = scanner.nextLine().charAt(0);
            Toy toy = new Animals(SN, name, brand, price, count, age, material, size);
            toys.add(toy);
        }
        else if (SN.startsWith("4") || SN.startsWith("5") || SN.startsWith("6")) {
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
    private boolean isSerialNumberUnique(String newSN) {
        for (Toy existingToy : toys) {
            if (existingToy.getSN() == newSN) {
                return false; // Serial Number already exists, not unique
            }
        }
        return true; // Serial Number is unique
    }
    


    private void removeToy(ArrayList<Toy> toys2) {
        Scanner scanner = new Scanner(System.in);

        // Validate user input for Serial Number (SN)
        String keyword;
        while (true) {
            System.out.println("Enter Serial Number (SN) to search: ");
            try {
                keyword = scanner.nextLine();
                Long.parseLong(keyword); // Try parsing the input as a long
                break; // If successful, exit the loop
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid SN containing numbers only.");
            }
        }

        ArrayList<Toy> searchResults = new ArrayList<>();

        // Search for items containing the keyword
        for (Toy item : toys2) {
            if (String.valueOf(item.getSN()).toLowerCase().contains(keyword.toLowerCase())) {
                searchResults.add(item);
            }
        }

        // Display search results
        if (searchResults.isEmpty()) {
            System.out.println("No matching items found.\n");
        } else {
            System.out.println("Matching item(s):\n");
            for (int i = 0; i < searchResults.size(); i++) {
                System.out.println((i + 1) + ". " + searchResults.get(i).toString());
            }

            // Ask the user for confirmation to remove the toy
            System.out.println("Do you want to remove the toy? (Y/N)");
            String confirmation = scanner.nextLine().toLowerCase();

            if (confirmation.equalsIgnoreCase("Y")) {
                // Remove the toy using the removeItem() method
                toys2.removeAll(searchResults);
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
                    serialNumSearch(toys);
                    break;
                case 2:
                    toyNameSearch(toys);
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

        
        
    

    private void typeSearch() {
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
    private void typeBgSearch() {
    	// Display the items starting with 4, 5, or 6
        List<String> matchedKeywords = new ArrayList<>();
        for (Toy toy : toys) {
            if (String.valueOf(toy.getSN()).startsWith("7") || String.valueOf(toy.getSN()).startsWith("8") || String.valueOf(toy.getSN()).startsWith("9") ) {
                matchedKeywords.add(toyToString(toy));
                System.out.println(matchedKeywords.size() + ". " + toyToString(toy));
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
                removeItem(toys, selectedKeyword);

                System.out.println(selectedKeyword + " has been purchased and removed from the list.");
            } else if (choice == 0) {
                System.out.println("Exiting the purchase process.");
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

private void typePuzzleSearch() {
    // Display the items starting with 4, 5, or 6
    List<String> matchedKeywords = new ArrayList<>();
    for (Toy toy : toys) {
        if (String.valueOf(toy.getSN()).startsWith("4") || String.valueOf(toy.getSN()).startsWith("5") || String.valueOf(toy.getSN()).startsWith("6") ) {
            matchedKeywords.add(toyToString(toy));
            System.out.println(matchedKeywords.size() + ". " + toyToString(toy));
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
            removeItem(toys, selectedKeyword);

            System.out.println(selectedKeyword + " has been purchased and removed from the list.");
        } else if (choice == 0) {
            System.out.println("Exiting the purchase process.");
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }
}
private void typeAnimalSearch() {
	// Display the items starting with 2 or 4
    List<String> matchedKeywords = new ArrayList<>();
    for (Toy toy : toys) {
        if (String.valueOf(toy.getSN()).startsWith("2") || String.valueOf(toy.getSN()).startsWith("3")) {
            matchedKeywords.add(toyToString(toy));
            System.out.println(matchedKeywords.size() + ". " + toyToString(toy));
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
            removeItem(toys, selectedKeyword);

            System.out.println(selectedKeyword + " has been purchased and removed from the list.");
        } else if (choice == 0) {
            System.out.println("Exiting the purchase process.");
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }
}

private void typeFigureSearch() {
    // Display the items starting with 0 or 1
    List<String> matchedKeywords = new ArrayList<>();
    for (Toy toy : toys) {
        if (String.valueOf(toy.getSN()).startsWith("0") || String.valueOf(toy.getSN()).startsWith("1")) {
            matchedKeywords.add(toyToString(toy));
            System.out.println(matchedKeywords.size() + ". " + toyToString(toy));
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
            removeItem(toys, selectedKeyword);

            System.out.println(selectedKeyword + " has been purchased and removed from the list.");
        } else if (choice == 0) {
            System.out.println("Exiting the purchase process.");
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }
}


public void toyNameSearch(ArrayList<Toy> toys2) {
    Scanner scanner = new Scanner(System.in);

    // Validate user input for Toy Name
    String keyword;
    while (true) {
        System.out.println("Enter a Toy Name to search: ");
        keyword = scanner.nextLine();

        // Check if the input contains only characters
        if (keyword.matches("[a-zA-Z]+")) {
            break; // If successful, exit the loop
        } else {
            System.out.println("Error: Please enter characters only, no numbers.");
        }
    }

    ArrayList<Toy> searchResults = new ArrayList<>();

    // Search for items containing the keyword
    for (Toy item : toys2) {
        if (item.getName().toLowerCase().contains(keyword.toLowerCase())) {
            searchResults.add(item);
        }
    }

    // Display search results
    if (searchResults.isEmpty()) {
        System.out.println("No matching items found.\n");
    } else {
        System.out.println("Matching items:\n");
        for (int i = 0; i < searchResults.size(); i++) {
            System.out.println((i + 1) + ". " + searchResults.get(i).getName());
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
            Toy itemToRemove = searchResults.get(choice - 1);
            removeToyBySerialNumber(toys2, itemToRemove.getSN());
            System.out.println(itemToRemove.getName() + " has been purchased.\n");
        } else {
            System.out.println("No item purchased.\n");
        }
    }

    // Do not close the scanner here (close it where it's no longer needed)
}

    

    public void serialNumSearch(ArrayList<Toy> toys2) {
        Scanner scanner = new Scanner(System.in);

        // Validate user input for Serial Number (SN)
        String keyword;
        while (true) {
            System.out.println("Enter Serial Number (SN) to search: ");
            try {
                keyword = scanner.nextLine();
                Long.parseLong(keyword); // Try parsing the input as a long
                break; // If successful, exit the loop
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid SN containing numbers only.");
            }
        }


        ArrayList<String> searchResults = new ArrayList<>();

        // Search for items containing the keyword
        for (Toy item : toys2) {
            if (item.getSN() == Long.parseLong(keyword)) {
                searchResults.add(item.toString());
            }
        }

        // Display search results
        if (searchResults.isEmpty()) {
            System.out.println("No matching items found.\n");
        } else {
            System.out.println("Matching items:\n");
            for (long i = 0; i < searchResults.size(); i++) {
                System.out.println((i + 1) + ". " + toys2.get((int) i).getSN() + ": " + searchResults.get((int) i));
            }


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
            	long serialNumberToRemove = Long.parseLong(searchResults.get(choice - 1));
            	removeToyBySerialNumber(toys2, serialNumberToRemove);



            	System.out.println(serialNumberToRemove + " has been purchased.\n");
            } else {
                System.out.println("No item purchased.\n");
            }
        }

        // Do not close the scanner here (close it where it's no longer needed)
    
    private void removeToyBySerialNumber(ArrayList<Toy> toyList, long serialNumberToRemove) {
        Iterator<Toy> iterator = toyList.iterator();

        while (iterator.hasNext()) {
            Toy toy = iterator.next();
            if (toy.getSN() == serialNumberToRemove) {
                iterator.remove();
                break; // Assuming you want to remove only one matching item
            }
        }
    }
    
    private String toyToString(Toy toy) {
        // Convert a Toy object to a String representation
        // Modify this method based on your Toy class structure
    	return toy.getSN() + ";" + toy.getName() + ";" /* other properties */;
    }

    public static void removeItem2(ArrayList<String> itemList, ArrayList<String> searchResults) {
        Iterator<String> iterator = itemList.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (searchResults.contains(item)) {
                iterator.remove();
                // You may choose whether to break after the first occurrence or continue removing all occurrences.
            }
        }
    }




    
    public static void removeItem(ArrayList<Toy> toys2, String itemToRemove) {
        Iterator<Toy> iterator = toys2.iterator();
        while (iterator.hasNext()) {
            Toy item = iterator.next();
            if (item.equals(itemToRemove)) {
                iterator.remove();
                break; // Stop after removing the first occurrence
            }
        }
    }
}
