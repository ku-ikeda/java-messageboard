package jp.co.sample.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Article;
import jp.co.sample.domain.Comment;
import jp.co.sample.form.ArticleForm;
import jp.co.sample.form.CommentForm;
import jp.co.sample.repository.ArticleRepository;
import jp.co.sample.repository.CommentRepository;

/**
 * 投稿画面の入力を受け取るコントローラー.
 * 
 * @author yuta.ikeda
 *
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

	/**
	 * articleRepositoryの注入.
	 */
	@Autowired
	private ArticleRepository articleRepository;
	
	/**
	 * commentRepositoryの注入.
	 */
	@Autowired
	private CommentRepository commentRepository;

	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {

		ArticleForm articleForm = new ArticleForm();
		return articleForm;

	}

	@ModelAttribute
	public CommentForm setUpCommentForm() {

		CommentForm commentForm = new CommentForm();
		return commentForm;

	}

	/**
	 * 投稿画面を表示する.
	 * 
	 * @param model
	 *            モデル
	 * 
	 * @return 投稿する画面へ
	 */
	@RequestMapping("/")
	public String index(Model model) {
		List<Article> articleList = articleRepository.findAll2();
//		List<Comment> commentList = new ArrayList<>();
//		for (Article article : articleList) {
//			commentList = commentRepository.findByArticleId(article.getId());
//			article.setCommentList(commentList);

//		}
		model.addAttribute("articleList", articleList);
		return "postscreen";
	}

	/**
	 * 入力内容を追加していく.
	 * 
	 * @param form
	 *            フォーム
	 * @return
	 */
	@RequestMapping("/insertArticle")
	public String insertArticle(ArticleForm form) {

		String name = form.getName();
		String content = form.getContent();

		Article article = new Article();
		article.setName(name);
		article.setContent(content);
		articleRepository.insert(article);
		return "redirect:/article/";
	}

	/**
	 * 記事に対してのコメント.
	 * 
	 * @param form
	 *            フォーム
	 * @return 投稿画面へ
	 */
	@RequestMapping("/insertComment")
	public String insertComment(CommentForm form) {
		String name = form.getName();
		String content = form.getContent();
		String articleId = form.getArticleId();
		int intValueOfArticleId = Integer.parseInt(articleId);

		Comment comment = new Comment();
		comment.setName(name);
		comment.setContent(content);
		comment.setArticleId(intValueOfArticleId);
		commentRepository.insert(comment);
		return "redirect:/article/";
	}

	/**
	 * 記事を削除する.
	 * 
	 * @param id
	 * @return 入力画面へ
	 */
	@RequestMapping("/deleteArticle")
	public String deleteArticle(Integer articleId, Model model) {

		commentRepository.deleteByArticleId(articleId);
		articleRepository.deleteById(articleId);

		return index(model);
	}

}
