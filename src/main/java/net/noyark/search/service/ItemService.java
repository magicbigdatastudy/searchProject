package net.noyark.search.service;

import net.noyark.search.entity.Item;

import java.util.List;

public interface ItemService {

    void createIndex() throws Exception;

    List<Item> search(String title, String value) throws Exception;
}
