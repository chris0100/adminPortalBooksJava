package com.books.adminportal.controller;

import com.books.adminportal.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ResourceController {

    @Autowired
    private BookService bookServiceObj;

    @PostMapping("/book/removeList")
    public String removeList(@RequestBody ArrayList<String> bookIdList, Model model){
        for (String id : bookIdList){
            String bookId = id.substring(8);
            bookServiceObj.removeOne(Long.parseLong(bookId));
        }
        return "delete success";
    }

}
