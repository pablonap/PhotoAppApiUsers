package com.binary_winters.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.binary_winters.photoapp.api.users.data.UserEntity;
import com.binary_winters.photoapp.api.users.data.UsersRepository;
import com.binary_winters.photoapp.api.users.shared.UserDto;

@Service
public class UsersServiceImpl implements UsersService {
	
	private UsersRepository usersRepository;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDto createUser(UserDto userDetails) {
		
		userDetails.setUserId(UUID.randomUUID().toString());
		
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

//		For this mapping to work the field names inside of the source object which is UserDto must match the fields in the destination 
//		object which is UserEntity.
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		
		usersRepository.save(userEntity);
		
		UserDto returnValue = modelMapper.map(userEntity, UserDto.class);

		return returnValue;
	}

	// When Spring framework is trying to authenticate the user, it will look for this method.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = usersRepository.findByEmail(username);
		
		if(userEntity == null) throw new UsernameNotFoundException(username);	
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}

}
