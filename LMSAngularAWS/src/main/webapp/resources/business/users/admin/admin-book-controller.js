lmsApp.controller("adminBookController", function(bookConstants, $scope, $http,
		$window, $location, bookService, authorService, genreService,
		publisherService, branchService, $filter, Pagination, $q) {
	$scope.addBook={}
	$scope.book={}
	$scope.book.authors = [];
	$scope.book.genres = [];
	$scope.book.bookCopies = [];
	
	if ($location.$$path === "/viewbooks") {
		bookService.getAllBooksService().then(
				function(backendBooksList) {
					$scope.books = backendBooksList;
					$scope.pagination = Pagination.getNew(10);
					$scope.pagination.numPages = Math.ceil($scope.books.length
							/ $scope.pagination.perPage);
				});
	}

	$scope.showAddBookModal = function() {
		$scope.addBookModal = true;

		$q.all([ authorService.getAllAuthorsService(),
				genreService.getAllGenresService(),
				publisherService.getAllPublishersService(),
				branchService.getAllBranchsService()]).then(
				function(responses) {

					$scope.addBook.authors = responses[0];
					$scope.addBook.genres = responses[1];
					$scope.addBook.publishers = responses[2];
					$scope.addBook.branchs = responses[3];})

		$http.get(bookConstants.INIT_BOOK_URL).success(
				function(backendBookObj) {
					$scope.addBook.book = backendBookObj;
				});
		
		
	}
	$scope.back = function() {
		$window.location.href = "#/admin";
		
	}
	$scope.showEditBookModal = function(bookId) {
		$scope.editBookModal = true;
		$q.all(
				[ bookService.getBookByPKService(bookId),
						authorService.getAllAuthorsService(),
						genreService.getAllGenresService(),
						publisherService.getAllPublishersService(),
						branchService.getBranchByBookIdService(bookId) ]).then(
				function(responses) {

					$scope.book = responses[0];
					$scope.authors = responses[1];
					$scope.genres = responses[2];
					$scope.publishers = responses[3];
					$scope.branchs = responses[4];

				})

	}

	$scope.showDeleteBookModal = function(bookId) {
		bookService.getBookByPKService(bookId).then(function(data) {
			$scope.deleteBookModal = true;
			$scope.book=data;
		});
	}
	$scope.closeModal = function() {
		$scope.editBookModal = false;
		$scope.deleteBookModal = false;
		$scope.addBookModal = false;

	}
	$scope.addBook = function(publishersmodel, authorsmodel, genresmodel) {
		$scope.addBookModal = false;
		$scope.addBook.book.authors = authorsmodel;
		$scope.addBook.book.genres = genresmodel;
		$scope.addBook.book.pubId = publishersmodel.publisherId;
		$http.post(bookConstants.ADD_BOOK_URL, $scope.addBook.book) .success(
				function() {
					$http.post(bookConstants.ADD_BOOKCOPIES_BY_BOOKID_URL,$scope.addBook.branchs)
					bookService.getAllBooksService().then(
							function(backendBooksList) {
								$scope.books = backendBooksList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.books.length
												/ $scope.pagination.perPage);
							});
				});
	}
	$scope.updateBook = function(publishersmodel, authorsmodel, genresmodel) {
		$scope.editBookModal = false;
		$scope.book.authors = authorsmodel;
		$scope.book.genres = genresmodel;
		$scope.book.publisher = publishersmodel;
		$http.post(bookConstants.UPDATE_BOOK_URL, $scope.book)
		$http.post(bookConstants.UPDATE_BOOKCOPIES_BY_BOOKID_URL,
				$scope.branchs).success(
				function() {
					bookService.getAllBooksService().then(
							function(backendBooksList) {
								$scope.books = backendBooksList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.books.length
												/ $scope.pagination.perPage);
							});
				});
	}
	$scope.deleteBook = function(bookbranchmodal) {
		 $scope.book.bookCopies[0].branch=bookbranchmodal
		$http.post(bookConstants.DELETE_BOOK_URL, $scope.book).success(
				function() {
					$scope.deleteBookModal = false;
					bookService.getAllBooksService().then(
							function(backendBooksList) {
								$scope.books = backendBooksList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.books.length
												/ $scope.pagination.perPage);
							});
				});
	}

	$scope.searchBooks = function() {
		$http.get(bookConstants.SEARCH_BOOK_URL + $scope.searchString)
				.success(
						function(data) {
							$scope.books = data;
							$scope.pagination = Pagination.getNew(10);
							$scope.pagination.numPages = Math
									.ceil($scope.books.length
											/ $scope.pagination.perPage);
						});
	}
})