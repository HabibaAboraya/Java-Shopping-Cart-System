public class ShoppingCart
{
    final int CAPACITY = 10;

    int count = 0;
    Item[] orderItems;

    ShoppingCart ()
    {
        orderItems = new Item[CAPACITY];
    }

    public void add ( Item item )
    {
        if ( count < orderItems.length)
        {
            orderItems[count++] = item;
        }
        else
        {
            System.out.println( "Shopping Cart is full" );
        }
    }

    public void remove ( Item item )
    {
        boolean found = false;

        for ( int i = 0; i < orderItems.length; i++ )
        {
            if ( orderItems[i]==( item ) )
            {
                found = true;
                orderItems[i] = null;
                count--;

                for ( int j = i; j < orderItems.length - 1; j++ )
                {
                    orderItems[j] = orderItems[j + 1];
                }
                orderItems[orderItems.length - 1] = null;

                // [1, 2, 3, 4, 5]
                // [1, 2, null, 4, 5]
                // [1, 2, 4, 4, 5]
                // [1, 2, 4, 5, 5]
                // [1, 2, 4, 5, null]
            }
        }

        if ( !found )
        {
            System.out.println( "Could not find item to remove" );
        }
    }

    public double getTotalPayment ()
    {
        double cost = 0.0;

        for ( int i = 0; i < count; i++ )
        {
            cost += ( orderItems[i].getItemPrice() * orderItems[i].getQuantity() );
        }

        return cost;
    }

    public String checkout(PaymentMethod paymentMethod)
    {
        if ( paymentMethod.isValid() )
        {
            return paymentMethod.pay( (int) getTotalPayment() );
        }
        else
        {
            return "Payment Method is not valid";
        }
    }

    public int size()
    {
        return count;
    }

    public int capacity()
    {
        return orderItems.length;
    }

    public String toString()
    {
        String result = "The items in your shopping cart are: \n";

        for ( int i = 0; i < count; i++ )
        {
            result += orderItems[i].getQuantity() + " of the " + orderItems[i].getItemPrice() + " dollar " + orderItems[i].getItemName() + "\n";
        }

        return result;
    }
}
