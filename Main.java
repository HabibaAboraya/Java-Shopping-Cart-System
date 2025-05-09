import javax.swing.*;
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
    public static final ShoppingCart CART = new ShoppingCart();

    public static final int INITIAL_PAYMENT_METHOD_BALANCE = 1000;

    public static void main ( String[] args )
    {
        getCheckoutItems();
        checkout();

    }

    public static String menuString ()
    {
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
        boolean doneShopping = false;
        boolean fullCart = false;
        boolean invalidCheckoutInput = false;

        while ( !doneShopping ) // While the user did not checkout (entered y or yes when asked to checkout)
        {
            Item item = null;

            while ( item == null && !invalidCheckoutInput ) // While the user did not enter a valid item
            {
                item = getUserItem(); // keep fetching a new item from the user
            }

            if ( item != null )
            {
                if ( CART.size() + 1 <= CART.capacity() )
                {
                    CART.add( item );
                }
                else
                {
                    System.out.println( "Shopping Cart is full, could not add item, proceeding to checkout" );
                    JOptionPane.showMessageDialog( null, "Shopping Cart is full, could not add item, proceeding to checkout" );
                    fullCart = true;
                    doneShopping = true;
                }
            }

            if ( !fullCart )
            {
                String input = JOptionPane.showInputDialog( "\nProceed to checkout (Y/N)" ).trim();

                if ( input.equalsIgnoreCase( "Y" ) || input.equalsIgnoreCase( "Yes" ) )
                {
                    doneShopping = true;
                    invalidCheckoutInput = false;
                }
                else if(input.equalsIgnoreCase( "N" ) || input.equalsIgnoreCase( "No" ))
                {
                    invalidCheckoutInput = false;
                }
                else
                {
                    System.out.println( "Invalid input, please try again" );
                    JOptionPane.showMessageDialog( null, "Invalid input, please try again" );
                    invalidCheckoutInput = true;
                }
            }
        }
    }

    public static Item getUserItem ()
    {
        Item toReturn = null;
        boolean validInput = true;

        System.out.println( menuString() );
        String input = JOptionPane.showInputDialog( menuString() );
        Scanner inputScanner = new Scanner( input );

        if ( inputScanner.hasNextInt() )
        {
            int itemQuantity = inputScanner.nextInt();

            if ( inputScanner.hasNextDouble() )
            {
                double itemPrice = inputScanner.nextDouble();

                if ( inputScanner.hasNext() )
                {
                    String itemName = inputScanner.next().trim();

                    for ( int i = 0; i < ITEMS.length && toReturn == null; i++ )
                    {
                        if ( ITEMS[i].getItemName().equalsIgnoreCase( itemName ) )
                        {
                            if ( ITEMS[i].getItemPrice() == itemPrice )
                            {
                                toReturn = new Item( itemName, itemPrice, itemQuantity );
                            }
                            else
                            {
                                System.out.println( "Found an item with the same name but different price, try again\n" );
                                JOptionPane.showMessageDialog( null, "Found an item with the same name but different price, try again\n" );
                                validInput = false;
                            }
                        }
                    }

                    if ( toReturn == null && validInput )
                    {
                        System.out.println( "You entered an item that is not on the menu, try again\n" );
                        JOptionPane.showMessageDialog( null, "You entered an item that is not on the menu, try again\n" );
                    }
                }
                else
                {
                    System.out.println( "You did not enter a valid item name, try again\n" );
                    JOptionPane.showMessageDialog( null, "You did not enter a valid item name, try again\n" );
                }
            }
            else
            {
                System.out.println( "You entered an invalid price, should be a number, try again\n" );
                JOptionPane.showMessageDialog( null, "You entered an invalid price, should be a number, try again\n" );
            }
        }
        else
        {
            System.out.println( "You entered an invalid quantity, should be a number, try again\n" );
            JOptionPane.showMessageDialog( null, "You entered an invalid quantity, should be a number, try again\n" );
        }

        return toReturn;
    }

    public static void checkout ()
    {
        boolean validInput = false;
        int paymentMethod = 0;

        while ( !validInput )
        {
            String input = JOptionPane.showInputDialog(CART.toString() + "\nPlease, select a payment method:\n1- Credit Card\n2- PayPal\n\nEnter the number or name of the payment method or the name:\nExample: 1 or Credit Card or CreditCard" ).trim();

            if ( input.equalsIgnoreCase( "1" ) || input.equalsIgnoreCase( "Credit Card" ) || input.equalsIgnoreCase( "CreditCard" ) )
            {
                paymentMethod = 1;
                validInput = true;
            }
            else if ( input.equalsIgnoreCase( "2" ) || input.equalsIgnoreCase( "PayPal" ) || input.equalsIgnoreCase( "Pay Pal" ))
            {
                paymentMethod = 2;
                validInput = true;
            }
            else
            {
                System.out.println( "Invalid input, please try again" );
                JOptionPane.showMessageDialog( null, "Invalid input, please try again" );
            }
        }

        if ( paymentMethod == 1 )
        {
            checkoutCreditCard();
        }
        else
        {
            checkoutPayPal();
        }
    }

    public static void checkoutCreditCard ()
    {
        boolean validInput = false;
        String cardHolderName = JOptionPane.showInputDialog( "Enter the card holder name: " );
        String cardNumber = "";
        String cvv = "";
        String expiryDate = "";

        while ( !validInput )
        {
            cardNumber = JOptionPane.showInputDialog( "Enter the card number: " );

            if ( isNumber( cardNumber ) && cardNumber.length() == 16 )
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

        while ( !validInput )
        {
            cvv = JOptionPane.showInputDialog( "Enter the cvv: " );

            if ( isNumber( cvv ) && cvv.length() == 3 )
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

        while ( !validInput )
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
        creditCard.setBalance( INITIAL_PAYMENT_METHOD_BALANCE );
        String checkoutReturn = CART.checkout( creditCard );
        System.out.println( checkoutReturn);
        JOptionPane.showMessageDialog( null, checkoutReturn );
    }

    public static void checkoutPayPal ()
    {
        String email = JOptionPane.showInputDialog( "Enter the email: " );
        String password = JOptionPane.showInputDialog( "Enter the password: " );
        PayPal payPal = new PayPal( email, password );
        payPal.setBalance( INITIAL_PAYMENT_METHOD_BALANCE );
        String checkoutReturn = CART.checkout( payPal );
        System.out.println( checkoutReturn );
        JOptionPane.showMessageDialog( null, checkoutReturn );
    }

    public static boolean isNumber ( String input )
    {
        boolean toReturn = true;

        for ( int i = 0; i < input.length() && toReturn; i++ )
        {
            if ( input.charAt( i ) < '0' || input.charAt( i ) > '9' )
            {
                toReturn = false;
            }
        }

        return toReturn;
    }

    public static boolean isDate ( String input )
    {
        boolean toReturn = true;

        if ( input.length() != 10 )
        {
            toReturn = false;
        }
        else
        {
            for ( int i = 0; i < input.length() && toReturn; i++ )
            {
                if ( i == 2 || i == 5 )
                {
                    if ( input.charAt( i ) != '/' )
                    {
                        toReturn = false;
                    }
                }
                else
                {
                    if ( input.charAt( i ) < '0' || input.charAt( i ) > '9' )
                    {
                        toReturn = false;
                    }
                }
            }
        }

        return toReturn;
    }
}
