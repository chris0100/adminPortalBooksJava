package com.books.adminportal.controller;

import com.books.adminportal.domain.Book;
import com.books.adminportal.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookServiceObj;


    @GetMapping("/add")
    public String addBook(Model model){
        Book book = new Book();
        model.addAttribute("book", book);
        return "addBook";
    }


    @PostMapping("/add")
    public String addBookPost(@ModelAttribute("book") Book book, HttpServletRequest request){
        bookServiceObj.save(book);
        MultipartFile bookImage = book.getBookImage();


        //se guarda la imagen en la ubicacion seleccionada
        try{
            byte[] bytes = bookImage.getBytes();
            String name = book.getId() + ".png";
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/img/book/" + name)));
            stream.write(bytes);
            stream.close();
        }catch (Exception fio){fio.printStackTrace();}

        return "redirect:/books/bookList";
    }

    @PostMapping("/updateBook")
    public String updateBookPost(@ModelAttribute("book") Book book, HttpServletRequest request){
        bookServiceObj.save(book);

        MultipartFile bookImage = book.getBookImage();

        if(!bookImage.isEmpty()){
            //se guarda la imagen en la ubicacion seleccionada
            try{
                byte[] bytes = bookImage.getBytes();
                String name = book.getId() + ".png";
                //Files.delete(Paths.get("src/main/resources/static/img/book/"+name));
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/img/book/" + name)));
                stream.write(bytes);
                stream.close();
            }catch (Exception fio){fio.printStackTrace();}
        }
        return "redirect:/books/bookInfo?id="+book.getId(); //retorna a la ventana de info del libro
    }


    @GetMapping("/bookList")
    public String bookList(Model model){
        List<Book> bookList = bookServiceObj.findAll();
        model.addAttribute("bookList", bookList);
        return "bookList";
    }



    @GetMapping("/bookInfo")
    public String bookInfo(@PathParam("id") Long id, Model model){
        Book book = bookServiceObj.findById(id);
        model.addAttribute("book",book);
        return "bookInfo";
    }



    @GetMapping("/updateBook")
    public String updateBook(@PathParam("id") Long id, Model model){
        Book book = bookServiceObj.findById(id);
        model.addAttribute("book",book);
        return "updateBook";
    }






    @PostMapping("/remove")
    public String remove(@ModelAttribute("id") String id, Model model){
        bookServiceObj.removeOne(Long.parseLong(id.substring(8)));
        List<Book> bookList = bookServiceObj.findAll();
        model.addAttribute("bookList",bookList);

        return "redirect:/book/bookList";
    }






}
