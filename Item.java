public class Item
{
    private String itemName;
    private int quantity;
    private double itemPrice;

    Item ( String itemName, double itemPrice, int quantity )
    {
        this.itemName = itemName;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    public String getItemName ()
    {
        return itemName;
    }

    public void setItemName ( String itemName )
    {
        this.itemName = itemName;
    }

    public int getQuantity ()
    {
        return quantity;
    }

    public void setQuantity ( int quantity )
    {
        this.quantity = quantity;
    }

    public double getItemPrice ()
    {
        return itemPrice;
    }

    public void setItemPrice ( double itemPrice )
    {
        this.itemPrice = itemPrice;
    }

    public String toString()
    {
        return "- " + itemName + ", $" + itemPrice;
    }
}
