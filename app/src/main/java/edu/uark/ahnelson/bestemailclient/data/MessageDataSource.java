package edu.uark.ahnelson.bestemailclient.data;

import androidx.annotation.NonNull;

import java.util.List;

public interface MessageDataSource {
    /**
     * LoadMessagesCallback interface
     * Example of how to implement callback functions depending on the result of functions in interfaces
     * Currently, onDataNotAvailable is not implemented
     */
    interface LoadMessagesCallback {

        void onToDoItemsLoaded(List<Message> messages);
        void onDataNotAvailable();
    }

    /**
     * GetMessagesCallback interface
     * Not currently implemented
     */
    interface GetMessageCallback {

        void onMessageLoaded(Message message);

        void onDataNotAvailable();
    }

    /**
     * getMessages loads all Messages, calls either success or failure function above
     * @param callback - Callback function
     */
    void getMessages(@NonNull LoadMessagesCallback callback);

    /**
     * getMessages - Get a single Message - currently not implemented
     * @param MessageId - String of the current MessageID to be retrieved
     * @param callback - Callback function
     */
    void getMessage(@NonNull Integer MessageId, @NonNull GetMessageCallback callback);

    /**
     * SaveMessage saves a Message to the database - No callback (should be implemented for
     * remote databases)
     * @param message - Message to be updated
     */
    void saveMessage(@NonNull final Message message);

    /**
     * CreateMessage adds a Message to the database - No callback (should be implemented for
     * remote databases)
     * @param message - Message to be added
     */
    void createMessage(@NonNull Message message);

}
