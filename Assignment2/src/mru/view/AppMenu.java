package mru.view;

import java.util.Scanner;
import mru.controller.Model; // Import the Model class

public class AppMenu {

	public Scanner input;
	/**
	 * Constructor that takes a Model instance
	 */
	public AppMenu(Model model) {
		input = new Scanner(System.in);
	}
    
    /**
     * Lets players input if they want to play the game or search
     * @return
     */
	public int showMainMenu() {
	    System.out.println("***********************************************");
	    System.out.println("*        WELCOME TO TOY STORE COMPANY!        *");
	    System.out.println("***********************************************\n");
	    System.out.println("\tHow May We Help You?\n");
	    System.out.println("\t(1) Search Inventory and Purchase Toy");
	    System.out.println("\t(2) Add New Toy");
	    System.out.println("\t(3) Remove Toy");
	    System.out.println("\t(4) Save & Exit");
	    
	    int option = input.nextInt();
	    return option;
	}


	
		
	}
	