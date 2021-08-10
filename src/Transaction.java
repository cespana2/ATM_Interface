import java.util.Date;

public class Transaction {
    //Amount for this transaction
    private double amount;

    //Timestamp for the transaction
    private Date timestamp;

    //Memo for transaction
    private String memo;

    //Account in which transaction was performed
    private Account inAccount;

    //Create a new Transaction
    public Transaction(double amount, Account inAccount) {

        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";

    }

    //Create a new Transaction
    public Transaction(double amount, Account inAccount, String memo) {

        //Call the two arg constructor
        this(amount, inAccount);

        //Set memo
        this.memo = memo;
         
    }

    //Get the amount of the transaction
    public double getAmount() {
        return this.amount;
    }

    //Get a string summarizing the transaction
    public String getSummaryLine() {

        if(this.amount >= 0) {
            return String.format("%s : $%.02f : %s", this.timestamp.toString(),
                this.amount, this.memo);
        }

        else {
            return String.format("%s : $(%.02f) : %s", this.timestamp.toString(),
                -this.amount, this.memo);
        }
    }
    
}
