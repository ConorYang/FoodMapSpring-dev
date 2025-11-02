package Food.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//import Food.vendor.service.VendorService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

	// @Autowired
	// private VendorService vendorService;
	//
	// @GetMapping("/")
	// public String index() {
	// return "index"; // 會去找 src/main/resources/templates/index.html
	// }

	// @GetMapping("/")
	// public String index(Model model) {
	// List<Vendor> vendors = vendorService.getAllVendors();
	//
	// // 將 vendors 每 3 個一組
	// List<List<Vendor>> vendorRows = new ArrayList<>();
	// for (int i = 0; i < vendors.size(); i += 3) {
	// vendorRows.add(vendors.subList(i, Math.min(i + 3, vendors.size())));
	// }
	//
	// model.addAttribute("vendorRows", vendorRows);
	// return "index";
	// }
	@GetMapping("/login")
	public String login() {
		return "login"; // 會去找 src/main/resources/templates/login.html
	}

	@GetMapping("/register")
	public String register() {
		return "register"; // 會去找 src/main/resources/templates/register.html
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
