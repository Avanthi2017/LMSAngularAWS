<%@include file="include.html" %>
${message} 
<div>
	<div class="modal-header">
		<h4 class="modal-title">Welcome Librarian!!</h4>
	</div>
	<form action="checklibrarian" id="myForm">
		<div class="modal-body">
			<p>Enter BranchId</p>
			<div class="form-group">
				<div class="col-lg-12">
			<input class="form-control required" type="number" name="branchId" id="branchId" value="">
		</div>
		</div>
		</div>
		<div class="modal-footer">
			<button type="submit" class="btn btn-primary">Submit</button>
		</div>
	</form>
</div>

<!-- /.modal-content -->
<script type="text/javascript">
	$('#myForm').on(
			'submit',
			function(e) {
				var branchId = $('#branchId');

				// Check if there is an entered value
				if (!branchId.val()) {
					// Add errors highlight
					branchId.closest('.form-group')
							.removeClass('has-success').addClass('has-error');

					// Stop submission of the form
					e.preventDefault();
				} else {
					// Remove the errors highlight
					branchId.closest('.form-group').removeClass('has-error')
							.addClass('has-success');
				}
			});
</script>