package com.example.williamkwok.appprototype;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Properties;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class menu1_Fragment extends Fragment implements OnClickListener {
    View rootview;
    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    TextView text = null;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.menu1_layout, container, false);
        button1 = (Button) rootview.findViewById(R.id.button1);
        button2 = (Button) rootview.findViewById(R.id.button2);
        button3 = (Button) rootview.findViewById(R.id.button3);
        button4 = (Button) rootview.findViewById(R.id.button4);
        button5 = (Button) rootview.findViewById(R.id.button5);
        //button6 = (Button) findViewById(R.id.button6);

        context = getActivity();

        button1.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        //button6.setOnClickListener(this);
        return rootview;
    }

    public void onClick(View v) {
        int vId = v.getId();
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("testing12345testt@gmail.com", "password12345l");
            }
        });

        pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);

        RetrieveFeedTask task = new RetrieveFeedTask(vId);
        task.execute();
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        int vid = 0;

        public RetrieveFeedTask(int vId)
        {
            vid = vId;
        }

        @Override
        protected String doInBackground(String... params) {
            String textMessage = null;
            switch(vid)
            {
                case R.id.button1:
                    textMessage = "Towel Requested - Button1 clicked";
                    break;
                case R.id.button2:
                    textMessage = "Room Service Requested - Button2 clicked";
                    break;
                case R.id.button3:
                    textMessage = "Amenities - Button3 clicked";
                    break;
                case R.id.button4:
                    textMessage = "Wake Up Call setup - Button4 clicked";
                    break;
                case R.id.button5:
                    textMessage = "Pay per view - Button5 clicked";
                    break;
//                case R.id.button6:
//                    textMessage = "Button6 clicked";
//                    break;
                case 0:
                    throw new RuntimeException("Unknown button ID");
                default:
                    throw new RuntimeException("Unknown button ID");
            }
            try{
                Message message = new MimeMessage (session);
                message.setFrom(new InternetAddress("testing12345testt@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("williamkwok92@gmail.com"));
                message.setSubject(textMessage);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
            Toast.makeText(context.getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
        }
    }
}
