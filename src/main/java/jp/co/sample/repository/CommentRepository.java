package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Comment;

/**
 * DB comments と連携させるリポジトリ.
 * 
 * @author yuta.ikeda
 *
 */
@Repository
public class CommentRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * rs(resultset)をcommentに変換するRowMapper.
	 * 
	 */
	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};

	/**
	 * articleId検索.
	 * 
	 * @param articleId 
	 * @return 
	 */
	public List<Comment> findByArticleId(Integer articleId) {
		String sql = "SELECT id, name, content, article_id FROM comments where article_id = :articleId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		List<Comment> comment = template.query(sql, param, COMMENT_ROW_MAPPER);
		return comment;
	}
	
	/**
	 * コメントの投稿.
	 * 
	 * @param comment　コメント.
	 */
	public void insert (Comment comment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		String sql = "INSERT INTO comments (name, content, article_id) VALUES (:name, :content, :articleId);";
		template.update(sql, param);
	}
	
	/**
	 * 記事を削除する.
	 * 
	 * @param articleId
	 */
	public void deleteByArticleId(int articleId) {
		String sql = "DELETE FROM comments where article_id = :articleId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId",articleId);
		template.update(sql, param);
	}
	

}
