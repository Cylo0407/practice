package com.example.springbootinit.Controller;

import com.example.springbootinit.Entity.User;
import com.example.springbootinit.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
//    @Autowired
//    private UserService userService;
//
//    //通过用户id获取用户所有信息
//    //http://localhost:8080/testBoot/getUser/1(此处1为要获取的id）
//    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
//    public String GetUser(@PathVariable int id) {
//        return userService.getUserInfo(id).toString();
//    }
//
//    //通过用户id删除用户
//    //http://localhost:8080/testBoot/delete?id=1(此处1为要删除的id）
//    @RequestMapping(value = "/delete", method = RequestMethod.GET)
//    public String delete(int id) {
//        int result = userService.deleteById(id);
//        if (result >= 1) {
//            return "删除成功";
//        } else {
//            return "删除失败";
//        }
//    }
//
//    //根据用户id更新用户信息
//    //http://localhost:8080/testBoot/update?id=1&userName=啵啵&passWord=123456
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public String update(User user) {
//        int result = userService.Update(user);
//        if (result >= 1) {
//            return "修改成功";
//        } else {
//            return "修改失败";
//        }
//    }
//
//    //插入新用户
//    //http://localhost:8080/testBoot/insert?id=100&userName=啵啵&passWord=123456
//    @RequestMapping(value = "/insert", method = RequestMethod.POST)
//    public User insert(User user) {
//        return userService.save(user);
//    }
//
//    //打印所有用户信息
//    //http://localhost:8080/testBoot/selectAll
//    @RequestMapping("/selectAll")
//    @ResponseBody
//    public List<User> ListUser() {
//        return userService.selectAll();
//    }

    @Resource
    private UserService UserService;

    /**
     * 新增用户
     */
    @PostMapping("")
    public User addUser(@RequestBody User user){
        return UserService.insertUser(user);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id){
        UserService.deleteUser(id);
    }

    /**
     * 修改用户
     */
    @PutMapping("")
    public User updateUser(@RequestBody User user){
        return UserService.updateUser(user);
    }

    /**
     * id查用户
     */
    @GetMapping("/{id}")
    public User findbyId(@PathVariable("id") int id){
        return UserService.findUserById(id);
    }

    /**
     * 全查用户
     */
    @GetMapping("")
    public List<User> findAll(){
        return UserService.findAllUser();
    }
}