package net.sp0gg.fridgezone.web;

import net.sp0gg.fridgezone.data.repository.ItemRepository;
import net.sp0gg.fridgezone.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

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
    public String inventory(Model model){
        List<Item> items = repo.findAll();
        model.addAttribute("items", items);
        return "inventory";
    }

    @RequestMapping(value = "/addItem", method=RequestMethod.POST)
    public String addItem(Item item){
        repo.save(item);
        return "redirect:/inventory";
    }

}
