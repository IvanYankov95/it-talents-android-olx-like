package model;

import android.util.Patterns;
import java.util.TreeSet;

/**
 * Created by Ivan on 29-Jan-16.
 */
public class UserAcc {

    private long userId;
    String email;//this field is required
    private String password;//this field is required

    private String userName;//this field is required
    private String firstName;//this field is required
    private String lastName;//this field is required

    private String phoneNumber; //this field is required

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

    public UserAcc(String mail, String password, String userName, String firstName, String lastName, String phoneNumber, String city, String address) throws IllegalArgumentException {
        this(mail, password, userName);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPhoneNumber(phoneNumber);
        this.setCity(city);
        this.setAddress(address);

    }

//    public void addOffer(String name, String description, double price, Offer.ProductCondition productCondition, Offer.Category category, String city){
//        Offer offer = new Offer(this, name, description, price, productCondition, category, city);
//
//        this.offers.add(offer);
//    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail (String email) throws IllegalArgumentException{
//        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            //TODO check in databese for existing email.
            this.email = email;
//        } else {
//            throw new IllegalArgumentException();
//        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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

    public long getUserId(){ return this.userId; }

    public void setUserId (long id){this.userId = id;}

}
