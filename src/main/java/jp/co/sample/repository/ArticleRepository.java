package jp.co.sample.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Article;
import jp.co.sample.domain.Comment;

/**
 * DB articlesと連携させるリポジトリ.
 * 
 * @author yuta.ikeda
 *
 */
@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * rs(resultSet)をarticleに変換するRowMapper.
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
	 * articles と commentsを結合後 全件検索.
	 * 
	 * @return article に入っているデータ全件
	 */
	public List<Article> findAll2() {
		String sql = "SELECT a.id as a_id, a.name as a_name, a.content as a_content, "
				+ "c.id as com_id, c.name as com_name, c.content as com_content, "
				+ "c.article_id as com_article_id FROM articles a  LEFT OUTER JOIN "
				+ "comments c ON a.id = c.article_id ORDER BY a_id DESC;";

		List<Article> articleList = template.query(sql, new ResultSetExtractor<List<Article>>() {

			@Override
			public List<Article> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				//articleList を生成.
				//commentListはnullにしておいてコメントが書かれたら追加.
				//   commentList　→　記事1　に対して　コメント多数.
				List<Article> articleList = new ArrayList<Article>();
				List<Comment> commentList = null;
				int previousArtcleId = 0;
				
				//新たに記事を投稿したら新しい記事を追加
				while (rs.next()) {
				    int nowArticleId = rs.getInt("a_id");
					
				    //投稿IDが異なる場合　新しい記事の作成
				    if (previousArtcleId != nowArticleId) {
						Article article = new Article();
						article.setId(			rs.getInt(		"a_id"));
						article.setName(		rs.getString(	"a_name"));
						article.setContent(		rs.getString(	"a_content"));
						commentList = new ArrayList<>();
						article.setCommentList(commentList);
						articleList.add(article);
					}
					int commentId = rs.getInt("com_id");
				   
					if(commentId != 0) {
					
					Comment comment = new Comment();
					comment.setId(			rs.getInt(		"com_id"));
					comment.setName(		rs.getString(	"com_name"));
					comment.setContent(		rs.getString(	"com_content"));
					comment.setArticleId(	rs.getInt(		"com_article_id"));
					commentList.add(comment);
					
					}
					previousArtcleId = nowArticleId;
				}
				return articleList;
			}

		});
		return articleList;

	}

	/**
	 * articleに入っているデータ全件を検索.
	 * 
	 * @return articleに入っているデータ全件.
	 */
	public List<Article> findAll() {
		String sql = "SELECT id, name, content FROM articles ORDER BY id DESC";
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);

		return articleList;
	}

	/**
	 * insert 新しい情報を追加.
	 * 
	 * @param article
	 *            追加したい投稿情報
	 * @return 投稿情報 自動採番したIDは入っていません
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
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);

	}

}
