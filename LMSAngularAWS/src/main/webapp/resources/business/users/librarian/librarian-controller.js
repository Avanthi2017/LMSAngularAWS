lmsApp.controller("librarianUserController", function(branchConstants, $scope,
		$window, $location, branchService,$http) {
	$scope.branch={}
	$scope.bookloan={}
	$scope.branchs={}
	$scope.lib_branchId=0
	$scope.checkLibrarian=function(){
		branchService.getBranchByPKService($scope.lib_branchId).then(
				function(backendBranch) {
					$scope.branch=backendBranch
					if(backendBranch===""||backendBranch===null){
						$scope.lib_branchId="";
						$scope.Invalid="";
						$window.location.href = "#/librarian";
					}else{
						$window.location.href = "#/viewlibraian";
					}
				});
		
	}
	$scope.back = function() {
		$window.location.href = "#/home";
		
	}
	if($location.$$path === "/viewlibraian"){
		$scope.branch=branchService.getBranch();
	}
	$scope.updateBranchLib = function() {
		$http.post(branchConstants.UPDATE_BRANCH_LIBRARIAN_URL, $scope.branch).success(
				function() {
					branchService.getBranchByPKService($scope.branch.branchId).then(
							function(backendbranch) {
								$scope.branch = backendbranch;
				});
					}
	
				)}
	
			})