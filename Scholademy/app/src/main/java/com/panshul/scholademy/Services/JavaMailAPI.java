package com.panshul.scholademy.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.panshul.scholademy.Activity.HomeScreen;
import com.panshul.scholademy.R;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import static android.content.Context.NOTIFICATION_SERVICE;

public class JavaMailAPI extends AsyncTask<Void,Void,Void>  {

    private Context mContext;
    private Session mSession;

    private String mEmail;
    private String mSubject;
    private String mMessage;
    private String mFile;
    private static final String TAG = "NotificationService";
    private static final String CHANNEL_ID = "PushNotifications";
    NotificationCompat.Builder notificationBuilder;
    NotificationManager notificationManager;

    private ProgressDialog mProgressDialog;
    NotificationManagerCompat notificationManagerCompat;

    //Constructor
    public JavaMailAPI(Context mContext, String mEmail, String mSubject, String mMessage, String mFile) {
        this.mContext = mContext;
        this.mEmail = mEmail;
        this.mSubject = mSubject;
        this.mMessage = mMessage;
        this.mFile = mFile;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Show progress dialog while sending email
        // mProgressDialog = ProgressDialog.show(mContext,"Uploading Paper", "Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismiss progress dialog when message successfully send

    }



    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        mContext.startActivity(new Intent(mContext, HomeScreen.class));
        Properties props = new Properties();
        createNotificationChannel();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        mSession = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Utils.EMAIL, Utils.PASSWORD);
                    }
                });

        try {
            sendNotification("Paper Upload","Paper Uploading...");
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(mSession);

            //Setting sender address
            mm.setFrom(new InternetAddress(Utils.EMAIL));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
            //Adding subject
            mm.setSubject(mSubject);
            //Adding message
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText(mMessage);

            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            String filename = mFile;//change accordingly
            FileDataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            mm.setContent(multipart);

            //Sending email
            Transport.send(mm);

            //mProgressDialog.dismiss();

            Thread thread = new Thread(){
                public void run(){
                    Looper.prepare();
                    Toast.makeText(mContext, "Paper uploaded successfully!", Toast.LENGTH_LONG).show();
                    Looper.loop();
                    //sendNotification1("Paper Upload","Paper Uploaded");


                }
            };
            thread.start();

        } catch (MessagingException e) {

            //mProgressDialog.dismiss();

            Thread thread = new Thread(){
                public void run(){
                    Looper.prepare();

                    Toast.makeText(mContext, "Error uploading paper!", Toast.LENGTH_LONG).show();
                    //sendNotification1("Paper Upload","Paper upload Unsuccessful. Please try again");

                    Looper.loop();
                    //sendNotification1("Paper Upload","Paper Upload Failed");
                    notificationBuilder.setContentText("Uploaded Failed")
                            .setProgress(0,0,false);
                    notificationManagerCompat.notify(0,notificationBuilder.build());
                    //mContext.startActivity(new Intent(mContext,MainActivity.class));
                }
            };
            thread.start();
            e.printStackTrace();
        }
        return null;
    }
    private void sendNotification(String title, String messageBody) {

        Intent intent = new Intent(mContext, HomeScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0 , intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notificationBuilder = new NotificationCompat.Builder(mContext,CHANNEL_ID)
                .setSmallIcon(R.drawable.googlelogo)
                //.setBadgeIconType(13)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(mContext.getResources().getColor(R.color.cardColor))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .setProgress(100, 10, true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        notificationManagerCompat = NotificationManagerCompat.from(mContext);
        notificationManagerCompat.notify(0,notificationBuilder.build());

    }
    //ANDROID 8.0 AND ABOVE
    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyNotifications";
            String description = "All MyNotifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager =(NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
}