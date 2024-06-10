package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.exception.CategoryNotEmptyException;
import ru.practicum.ewm.exception.DataNotFoundException;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.repository.JpaCategoriesRepository;
import ru.practicum.ewm.utils.OffsetBasedPageRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final JpaCategoriesRepository repository;
    private final CategoryMapper mapper;

    @Override
    @Transactional
    public CategoryDto saveCategory(NewCategoryDto newCategoryDto) {
        return mapper.toDtoCategory(repository.save(mapper.toModelCategory(newCategoryDto)));
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(int catId, NewCategoryDto newCategoryDto) {
        Category category = repository.findById(catId)
                .orElseThrow(() -> new DataNotFoundException("Category with id =" + catId + " not found"));
        category.setName(newCategoryDto.getName());
        return mapper.toDtoCategory(repository.save(category));
    }

    @Override
    public CategoryDto getCategory(int catId) {
        Category category = repository.findById(catId)
                .orElseThrow(() -> new DataNotFoundException("Category with id =" + catId + " not found"));
        return mapper.toDtoCategory(category);
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        return mapper.toDtoCategories(repository.findAll(new OffsetBasedPageRequest(from, size)).toList());
    }

    @Override
    @Transactional
    public void removeCategory(int catId) {
        Category category = repository.findById(catId)
                .orElseThrow(() -> new DataNotFoundException("Category with id =" + catId + " not found"));
        if (!category.getEvents().isEmpty()) {
            throw new CategoryNotEmptyException("The category is not empty");
        }
        repository.deleteById(catId);
    }
}