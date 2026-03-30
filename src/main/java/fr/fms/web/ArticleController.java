package fr.fms.web;

import fr.fms.dao.ArticleRepository;
import fr.fms.entities.Article;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArticleController {

  @Autowired
  private ArticleRepository articleRepository;

  //@RequestMapping(value = "/index", method = RequestMethod.GET)
  @GetMapping("/index")
  public String index(Model model) {
    List<Article> articles = articleRepository.findAll();
    model.addAttribute("articles", articles);
    return "articles";
  }
}
