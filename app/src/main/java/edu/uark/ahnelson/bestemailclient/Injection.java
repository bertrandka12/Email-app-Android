package edu.uark.ahnelson.bestemailclient;

import android.content.Context;

import androidx.annotation.NonNull;

import edu.uark.ahnelson.bestemailclient.data.MessageDatabase;
import edu.uark.ahnelson.bestemailclient.data.MessagesRepository;
import util.AppExecutors;

public class Injection {
    public static MessagesRepository provideMessagesRepository(AppExecutors executors,@NonNull Context context) {
        return MessagesRepository.getInstance(executors,context);
    }
}
