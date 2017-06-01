lmsApp.factory("branchService", function($http, branchConstants){
	var getBranchByPkData = {};
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
			var getBookCopiesDatabyIds = {};
			return $http({
				url: branchConstants.GET_NOOFCOPIES_BY_BOOKID_BRANCHID_URL+bookId+"/"+branchId
			}).success(function(data){
				getBookCopiesDatabyIds = data;
			}).then(function(){
				return getBookCopiesDatabyIds;
			})
		},
		getBranchByBookIdService: function(bookId){
			var getBranchByBookId = {};
			return $http({
				url: branchConstants.GET_ALL_BRANCHS_BY_BOOKID_URL+bookId
			}).success(function(data){
				getBranchByBookId = data;
			}).then(function(){
				return getBranchByBookId;
			})
		},
		getBookCopiesByBranchIdService: function(branchId){
			var getBookCopiesDataByBranchId = {};
			return $http({
				url: branchConstants.GET_ALL_BOOKCOPIES_BY_BRANCHID_URL+branchId
			}).success(function(data){
				getBookCopiesDataByBranchId = data;
			}).then(function(){
				return getBookCopiesDataByBranchId;
			});
		},
		getBranchByPKService: function(branchId){
			return $http({
				url: branchConstants.GET_BRANCH_BY_PK_URL+branchId
			}).success(function(data){
				getBranchByPkData = data;
			}).then(function(){
				return getBranchByPkData;
			})
		},
		
		getBranch: function () {
            return getBranchByPkData;
        }
		
		
	}
})