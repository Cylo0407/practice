package com.example.springbootinit.Service.Impl;

import com.example.springbootinit.Entity.User;
import com.example.springbootinit.Repository.UserRepository;
import com.example.springbootinit.Service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository UserRepository;

    @Override
    public User insertUser(User user) {
        return UserRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        UserRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        return UserRepository.save(user);
    }

    @Override
    public User findUserById(int id) {
        return UserRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAllUser() {
        return UserRepository.findAll();
    }
}