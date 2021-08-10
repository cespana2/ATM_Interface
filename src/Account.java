import java.util.ArrayList;

public class Account {
    //Name of account
    private String name;

    //Account ID number
    private String uuid;

    //User object that owns account
    private User holder;

    //List of transactions under this account
    private ArrayList<Transaction> transactions;

    //Create new Account
    public Account(String name, User holder, Bank theBank){

        //Set Account name and holder
        this.name = name;
        this.holder = holder;

        //Get new account UUID
        this.uuid = theBank.getNewAccountUUID();

        //Initalize transactions
        this.transactions = new ArrayList<Transaction>();
    }

    public String getUUID() {
        return this.uuid;
    }

    //Get summary line for the account 
    public String getSummaryLine() {

        //Get the account's balance
        double balance = this.getBalance();

        //Format the summary line, depending on whether the balance is negative 
        if(balance >= 0) {
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        }

        else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
        }
    } 

    public double getBalance() {
        
        double balance = 0;
        for(Transaction t: this.transactions) {
            balance += t.getAmount();
        }

        return balance;
    }

    //Print the transaction history of the account
    public void printTransHistory() {

        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for(int t = this.transactions.size()-1; t>=0; t--) {
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    //Add a new transaction in this account
    public void addTransaction(double amount, String memo) {

        //Create new transaction object and add it to list
        Transaction newTrans = new Transaction(amount, this, memo);
        this.transactions.add(newTrans);

    }

}
