package auction.controller;

import auction.domain.Category;
import auction.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }
}
