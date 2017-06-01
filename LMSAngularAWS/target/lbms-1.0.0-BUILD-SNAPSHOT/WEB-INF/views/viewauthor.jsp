
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lbms.entity.Author"%>
<%@page import="com.gcit.lbms.service.AuthorService"%>
<%@page import="com.gcit.lbms.entity.Book"%>
<%@include file="include.html"%>
<%
ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
	AuthorService service = (AuthorService) ac.getBean("authorService");
	List<Author> authors = new ArrayList<Author>();
	//Integer authoursCount = service.getAuthoursCount();
	Integer authoursCount = 20;
	Integer noOfPages = 0;
	authors = service.readAllAuthors();
%>
${message}
<script>
	function searchAuthors() {
		$.ajax({
			url : "searchAuthors",
			method : "post",
			data : {
				searchString : $('#searchString').val()
			}
		}).done(function(data) {
			$('#authorsTable').html(data);
		});
	}
	$(document).ready(function() {
		$('.modal').on('hidden.bs.modal', function(e) {
			$(this).removeData();
		});
	});
</script>

<p><a href="author/add" class="btn btn-info pull-center" role="button">AddAuthor</a></p>
<p><a href="author/backToAdmin" class="btn btn-info pull-right" role="button">Back</a></p>

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">...</div>
	</div>
</div>
<script>
	function searchAuthors() {
		$.ajax({
			url : "searchAuthors",
			data : {
				searchString : $('#searchString').val()
			}
		
		}).done(function(data) {
			$('#authorsTable').html(data);
		})
	}
</script>
<div class="input-group">
	<form action="searchAuthors">
		<input type="text" name="searchString" id="searchString"
			class="form-control" placeholder="Search for..."
			oninput="searchAuthors()">
	</form>
</div>
<!-- /input-group -->

<nav aria-label="Page navigation">
	<ul class="pagination">
		<li><a href="#" aria-label="Previous"> <span
				aria-hidden="true">&laquo;</span>
		</a></li>
		<%
			for (int i = 1; i <= noOfPages; i++) {
		%>
		<li><a href="pageAuthors?pageNo=<%=i%>"><%=i%></a></li>
		<%
			}
		%>

		<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>
<div class="col-md-6">
	<table class="table table-striped" id="authorsTable">
		<thead>
			<tr>
				<th>#</th>
				<th>Author Name</th>
				<th>Books Written</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (Author a : authors) {
			%>
			<tr>
				<td><%=authors.indexOf(a) + 1%></td>
				<td><%=a.getAuthorName()%></td>

				<td>
					<%
						if (a.getBooks() != null && !a.getBooks().isEmpty()) {
								List<Book> books = a.getBooks();
								for (Book b : books) {
									out.println(b.getTitle());
									out.println(", ");

								}
							}
					%>
				</td>
				
				<td><a class="btn btn-primary"	data-toggle="modal" data-target="#editAuthourModal" 
					    href="loadEditPage?authorId=<%=a.getAuthorId()%>&authorName=<%=a.getAuthorName()%>">update</a></td>
				<td><button type="button" class="btn btn-sm btn-danger"
						data-toggle="modal" data-target="#deleteAuthourModal"
						href="author/loadDeletePage?authorId=<%=a.getAuthorId()%>&authorName=<%=a.getAuthorName()%>">delete</button></td>
			</tr>
			<%
				}	
			%>

		</tbody>
	</table>
</div>
<div class="modal fade delete-authour-modal" id="deleteAuthourModal"
	tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document"></div>
			<div class="modal-content"></div>
</div>

<div class="modal fade edit-authour-modal" id="editAuthourModal"
	tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content"></div>
	</div>
</div>




