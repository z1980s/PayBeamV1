package info.paybeam.www.paybeamv1.PayBeam.ListAdapter;

/**
 * Created by dflychew on 26/4/18.
 */



public class Cards{

    // Store the id of the  card image
    private int cardImage;
    // Store the last 4 digits of card
    private String cardNum;
    // Store the expiry date of card
    private String expiryDate;

    // Constructor that is used to create an instance of the Movie object
    public Cards(int cardImage, String cardNum, String expiryDate) {
        this.cardImage = cardImage;
        this.cardNum = cardNum;
        this.expiryDate = expiryDate;
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
}
