<%@page import="com.gcit.lbms.service.AuthorService"%>
<%@page import="com.gcit.lbms.entity.Author"%>
<div>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">Delete Author</h4>
	</div>
	<form action="deleteAuthor">
		<div class="modal-body">
			<p>You have selected to delete the author</p>
			<input type="hidden" name="authorId" value="${author.getAuthorId() %>">
			<p>"${author.getAuthorName()}</p>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">delete</button>
		</div>
	</form>
</div>
<!-- /.modal-content -->
