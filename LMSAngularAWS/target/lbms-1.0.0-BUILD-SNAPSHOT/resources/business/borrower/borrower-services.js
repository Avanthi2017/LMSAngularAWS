lmsApp.factory("borrowerService", function($http, borrowerConstants){
	return{
		getAllBorrowersService: function(){
			var getBorrowerData = {};
			return $http({
				url: borrowerConstants.GET_ALL_BORROWERS_URL
			}).success(function(data){
				getBorrowerData = data;
			}).then(function(){
				return getBorrowerData;
			})
		},
		getAllActiveBorrowersService: function(){
			var getBorrowerData = {};
			return $http({
				url: borrowerConstants.GET_ACTIVE_BORROWERS_URL
			}).success(function(data){
				getBorrowerData = data;
			}).then(function(){
				return getBorrowerData;
			})
		},
		getBookLoanByIDSBorrowersService: function(bookloan){
			var getBookLoanData = {};
			return $http.post({
				url: borrowerConstants.GET_BOOKLOANS_BY_IDS_URL,bookloan
			}).success(function(data){
				getBookLoanData = data;
			}).then(function(){
				return getBookLoanData;
			})
		},
	
	
		getBorrowerByCardService: function(cardNo){
			return $http({
				url: borrowerConstants.GET_BORROWER_BY_PK_URL+cardNo
			}).success(function(data){
				getBorrowerByCardData = data;
			}).then(function(){
				return getBorrowerByCardData;
			})
		},
		
		getBorrower:function(){
			return getBorrowerByCardData;
		}
	}
})