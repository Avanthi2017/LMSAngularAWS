lmsApp.controller("borrowerUserController", function(borrowerConstants, $scope,$http,
		$window, $location, borrowerService,branchService) {
	$scope.cardNo=0;
	$scope.borrower={}
	$scope.bookloan={}
	$scope.branchs={}

    // switch flag
    $scope.switchBool = function (value) {
        $scope[value] = !$scope[value];
    };
	$scope.checkBorrower=function(){
		borrowerService.getBorrowerByCardService($scope.cardNo).then(
				function(backendBorrower) {
					if(backendBorrower===""||backendBorrower===null){
						$scope.submissionSuccess=true;
						$scope.cardNo="";
						$scope.Invalid="";
						$window.location.href = "#/borrower";
						
					}else{
						$window.location.href = "#/viewborrower";
					}
				});
		
	}
	
	$scope.back = function() {
		$window.location.href = "#/home";
		
	}
	if($location.$$path === "/viewborrower"){
			$scope.borrower = borrowerService.getBorrower();
	}
	$scope.checkInBook = function(bookLoan) {
		$http.post(borrowerConstants.UPDATE_BOOKLOAN_URL, bookLoan).success(
				function() {
						borrowerService.getBorrowerByCardService($scope.borrower.cardNo).then(
								function(backendborrowerList) {
									$scope.borrower = backendborrowerList;
					});
				});
	}
	$scope.getAllBranchs = function() {
		branchService.getAllBranchsService().then(
				function(backendBranchsList) {
					$http.get(borrowerConstants.INIT_BOOKLOAN_URL).success(
							function(backendBookLoanObject) {
								$scope.branchs = backendBranchsList;
								$scope.branchSelectionModal = true;
							});
				})

	}
	$scope.getBooksByBranchId = function(branchmodel) {
		$scope.branchSelectionModal = false;
		branchService.getBookCopiesByBranchIdService(branchmodel.branchId)
				.then(function(backendBookCopiesList) {
					$scope.bookCopies = backendBookCopiesList;
					$scope.bookSelectionModal = true;

				});
	}
	$scope.checkOutBook = function(bookCopiesmodel) {
		$scope.bookSelectionModal = false;
		$scope.bookloan.book = bookCopiesmodel.book;
		$scope.bookloan.borrower = $scope.borrower;
		$scope.bookloan.branch=bookCopiesmodel.branch;
		$http.post(borrowerConstants.ADD_BOOKLOAN_URL, $scope.bookloan)
				.success(function() {
					$scope.bookCopies ={};
					$scope.branchs ={};
					borrowerService.getBorrowerByCardService($scope.borrower.cardNo).then(
							function(backendborrowerList) {
								$scope.borrower = backendborrowerList;
				});
			
		});
	}
	$scope.closeModal = function() {
		$scope.bookSelectionModal = false;
		$scope.branchSelectionModal = false;

	}
	
})