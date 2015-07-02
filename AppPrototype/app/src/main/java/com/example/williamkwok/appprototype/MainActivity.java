package com.example.williamkwok.appprototype;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


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


public class MainActivity extends Activity implements OnClickListener{

    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    TextView text = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        text = new TextView(this);
        text = (TextView)findViewById(R.id.textView);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        int vId = v.getId();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port","465");

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
                    textMessage = "Button1 clicked";
                    break;
                case R.id.button2:
                    textMessage = "Button2 clicked";
                    break;
                case R.id.button3:
                    textMessage = "Button3 clicked";
                    break;
                case R.id.button4:
                    textMessage = "Button4 clicked";
                    break;
                case R.id.button5:
                    textMessage = "Button5 clicked";
                    break;
                case R.id.button6:
                    textMessage = "Button6 clicked";
                    break;
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
            Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
        }
    }
}
