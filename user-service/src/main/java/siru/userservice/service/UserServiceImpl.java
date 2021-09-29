package siru.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import siru.userservice.client.OrderServiceClient;
import siru.userservice.dto.UserDto;
import siru.userservice.jpa.UserEntity;
import siru.userservice.jpa.UserRepository;
import siru.userservice.vo.ResponseOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final OrderServiceClient orderServiceClient;
//    private final RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true,
                true, true,
                new ArrayList<>());
    }

    @Override
    public UserDto createUser(UserDto user) {
        user.setUserId(UUID.randomUUID().toString());
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(user.getPwd()));

        userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        UserDto userDto = modelMapper.map(userEntity, UserDto.class);

        /* Using as rest template */
//        String orderUrl = String.format(env.getProperty("order_service.url"), userId);
//        ResponseEntity<List<ResponseOrder>> orderListResponse = restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<ResponseOrder>>() {
//        });
//        List<ResponseOrder> orderList = orderListResponse.getBody();


        /* Using as feign client */
//        List<ResponseOrder> orderList;
//        try {
//            orderList = orderServiceClient.getOrders(userId);
//        } catch (FeignException ex) {
//            log.error(ex.getMessage());
//        }

        /* Using as feign client with error decoder */
        List<ResponseOrder> orderList = orderServiceClient.getOrders(userId);
        userDto.setOrderList(orderList);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(String.format("email[%s] not found", email));
        }

        UserDto result = modelMapper.map(userEntity, UserDto.class);
        return result;
    }
}
