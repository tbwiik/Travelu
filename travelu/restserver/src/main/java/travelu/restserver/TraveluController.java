package travelu.restserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
@RequestMapping("/api/v1/entries") // TODO rename??
public class TraveluController {

    private final TraveluService traveluService = new TraveluService();

    @GetMapping(value = "/destinationlist")
    @ResponseBody
    public String getTestStr() {
        Gson gson = new Gson();
        String output = new String();
        try {
            output = gson.toJson(traveluService.getDestinationList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
