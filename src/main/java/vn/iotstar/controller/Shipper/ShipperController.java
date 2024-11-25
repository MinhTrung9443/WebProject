package vn.iotstar.controller.Shipper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Shipper")
public class ShipperController {
    @GetMapping("")
    public String home() {
        return "Shipper/index";
    }
}
