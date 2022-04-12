package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.OrderDto;
import com.example.demo.payload.OrderStatusDto;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserDetailsImpl;
import com.example.demo.service.UserService;

//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/user/order")
public class OrderControllerUser {
	@Autowired
	OrderService orderService;

	@GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllOrder() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		List<OrderDto> orderList = orderService.getOrderListByUser(userDetails.getUsername());
    		if (!orderList.isEmpty())
            	return new ResponseEntity<>(orderList, HttpStatus.OK);
            else
                return new ResponseEntity<>("No order found!", HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
    	if (order != null)
        	return new ResponseEntity<>(order, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (!(authentication instanceof AnonymousAuthenticationToken)) {
    		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    		OrderDto result = orderService.createOrder(orderDto, userDetails.getUsername());
    		if (result != null)
    			return new ResponseEntity<>(result, HttpStatus.OK);
    		return new ResponseEntity<>("Error: Some items are out of stock, please check your cart again." ,HttpStatus.NOT_ACCEPTABLE);
    	}
    	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @PutMapping("/cancel")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> cancelOrder(@RequestBody Long id) {
        if (orderService.getOrderById(id) != null) {
        	orderService.changeOrderStatus(id, new OrderStatusDto("UNSUCCESSFUL", "UNSUCCESSFUL"));
        	return new ResponseEntity<>("Delete order successfully!", HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
