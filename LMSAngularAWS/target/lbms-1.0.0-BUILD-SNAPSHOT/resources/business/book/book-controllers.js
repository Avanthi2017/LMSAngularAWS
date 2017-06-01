lmsApp.controller(
				"bookController",
				function(bookConstants, $scope, $http, $window, $location,
						bookService, authorService, genreService,
						publisherService, branchService, $filter, Pagination) {
					
					$scope.branchAndCopies ={};
					$scope.authorIds = {};
					$scope.publisherId="";
					$scope.bookPublisher={};
					if ($location.$$path === "/viewbooks") {
						bookService
								.getAllBooksService()
								.then(
										function(backendBooksList) {
											$scope.books = backendBooksList;
											$scope.pagination = Pagination
													.getNew(10);
											$scope.pagination.numPages = Math
													.ceil($scope.books.length
															/ $scope.pagination.perPage);
										});
					}
					
					$scope.isSelected=function(listOfItems, item) {
						var resArr = listOfItems.split(",");
						if (resArr.indexOf(item.toString()) > -1) {
						    return true;
						  } else {
						    return false;
						  }
						}

					$scope.saveAuthor = function() {
						$http.post(bookConstants.ADD_AUTHOR_URL, $scope.author)
								.success(function() {
									$window.location.href = "#/viewbooks";
								});
					}

					$scope.sort = function() {
						$scope.books = $filter('orderBy')($scope.books,
								'authorName');
					}
					$scope.showAddAuthorModal = function() {
						$scope.addAuthorModal = true;
						$http.get(bookConstants.INIT_AUTHOR_URL).success(
								function(backendBooksList) {
									$scope.author = backendBooksList;
								});
					}

					$scope.showEditBookModal = function(bookId) {
						$scope.editBookModal = true;
						bookService
								.getBookByPKService(bookId)
								.then(
										function(data) {
											authorService
													.getAllAuthorsService()
													.then(
															function(
																	backendAuthorsList) {
																genreService
																		.getAllGenresService()
																		.then(
																				function(
																						backendGenresList) {
																					publisherService
																							.getAllPublishersService()
																							.then(
																									function(
																											backendPublisherList) {
																										branchService
																												.getAllBranchsService()
																												.then(
																														function(
																																backendBranchList) {

																															$scope.authors = backendAuthorsList;
																															$scope.genres = backendGenresList;
																															$scope.publishers = backendPublisherList;
																															$scope.branchs = backendBranchList;
																															$scope.book = data;
																															//$scope.bookPublisher = $scope.book.publisher.publisherName;
																															//$scope.bookPublisher = $scope.publishers[0];

																														});
																									});
																				});
															});
										});
					}

					$scope.getNoOfCopies = function(book, branchId) {
						for (var i = 0; i < book.bookCopies; i++) {
							if (book.bookCopies.get(i).branchId === branchId) {
								$scope[branchId][book.title]["noOfCopies"] = book.bookCopies.get(i).noOfCopies;
								return $scope[branchId][book.title]["noOfCopies"];
							}
						}
					}

					$scope.showDeleteAuthorModal = function(authorId) {
						bookService.getAuthorByPKService(authorId).then(
								function(data) {
									$scope.deleteAuthorModal = true;
									$scope.author = data;

								});
					}
					$scope.closeModal = function() {
						$scope.editBookModal = false;
						$scope.deleteBookModal = false;
						$scope.addBookModal = false;

					}
					$scope.addAuthor = function() {
						$http
								.post(bookConstants.ADD_AUTHOR_URL,
										$scope.author)
								.success(
										function() {
											$scope.addAuthorModal = false;
											bookService
													.getAllBooksService()
													.then(
															function(
																	backendBooksList) {
																$scope.books = backendBooksList;
																$scope.pagination = Pagination
																		.getNew(10);
																$scope.pagination.numPages = Math
																		.ceil($scope.books.length
																				/ $scope.pagination.perPage);
															});
										});
					}
					$scope.updateBook = function() {
						
						
						
						alert($scope.bookPublisher.publisherName)
								.success(
										function() {
											bookService
													.getAllBooksService()
													.then(
															function(
																	backendBooksList) {
																$scope.books = backendBooksList;
																$scope.pagination = Pagination
																		.getNew(10);
																$scope.pagination.numPages = Math
																		.ceil($scope.books.length
																				/ $scope.pagination.perPage);
															});
										});
					}
					$scope.deleteBook = function() {
						$http.post(bookConstants.UPDATE_BOOK_URL, $scope.book)
						$http.get(bookConstants.DELETE_BOOK_GENRE_URL,
								$scope.book.bookId)
						$http.get(bookConstants.DELETE_BOOK_AUTHOR_URL,
								$scope.book.bookId)

						$http
								.post(bookConstants.DELETE_AUTHOR_URL,
										$scope.author)
								.success(
										function() {
											$scope.deleteAuthorModal = false;
											bookService
													.getAllBooksService()
													.then(
															function(
																	backendBooksList) {
																$scope.books = backendBooksList;
																$scope.pagination = Pagination
																		.getNew(10);
																$scope.pagination.numPages = Math
																		.ceil($scope.books.length
																				/ $scope.pagination.perPage);
															});
										});
					}

					$scope.searchBooks = function() {
						$http
								.get(
										bookConstants.SEARCH_AUTHOR_URL
												+ $scope.searchString)
								.success(
										function(data) {
											$scope.books = data;
											$scope.pagination = Pagination
													.getNew(10);
											$scope.pagination.numPages = Math
													.ceil($scope.books.length
															/ $scope.pagination.perPage);
										});
					}
				})