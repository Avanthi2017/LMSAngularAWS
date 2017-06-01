lmsApp.factory("genreService", function($http, genreConstants){
	return{
		getAllGenresService: function(){
			var getGenreData = {};
			return $http({
				url: genreConstants.GET_ALL_GENRES_URL
			}).success(function(data){
				getGenreData = data;
			}).then(function(){
				return getGenreData;
			})
		},
	
		getGenreByPKService: function(genreId){
			var getGenreByPkData = {};
			return $http({
				url: genreConstants.GET_GENRE_BY_PK_URL+genreId
			}).success(function(data){
				getGenreByPkData = data;
			}).then(function(){
				return getGenreByPkData;
			})
		}
	}
})