package jp.co.sample.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 投稿者名と投稿内容を表すフォーム.
 * 
 * @author yuta.ikeda
 *
 */
public class ArticleForm {

	/** 投稿者名 */
	@NotBlank(message="名前入力は必須です")
	private String name;
	
	/** 投稿内容 */
	@NotBlank(message="投稿内容を入力してください")
	@Size(min=3,max=250,message="3文字以上250文字以内で入力してください")
	private String content;

	@Override
	public String toString() {
		return "ArticleForm [name=" + name + ", content=" + content + "]";
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

}
