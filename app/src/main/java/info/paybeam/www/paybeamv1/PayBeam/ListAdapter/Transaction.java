package info.paybeam.www.paybeamv1.PayBeam.ListAdapter;

/**
 * Created by dflychew on 3/5/18.
 */

public class Transaction {


    // Store the transaction amount
    private String amount;
    // Store the merchant
    private String merchant;
    // Store the expiry date of card
    private String date;
    //Store the card Number
    private String cardNum;

    // Constructor that is used to create an instance of the Movie object
    public Transaction(String amount, String merchant, String date, String cardNum) {
        this.amount = amount;
        this.merchant = merchant;
        this.date = date;
        this.cardNum = cardNum;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}
