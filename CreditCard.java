import java.util.Date;

public class CreditCard implements PaymentMethod
{
    private String cardHolderName;
    private String cardNumber;
    private int cvv;
    private Date expiryDate;
    private int balance;

    public CreditCard ( String cardHolderName, String cardNumber, int cvv, Date expiryDate )
    {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    public String getCardHolderName ()
    {
        return cardHolderName;
    }

    public void setCardHolderName ( String cardHolderName )
    {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber ()
    {
        return cardNumber;
    }

    public void setCardNumber ( String cardNumber )
    {
        this.cardNumber = cardNumber;
    }

    public int getCvv ()
    {
        return cvv;
    }

    public void setCvv ( int cvv )
    {
        this.cvv = cvv;
    }

    public Date getExpiryDate ()
    {
        return expiryDate;
    }

    public void setExpiryDate ( Date expiryDate )
    {
        this.expiryDate = expiryDate;
    }

    public int getBalance ()
    {
        return balance;
    }

    public void setBalance ( int balance )
    {
        this.balance = balance;
    }

    public boolean isValid ()
    {
        return expiryDate.compareTo( new Date() ) > 0;
    }

    public String pay ( int amount )
    {
        if ( isValid() )
        {
            if( (balance - amount) >= 0 )
            {
                balance -= amount;
                return "Approved";
            }
            else
            {
                return "Declined, insufficient funds";
            }
        }
        else
        {
            return "Declined, card expired";
        }
    }
}
