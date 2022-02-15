package com.example.authentication_api.service;

import com.example.authentication_api.dto.UserOrderDto;
import com.example.authentication_api.entity.UserOrder;
import com.example.authentication_api.repository.UserOrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// todo : service interface is missing .. this is not a right way of coding
// todo : remove this service completely!!
@Service
public class UserOrderServiceImpl {
    @Autowired
    private UserOrderRepository userOrderRepository;

    private  UserOrder convertFromDtoToEntity(UserOrderDto userOrderDto)
    {
        UserOrder userOrder=new UserOrder();
        BeanUtils.copyProperties(userOrderDto,userOrder);
        return userOrder;
    }

    public void add(UserOrderDto userOrderDto)
    {
        System.out.println(userOrderDto.getEmail()+"----:---"+userOrderDto.getOrderId());

        try
        {
            userOrderRepository.save(convertFromDtoToEntity(userOrderDto));
        }
        catch (Exception ex)
        {
            System.out.println("Error adding Data in UserOrderTable");
            ex.printStackTrace();
        }

    }

    public List<String> getOrderIdByUserEmail(String email)
    {
        List<UserOrder> userOrders=userOrderRepository.findAllByEmail(email);
        if(userOrders!=null)
        {
            List<String > orderIds=new ArrayList<>();
            for(UserOrder userOrder:userOrders)
            {
                orderIds.add(userOrder.getOrderId());
            }
            return orderIds;
        }
        return  null;

    }


}
