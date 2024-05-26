package convert;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("/convert")
public class ConverterController {

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public void convertEx(@RequestBody HashMap body) {

    }
}
