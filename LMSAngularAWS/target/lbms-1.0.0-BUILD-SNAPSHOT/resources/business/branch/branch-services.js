lmsApp.factory("branchService", function($http, branchConstants){
	return{
		getAllBranchsService: function(){
			var getBranchData = {};
			return $http({
				url: branchConstants.GET_ALL_BRANCHS_URL
			}).success(function(data){
				getBranchData = data;
			}).then(function(){
				return getBranchData;
			})
		},
		getAllBookCopiesService: function(){
			var getBookCopiesData = {};
			return $http({
				url: branchConstants.GET_ALL_BOOKCOPIES_URL
			}).success(function(data){
				getBookCopiesData = data;
			}).then(function(){
				return getBookCopiesData;
			})
		},
		getBookCopiesByIdsService: function(bookId,branchId){
			var getBookCopiesData = {};
			return $http({
				url: branchConstants.GET_NOOFCOPIES_BY_BOOKID_BRANCHID_URL+bookId+"/"+branchId
			}).success(function(data){
				getBookCopiesData = data;
			}).then(function(){
				return getBookCopiesData;
			})
		},
	
		getBranchByPKService: function(branchId){
			var getBranchByPkData = {};
			return $http({
				url: branchConstants.GET_BRANCH_BY_PK_URL+branchId
			}).success(function(data){
				getBranchByPkData = data;
			}).then(function(){
				return getBranchByPkData;
			})
		}
	}
})