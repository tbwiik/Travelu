package travelu.restserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import travelu.localpersistence.TraveluHandler;

@SpringBootTest(classes = TraveluController.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class RestserverApplicationTests {

	private final static String API_ADRESS = "/api/v1/entries/";

	private final static String emptyDestinationList = "{\"destinations\":[]}";
	private final static String mockDestinationName = "Mock1"; // Must be same as in mockDestination
	private final static String mockArrivalDate = "01/10/2022";
	private final static String mockDepartureDate = "24/12/2022";
	private final static String mockRating = "3";
	private final static String mockActivity = "\"activities\":[\"Mock it\"],";
	private final static String mockComment = "Mock comment";
	private final static String mockDestination = "{\"name\":\"Mock1\",\"dateInterval\":{\"arrivalDate\":\"02/11/2022\",\"departureDate\":\"26/11/2022\"},\"rating\":2,\"activities\":[],\"comment\":\"Mock comment\"}";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TraveluController traveluController;

	@BeforeAll
	public void testSetUp() throws Exception {

		try {
			TraveluHandler.clearDestinationName();
			TraveluHandler.clearDestinationList();
		} catch (Exception e) {
			fail("Failed to delete files");
		}

	}

	@AfterAll
	public void tearDown() {
		try {
			TraveluHandler.clearDestinationName();
			TraveluHandler.clearDestinationList();
		} catch (Exception e) {
			fail("Failed to delete files");
		}
	}

	@Test
	public void testApp() {
		TraveluApplication.main();
	}

	@Test
	public void testContextLoads() {
		assertNotNull(traveluController);
	}

	@Test
	public void testGetDestinationList() {

		try {
			MvcResult result = mockMvc.perform(get(API_ADRESS + "destinationlist")).andDo(print())
					.andExpect(status().isOk()).andReturn();
			assertEquals(mockDList, result.getResponse().getContentAsString());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@AfterAll
	public void tearDown() throws Exception {
		// TODO clear files
	}

}
