package com.rviewer.skeletons.domain.services;

import com.rviewer.skeletons.domain.responses.PongResponse;
import com.rviewer.skeletons.domain.services.persistence.DatabaseConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PongService {

    @Autowired
    DatabaseConnector postgresConnector;

    public PongResponse getPong() {
        return new PongResponse("pong", postgresConnector.getConnectionStatus());
    }
}
