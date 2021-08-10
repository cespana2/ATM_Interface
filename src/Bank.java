import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;

    private ArrayList<User> users;

    private ArrayList<Account> accounts;

    //Create new Bank object with empty list of users and accounts
    public Bank(String name) {

        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public String getNewUserUUID() {

        //Initialize UUID
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;

        //Continue looping until we get a unique ID
        do {
            uuid = "";
            for(int c = 0; c < len; c++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            //Check to make sure uuid is unique
            nonUnique = false;
            for(User u : this.users) {
                if(uuid.compareTo(u.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        }
        while(nonUnique);

        return uuid;

    }

    public String getNewAccountUUID() {

         //Initialize UUID
         String uuid;
         Random rng = new Random();
         int len = 10;
         boolean nonUnique;
 
         //Continue looping until we get a unique ID
         do {
             uuid = "";
             for(int c = 0; c < len; c++){
                 uuid += ((Integer)rng.nextInt(10)).toString();
             }
 
             //Check to make sure uuid is unique
             nonUnique = false;
             for(Account acc : this.accounts) {
                 if(uuid.compareTo(acc.getUUID()) == 0){
                     nonUnique = true;
                     break;
                 }
             }
         }
         while(nonUnique);
 
         return uuid;
    }

    //Add an account
    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount);
    }

    //Create new user of the bank
    public User addUser(String firstName, String lastName, String pin) {

        //Create a new User and add to list
        User newUser = new User(firstName,lastName,pin,this);
        this.users.add(newUser);

        //Create a savings account for user and add to User and Bank account Lists
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;

    }

    //Get user object associated with userID and pin if they are valid
    public User userLogin(String userID, String pin) {

        //Search through list of users
        for(User u: this.users){

            //Check if user ID is valid
            if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;
            }
        }

        //If we haven't found user or pin is incorrect
        return null;

    }

    //Get name of Bank
    public String getName() {
        return this.name;
    }
     
}
