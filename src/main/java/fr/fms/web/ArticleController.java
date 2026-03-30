package fr.fms.web;

import fr.fms.dao.ArticleRepository;
import fr.fms.entities.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController {

  @Autowired
  ArticleRepository articleRepository;

  //@RequestMapping(value = "/index", method = RequestMethod.GET)
  @GetMapping("/index")
  public String index(
    Model model,
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "keyword", defaultValue = "") String keyword
  ) {
    Page<Article> articles = articleRepository.findByDescriptionContains(
      keyword,
      PageRequest.of(page, 5)
    );
    model.addAttribute("articles", articles.getContent());
    model.addAttribute("pages", new int[articles.getTotalPages()]);
    model.addAttribute("currentPage", page);
    return "articles";
  }
}
