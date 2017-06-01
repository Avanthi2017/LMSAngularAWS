lmsApp.controller("adminGenreController", function(genreConstants, $scope, $http,
		$window, $location, genreService, $filter, Pagination) {
	if ($location.$$path === "/viewgenres") {
		genreService.getAllGenresService().then(
				function(backendGenresList) {
					$scope.genres = backendGenresList;
					$scope.pagination = Pagination.getNew(10);
					$scope.pagination.numPages = Math
							.ceil($scope.genres.length
									/ $scope.pagination.perPage);
				});
	} 


	$scope.sort = function() {
		$scope.genres = $filter('orderBy')($scope.genres, 'genreName');
	}
	$scope.showAddGenreModal = function() {
			$scope.addGenreModal = true;
			$http.get(genreConstants.INIT_GENRE_URL).success(
					function(backendGenresList) {
						$scope.genre = backendGenresList;
					});
	}

	$scope.showEditGenreModal = function(genreId) {
		genreService.getGenreByPKService(genreId).then(function(data) {
			$scope.editGenreModal = true;
			$scope.genre = data;

		});
	}
	$scope.back = function() {
		$window.location.href = "#/admin";
		
	}
	$scope.showDeleteGenreModal = function(genreId) {
		genreService.getGenreByPKService(genreId).then(function(data) {
			$scope.deleteGenreModal = true;
			$scope.genre = data;

		});
	}
	$scope.closeModal = function() {
		$scope.editGenreModal = false;
		$scope.deleteGenreModal = false;
		$scope.addGenreModal = false;
		
	}
	$scope.addGenre = function() {
		$http.post(genreConstants.ADD_GENRE_URL, $scope.genre).success(
				function() {
					$scope.addGenreModal = false;
					genreService.getAllGenresService().then(
							function(backendGenresList) {
								$scope.genres = backendGenresList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.genres.length
												/ $scope.pagination.perPage);
							});
				});
	}
	$scope.updateGenre = function() {
		$http.post(genreConstants.UPDATE_GENRE_URL, $scope.genre).success(
				function() {
					$scope.editGenreModal = false;
					genreService.getAllGenresService().then(
							function(backendGenresList) {
								$scope.genres = backendGenresList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.genres.length
												/ $scope.pagination.perPage);
							});
				});
	}
	$scope.deleteGenre = function() {
		$http.post(genreConstants.DELETE_GENRE_URL, $scope.genre).success(
				function() {
					$scope.deleteGenreModal = false;
					genreService.getAllGenresService().then(
							function(backendGenresList) {
								$scope.genres = backendGenresList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.genres.length
												/ $scope.pagination.perPage);
							});
				});
	}
	
	$scope.searchGenres = function() {
		$http.get(genreConstants.SEARCH_GENRE_URL + $scope.searchString)
				.success(
						function(data) {
							$scope.genres = data;
							$scope.pagination = Pagination.getNew(10);
							$scope.pagination.numPages = Math
									.ceil($scope.genres.length
											/ $scope.pagination.perPage);
						});
	}
})