package com.example.productapii.services;

import com.example.productapii.dtos.CategoryDto;
import com.example.productapii.entity.Category;
import com.example.productapii.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;


    public void add(CategoryDto categoryDto)
    {
        Category category=new Category();
        BeanUtils.copyProperties(categoryDto,category);
        try
        {
            categoryRepository.save(category);
        }
        catch (Exception ex)
        {
            System.out.println("error adding category");
            ex.printStackTrace();
        }
    }


    public String getCategoryNameById(String id)
    {
        Optional<Category> category=categoryRepository.findById(id);
        if(category.isPresent())
        {
            return category.get().getCategoryName();
        }
        return null;
    }

    public List<Category> listCategory() {
        return (List)categoryRepository.findAll();
    }

}
