lmsApp.controller("adminPublisherController", function(publisherConstants, $scope, $http,
		$window, $location, publisherService, $filter, Pagination) {
	if ($location.$$path === "/viewpublishers") {
		publisherService.getAllPublishersService().then(
				function(backendPublishersList) {
					$scope.publishers = backendPublishersList;
					$scope.pagination = Pagination.getNew(10);
					$scope.pagination.numPages = Math
							.ceil($scope.publishers.length
									/ $scope.pagination.perPage);
				});
	} 


	$scope.showAddPublisherModal = function() {
			$scope.addPublisherModal = true;
			$http.get(publisherConstants.INIT_PUBLISHERS_URL).success(
					function(backendPublishersList) {
						$scope.publisher = backendPublishersList;
					});
	}

	$scope.showEditPublisherModal = function(publisherId) {
		publisherService.getPublisherByPKService(publisherId).then(function(data) {
			$scope.editPublisherModal = true;
			$scope.publisher = data;

		});
	}
	$scope.back = function() {
		$window.location.href = "#/admin";
		
	}
	$scope.showDeletePublisherModal = function(publisherId) {
		publisherService.getPublisherByPKService(publisherId).then(function(data) {
			$scope.deletePublisherModal = true;
			$scope.publisher = data;

		});
	}
	$scope.closeModal = function() {
		$scope.editPublisherModal = false;
		$scope.deletePublisherModal = false;
		$scope.addPublisherModal = false;
		
	}

	$scope.addPublisher = function() {
		$scope.addPublisherModal = false;
		$http.post(publisherConstants.ADD_PUBLISHERS_URL, $scope.publisher).success(
				function() {
					
					publisherService.getAllPublishersService().then(
							function(backendPublishersList) {
								$scope.publishers = backendPublishersList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.publishers.length
												/ $scope.pagination.perPage);
							});
				});
	}
	$scope.updatePublisher = function() {
		$http.post(publisherConstants.UPDATE_PUBLISHERS_URL, $scope.publisher).success(
				function() {
					$scope.editPublisherModal = false;
					publisherService.getAllPublishersService().then(
							function(backendPublishersList) {
								$scope.publishers = backendPublishersList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.publishers.length
												/ $scope.pagination.perPage);
							});
				});
	}
	$scope.deletePublisher = function() {
		$http.post(publisherConstants.DELETE_PUBLISHERS_URL, $scope.publisher).success(
				function() {
					$scope.deletePublisherModal = false;
					publisherService.getAllPublishersService().then(
							function(backendPublishersList) {
								$scope.publishers = backendPublishersList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.publishers.length
												/ $scope.pagination.perPage);
							});
				});
	}
	
	$scope.searchPublishers = function() {
		$http.get(publisherConstants.SEARCH_PUBLISHERS_URL + $scope.searchString)
				.success(
						function(data) {
							$scope.publishers = data;
							$scope.pagination = Pagination.getNew(10);
							$scope.pagination.numPages = Math
									.ceil($scope.publishers.length
											/ $scope.pagination.perPage);
						});
	}
})