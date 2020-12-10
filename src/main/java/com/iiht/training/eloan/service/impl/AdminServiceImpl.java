package com.iiht.training.eloan.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.entity.Users;
import com.iiht.training.eloan.repository.UsersRepository;
import com.iiht.training.eloan.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UsersRepository usersRepository;
	
	//@Ravinder utility method
    
		private UserDto convertUsersEntitytoUserDto(Users users) {
			UserDto userdto = new UserDto();
			userdto.setId(users.getId());
			userdto.setFirstName(users.getFirstName());
			userdto.setLastName(users.getLastName());
			userdto.setEmail(users.getEmail());
			userdto.setMobile(users.getMobile());
			//String role = users.getRole();
			return userdto;
			
		}
		
		private Users convertUserDtotoEntityclerk(UserDto userdto) {
			Users user = new Users();
			user.setFirstName(userdto.getFirstName());
			user.setLastName(userdto.getLastName());
			user.setId(userdto.getId());
			user.setEmail(userdto.getEmail());
			user.setMobile(userdto.getMobile());
			user.setRole("Clerk");
			return user;
		}
		
		private Users convertUserDtotoEntitymanager(UserDto userdto) {
			Users user = new Users();
			user.setFirstName(userdto.getFirstName());
			user.setLastName(userdto.getLastName());
			user.setId(userdto.getId());
			user.setEmail(userdto.getEmail());
			user.setMobile(userdto.getMobile());
			user.setRole("Manager");
			return user;
		}
	
	@Override
	public UserDto registerClerk(UserDto userDto) {
		// ravinder code convert dto into entity
		Users user = this.convertUserDtotoEntityclerk(userDto);
		//save this to db
		Users newClerk = this.usersRepository.save(user);
		//convert entity to dto
		UserDto clerkoutput = this.convertUsersEntitytoUserDto(newClerk);
		return clerkoutput;
	}

	@Override
	public UserDto registerManager(UserDto userDto) {
		// ravinder code convert dto into entity
				Users user = this.convertUserDtotoEntitymanager(userDto);
				//save this to db
				Users newManager = this.usersRepository.save(user);
				//convert entity to dto
				UserDto manageroutput = this.convertUsersEntitytoUserDto(newManager);
				return manageroutput;
	}
	
	
	
	@Override
	public List<UserDto> getAllClerks() {
		// Ravinder adding method (video 11/26 1.22.42
		List<Users> clerks = this.usersRepository.findAll();
		@SuppressWarnings("unlikely-arg-type")
		List<UserDto> userdtos = clerks.stream()
				                       .filter(role -> role.getRole().equalsIgnoreCase("Clerk"))
				                       .map(this :: convertUsersEntitytoUserDto)
				                       .collect(Collectors.toList());
		return userdtos;
	}

	@Override
	public List<UserDto> getAllManagers() {
		// Ravinder adding method (video 11/26 1.22.42
				List<Users> manager = this.usersRepository.findAll();
				@SuppressWarnings("unlikely-arg-type")
				List<UserDto> userdtos = manager.stream()
						                       .filter(role -> role.getRole().equalsIgnoreCase("Manager"))
						                       .map(this :: convertUsersEntitytoUserDto)
						                       .collect(Collectors.toList());
				return userdtos;
		
	}

}
