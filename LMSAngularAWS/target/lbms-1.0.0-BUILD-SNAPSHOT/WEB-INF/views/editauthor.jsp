
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">Edit Author</h4>
	</div>
	<form action="updateAuthor" method="post" id="myForm">
		<div class="modal-body">
			<p>Enter the details of author to update</p>
			<div class="form-group">
				<div class="col-lg-12">
			<input type="hidden" name="authorId" value="${author.authorId}">
			<input class="form-control required" type="text" name="authorName" id="name" value="${author.authorName}">
		</div>
		</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="submit" class="btn btn-primary">Save changes</button>
		</div>
	</form>
