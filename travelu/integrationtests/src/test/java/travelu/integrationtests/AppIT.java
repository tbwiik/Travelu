package travelu.integrationtests;

import travelu.client.Client;
import travelu.core.DateInterval;
import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.restserver.TraveluApplication;
import travelu.restserver.TraveluController;
import travelu.restserver.TraveluService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { TraveluController.class, TraveluApplication.class, TraveluService.class })
public class AppIT extends ApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TraveluController controller;

    private Client client;

    @BeforeEach
    public void setUp() throws Exception {
        client = new Client("http://localhost", port);

    }


}
