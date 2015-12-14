(function() {
	
	var app = angular.module('sapp',[]);
	app.controller('SentimentsCtrl',function($scope,hotelService,hotelReviewCrawler){
		
		$scope.hotels = [];
		$scope.message = "Please select hotel for review"; 

		loadRemoteData();
		$scope.analyzeHotelSentiements = function(){
		
			console.log("hotel id: "+$scope.hotel.hotelid);
			$scope.message = "Starting reviews analysis";
			hotelReviewCrawler.hotelReviewCrawler($scope.hotel.hotelid).then(function(hotelreview) {

				showReviewAnalysis(hotelreview);

			});
		};
		
		function showReviewAnalysis(hotelreview)
		{
			$scope.hotelreviews = hotelreview;
			$scope.message = "Reviews analysis completed";
		}
		function loadRemoteData() {

			hotelService.getHotelList().then(function(hotellist) {

				applyRemoteData(hotellist);

			});
		}

		function applyRemoteData(hotellist) {
			$scope.hotels = hotellist;
			

		}
	});
	app.service("hotelService", function($http, $q) {

		return ({

			getHotelList : getHotelList
		});

		function getHotelList() {
			var request = $http({
				method : "get",
				url : "http://" + window.location.host
						+ "/RepUpEngine/getHotelIdNamePairs.repup"
			})

			return (request.then(handleSuccess, handleError));
		}

		function handleError(response) {
			if (!angular.isObject(response.data)) {
				return ($q.reject("An unknown error occurred."));
			}
			return ($q.reject("An unknown error occurred."));
		}
		function handleSuccess(response) {
			return (response.data);
		}

	});
	app.service("hotelReviewCrawler", function($http, $q) {

		return ({

			hotelReviewCrawler : hotelReviewCrawler
		});

		function hotelReviewCrawler(hotelid) {
			var request = $http({
				method : "get",
				url : "http://" + window.location.host+ "/SentimentsCrawler/crawlhotel?hotelid="+hotelid
			})

			return (request.then(handleSuccess, handleError));
		}

		function handleError(response) {
			if (!angular.isObject(response.data)) {
				return ($q.reject("An unknown error occurred."));
			}
			return ($q.reject("An unknown error occurred."));
		}
		function handleSuccess(response) {
			return (response.data);
		}

	});
	
	
}());