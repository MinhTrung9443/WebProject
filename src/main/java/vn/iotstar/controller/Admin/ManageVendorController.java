package vn.iotstar.controller.Admin;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vn.iotstar.entity.Account;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.Vendor;
import vn.iotstar.service.IVendorService;

@Controller
@RequestMapping("/Admin/vendor")
public class ManageVendorController {

	@Autowired
	IVendorService vendorService;

	@RequestMapping("")
	public String getVendor(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		int count = (int) vendorService.count();
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

		int totalPages = vendorService.findAll(pageable).getTotalPages();
		Page<Vendor> list = vendorService.findAll(pageable);
		int start = Math.max(1, currentPage - 2);
		int end = Math.min(currentPage + 2, totalPages);

		if (totalPages > count) {
			if (end == totalPages)
				start = end - count;
			else if (start == 1) {
				end = start + count;
			}
		}

		List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

		model.addAttribute("list", list);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pageNumbers", pageNumbers);
		return "Admin/vendor/list";
	}
	
	@GetMapping("/detail/{id}")
	public String viewUserDetail(@PathVariable("id") int id, Model model) {
		Optional<Vendor> optVendor = vendorService.findById(id);
		if (optVendor.isPresent()) {
			Vendor vendor = optVendor.get();
			model.addAttribute("vendor", vendor);
			return "Admin/vendor/detail";
		}
		return "Admin/vendor";
	}
	
	@GetMapping("/add")
	public String addvendor(Model model) {
		Vendor vendor = new Vendor();
		Account account = new Account();

		vendor.setAccount(account);
		model.addAttribute("vendor", vendor);
		return "Admin/vendor/add";
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView editVendor(ModelMap model, @PathVariable("id") Integer id) {
		Optional<Vendor> optVendor = vendorService.findById(id);

		Vendor vendor = new Vendor();
		if (optVendor.isPresent()) {
			Vendor entity = optVendor.get();

			BeanUtils.copyProperties(entity, vendor);

			model.addAttribute("vendor", vendor);
	

			return new ModelAndView("Admin/vendor/edit", model);
		}
		return new ModelAndView("forward:/Admin/vendor", model);
	}
	
	public boolean checkExistPhone(ModelMap model, Vendor vendor, int id) {
		if (vendorService.findByPhone(vendor.getPhone()) != null) {
			if (id != 0) {
				if (vendorService.findByPhone(vendor.getPhone()).getId() != id) {
					model.addAttribute("checkPhone", "SỐ ĐIỆN THOẠI NÀY ĐÃ TỒN TẠI!");
					return true;
				}
			} else {
				model.addAttribute("checkPhone", "SỐ ĐIỆN THOẠI NÀY ĐÃ TỒN TẠI!");
				return true;
			}
		}
		return false;
	}
	
	public boolean checkExistEmail(ModelMap model, Vendor vendor, int id) {
		if (vendorService.findByEmail(vendor.getEmail()) != null) {
			if (id != 0) {
				if (vendorService.findByEmail(vendor.getEmail()).getId() != id) {
					model.addAttribute("existEmail", "EMAIL NÀY ĐÃ TỒN TẠI!");
					return true;
				}
			} else {
				model.addAttribute("existEmail", "EMAIL NÀY ĐÃ TỒN TẠI!");
				return true;
			}
		}
		return false;
	}
	
	public boolean checkExistUsername(ModelMap model, Vendor vendor, int id) {
	    if (id == 0 && vendorService.findByUsername(vendor.getAccount().getUsername()) != null) {
	        model.addAttribute("existUsername", "USERNAME NÀY ĐÃ TỒN TẠI!");
	        return true;
	    }
	    return false;
	}


	public boolean checkPhoneValid(ModelMap model, Vendor vendor) {
		String phoneRegex = "^\\d{10}$";
		if (!Pattern.matches(phoneRegex, vendor.getPhone())) {
			model.addAttribute("checkPhone", "SỐ ĐIỆN THOẠI PHẢI CÓ 10 SỐ!");
			return true;
		}
		return false;
	}
	
	@PostMapping("/save")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute Vendor vendor, BindingResult result,
			@Valid @ModelAttribute Account account) {
		if (result.hasErrors()) {
			model.addAttribute("vendor", vendor);
			if (vendor.getId() != 0) {
				return new ModelAndView("Admin/vendor/edit", model);
			} else {
				return new ModelAndView("Admin/vendor/add", model);
			}
		}
		int id = vendor.getId();
		vendor.setAccount(account);
		
		boolean check = false;
		if (checkExistPhone(model, vendor, id)) {
			check = true;
		}

		if (checkExistEmail(model, vendor, id)) {
			check = true;
		}

		if (checkExistUsername(model, vendor, id)) {
			check = true;
		}

		if (checkPhoneValid(model, vendor)) {
			check = true;
		}
		if (check) {
			Vendor staff = new Vendor();
			BeanUtils.copyProperties(vendor, staff);
			staff.setAccount(account);
			
			model.addAttribute("vendor", staff);
			if (vendor.getId() != 0) {
				return new ModelAndView("Admin/vendor/edit", model);
			} else {
				return new ModelAndView("Admin/vendor/add", model);
			}
		}

		Vendor entity = new Vendor();
		BeanUtils.copyProperties(vendor, entity);

		account.setRole(new Role(3, "vendor"));
		entity.setAccount(account);

		vendorService.save(entity);

		return new ModelAndView("forward:/Admin/vendor", model);
	}
	
	@Transactional
	@GetMapping("/delete/{id}")
	public ModelAndView delete(ModelMap model, @PathVariable("id") int id) {
		Optional<Vendor> optVendor = vendorService.findById(id);
		if (optVendor.isPresent()) {

			Account acc = optVendor.get().getAccount();
			Integer accountid = acc.getAccountId();

			vendorService.deleteByAccountId(accountid);

			vendorService.deleteById(id);

			List<Vendor> list = vendorService.findAll();
			model.addAttribute("list", list);
			return new ModelAndView("forward:/Admin/vendor", model);
		}
		return new ModelAndView("forward:/Admin/vendor", model);
	}

}
