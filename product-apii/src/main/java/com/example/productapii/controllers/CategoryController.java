package com.example.productapii.controllers;

import com.example.productapii.dtos.CategoryDto;
import com.example.productapii.entity.Category;
import com.example.productapii.services.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryServiceImpl categoryService;

    @GetMapping("/list")
    public List<Category> listCategory() {
        return categoryService.listCategory();
    }


    @GetMapping("/name/{id}")
    public String getCategoryName(@PathVariable String id) {
        return categoryService.getCategoryNameById(id);
    }

    @PostMapping("/add")
    public void addCategory(@RequestBody CategoryDto categoryDto)
    {
        System.out.println("added");
        categoryService.add(categoryDto);
    }
}
