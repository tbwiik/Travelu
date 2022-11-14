package travelu.restserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import travelu.core.Destination;
import travelu.core.DestinationList;

@RestController
@RequestMapping("/api/v1/entries") // TODO rename??
public class TraveluController {

    private final TraveluService traveluService = new TraveluService();

    @GetMapping(value = "/destinationlist", produces = "application/json")
    public String getDestinationListJSON() {
        Gson gson = new Gson();
        String output = "";
        try {
            output = gson.toJson(traveluService.getDestinationList());
        } catch (Exception e) {
            // TODO
            e.printStackTrace();
        }
        return output;
    }

    @GetMapping(value = "/{destinationName}", produces = "application/json")
    public String getDestinationJSON(final @PathVariable("destinationName") String destinationName) {
        Gson gson = new Gson();
        String result = "";
        try {
            result = gson.toJson(traveluService.getDestinationList().getDestinationCopyByName(destinationName));
        } catch (Exception e) {
            // TODO
        }
        return result;
    }

    @PostMapping(value = "/add", produces = "application/json")
    public void addDestinationJSON(final @RequestBody String destinationJSON) {
        Gson gson = new Gson();
        Destination destination = gson.fromJson(destinationJSON, Destination.class);
        try {
            traveluService.getDestinationList().addDestination(destination);
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println(destination.toString());
    }
}
