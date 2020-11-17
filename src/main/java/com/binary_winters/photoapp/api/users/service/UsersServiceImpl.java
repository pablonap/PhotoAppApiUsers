package com.binary_winters.photoapp.api.users.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.binary_winters.photoapp.api.users.data.UserEntity;
import com.binary_winters.photoapp.api.users.data.UsersRepository;
import com.binary_winters.photoapp.api.users.shared.UserDto;

@Service
public class UsersServiceImpl implements UsersService {
	
	private UsersRepository usersRepository;
	
	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}



	@Override
	public UserDto createUser(UserDto userDetails) {
		
		userDetails.setUserId(UUID.randomUUID().toString());
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

//		For this mapping to work the field names inside of the source object which is UserDto must match the fields in the destination 
//		object which is UserEntity.
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		
		userEntity.setEncryptedPassword("test");
		
		usersRepository.save(userEntity);

		return null;
	}

}
