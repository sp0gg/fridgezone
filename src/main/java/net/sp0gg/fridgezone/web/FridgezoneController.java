package net.sp0gg.fridgezone.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.sp0gg.fridgezone.data.repository.FridgezoneRepository;
import net.sp0gg.fridgezone.domain.Item;

@Controller
@RequestMapping("/")
public class FridgezoneController {
	
	private FridgezoneRepository repo;
	
	@Autowired
	public FridgezoneController(FridgezoneRepository repo) {
		this.repo = repo;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String inventory(Model model){
		List<Item> items = repo.findAll();
		model.addAttribute("items", items);
		return "inventory";
	}
	
//	@RequestMapping(method = RequestMethod.GET)
//	public String hello(Model model) {
//		model.addAttribute("message", "iohisdufh");
//		return "hello";
//	}

}
