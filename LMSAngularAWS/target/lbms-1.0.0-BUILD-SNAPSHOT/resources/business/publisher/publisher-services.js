lmsApp.factory("publisherService", function($http, publisherConstants){
	return{
		getAllPublishersService: function(){
			var getPublisherData = {};
			return $http({
				url: publisherConstants.GET_ALL_PUBLISHERS_URL
			}).success(function(data){
				getPublisherData = data;
			}).then(function(){
				return getPublisherData;
			})
		},
	
		getPublisherByPKService: function(publisherId){
			var getPublisherByPkData = {};
			return $http({
				url: publisherConstants.GET_PUBLISHERS_BY_PK_URL+publisherId
			}).success(function(data){
				getPublisherByPkData = data;
			}).then(function(){
				return getPublisherByPkData;
			})
		}
	}
})