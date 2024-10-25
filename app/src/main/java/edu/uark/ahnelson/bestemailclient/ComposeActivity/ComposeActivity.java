package edu.uark.ahnelson.bestemailclient.ComposeActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.uark.ahnelson.bestemailclient.Injection;
import edu.uark.ahnelson.bestemailclient.R;
import edu.uark.ahnelson.bestemailclient.data.Message;
import edu.uark.ahnelson.bestemailclient.data.MessageDataSource;
import edu.uark.ahnelson.bestemailclient.data.MessagesRepository;
import util.AppExecutors;

public class ComposeActivity extends AppCompatActivity {

    MessagesRepository inbox;
    Message myMessage;

    //private static final String BTN_SEND = "btnSend";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Intent callingIntent = this.getIntent();
        Integer emailId = callingIntent.getIntExtra("email_id",-1);
        myMessage = new Message();
        inbox = Injection.provideMessagesRepository(new AppExecutors(),getApplicationContext());
        if(emailId == -1){
            populateNewEmail();
        }else{
            populateExistingEmail(emailId);
        }
    }

    private void populateExistingEmail(Integer emailId){
        Log.d("ComposeActivity","EmailId= "+emailId);
        inbox.getMessage(emailId, new MessageDataSource.GetMessageCallback() {
            @Override
            public void onMessageLoaded(Message message) {
                myMessage = message;
                //populateViews();
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void populateNewEmail(){
        return;
    }

    private void createMessage(){
        Message message = new Message();
        message.setTo("ahnelson@uark.edu");
        message.setFrom("info@csce.uark.edu");
        message.setSubject("Advising Sessions Soon!");
        message.setContent("Remember to start scheduling advising soon!");
        message.setDate(System.currentTimeMillis());
        message.setRead(Boolean.FALSE);
        if(inbox!= null){
            inbox.createMessage(message);
        }
    }

    public void onClick(View v){
        createMessage();
    }


}