package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.model.Category;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface CategoryMapper {
    Category toModelCategory(NewCategoryDto newCategoryDto);

    CategoryDto toDtoCategory(Category category);

    List<CategoryDto> toDtoCategories(List<Category> categories);
}