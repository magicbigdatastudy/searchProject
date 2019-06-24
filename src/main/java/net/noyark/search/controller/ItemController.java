package net.noyark.search.controller;

import net.noyark.search.entity.Item;
import net.noyark.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /**
     * 搜索索引返回数据
     * localhost:8081/search/title/三星
     * @return
     */
    @RequestMapping("search/{title}/{value}")
    public List<Item> searchIndex(@PathVariable String title,@PathVariable String value){
        try{
            List<Item> list = service.search(title,value);
            return list;
        }catch (Exception e){
            return null;
        }
    }
}
