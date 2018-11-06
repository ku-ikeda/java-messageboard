<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


<!-- 入力フォーム -->
	<h2>掲示板アプリケーション</h2>
	<form:form modelAttribute="articleForm" action="${pageContext.request.contextPath}/article/insertArticle">
	<form:errors path="name" cssStyle="color:red" element="div"/>
	投稿者名：<form:input path="name"/><br>
	<form:errors path="content" cssStyle="color:red" element="div"/>
	投稿内容：<form:input path="content"/><br>
	<input type="submit" value="記事投稿">
	</form:form>


<!-- 記事一覧のみ表示 -->
	<c:forEach var="article" items="${articleList}">
		<hr>
	投稿者ID:<c:out value="${article.id}"></c:out><br>
	投稿者名：<c:out value="${article.name}"></c:out><br>
	投稿内容：<c:out value="${article.content}"></c:out><br>
    
    <!-- 記事削除 -->
    <form:form modelAttribute="article" action="${pageContext.request.contextPath}/article/deleteArticle">
    	<input type="hidden" name="articleId" value="${article.id}">
    	<input type="submit" value="記事削除">
    </form:form>
    
    <!-- 投稿に対してのコメント -->
    <c:forEach var="comment" items="${article.commentList}">
	コメントID:<c:out value="${comment.id}"/><br>
	コメント者名：<c:out value="${comment.name}"/><br>
	コメント内容：<c:out value="${comment.content}"/><br>
	</c:forEach>
	
	<form:form modelAttribute="commentForm" action="${pageContext.request.contextPath}/article/insertComment">
	<c:if test="${article.id == commentForm.articleId}">
	<form:errors path="name" cssStyle="color:red" element="div"/></c:if>
	投稿者名：<form:input path="name"/><br>
	<c:if test="${article.id == commentForm.articleId}">
	<form:errors path="content" cssStyle="color:red" element="div"/></c:if>
	投稿内容：<form:input path="content"/><br>
	<input type="hidden" name="articleId" value="${article.id}">
	<input type="submit" value="コメント投稿">
	</form:form>
	</c:forEach>
	
	
	
	

</body>
</html>