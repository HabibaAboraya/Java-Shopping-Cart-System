public class PayPal implements PaymentMethod
{
    private String email;
    private String password;
    private int balance;

    public PayPal ( String email, String password )
    {
        this.email = email;
        this.password = password;
        this.balance = 0;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail ( String email )
    {
        this.email = email;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword ( String password )
    {
        this.password = password;
    }

    public int getBalance ()
    {
        return balance;
    }

    public void setBalance ( int balance )
    {
        this.balance = balance;
    }

    @Override
    public boolean isValid ()
    {
        return isValidEmail() && isValidPassword();
    }

    public boolean isValidEmail ()
    {
        return email.matches( "[a-z]{1}+[a-z0-9._-]{2,63}+[@]{1}+[a-z0-9]{1,63}+[.]{1}+[a-z]{2,4}" );
    }

    public boolean isValidPassword()
    {
        boolean hasDigit = false;
        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasSpecialChar = false;

        String specialChars = "@#$%^&*_-'\"><+=!()[]{}|;:,.?/";


        int length = password.length();

        if ( length < 8 )
        {
            return false;
        }

        for ( int i = 0; i < length; i++ )
        {
            char c = password.charAt( i );

            if ( Character.isDigit( c ) )
            {
                hasDigit = true;
            }

            if ( Character.isLowerCase( c ) )
            {
                hasLowercase = true;
            }

            if ( Character.isUpperCase( c ) )
            {
                hasUppercase = true;
            }

            if ( specialChars.indexOf( c ) != -1 )
            {
                hasSpecialChar = true;
            }
        }

        return hasDigit && hasLowercase && hasUppercase && hasSpecialChar;
    }

    @Override
    public String pay ( int amount )
    {
        if ( isValid() && balance >= amount )
        {
            balance -= amount;
            return "Transaction Successful !\n";
        }
        else
        {
            return "Transaction Failed ! Invalid payment method or Insufficient balance. \n";
        }
    }
}


