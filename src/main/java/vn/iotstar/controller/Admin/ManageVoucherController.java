package vn.iotstar.controller.Admin;

import java.util.List;
import java.util.Optional;
import java.util.Random;
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
import vn.iotstar.dto.VoucherDTO;
import vn.iotstar.entity.Voucher;
import vn.iotstar.service.IVoucherService;

@Controller
@RequestMapping("/Admin/voucher")
public class ManageVoucherController {

	@Autowired
	IVoucherService voucherService;

	@RequestMapping("")
	public String home(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		int count = (int) voucherService.count();
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

		int totalPages = voucherService.findAll(pageable).getTotalPages();
		Page<Voucher> list = voucherService.findAll(pageable);
		int start = Math.max(1, currentPage - 2);
		int end = Math.min(currentPage + 2, totalPages);

		if (totalPages > count) {
			if (end == totalPages)
				start = end - count;
			else if (start == 1)
				end = start + count;
		}

		List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

		model.addAttribute("list", list);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pageNumbers", pageNumbers);
		return "Admin/voucher/list";
	}

	@GetMapping("/add")
	public String add(Model model) {
		VoucherDTO voucher = new VoucherDTO();

		model.addAttribute("voucher", voucher);
		return "Admin/voucher/add";
	}

	@GetMapping("/edit/{id}")
	public ModelAndView edit(ModelMap model, @PathVariable("id") Integer voucherId) {
		Optional<Voucher> optVoucher = voucherService.findById(voucherId);
		Voucher voucher = new Voucher();
		if (optVoucher.isPresent()) {
			Voucher entity = optVoucher.get();
			BeanUtils.copyProperties(entity, voucher);
			model.addAttribute("voucher", voucher);
			return new ModelAndView("Admin/voucher/edit", model);
		}
		return new ModelAndView("forward:/Admin/voucher", model);
	}

	@PostMapping("/save")
	public ModelAndView addOrEdit(ModelMap model, @Valid @ModelAttribute Voucher voucher,
			@Valid @ModelAttribute VoucherDTO voucherDTO, BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("voucher", voucher);
			return new ModelAndView("Admin/voucher", model);
		}

		if (voucher.getVoucherId() != 0) {
			Optional<Voucher> optionalEntity = voucherService.findById(voucher.getVoucherId());

			Voucher entity = optionalEntity.get();
			String name = entity.getVoucherType();

			List<Voucher> listByType = voucherService.findByVoucherType(name);

			for (Voucher mgg : listByType) {
				mgg.setVoucherCode(voucher.getVoucherCode());
				mgg.setVoucherType(voucher.getVoucherType());
				mgg.setVoucherValue(voucher.getVoucherValue());
				mgg.setStartDate(voucher.getStartDate());
				mgg.setEndDate(voucher.getEndDate());
				mgg.setActive(voucher.getActive());
				voucherService.save(mgg);
			}

		} else {
			int quantity = voucherDTO.getQuantity();

			if (quantity <= 0) {
				model.addAttribute("error", "Quantity must be greater than 0!");
				return new ModelAndView("Admin/voucher/list", model);
			}

			for (int i = 0; i < quantity; i++) {
				Voucher newVoucher = new Voucher();

				newVoucher.setVoucherCode(voucherDTO.getVoucherCode());
				newVoucher.setVoucherType(voucherDTO.getVoucherType());
				newVoucher.setVoucherValue(voucherDTO.getVoucherValue());
				newVoucher.setStartDate(voucherDTO.getStartDate());
				newVoucher.setEndDate(voucherDTO.getEndDate());
				newVoucher.setActive(voucherDTO.getActive());
				voucherService.save(newVoucher);
			}
		}
		return new ModelAndView("forward:/Admin/voucher", model);
	}

	@Transactional
	@GetMapping("/delete/{id}")
	public ModelAndView delete(ModelMap model, @PathVariable("id") int voucherId) {
		Optional<Voucher> optVoucher = voucherService.findById(voucherId);
		if (optVoucher.isPresent()) {
			voucherService.deleteById(voucherId);
			List<Voucher> list = voucherService.findAll();
			model.addAttribute("list", list);
			return new ModelAndView("forward:/Admin/voucher", model);
		}
		return new ModelAndView("forward:/Admin/voucher", model);
	}

}
