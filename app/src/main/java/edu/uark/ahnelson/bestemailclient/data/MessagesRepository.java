package edu.uark.ahnelson.bestemailclient.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import edu.uark.ahnelson.bestemailclient.data.dummydata.DummyDataGenerator;
import edu.uark.ahnelson.bestemailclient.data.dummydata.DummyDataGeneratorCallback;
import util.AppExecutors;

public class MessagesRepository implements MessageDataSource{

    //Memory leak here by including the context - Fix this at some point
    private static volatile MessagesRepository INSTANCE;

    //Thread pool for execution on other threads
    private AppExecutors mAppExecutors;
    //Context for calling MessageProvider
    private Context mContext;

    /**
     * private constructor - prevent direct instantiation
     * @param appExecutors - thread pool
     * @param context
     */
    private MessagesRepository(@NonNull AppExecutors appExecutors, @NonNull Context context){
        mAppExecutors = appExecutors;
        mContext = context;
    }

    /**
     * public constructor - prevent creation of instance if one already exists
     * @param appExecutors
     * @param context
     * @return
     */
    public static MessagesRepository getInstance(@NonNull AppExecutors appExecutors, @NonNull Context context){
        if(INSTANCE == null){
            synchronized (MessagesRepository.class){
                if(INSTANCE == null){
                    INSTANCE = new MessagesRepository(appExecutors, context);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * getMessages runs query in a separate thread, and on success loads data from cursor into a list
     * @param callback
     */
    @Override
    public void getMessages(@NonNull final LoadMessagesCallback callback) {
        Log.d("REPOSITORY","Loading...");
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                String[] projection = {
                        Message.MESSAGE_ID,
                        Message.MESSAGE_SUBJECT,
                        Message.MESSAGE_FROM,
                        Message.MESSAGE_TO,
                        Message.MESSAGE_READ,
                        Message.MESSAGE_DATE,
                        Message.MESSAGE_CONTENT};
                final Cursor c = mContext.getContentResolver().query(Uri.parse("content://" + MessageProvider.AUTHORITY + "/" + MessageProvider.MESSAGE_TABLE_NAME), projection, null, null, null);
                final List<Message> messages = new ArrayList<Message>(0);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(c == null){
                            callback.onDataNotAvailable();
                        } else{
                            if(c.getCount() == 0){
                                callback.onDataNotAvailable();
                            }
                            while(c.moveToNext()) {
                                Message message = new Message();
                                message.setId(c.getInt(c.getColumnIndex(Message.MESSAGE_ID)));
                                message.setSubject(c.getString(c.getColumnIndex(Message.MESSAGE_SUBJECT)));
                                message.setTo(c.getString(c.getColumnIndex(Message.MESSAGE_TO)));
                                message.setFrom(c.getString(c.getColumnIndex(Message.MESSAGE_FROM)));
                                message.setDate(c.getLong(c.getColumnIndex(Message.MESSAGE_DATE)));
                                message.setRead(c.getInt(c.getColumnIndex(Message.MESSAGE_READ)) > 0);
                                message.setContent(c.getString(c.getColumnIndex(Message.MESSAGE_CONTENT)));
                                messages.add(message);
                            }
                            c.close();
                            callback.onToDoItemsLoaded(messages);
                        }
                    }
                });

            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }


    /**
     * Not implemented yet
     * @param messageId
     * @param callback
     */
    @Override
    public void getMessage(@NonNull Integer messageId, @NonNull GetMessageCallback callback) {
        Log.d("REPOSITORY","GetMessage");
        callback.onDataNotAvailable();
    }

    /**
     * saveToDoItem runs contentProvider update in separate thread
     * @param message
     */
    @Override
    public void saveMessage(@NonNull final Message message) {
        Log.d("REPOSITORY","SaveToDoItem");
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                ContentValues myCV = new ContentValues();
                myCV.put(Message.MESSAGE_ID,message.getId());
                myCV.put(Message.MESSAGE_SUBJECT,message.getSubject());
                myCV.put(Message.MESSAGE_TO,message.getTo());
                myCV.put(Message.MESSAGE_FROM,message.getFrom());
                myCV.put(Message.MESSAGE_DATE,message.getDate());
                myCV.put(Message.MESSAGE_READ,message.getRead());
                final int numUpdated = mContext.getContentResolver().update(Uri.parse("content://" + MessageProvider.AUTHORITY + "/" + MessageProvider.MESSAGE_TABLE_NAME), myCV,null,null);
                Log.d("REPOSITORY","Update Messages updated " + String.valueOf(numUpdated) + " rows");
            }
        };
        mAppExecutors.diskIO().execute(runnable);

    }

    /**
     * createMessage runs contentProvider insert in separate thread
     * @param message
     */
    @Override
    public void createMessage(@NonNull final Message message) {
        Log.d("REPOSITORY","CreateMessage");
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                ContentValues myCV = new ContentValues();
                myCV.put(Message.MESSAGE_SUBJECT,message.getSubject());
                myCV.put(Message.MESSAGE_TO,message.getTo());
                myCV.put(Message.MESSAGE_FROM,message.getFrom());
                myCV.put(Message.MESSAGE_DATE,message.getDate());
                myCV.put(Message.MESSAGE_READ,message.getRead());
                myCV.put(Message.MESSAGE_CONTENT,message.getContent().toString());
                final Uri uri = mContext.getContentResolver().insert(Uri.parse("content://" + MessageProvider.AUTHORITY + "/" + MessageProvider.MESSAGE_TABLE_NAME), myCV);
                Log.d("REPOSITORY","Create ToDo finished with URI" + uri.toString());
            }
        };
        mAppExecutors.diskIO().execute(runnable);

    }

    //deleteMessage()
    //@Override
    public void deleteMessage(@NonNull final Message message) {
        Log.d("REPOSITORY","DeleteMessage");
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                ContentValues myCV = new ContentValues();
                myCV.put(Message.MESSAGE_SUBJECT,message.getSubject());
                myCV.put(Message.MESSAGE_TO,message.getTo());
                myCV.put(Message.MESSAGE_FROM,message.getFrom());
                myCV.put(Message.MESSAGE_DATE,message.getDate());
                myCV.put(Message.MESSAGE_READ,message.getRead());
                myCV.put(Message.MESSAGE_CONTENT,message.getContent().toString());
                final int numDeleted = mContext.getContentResolver().delete(Uri.parse("content://" + MessageProvider.AUTHORITY + "/" + MessageProvider.MESSAGE_TABLE_NAME), null,null);
                Log.d("REPOSITORY","Delete Messages deleted " + String.valueOf(numDeleted) + " rows");

            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }




}
