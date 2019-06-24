package net.noyark.search.controller;

import net.noyark.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    @Autowired
    private ItemService service;

    /**
     * 接收一个create请求地址的方法
     * 没有参数调用service业务层创建索引
     *
     */
    @RequestMapping("create")
    public String createIndex(){
        try{
            service.createIndex();
            return "successfully";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
