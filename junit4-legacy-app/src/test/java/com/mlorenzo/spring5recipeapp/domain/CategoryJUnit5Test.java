package com.mlorenzo.spring5recipeapp.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryJUnit5Test {
    Category category;

    @BeforeEach
    void setUp(){
        category = new Category();
    }

    @Test
    void getId() throws Exception {
        Long idValue = 4L;
        category.setId(idValue);
        assertEquals(idValue, category.getId());
    }

    @Test
    void getDescription() throws Exception {
    }

    @Test
    void getRecipes() throws Exception {
    }

}