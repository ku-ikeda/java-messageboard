package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Article;

/**
 * 　　DB　　articlesと連携させるリポジトリ.
 * 
 * @author yuta.ikeda
 *
 */
@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	
	
	/**
	 * rs(resultset)をarticleに変換するRowMapper.
	 * 
	 */
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {

		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};

	/**
	 * articleに入っているデータ全件を検索.
	 * 
	 * @return　articleに入っているデータ全件.
	 */
	public List<Article> findAll() {
		String sql = "SELECT id, name, content FROM articles ORDER BY id DESC";
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
		
		
		return articleList;
	}
	
	/**
	 * insert　新しい情報を追加.
	 * 
	 * @param article　
	 * @return　入力された　name,content.
	 */
	public Article insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String sql = "INSERT INTO articles(name,content) VALUES (:name,:content);";
		template.update(sql, param);
		return article;
	}
	
	/**
	 * 記事を削除する.
	 * 
	 * @param id
	 */
	public void deleteById(int id) {
		String sql = "DELETE FROM articles where id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
		template.update(sql, param);
	}
	

}
