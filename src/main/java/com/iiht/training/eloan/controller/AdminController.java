package com.iiht.training.eloan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("/register-clerk")
	public ResponseEntity<UserDto> registerClerk(@RequestBody UserDto userDto){
		//Ravinder
		UserDto clerk = this.adminService.registerClerk(userDto);
		ResponseEntity<UserDto> response = new ResponseEntity<UserDto>(clerk, HttpStatus.OK);
		return response;
	}
	
	@PostMapping("/register-manager")
	public ResponseEntity<UserDto> registerManager(@RequestBody UserDto userDto){
		//Ravinder
		UserDto manager = this.adminService.registerManager(userDto);
		ResponseEntity<UserDto> response = new ResponseEntity<UserDto>(manager, HttpStatus.OK);
		return response;
	}
	
	
	@GetMapping("/all-clerks")
	public ResponseEntity<List<UserDto>> getAllClerks(){
		//adding method to fetch all clerks
		List<UserDto> clerks = this.adminService.getAllClerks();
		ResponseEntity<List<UserDto>> response = new ResponseEntity<List<UserDto>>(clerks, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/all-managers")
	public ResponseEntity<List<UserDto>> getAllManagers(){
		List<UserDto> manager = this.adminService.getAllManagers();
		ResponseEntity<List<UserDto>> response = new ResponseEntity<List<UserDto>>(manager, HttpStatus.OK);
		return response;
		
	}
	
	
}
