package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto saveCategory(NewCategoryDto newCategoryDto);

    CategoryDto updateCategory(int catId, NewCategoryDto newCategoryDto);

    CategoryDto getCategory(int catId);

    List<CategoryDto> getCategories(int from, int size);

    void removeCategory(int catId);
}