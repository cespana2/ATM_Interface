import java.util.Scanner;

public class AtmApp  {

    public static void main(String[] args) {

        //Initialize Scanner
        Scanner sc = new Scanner(System.in);

        //Initialize Bank
        Bank theBank = new Bank("Bank of Talis");

        //Add a user, which also creates a savings account
        User aUser = theBank.addUser("Carlos", "Espana", "1234");

        //Add a checking account for our user
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);
        
        User curUser;
        while(true) {

            //Stay in login prompt until successful login
            curUser = AtmApp.mainMenuPrompt(theBank, sc);

            //Stay in main menu until user quits
            AtmApp.printUserMenu(curUser, sc);

        }

    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc) {

        //Initialize
        String userID;
        String pin;
        User authUser;

        //Prompt the user for ID/pin combo until correct one is reached
        do {

            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

            //Try to ger user object corresponding to ID and pin combo
            authUser = theBank.userLogin(userID, pin);
            if(authUser == null){
                System.out.println("Incorrect user ID/pin combination" + 
                    "Please try again.");
            }
            
        } while (authUser == null); //Continue looping until successful login

        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner sc){

        //Print a summary of user's accounts
        theUser.printAccountsSummary();

        //Initialize
        int choice;

        //User menu
        do{
            System.out.printf("Welcome %s, what would you like to do?\n", 
                theUser.getFirstName());
            
            System.out.println(" 1) Show Account Transaction History");
            System.out.println(" 2) Make a Withdrawal");
            System.out.println(" 3) Make a Deposit");
            System.out.println(" 4) Make a Transfer");
            System.out.println(" 5) Quit");
            System.out.println();
            System.out.println("Enter Choice: ");
            choice = sc.nextInt();

            if(choice < 1 || choice > 5){
                System.out.println("Invalid Choice. Please Choose 1-5.");
            }

        } while(choice < 1 || choice > 5);

        //Process the choice
        switch(choice) {

            case 1: 
                AtmApp.showTransHistory(theUser, sc);
                break;

            case 2:
                AtmApp.withdrawFunds(theUser, sc);
                break;

            case 3:
                AtmApp.depositFunds(theUser, sc);
                break;
        
            case 4:
                AtmApp.transferFunds(theUser, sc);
                break;

            case 5:
                //Gobble up rest of input line
                sc.nextLine();
                break;

        }

        //Redisplay menu unless user wants to quit
        if(choice != 5){
            AtmApp.printUserMenu(theUser, sc);
        }
    }

    //Show the transaction history for an account
    public static void showTransHistory(User theUser, Scanner sc) {

        int theAcct;

        //Get which account and look at its history
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + 
                "whose transactions you want to see: ", theUser.numAccounts());
            
                theAcct = sc.nextInt()-1;
                if(theAcct < 0 || theAcct >= theUser.numAccounts()) {
                    System.out.println("Invalid Account. Please Try Again.");
                }

            
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());

        //Print the transaction history
        theUser.printAcctTransHistory(theAcct);
    }

    //Process transferring funds from one account to another
    public static void transferFunds(User theUser, Scanner sc) {

        //Initialize
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        //Get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + 
                "to transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid Account. Please Try Again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts() );
        acctBal = theUser.getAcctBalance(fromAcct);

        //Get the account to transfer to 
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + 
                "to transfer to: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid Account. Please Try Again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        //Get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", 
                acctBal);
            amount = sc.nextDouble();
            if(amount < 0) {
                System.out.println("Amount must be greater than zero.");
            }

            else if(amount > acctBal) {
                System.out.printf("Amount must not be greater than balance" + 
                    "of $%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        //Finally do the transfer
        theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
            "Transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format(
            "Transfer to account %s", theUser.getAcctUUID(fromAcct)));
    }

    //Process a fund withdrawal from an account
    public static void withdrawFunds(User theUser, Scanner sc) {
        
        //Initialize
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        //Get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + 
                "to withdraw from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid Account. Please Try Again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts() );
        acctBal = theUser.getAcctBalance(fromAcct);

        //Get the amount to transfer
        do {
            System.out.printf("Enter the amount to withdraw (max $%.02f): $", 
                acctBal);
            amount = sc.nextDouble();
            if(amount < 0) {
                System.out.println("Amount must be greater than zero.");
            }

            else if(amount > acctBal) {
                System.out.printf("Amount must not be greater than balance" + 
                    "of $%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        //Gobble up rest of input line
        sc.nextLine();

        //Get the memo
        System.out.print ("Enter memo: ");
        memo = sc.nextLine();

        //Do the withdrawal
        theUser.addAcctTransaction(fromAcct, -1*amount, memo);
    }

    //Process a fund deposit to an account
    public static void depositFunds(User theUser, Scanner sc) {
        
        //Initialize
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        //Get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + 
                "to deposit from: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid Account. Please Try Again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts() );
        acctBal = theUser.getAcctBalance(toAcct);

        //Get the amount to transfer
        do {
            System.out.printf("Enter the amount to deposit (max $%.02f): $", 
                acctBal);
            amount = sc.nextDouble();
            if(amount < 0) {
                System.out.println("Amount must be greater than zero.");
            }
        } while (amount < 0);

        //Gobble up rest of input line
        sc.nextLine();

        //Get the memo
        System.out.print("Enter memo: ");
        memo = sc.nextLine();

        //Do the withdrawal
        theUser.addAcctTransaction(toAcct, amount, memo);
    }
}
