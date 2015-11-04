package net.sp0gg.fridgezone.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FridgezoneController {
	
    @RequestMapping(value = "/", method=RequestMethod.GET)
	public String root(){
		return "redirect:/inventory";
	}

    @RequestMapping(value = "/inventory", method=RequestMethod.GET)
    public String inventory(){
        return "inventory";
    }
}