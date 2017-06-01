lmsApp.controller("adminBranchController", function(branchConstants, $scope, $http,
		$window, $location, branchService, $filter, Pagination) {
	if ($location.$$path === "/viewbranchs") {
		branchService.getAllBranchsService().then(
				function(backendbranchsList) {
					$scope.branchs = backendbranchsList;
					$scope.pagination = Pagination.getNew(10);
					$scope.pagination.numPages = Math
							.ceil($scope.branchs.length
									/ $scope.pagination.perPage);
				});
	} 

	$scope.showAddBranchModal = function() {
			$scope.addBranchModal = true;
			$http.get(branchConstants.INIT_BRANCH_URL).success(
					function(backendbranchsList) {
						$scope.branch = backendbranchsList;
					});
	}

	$scope.showEditBranchModal = function(branchId) {
		branchService.getBranchByPKService(branchId).then(function(data) {
			$scope.editBranchModal = true;
			$scope.branch = data;

		});
	}
	$scope.back = function() {
		$window.location.href = "#/admin";
		
	}
	$scope.showDeleteBranchModal = function(branchId) {
		branchService.getBranchByPKService(branchId).then(function(data) {
			$scope.deleteBranchModal = true;
			$scope.branch = data;

		});
	}
	$scope.closeModal = function() {
		$scope.editBranchModal = false;
		$scope.deleteBranchModal = false;
		$scope.addBranchModal = false;
		
	}

	$scope.addBranch = function() {
		$scope.addBranchModal = false;
		$http.post(branchConstants.ADD_BRANCH_URL, $scope.branch).success(
				function() {
					
					branchService.getAllBranchsService().then(
							function(backendbranchsList) {
								$scope.branchs = backendbranchsList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.branchs.length
												/ $scope.pagination.perPage);
							});
				});
	}
	$scope.updateBranch = function() {
		$http.post(branchConstants.UPDATE_BRANCH_URL, $scope.branch).success(
				function() {
					$scope.editBranchModal = false;
					branchService.getAllBranchsService().then(
							function(backendbranchsList) {
								$scope.branchs = backendbranchsList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.branchs.length
												/ $scope.pagination.perPage);
							});
				});
	}
	$scope.deleteBranch = function() {
		$http.post(branchConstants.DELETE_BRANCH_URL, $scope.branch).success(
				function() {
					$scope.deleteBranchModal = false;
					branchService.getAllBranchsService().then(
							function(backendbranchsList) {
								$scope.branchs = backendbranchsList;
								$scope.pagination = Pagination.getNew(10);
								$scope.pagination.numPages = Math
										.ceil($scope.branchs.length
												/ $scope.pagination.perPage);
							});
				});
	}
	
	$scope.searchbranchs = function() {
		$http.get(branchConstants.SEARCH_BRANCH_URL + $scope.searchString)
				.success(
						function(data) {
							$scope.branchs = data;
							$scope.pagination = Pagination.getNew(10);
							$scope.pagination.numPages = Math
									.ceil($scope.branchs.length
											/ $scope.pagination.perPage);
						});
	}
})