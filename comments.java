/*import javax.swing.*;
import java.util.Scanner;
import java.util.Date;

public class Main
{
    public static final Item PIZZA = new Item( "Pizza", 40, 1 );
    public static final Item CHEESE_BURGER = new Item( "Cheeseburger", 20, 1 );
    public static final Item COFFEE = new Item( "Coffee", 5, 1 );
    public static final Item SODA = new Item( "Soda", 4, 1 );
    public static final Item WATER = new Item( "Water", 2, 1 );
    public static final Item[] ITEMS = { PIZZA, CHEESE_BURGER, COFFEE, SODA, WATER };
    public static final ShoppingCart CART = new ShoppingCart(); //  Global because we need to access it from multiple functions
    public static final int INITIAL_PAYMENT_METHOD_BALANCE = 1000; // How much should be in the PayPal or credit card?

    public static void main ( String[] args )
    {
        getCheckoutItems(); // Keep asking the user for items until they check out
        checkout(); // Ask the user for a payment method and checkout
    }

    public static String menuString ()
    {
        // What's on the menu?
        String toReturn = "\nPlease, select a product:";

        for ( int i = 0; i < ITEMS.length; i++ )
        {
            toReturn += "\n" + ITEMS[i].toString();
        }

        toReturn += "\n\nEnter the quantity, price, and name of the item you want to purchase: ";
        toReturn += "\nExample: 3 40 Pizza, means get 3 of the 40 dollar pizzas";

        return toReturn;
    }

    public static void getCheckoutItems ()
    {
        boolean doneShopping = false; // True when the user enters y or yes when asked to check out
        boolean fullCart = false; // True when the cart is full
        boolean invalidCheckoutInput = false; // True when the user enters an invalid input when asked to check out (not y or yes or n or no)

        while ( !doneShopping ) // While the user did not check out (entered y or yes when asked to check out)
        {
            Item item = null;

            while ( item == null && !invalidCheckoutInput ) // While the user did not enter a valid item, and they are not still checking out
            {
                item = getUserItem(); // keep fetching a new item from the user
            }

            if ( item != null ) // If the user entered an item
            {
                if ( CART.size() + 1 <= CART.capacity() ) // If a new item can be added to the cart
                {
                    CART.add( item ); // Add the new item to the cart
                }
                else
                {
                    System.out.println( "Shopping Cart is full, could not add item, proceeding to checkout" );
                    JOptionPane.showMessageDialog( null, "Shopping Cart is full, could not add item, proceeding to checkout" );
                    fullCart = true; // Do not ask the user to check out because the cart is full
                    doneShopping = true; // Stop and check out
                }
            }

            if ( !fullCart ) // If the cart is not full
            {
                String input = JOptionPane.showInputDialog( "\nProceed to checkout (Y/N)" ).trim(); // Ask the user if they want to check out or not

                if ( input.equalsIgnoreCase( "Y" ) || input.equalsIgnoreCase( "Yes" ) ) // If the user entered y or yes
                {
                    doneShopping = true; // Stop and check out
                    invalidCheckoutInput = false; // The user entered a valid input (no need to ask them again)
                }
                else if(input.equalsIgnoreCase( "N" ) || input.equalsIgnoreCase( "No" )) // If the user entered n or no
                {
                    invalidCheckoutInput = false; // The user entered a valid input (no need to ask them again)
                }
                else // If the user entered an invalid input
                {
                    System.out.println( "Invalid input, please try again" ); // Tell the user they entered an invalid input
                    JOptionPane.showMessageDialog( null, "Invalid input, please try again" );
                    invalidCheckoutInput = true; // Do not ask for a new item, ask the user to check out again
                }
            }
        }
    }

    public static Item getUserItem ()
    {
        Item toReturn = null; // The item to be returned from the user input
        boolean validInput = true; // True when the user enters a valid item price, quantity, and name

        System.out.println( menuString() ); // Print the menu
        String input = JOptionPane.showInputDialog( menuString() ); // Ask the user for an item
        Scanner inputScanner = new Scanner( input ); // Put the user input into a scanner to parse it

        if ( inputScanner.hasNextInt() ) // If the user entered a valid quantity
        {
            int itemQuantity = inputScanner.nextInt(); // Save the quantity

            if ( inputScanner.hasNextDouble() ) // If the user entered a valid price
            {
                double itemPrice = inputScanner.nextDouble(); // Save the price

                if ( inputScanner.hasNext() ) // If the user entered a valid item name
                {
                    String itemName = inputScanner.next().trim(); // Save the item name .trim() removes any leading or trailing spaces

                    for ( int i = 0; i < ITEMS.length && toReturn == null; i++ )  // Loop through the items to find the one the user entered
                    {
                        if ( ITEMS[i].getItemName().equalsIgnoreCase( itemName ) ) // If the user entered an item name that is on the menu
                        {
                            if ( ITEMS[i].getItemPrice() == itemPrice ) // Check if it has the same price
                            {
                                toReturn = new Item( itemName, itemPrice, itemQuantity ); // Create the item to be returned with the quantity given
                            }
                            else // If the item has the same name but different price
                            {
                                System.out.println( "Found an item with the same name but different price, try again\n" );
                                JOptionPane.showMessageDialog( null, "Found an item with the same name but different price, try again\n" );
                                validInput = false; // The user entered an invalid input (toReturn is still null)
                            }
                        }
                    }

                    if ( toReturn == null && validInput ) // If the user entered an item name that is not on the menu (name is not found)
                    {
                        System.out.println( "You entered an item that is not on the menu, try again\n" );
                        JOptionPane.showMessageDialog( null, "You entered an item that is not on the menu, try again\n" );
                    }
                }
                else // If the user entered an invalid item name
                {
                    System.out.println( "You did not enter a valid item name, try again\n" );
                    JOptionPane.showMessageDialog( null, "You did not enter a valid item name, try again\n" );
                }
            }
            else // If the user entered an invalid price
            {
                System.out.println( "You entered an invalid price, should be a number, try again\n" );
                JOptionPane.showMessageDialog( null, "You entered an invalid price, should be a number, try again\n" );
            }
        }
        else // If the user entered an invalid quantity
        {
            System.out.println( "You entered an invalid quantity, should be a number, try again\n" );
            JOptionPane.showMessageDialog( null, "You entered an invalid quantity, should be a number, try again\n" );
        }

        return toReturn; // Return the item the user entered, or null if the user entered an invalid item
    }

    public static void checkout ()
    {
        boolean validInput = false; // True when the user enters a valid payment method
        int paymentMethod = 0; // 1 for credit card, 2 for PayPal

        while ( !validInput ) // While the user did not enter a valid payment method
        {
            // Ask the user for a payment method
            String input = JOptionPane.showInputDialog(CART.toString() + "\nPlease, select a payment method:\n1- Credit Card\n2- PayPal\n\nEnter the number or name of the payment method or the name:\nExample: 1 or Credit Card or CreditCard" ).trim();

            // Did the user enter "1" or "Credit Card" or "CreditCard"?
            if ( input.equalsIgnoreCase( "1" ) || input.equalsIgnoreCase( "Credit Card" ) || input.equalsIgnoreCase( "CreditCard" ) )
            {
                paymentMethod = 1;
                validInput = true;
            }
            // Did the user enter "2" or "PayPal" or "Pay Pal"?
            else if ( input.equalsIgnoreCase( "2" ) || input.equalsIgnoreCase( "PayPal" ) || input.equalsIgnoreCase( "Pay Pal" ))
            {
                paymentMethod = 2;
                validInput = true;
            }
            else // The user entered an invalid input
            {
                System.out.println( "Invalid input, please try again" );
                JOptionPane.showMessageDialog( null, "Invalid input, please try again" );
            }
        }

        if ( paymentMethod == 1 ) // If the user chose credit card
        {
            checkoutCreditCard(); // Check out with a credit card
        }
        else // If the user chose PayPal
        {
            checkoutPayPal(); // Check out with PayPal
        }
    }

    public static void checkoutCreditCard ()
    {
        boolean validInput = false;
        String cardHolderName = JOptionPane.showInputDialog( "Enter the card holder name: " ); // Ask the user for the card holder name
        String cardNumber = "";
        String cvv = "";
        String expiryDate = "";

        while ( !validInput ) // Keep asking the user for the card number until they enter a valid one
        {
            cardNumber = JOptionPane.showInputDialog( "Enter the card number: " );

            if ( isNumber( cardNumber ) && cardNumber.length() == 16 ) // If the card number is 16 digits
            {
                validInput = true;
            }
            else
            {
                System.out.println( "Invalid card number, please try again, should be 16 numbers" );
                JOptionPane.showMessageDialog( null, "Invalid card number, please try again, should be 16 numbers" );
            }
        }

        validInput = false;

        while ( !validInput ) // Keep asking the user for the cvv until they enter a valid one
        {
            cvv = JOptionPane.showInputDialog( "Enter the cvv: " );

            if ( isNumber( cvv ) && cvv.length() == 3 ) // If the cvv is 3 digits
            {
                validInput = true;
            }
            else
            {
                System.out.println( "Invalid cvv, please try again, should be 3 numbers" );
                JOptionPane.showMessageDialog( null, "Invalid cvv, please try again, should be 3 numbers" );
            }
        }

        validInput = false;

        while ( !validInput ) // Keep asking the user for the expiry date until they enter a valid one
        {
            expiryDate = JOptionPane.showInputDialog( "Enter the expiry date: " );

            if ( isDate( expiryDate ) )
            {
                validInput = true;
            }
            else
            {
                System.out.println( "Invalid expiry date, please try again, should be in the format of dd/mm/yyyy" );
                JOptionPane.showMessageDialog( null, "Invalid expiry date, please try again,  should be in the format of dd/mm/yyyy" );
            }
        }

        CreditCard creditCard = new CreditCard( cardHolderName, cardNumber, Integer.parseInt( cvv ), new Date( expiryDate ) );
        creditCard.setBalance( INITIAL_PAYMENT_METHOD_BALANCE ); // Set the balance of the credit card to the initial balance set in the main class
        String checkoutReturn = CART.checkout( creditCard );  // Check out with the credit card
        System.out.println( checkoutReturn); // Print the return from the checkout
        JOptionPane.showMessageDialog( null, checkoutReturn );
    }

    public static void checkoutPayPal ()
    {
        String email = JOptionPane.showInputDialog( "Enter the email: " ); // Ask the user for the email
        String password = JOptionPane.showInputDialog( "Enter the password: " ); // Ask the user for the password
        PayPal payPal = new PayPal( email, password ); // Create a new PayPal object with the email and password
        payPal.setBalance( INITIAL_PAYMENT_METHOD_BALANCE ); // Set the balance of the PayPal to the initial balance set in the main class
        String checkoutReturn = CART.checkout( payPal ); // Check out with the PayPal
        System.out.println( checkoutReturn ); // Print the return from the checkout
        JOptionPane.showMessageDialog( null, checkoutReturn );
    }

    public static boolean isNumber ( String input )
    {
        boolean toReturn = true; // True if the input is a number

        for ( int i = 0; i < input.length() && toReturn; i++ ) // For every character in the input string
        {
            if ( input.charAt( i ) < '0' || input.charAt( i ) > '9' ) // If the character is not a number
            {
                toReturn = false; // Return false
            }
        }

        return toReturn;
    }

    public static boolean isDate ( String input )
    {
        boolean toReturn = true; // True if the input is a valid date

        if ( input.length() != 10 ) // If the input is not 10 characters long "dd/mm/yyyy"
        {
            toReturn = false; // Return false
        }
        else // If the input is 10 characters long
        {
            for ( int i = 0; i < input.length() && toReturn; i++ ) // For every character in the input string
            {
                if ( i == 2 || i == 5 ) // If the character should be a "/"
                {
                    if ( input.charAt( i ) != '/' ) // If the character is not a "/"
                    {
                        toReturn = false; // Return false
                    }
                }
                else // If the character should be a number
                {
                    if ( input.charAt( i ) < '0' || input.charAt( i ) > '9' ) // If the character is not a number
                    {
                        toReturn = false; // Return false
                    }
                }
            }
        }

        return toReturn; // Return true if the input is a valid date
    }
}*/
