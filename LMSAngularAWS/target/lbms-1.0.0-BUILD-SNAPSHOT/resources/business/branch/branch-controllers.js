lmsApp.controller("branchController", function(branchConstants, $scope, $http,
		$window, $location, branchService, $filter, Pagination) {
	if ($location.$$path === "/viewbranchs") {
		branchService.getAllAuthorsService().then(
				function(backendAuthorsList) {
					$scope.authors = backendAuthorsList;
					$scope.pagination = Pagination.getNew(10);
					$scope.pagination.numPages = Math
							.ceil($scope.authors.length
									/ $scope.pagination.perPage);
				});
	} 

	$scope.checkLibrarian=function(){
		branchService.getBranchByPKService($scope.branchId).then(
				function(backendBranch) {
					if(backendBranch===null){
						$scope.Invalid=true;
					}
				});
		
	}
	$scope.saveAuthor = function() {
		$http.post(branchConstants.ADD_AUTHOR_URL, $scope.author).success(
				function() {
					$window.location.href = "#/viewauthors";
				});
	}

	$scope.sort = function() {
		$scope.authors = $filter('orderBy')($scope.authors, 'authorName');
	}
	$scope.showAddAuthorModal = function() {
			$scope.addAuthorModal = true;
			$http.get(branchConstants.INIT_AUTHOR_URL).success(
					function(backendAuthorsList) {
						$scope.author = backendAuthorsList;
					});
	}

	$scope.showEditAuthorModal = function(authorId) {
		branchService.getAuthorByPKService(authorId).then(function(data) {
			$scope.editAuthorModal = true;
			$scope.author = data;

		});
	}

	$scope.showDeleteAuthorModal = function(authorId) {
		branchService.getAuthorByPKService(authorId).then(function(data) {
			$scope.deleteAuthorModal = true;
			$scope.author = data;

		});
	}
	$scope.closeModal = function() {
		$scope.editAuthorModal = false;
		$scope.deleteAuthorModal = false;
		$scope.addAuthorModal = false;
		
	}
	$scope.addAuthor = function() {
		$http.post(branchConstants.ADD_AUTHOR_URL, $scope.author).success(
				function() {
					$scope.addAuthorModal = false;
					branchService.getAllAuthorsService().then(
							function(backendAuthorsList) {
								$scope.authors = backendAuthorsList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.authors.length
												/ $scope.pagination.perPage);
							});
				});
	}
	$scope.updateAuthor = function() {
		$http.post(branchConstants.UPDATE_AUTHOR_URL, $scope.author).success(
				function() {
					$scope.editAuthorModal = false;
					branchService.getAllAuthorsService().then(
							function(backendAuthorsList) {
								$scope.authors = backendAuthorsList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.authors.length
												/ $scope.pagination.perPage);
							});
				});
	}
	$scope.deleteAuthor = function() {
		$http.post(branchConstants.DELETE_AUTHOR_URL, $scope.author).success(
				function() {
					$scope.deleteAuthorModal = false;
					branchService.getAllAuthorsService().then(
							function(backendAuthorsList) {
								$scope.authors = backendAuthorsList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.authors.length
												/ $scope.pagination.perPage);
							});
				});
	}
	
	$scope.searchAuthors = function() {
		$http.get(branchConstants.SEARCH_AUTHOR_URL + $scope.searchString)
				.success(
						function(data) {
							$scope.authors = data;
							$scope.pagination = Pagination.getNew(10);
							$scope.pagination.numPages = Math
									.ceil($scope.authors.length
											/ $scope.pagination.perPage);
						});
	}
})