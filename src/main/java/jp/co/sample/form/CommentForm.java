package jp.co.sample.form;

/**
 * 表示内容を受け取るフォーム.
 * 
 * @author yuta.ikeda
 *
 */
public class CommentForm {

	/** articleI */
	private String articleId;
	/** 名前 */
	private String name;
	/** 投稿内容 */
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
