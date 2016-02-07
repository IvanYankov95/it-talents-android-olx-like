package model;

import android.util.Patterns;
import java.util.TreeSet;

/**
 * Created by Ivan on 29-Jan-16.
 */
public class UserAcc {

    CharSequence email;//this field is required
    private String password;//this field is required

    private String userName;//this field is required
    private String firstName;//this field is required
    private String lastName;//this field is required

    private String phoneNumber; //this field is required
    private String secondPhoneNumber; //optional

    private String city;
    private String address;//optional?

    private TreeSet<Message> messages = new TreeSet<>();
    private TreeSet<Offer> offers = new TreeSet<>();

    public UserAcc(String mail, String password, String userName) throws IllegalArgumentException {
        this.setEmail(mail);
        this.setPassword(password);
        this.setUserName(userName);
        this.messages = new TreeSet<>();
        this.offers = new TreeSet<>();
    }

    public void addOffer(String name, String description, double price, Offer.ProductCondition productCondition, Offer.Category category){
        Offer offer = new Offer(this, name, description, price, productCondition, category);

        this.offers.add(offer);
    }

    public void archiveOffer(Offer offer){
        offer.switchIfActive(false);
    }

    public void reactivateOffer(Offer offer){
        offer.switchIfActive(true);
    }

    public void sendMessage(UserAcc userAcc, String heading, String text){
        Message message = new Message(heading, text);

        userAcc.receiveMessage(message);
    }

    public void receiveMessage(Message message){
        this.messages.add(message);
    }

    public void deleteUserAcc(){
        //TODO delete from database
    }

    public CharSequence getEmail() {
        return email;
    }

    public void setEmail (CharSequence email) throws IllegalArgumentException{
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //TODO check in databese for existing email.
            this.email = email;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getPassword() {
        return password;
    }

    //това ще трябва да се промени само засега е така. Ще трябва да проверяваме дали паролата огтоваря на изискванята но след това да я криптираме и записваме.
    public void setPassword(String password) throws IllegalArgumentException {
        char[] test = password.toCharArray();

        boolean lowercase = false;
        boolean uppercase = false;
        boolean number = false;

        if(test.length < 8)
            throw new IllegalArgumentException();

        for (char aTest : test) {
            if (aTest >= 65 && aTest <= 90)
                lowercase = true;
            if (aTest >= 97 && aTest <= 122)
                uppercase = true;
            if (aTest >= 48 && aTest <= 57)
                number = true;
        }

        if(!lowercase || !uppercase || !number)
            throw new IllegalArgumentException();

        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        //TODO check in database
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecondPhoneNumber() {
        return secondPhoneNumber;
    }

    public void setSecondPhoneNumber(String secondPhoneNumber) {
        this.secondPhoneNumber = secondPhoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
