lmsApp.controller("adminBorrowerController", function(borrowerConstants, $scope, $http,
		$window, $location, borrowerService, $filter, Pagination) {
	if ($location.$$path === "/viewborrowers") {
		borrowerService.getAllBorrowersService().then(
				function(backendBorrowersList) {
					$scope.borrowers = backendBorrowersList;
					$scope.pagination = Pagination.getNew(10);
					$scope.pagination.numPages = Math
							.ceil($scope.borrowers.length
									/ $scope.pagination.perPage);
				});
	} 
	if ($location.$$path === "/viewbookLoans") {
		borrowerService.getAllActiveBorrowersService().then(
				function(backendBorrowersList) {
					$scope.borrowers = backendBorrowersList;
					$scope.pagination = Pagination.getNew(10);
					$scope.pagination.numPages = Math
							.ceil($scope.borrowers.length
									/ $scope.pagination.perPage);
				});
	}
	$scope.showEditBookLoan = function(Overridebookloan) {
		$scope.editBookLoanModal = true;
		$scope.bookloan = Overridebookloan;
	
}
	$scope.back = function() {
		$window.location.href = "#/admin";
		
	}
$scope.editBookLoan = function(noOfDays) {
	$scope.editBookLoanModal = false;
	$http.get(borrowerConstants.OVERWRITE_BOOKLOAN_URL+$scope.bookloan.book.bookId
			+"/"+$scope.bookloan.branch.branchId+"/"+$scope.bookloan.borrower.cardNo+"/"+noOfDays)
			.success(function() {
				borrowerService.getAllActiveBorrowersService().then(
						function(backendBorrowersList) {
							$scope.borrowers = backendBorrowersList;
						});
				
			}
			);
}

	$scope.saveBorrower = function() {
		$scope.addBorrowerModal = false;
		$http.post(borrowerConstants.ADD_BORROWER_URL, $scope.borrower).success(
				function() {
					borrowerService.getAllBorrowersService().then(
							function(backendBorrowersList) {
								$scope.borrowers = backendBorrowersList;
				});
				});
	}

	$scope.showAddBorrowerModal = function() {
			$scope.addBorrowerModal = true;
			$http.get(borrowerConstants.INIT_BORROWER_URL).success(
					function(backendBorrowersList) {
						$scope.borrower = backendBorrowersList;
					});
	}

	$scope.showEditBorrowerModal = function(cardNo) {
		borrowerService.getBorrowerByCardService(cardNo).then(function(data) {
			$scope.editBorrowerModal = true;
			$scope.borrower = data;

		});
	}

	$scope.showDeleteBorrowerModal = function(cardNo) {
		borrowerService.getBorrowerByCardService(cardNo).then(function(data) {
			$scope.deleteBorrowerModal = true;
			$scope.borrower = data;

		});
	}
	$scope.closeModal = function() {
		$scope.editBorrowerModal = false;
		$scope.deleteBorrowerModal = false;
		$scope.addBorrowerModal = false;
		$scope.editBookLoanModal = false;
		
	}

	$scope.addBorrower = function() {
		$scope.addBorrowerModal = false;
		$http.post(borrowerConstants.ADD_BORROWER_URL, $scope.borrower).success(
				function() {
					
					borrowerService.getAllBorrowersService().then(
							function(backendBorrowersList) {
								$scope.borrowers = backendBorrowersList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.borrowers.length
												/ $scope.pagination.perPage);
							});
				});
	}
	$scope.updateBorrower = function() {
		$http.post(borrowerConstants.UPDATE_BORROWER_URL, $scope.borrower).success(
				function() {
					$scope.editBorrowerModal = false;
					borrowerService.getAllBorrowersService().then(
							function(backendBorrowersList) {
								$scope.borrowers = backendBorrowersList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.borrowers.length
												/ $scope.pagination.perPage);
							});
				});
	}
	$scope.deleteBorrower = function() {
		$http.post(borrowerConstants.DELETE_BORROWER_URL, $scope.borrower).success(
				function() {
					$scope.deleteBorrowerModal = false;
					borrowerService.getAllBorrowersService().then(
							function(backendBorrowersList) {
								$scope.borrowers = backendBorrowersList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.borrowers.length
												/ $scope.pagination.perPage);
							});
				});
	}
	
	$scope.searchBorrowers = function() {
		$http.get(borrowerConstants.SEARCH_BORROWER_URL + $scope.searchString)
				.success(
						function(data) {
							$scope.borrowers = data;
							$scope.pagination = Pagination.getNew(10);
							$scope.pagination.numPages = Math
									.ceil($scope.borrowers.length
											/ $scope.pagination.perPage);
						});
	}
})