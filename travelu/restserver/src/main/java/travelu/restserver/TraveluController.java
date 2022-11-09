package travelu.restserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/entries") // TODO rename??
public class TraveluController {

    @GetMapping(value = "/test")
    @ResponseBody
    public String getTestStr() {
        String str = new String("test");
        return str;
    }
}
