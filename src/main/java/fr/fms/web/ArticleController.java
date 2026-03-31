package fr.fms.web;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController {

  @Autowired
  ArticleRepository articleRepository;

  @Autowired
  CategoryRepository categoryRepository;

  //@RequestMapping(value = "/index", method = RequestMethod.GET)
  @GetMapping("/index")
  public String index(
    Model model,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "") String keyword,
    @RequestParam(defaultValue = "") String categoryName
  ) {
    if (!categoryName.equals("")) {
      Page<Article> articles = articleRepository.findByCategoryName(
        categoryRepository.findByName(categoryName),
        PageRequest.of(page, 5)
      );
      model.addAttribute("articles", articles);
      model.addAttribute("categoryName", categoryName);
      model.addAttribute("pages", new int[articles.getTotalPages()]);
      model.addAttribute("currentPage", page);
      return "articles";
    } else {
      Page<Article> articles = articleRepository.findByDescriptionContains(
        keyword,
        PageRequest.of(page, 5)
      );
      model.addAttribute("articles", articles.getContent());
      model.addAttribute("pages", new int[articles.getTotalPages()]);
      model.addAttribute("currentPage", page);
      model.addAttribute("keyword", keyword);
      return "articles";
    }
  }

  @GetMapping("/delete")
  public String delete(Long id, int page, String keyword) {
    articleRepository.deleteById(id);
    return "redirect:/index?page=" + page + "&keyword=" + keyword;
  }

  @GetMapping("/article")
  public String article(Model model) {
    model.addAttribute("article", new Article());
    return "article";
  }

  @PostMapping("/save")
  public String save(
    Model model,
    @Valid Article article,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) return "article";
    articleRepository.save(article);
    return "redirect:/index";
  }
}
