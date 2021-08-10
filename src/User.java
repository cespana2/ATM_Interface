import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    //User first name
    private String firstName;

    //User last name
    private String lastName;
    
    //User ID number
    private String uuid;
    
    //MD5 hash of user's pin number
    private byte pinHash[];
    
    //List of accounts for user
    private ArrayList<Account> accounts;

    //Create New User
    public User(String firstName, String lastName, String pin, Bank theBank){

        //Set user's name 
        this.firstName = firstName;
        this.lastName = lastName;

        //Store pin's MD5 hash, rather than original pin for security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: Caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        //Get a new, unique universal ID for user
        this.uuid = theBank.getNewUserUUID();

        //Create empty list of accounts
        this.accounts = new ArrayList<Account>();

        //Print log message
        System.out.printf("New User %s, %s with ID %s created\n", lastName, 
            firstName, this.uuid);

    }

    //Add account for the user
    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount);
    }

    public String getUUID() {
        return this.uuid;
    }

    public String getFirstName() {
        return this.firstName;
    }

    //Check whether a given pin matches the user's pin 
    public boolean validatePin(String aPin) {
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } 
        catch (NoSuchAlgorithmException e) {
            System.err.println("Error: Caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        
        return false;
    }

    //Print summaries of this user's accounts
    public void printAccountsSummary() {

        System.out.printf("\n\n%s %s's accounts summary\n", this.firstName, this.lastName);
        for(int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("  %d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();

    }

    //Get number of accounts the user has
    public int numAccounts() {
        return this.accounts.size();
    }

    //Print transaction history for a particular account
    public void printAcctTransHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }

    //Get the balance of a particular account
    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    //Get UUID of particular account
    public String getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }

    //Add a transaction to a particular account
    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
}
