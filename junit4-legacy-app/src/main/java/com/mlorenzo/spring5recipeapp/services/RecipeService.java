package com.mlorenzo.spring5recipeapp.services;

import com.mlorenzo.spring5recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}
