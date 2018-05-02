package info.paybeam.www.paybeamv1.PayBeam.ListAdapter;

import java.io.Serializable;

/**
 * Created by dflychew on 26/4/18.
 */



public class Cards implements Serializable{

    // Store the id of the  card image
    private int cardImage;
    // Store the last 4 digits of card
    private String cardNum;
    // Store the expiry date of card
    private String expiryDate;
    // Store if the card is primary
    private Boolean primary;

    // Constructor that is used to create an instance of the Movie object
    public Cards(int cardImage, String cardNum, String expiryDate, Boolean primary) {
        this.cardImage = cardImage;
        this.cardNum = cardNum;
        this.expiryDate = expiryDate;
        this.primary = primary;

    }

    public int getCardImage() {
        return cardImage;
    }

    public void setCardImage(int cardImage) {
        this.cardImage = cardImage;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }
}
