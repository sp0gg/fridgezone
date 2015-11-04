package net.sp0gg.fridgezone.web;

import net.sp0gg.fridgezone.data.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FridgezoneController {
	
	private ItemRepository repo;
	
	@Autowired
	public FridgezoneController(ItemRepository repo) {
		this.repo = repo;
	}

    @RequestMapping(value = "/", method=RequestMethod.GET)
	public String root(){
		return "redirect:/inventory";
	}

    @RequestMapping(value = "/inventory", method=RequestMethod.GET)
    public String inventory(){
        return "inventory";
    }

}
