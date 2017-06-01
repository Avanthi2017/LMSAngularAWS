lmsApp.factory("bookService", function($http, bookConstants){
	return{
		getAllBooksService: function(){
			var getBookData = {};
			return $http({
				url: bookConstants.GET_ALL_BOOKS_URL
			}).success(function(data){
				getBookData = data;
			}).then(function(){
				return getBookData;
			})
		},
	
		getBookByPKService: function(bookId){
			var getBookByPkData = {};
			return $http({
				url: bookConstants.GET_BOOK_BY_PK_URL+bookId
			}).success(function(data){
				getBookByPkData = data;
			}).then(function(){
				return getBookByPkData;
			})
		},
	}
})