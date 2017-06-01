lmsApp.controller("authorController", function(authorConstants, $scope, $http,
		$window, $location, authorService, $filter, Pagination) {
	if ($location.$$path === "/viewauthors") {
		authorService.getAllAuthorsService().then(
				function(backendAuthorsList) {
					$scope.authors = backendAuthorsList;
					$scope.pagination = Pagination.getNew(10);
					$scope.pagination.numPages = Math
							.ceil($scope.authors.length
									/ $scope.pagination.perPage);
				});
	} 

	$scope.saveAuthor = function() {
		$http.post(authorConstants.ADD_AUTHOR_URL, $scope.author).success(
				function() {
					$window.location.href = "#/viewauthors";
				});
	}

	$scope.sort = function() {
		$scope.authors = $filter('orderBy')($scope.authors, 'authorName');
	}
	$scope.showAddAuthorModal = function() {
			$scope.addAuthorModal = true;
			$http.get(authorConstants.INIT_AUTHOR_URL).success(
					function(backendAuthorsList) {
						$scope.author = backendAuthorsList;
					});
	}

	$scope.showEditAuthorModal = function(authorId) {
		authorService.getAuthorByPKService(authorId).then(function(data) {
			$scope.editAuthorModal = true;
			$scope.author = data;

		});
	}

	$scope.showDeleteAuthorModal = function(authorId) {
		authorService.getAuthorByPKService(authorId).then(function(data) {
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
		$http.post(authorConstants.ADD_AUTHOR_URL, $scope.author).success(
				function() {
					$scope.addAuthorModal = false;
					authorService.getAllAuthorsService().then(
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
		$http.post(authorConstants.UPDATE_AUTHOR_URL, $scope.author).success(
				function() {
					$scope.editAuthorModal = false;
					authorService.getAllAuthorsService().then(
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
		$http.post(authorConstants.DELETE_AUTHOR_URL, $scope.author).success(
				function() {
					$scope.deleteAuthorModal = false;
					authorService.getAllAuthorsService().then(
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
		$http.get(authorConstants.SEARCH_AUTHOR_URL + $scope.searchString)
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