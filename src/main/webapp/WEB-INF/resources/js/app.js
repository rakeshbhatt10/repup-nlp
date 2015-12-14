(function() {
	var japp = angular.module('reviewapp',  ['ui.bootstrap','daterangepicker']);
	japp.directive('loading', function () {
      return {
        restrict: 'E',
        replace:true,
        template: '<div class="loading"><img src="http://www.nasa.gov/multimedia/videogallery/ajax-loader.gif" width="20" height="20" />LOADING...</div>',
        link: function (scope, element, attr) {
              scope.$watch('loading', function (val) {
                  if (val)
                      $(element).show();
                  else
                      $(element).hide();
              });
        }
      }
  	});
	var flag_tripadvisor = "tripadvisor";
	var flag_goibibo = "goibibo";
	var flag_booking = "booking";
	var flag_expedia = "expedia";
	var flag_mmt = "mmt";
	var flag_agoda = "agoda";


	japp.config([ '$httpProvider', function($httpProvider) {
		$httpProvider.defaults.useXDomain = true;
		delete $httpProvider.defaults.headers.common['X-Requested-With'];
	} ]);
	japp.controller('HotelReviewController', function($scope, hotelService,
			hotelReferenceKeyService, hotelReviewCrawlerService,sentimentsPostService) {

		$scope.hotels = [];
		$scope.formData = {};
		$scope.hoteldate = {startDate: null, endDate: null};
		$scope.dateOptions = {
			formatYear : 'yy',
			startingDay : 0,
			showWeeks : 'false'
		};
		$scope.loading = false;
		$scope.data = {};
		$scope.sentiment_response = "";
		loadRemoteData();

		function applyRemoteData(hotellist) {
			$scope.hotels = hotellist;

		}
		function loadHotelReferenceKeyList(hotelkeylist) {
			console.log("Hotel Key List");
			$scope.hotelkeylist = hotelkeylist;

			if ($scope.hotelkeylist.tripAdvisorReferenceKey != 'na') {
				console.log("Fetching hotel Reviews for "
						+ $scope.hotelkeylist.tripAdvisorReferenceKey
						+ " OTA: " + flag_tripadvisor);
				fetchRepupReviews($scope.hotelkeylist.tripAdvisorReferenceKey,
						flag_tripadvisor);

			}
			if ($scope.hotelkeylist.makeMyTripReferenceKey != 'na') {
				console.log("Fetching hotel Reviews for "
						+ $scope.hotelkeylist.makeMyTripReferenceKey + " OTA: "
						+ flag_mmt);
				fetchRepupReviews($scope.hotelkeylist.makeMyTripReferenceKey,
						flag_mmt);
			}
			if ($scope.hotelkeylist.goibiboReferenceKey != 'na') {
				console.log("Fetching hotel Reviews for "
						+ $scope.hotelkeylist.goibiboReferenceKey + " OTA: "
						+ flag_goibibo);
				fetchRepupReviews($scope.hotelkeylist.goibiboReferenceKey,
						flag_goibibo);
			}
			if ($scope.hotelkeylist.expediaReferenceKey != 'na') {
				console.log("Fetching hotel Reviews for "
						+ $scope.hotelkeylist.expediaReferenceKey + " OTA: "
						+ flag_expedia);
				fetchRepupReviews($scope.hotelkeylist.expediaReferenceKey,
						flag_expedia);
			}
			if ($scope.hotelkeylist.bookingReferenceKey != 'na') {
				console.log("Fetching hotel Reviews for "
						+ $scope.hotelkeylist.bookingReferenceKey + " OTA: "
						+ flag_booking);
				fetchRepupReviews($scope.hotelkeylist.bookingReferenceKey,
						flag_booking);
			}
			if ($scope.hotelkeylist.agodaReferenceKey != 'na') {
				console.log("Fetching hotel Reviews for "
						+ $scope.hotelkeylist.agodaReferenceKey + " OTA: "
						+ flag_agoda);
				fetchRepupReviews($scope.hotelkeylist.agodaReferenceKey,
						flag_agoda);



			}

			$scope.loading = false;

		}
		function fetchRepupReviews(hotelid, hoteltype) {
			hotelReviewCrawlerService.getHotelReviews(hotelid, hoteltype,$scope.hoteldate).then(
					function(reviewlist) {

						saveReviewData(reviewlist, hoteltype);

					});
		}

		function saveReviewData(reviewlist, hoteltype) {
			if (hoteltype == flag_tripadvisor) {
				console.log("Tripadvisor Review List");
				console.log(reviewlist);
				$scope.tripadvisor_reviews = reviewlist;

			} else if (hoteltype == flag_goibibo) {
				console.log("Goibibo Review List");
				console.log(reviewlist);
				$scope.goibibo_reviews = reviewlist;

			} else if (hoteltype == flag_agoda) {
				console.log("Agoda Review List");
				console.log(reviewlist);

				$scope.agoda_reviews = reviewlist;
			} else if (hoteltype == flag_booking) {
				console.log("Booking Review List");
				console.log(reviewlist);
				$scope.booking_reviews = reviewlist;
			} else if (hoteltype == flag_mmt) {
				console.log("Make My Trip Review List");
				console.log(reviewlist);
				$scope.mmt_reviews = reviewlist;
			} else if (hoteltype == flag_expedia) {
				console.log("Expedia Review List");
				console.log(reviewlist);
				$scope.expedia_reviews = reviewlist;
			}
		}

		function loadRemoteData() {

			hotelService.getHotelList().then(function(hotellist) {

				applyRemoteData(hotellist);

			});
		}
		
		$scope.processSentimentsForm = function() {

			$scope.formData.hotelid = $scope.hotel.hotelid;
			$scope.sentiment_response = "";
			console.log($scope.formData);
			sentimentsPostService.postSentimentsText(
					$scope.formData).then(function(response) {
				console.log(response);
				handleSentimentsPostResponse(response);
				
			});

		}
		function handleSentimentsPostResponse(response)
		{
			console.log("Response : "+response);
			if(response=="true")
				{
					$scope.sentiment_response = "Sentiments Succesfully Stored";
				}else
				{
					$scope.sentiment_response = "Problem in storing sentiments";
				}
		}

		$scope.processHotelId = function() {

			console.log("Hotel Id: " + $scope.hotel.hotelid);
			$scope.loading = true;
			$scope.tripadvisor_reviews = {};
			$scope.booking_reviews = {};
			$scope.mmt_reviews = {};
			$scope.goibibo_reviews = {};
			$scope.expedia_reviews = {};
			$scope.agoda_reviews = {};

			hotelReferenceKeyService.getHotelReferenceKeyList(
					$scope.hotel.hotelid).then(function(hotellist) {
				console.log(hotellist);
				
				loadHotelReferenceKeyList(hotellist);
			});
		}
		$scope.$watch('formData.reviewWeekDate', function(dateVal) {
			var weekYear = getWeekNumber(dateVal);
			var year = weekYear[0];
			var week = weekYear[1];

			if (angular.isDefined(week) && angular.isDefined(year)) {
				var startDate = getStartDateOfWeek(week, year);
			}
			var weekPeriod = getStartDateOfWeek(week, year);
			if (weekPeriod[0] != 'NaN/NaN/NaN'
					&& weekPeriod[1] != 'NaN/NaN/NaN') {
				$scope.formData.reviewWeekDate = weekPeriod[0] + " to "
						+ weekPeriod[1];
			}

		});

		function getStartDateOfWeek(w, y) {
			var simple = new Date(y, 0, 1 + (w - 1) * 7);
			var dow = simple.getDay();
			var ISOweekStart = simple;
			if (dow <= 4)
				ISOweekStart.setDate(simple.getDate() - simple.getDay());
			else
				ISOweekStart.setDate(simple.getDate() + 7 - simple.getDay());

			var ISOweekEnd = new Date(ISOweekStart);
			ISOweekEnd.setDate(ISOweekEnd.getDate() + 6);

			ISOweekStart = ISOweekStart.getDate() + '/'
					+ (ISOweekStart.getMonth() + 1) + '/'
					+ ISOweekStart.getFullYear();
			ISOweekEnd = ISOweekEnd.getDate() + '/'
					+ (ISOweekEnd.getMonth() + 1) + '/'
					+ ISOweekEnd.getFullYear();
			return [ ISOweekStart, ISOweekEnd ];
		}

		function getWeekNumber(d) {
			d = new Date(+d);
			d.setHours(0, 0, 0);
			d.setDate(d.getDate() + 4 - (d.getDay() || 7));
			var yearStart = new Date(d.getFullYear(), 0, 1);
			var weekNo = Math.ceil((((d - yearStart) / 86400000) + 1) / 7);
			return [ d.getFullYear(), weekNo ];
		}

	});

	japp.service("hotelService", function($http, $q) {

		return ({

			getHotelList : getHotelList
		});

		function getHotelList() {
			var request = $http({
				method : "get",
				url : "http://" + window.location.host
						+ "/repupengine/getHotelIdNamePairs.repup"
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
	japp.service("hotelReferenceKeyService", function($http, $q) {

		return ({

			getHotelReferenceKeyList : getHotelReferenceKeyListFun
		});

		function getHotelReferenceKeyListFun(hotelid) {
			var request = $http({
				method : "get",
				url : "http://" + window.location.host
						+ "/repupengine/getHotelReferenceKeys.repup?hotelId="
						+ hotelid
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

	japp.service("hotelReviewCrawlerService", function($http, $q) {

		return ({

			getHotelReviews : getHotelReviews
		});

		function getHotelReviews(hotelid, type,hoteldate) {
			var request = $http({
				method : "get",
				url : "http://" + window.location.host
						+ "/repup_review_crawler/webapi/reviewsJsonData/" + hotelid
						+ "/" + type+"?startDate="+hoteldate.startDate+"&endDate="+hoteldate.endDate
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
			console.log(response.data);
			return (response.data);
		}

	});
	japp.service("sentimentsPostService", function($http, $q) {

		return ({

			postSentimentsText : postSentimentsText
		});

		function postSentimentsText(formdata) {
			
			var request = $http({
				method : "POST",
				url : "http://" + window.location.host
						+ "/repup_admin_creator/saveSentimentText",
				data    : formdata, //forms user object
          		headers : {'Content-Type': 'application/x-www-form-urlencoded'} 
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
			console.log(response.data);
			return (response.data);
		}

	});
}());