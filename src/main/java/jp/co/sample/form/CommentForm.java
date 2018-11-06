package jp.co.sample.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 表示内容を受け取るフォーム.
 * 
 * @author yuta.ikeda
 *
 */
public class CommentForm {

	
	/** 記事のID */
	private String articleId;
	
	/** 名前 */
	@NotBlank(message="※名前の入力は必須です")
	private String name;

	/** 投稿内容 */
	@NotBlank(message="※投稿内容を入力してください")
	@Size(min=3,max=250,message="※3文字以上250文字以内で入力してください")
	private String content;

	@Override
	public String toString() {
		return "CommentForm [articleId=" + articleId + ", name=" + name + ", content=" + content + "]";
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public int getIntValueOfArticleId(){
		return Integer.parseInt(articleId);
	}
}
