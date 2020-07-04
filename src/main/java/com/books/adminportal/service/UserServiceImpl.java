package com.books.adminportal.service;

import com.books.adminportal.domain.ShoppingCart;
import com.books.adminportal.domain.User;
import com.books.adminportal.domain.UserPayment;
import com.books.adminportal.domain.UserShipping;
import com.books.adminportal.domain.security.UserRole;
import com.books.adminportal.repository.RoleRepository;
import com.books.adminportal.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User createUser(User user, Set<UserRole> userRoles){
        User localUser = userRepository.findByUsername(user.getUsername());

        if(localUser != null) {
            LOG.info("user {} already exists. Nothing will be done.", user.getUsername());
        } else {
            for (UserRole ur : userRoles) {
                roleRepository.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);

            //se establece un shoppinCart vacio desde la creacion del usuario.
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            user.setShoppingCart(shoppingCart);

            //se crean listas vacias.
            user.setUserShippingList(new ArrayList<UserShipping>());
            user.setUserPaymentList(new ArrayList<UserPayment>());


            localUser = userRepository.save(user);
        }

        return localUser;
    }


    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

}
