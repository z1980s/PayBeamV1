package info.paybeam.www.paybeamv1.PayBeam.NFCReaderModule;

/**
 * Created by Dai Wei on 28/3/2018.
 */

public class CardInfo {
    private static String sData = null;

    public static void SetData(String input) {
        sData = input;
    }

    public static String GetData() {
        if(sData == null) {
            sData = "No Card Data";
        }
        return sData;
    }
}