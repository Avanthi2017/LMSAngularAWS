<%@include file="include.html" %>
${message}
<div>
	<div class="modal-header">
		<h4 class="modal-title">Welcome Borrower!!</h4>
	</div>
	<form action="checkborrower" id="myForm">
		<div class="modal-body">
			<p>Enter cardNo</p>
			<div class="form-group">
				<div class="col-lg-12">
			<input size="auto" class="form-control required" type="number" name="cardNo" id="cardNo" value="">
		</div>
		</div>
		</div>
		<div class="modal-footer">
			<button type="submit" class="btn btn-primary">Submit</button>
		</div>
	</form>
</div>
<!-- /.modal-content -->
