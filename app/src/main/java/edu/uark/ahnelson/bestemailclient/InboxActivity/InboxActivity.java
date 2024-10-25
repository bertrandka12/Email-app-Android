package edu.uark.ahnelson.bestemailclient.InboxActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import edu.uark.ahnelson.bestemailclient.BuildConfig;
import edu.uark.ahnelson.bestemailclient.Injection;
import edu.uark.ahnelson.bestemailclient.R;
import edu.uark.ahnelson.bestemailclient.data.Message;
import edu.uark.ahnelson.bestemailclient.data.MessagesRepository;
import edu.uark.ahnelson.bestemailclient.data.dummydata.DummyDataGenerator;
import edu.uark.ahnelson.bestemailclient.data.dummydata.DummyDataGeneratorCallback;
import util.AppExecutors;

public class InboxActivity extends AppCompatActivity {

    InboxContract.View mView;
    InboxContract.Presenter mPresenter;
    MessagesRepository inbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        mPresenter = new InboxPresenter();
        mView = (InboxContract.View) getSupportFragmentManager().findFragmentById(R.id.fragmentInboxView);
        mPresenter.setView(mView);
        inbox = Injection.provideMessagesRepository(new AppExecutors(),getApplicationContext());
        mPresenter.setRepository(inbox);
        checkFirstRun();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }
//activity_compose
    public void generateDummyData() {
        DummyDataGenerator dummyDataGenerator = new DummyDataGenerator();
        dummyDataGenerator.generateDummyData(new DummyDataGeneratorCallback() {
            @Override
            public void dummyDataCreated(List<Message> messages) {

                for (int i = 0; i < messages.size(); i++) {
                    inbox.createMessage(messages.get(i));
                }
                mPresenter.getMessagesFromRepository();
            }

            @Override
            public void onDataNotCreated() {
                Log.e("MessageProvider", "Data Not Created");
            }
        });
    }


    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {
            generateDummyData();
            prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();

            // TODO This is a new install (or the user cleared the shared preferences)

        } else if (currentVersionCode > savedVersionCode) {
            generateDummyData();
            prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();

            // TODO This is an upgrade
        }

        // Update the shared preferences with the current version code
    }


}