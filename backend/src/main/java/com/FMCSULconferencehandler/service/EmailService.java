package com.FMCSULconferencehandler.service;

import com.FMCSULconferencehandler.model.Participant;

public interface EmailService {

    void sendAccountCreationMessage(Participant participant);
}
