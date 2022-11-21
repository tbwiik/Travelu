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

	/**
	 * Testing getting destination-list
	 */
	@Test
	public void testGetDestinationList() {

		try {

			// Get destination-list
			MvcResult result = mockMvc.perform(get(API_ADRESS + "destinationlist")).andDo(print())
					.andExpect(status().isOk()).andReturn();

			// Check that destination-list is empty
			assertEquals(emptyDestinationList, result.getResponse().getContentAsString());

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test storing and getting current destination
	 */
	@Test
	public void testStoreAndGetCurrentDestination() {
		try {

			// Add current destination
			mockMvc.perform(put(API_ADRESS + "storeCurrent").content("Mock"))
					.andDo(print()).andExpect(status().isOk())
					.andReturn();

			// Get current destination
			MvcResult result = mockMvc.perform(get(API_ADRESS + "/currentDestination").characterEncoding("UTF-8"))
					.andDo(print())
					.andExpect(status().isOk())
					.andReturn();

			// Check that storing was successfull
			assertEquals("Mock", result.getResponse().getContentAsString());

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test adding and getting destination
	 */
	@Test
	public void testAddAndGetDestination() {
		try {

			// Add destination
			mockMvc.perform(post(API_ADRESS + "add").content(mockDestination))
					.andDo(print()).andExpect(status().isOk())
					.andReturn();

			// Get same destination
			MvcResult getResult = mockMvc.perform(get(API_ADRESS + mockDestinationName).characterEncoding("UTF-8"))
					.andDo(print())
					.andExpect(status().isOk())
					.andReturn();

			// Check that this destination is same as input
			assertEquals(mockDestination, getResult.getResponse().getContentAsString());

		} catch (Exception e) {
			fail(e.getMessage());
		}

		// Tear down destination()
		tearDownDestination();
	}

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@AfterAll
	public void tearDown() throws Exception {
		// TODO clear files
	}

}
