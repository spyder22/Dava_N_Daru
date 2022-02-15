package com.example.authentication_api.service;

import com.example.authentication_api.dto.ResponseDto;
import com.example.authentication_api.entity.AuthenticationToken;
import com.example.authentication_api.entity.Customer;
import com.example.authentication_api.exceptions.CustomException;
import com.example.authentication_api.payloads.user.AccountDto;
import com.example.authentication_api.payloads.user.SignInDto;
import com.example.authentication_api.payloads.user.SignInResponseDto;
import com.example.authentication_api.payloads.user.SignUpDto;
import com.example.authentication_api.repository.CustomerRepository;
import com.example.authentication_api.service.interf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// todo : service interface is missing .. this is not a right way of coding
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    AuthenticationTokenServiceImpl authenticationTokenServiceImpl;

    @Autowired
    private UserOrderServiceImpl userOrderServiceImpl;


    // todo : no need to keep this method, remove this
    public List<String> getOrderIdsOfUserByMail(String userEmail)
    {
        Customer user= customerRepository.findByEmail(userEmail);
        if(user==null) {
            return null;
        }
        return userOrderServiceImpl.getOrderIdByUserEmail(userEmail);
    }

    @Transactional
    public ResponseDto signUp(SignUpDto signupDto) throws CustomException {
        // check if user is already present
        if (Objects.nonNull(customerRepository.findByEmail(signupDto.getEmail()))) {
            // we have an user
            throw new CustomException("user already present");
        }



        String encryptedPassword = signupDto.getPassword();

        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Customer user = new Customer(signupDto.getName(), signupDto.getEmail(),encryptedPassword);

        customerRepository.save(user);

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);

        authenticationTokenServiceImpl.saveConfirmationToken(authenticationToken);

        ResponseDto responseDto = new ResponseDto("success", "user created succesfully");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInResponseDto signIn(SignInDto signInDto) throws Exception {
        // find user by email
        Customer user = customerRepository.findByEmail(signInDto.getEmail());

        if (Objects.isNull(user)) {
            return null;
        }

        if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
            return null;
        }

        AuthenticationToken token = authenticationTokenServiceImpl.getToken(user);

        if (Objects.isNull(token)) {
            throw new CustomException("token is not present");
        }

        return new SignInResponseDto("success", token.getToken());
    }

    public String getUserEmailById(Integer id)
    {
        Optional<Customer> customer= customerRepository.findById(id);
        if (customer.isPresent())
        {
            return customer.get().getEmail();
        }
        return null;
    }

    public void googleSignIn(SignUpDto signUpDto)
    {

        Customer user = customerRepository.findByEmail(signUpDto.getEmail());
        if(user!=null)
        {
            System.out.println("google user already present");
            return ;
        }

        Customer customer=new Customer();
        customer.setAddress(signUpDto.getAddress());
        customer.setEmail(signUpDto.getEmail());
        customer.setName(signUpDto.getName());
        customerRepository.save(customer);
        System.out.println(customer.getId());
        System.out.println("google user success fully created");
    }

    public AccountDto getAccountDetailById(String email)
    {

        AccountDto accountDto=new AccountDto();
        Customer user = customerRepository.findByEmail(email);

        accountDto.setEmail(email);
        accountDto.setAddress(user.getAddress());
        accountDto.setName(user.getName());
        return accountDto;

    }







}
