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
	private final static String mockDList = "{\"destinations\":[]}";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TraveluController traveluController;

	@BeforeEach
	public void testSetUp() throws Exception {
		// TODO clear files
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
