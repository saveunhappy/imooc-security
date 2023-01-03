package com.imooc.comtroller;

import com.imooc.dto.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @GetMapping("/user")
    @ResponseBody
    public List<User> query(@RequestParam(value = "username",required = false,defaultValue = "tom")String nickname){
        System.out.println(nickname);
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }
}
