package edu.uark.ahnelson.bestemailclient.data.dummydata;

import java.util.List;

import edu.uark.ahnelson.bestemailclient.data.Message;

public interface DummyDataGeneratorCallback {

    void dummyDataCreated(List<Message> messages);
    void onDataNotCreated();
}