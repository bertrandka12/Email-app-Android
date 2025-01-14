package edu.uark.ahnelson.bestemailclient.InboxActivity;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.uark.ahnelson.bestemailclient.Injection;
import edu.uark.ahnelson.bestemailclient.data.Message;
import edu.uark.ahnelson.bestemailclient.data.MessageDataSource;
import edu.uark.ahnelson.bestemailclient.data.MessagesRepository;

public class InboxPresenter implements InboxContract.Presenter{

    private InboxContract.View mView;
    private MessagesRepository mInbox;
    private List<Message> messagesList;

    @Override
    public void setView(InboxContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.setPresenter(this);
        messagesList = new ArrayList<>();
        getMessagesFromRepository();
    }

    @Override
    public void getMessagesFromRepository(){
        mInbox.getMessages(new MessageDataSource.LoadMessagesCallback() {
            @Override
            public void onToDoItemsLoaded(List<Message> messages) {
                messagesList = messages;
                mView.notifyMessagesLoaded();
            }

            @Override
            public void onDataNotAvailable() {
                Log.e("InboxPresenter","Data not loaded");
            }
        });
    }

    @Override
    public void sendClicked() {
        mView.startComposeActivity();
    }

    @Override
    public void setRepository(MessagesRepository inbox) {
        mInbox = inbox;
    }

    @Override
    public List<Message> getMessages() {
        return messagesList;
    }


}
