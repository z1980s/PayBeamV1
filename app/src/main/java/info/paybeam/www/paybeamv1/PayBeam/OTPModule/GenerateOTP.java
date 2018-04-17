package info.paybeam.www.paybeamv1.PayBeam.OTPModule;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GenerateOTP
{
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minutes;
    private int seconds;
    private String dateString;
    private int totalSeconds;

    public GenerateOTP()
    {

    }

    public void getDate()
    {
        Date date = Calendar.getInstance().getTime();
        Format formatter = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");

        String time = formatter.format(date);
        String array[]=time.split(":");

        year = Integer.parseInt(array[0]);
        month = Integer.parseInt(array[1]);
        day = Integer.parseInt(array[2]);
        hour = Integer.parseInt(array[3]);
        minutes = Integer.parseInt(array[4]);
        seconds = Integer.parseInt(array[5]);
    }

    public void dateString()
    {
        convertSeconds();

        dateString = Integer.toString(year)+Integer.toString(month)+
                Integer.toString(day)+Integer.toString(totalSeconds);//Integer.toString(hour)+minutes+
        //Integer.toString(seconds);
    }

    public void convertSeconds()
    {
        totalSeconds = (hour*60*60) + (minutes*60) + seconds;
    }

    public int getOTP()
    {
            getDate();
            convertSeconds();
            dateString();

            int OTP = 0;

            //change later, need to use client's username and password, extract from local files
            OTP = Math.abs((dateString+"userName"+"password").hashCode() % 10000);

            if(OTP < 1000)
                OTP = OTP+1000;

            return OTP;
    }
}
