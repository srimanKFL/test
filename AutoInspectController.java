package com.kfl.portal.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kfl.portal.entity.AutoInspectPojo;
import com.kfl.portal.service.AutoInspectService;

@RestController
@RequestMapping("/auto")
public class AutoInspectController {
	
	@Autowired
	AutoInspectService autoInspectService;
	
	@PostMapping("/product/new")
    public ResponseEntity<Object> create(@Valid @RequestBody AutoInspectPojo product,
                                 BindingResult bindingResult) {
		System.out.println(product);
		AutoInspectPojo productIdExists = autoInspectService.findOne(product.getRequestNo());
        if (productIdExists != null) {
            bindingResult
                    .rejectValue("productId", "error.product",
                            "There is already a product with the code provided");
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }
        return ResponseEntity.ok(autoInspectService.save(product));
    }

	@GetMapping("/AutoInspectProducts")
    public Page<AutoInspectPojo> findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "3") Integer size) {
    	System.out.println("hiii");
        PageRequest request = PageRequest.of(page - 1, size);
        return autoInspectService.findAll(request);
    }
	
}
