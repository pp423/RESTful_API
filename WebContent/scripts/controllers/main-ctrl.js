(function() {
	'use strict';

	angular.module('restApp').controller('MainCtrl', MainCtrlFn);

	MainCtrlFn.$inject = [ '$http' ];
	function MainCtrlFn($http) {
		var mctrl = this;

		$http({
			method : 'GET',
			url : 'api/employee/checkLogin'
		}).success(function(data) {
			if (data.status == 'success') {
				mctrl.loggedIn = true;
			} else {
				mctrl.loggedIn = false;
			}
		}).error(function(err) {
			console.log(err);
		});

		mctrl.tryLogin = function() {
			$http({
				method : 'POST',
				url : 'api/employee/login',
				data : mctrl.auth
			}).success(function(data) {
				console.log(data);
				if (data.status == 'success') {
					mctrl.loggedIn = true;
				} else {
					mctrl.loggedIn = false;
				}
			}).error(function(err) {
				console.log(err);
			});
		};

		mctrl.getAllEmp = function() {
			$http({
				method : 'GET',
				url : 'api/employee/all'
			}).success(function(data) {
				console.log(data);
			}).error(function(err) {
				console.log(err);
			});
		};

		mctrl.getEmp = function() {
			if (mctrl.empId) {
				$http({
					method : 'GET',
					url : 'api/employee/get/' + mctrl.empId
				}).success(function(data) {
					console.log(data);
				}).error(function(err) {
					console.log(err);
				});
			}
		};

		mctrl.addEmp = function() {
			$http({
				method : 'POST',
				url : 'api/employee/add',
				data : mctrl.newEmp
			}).success(function(data) {
				console.log(data);
				mctrl.newEmp = null;
			}).error(function(err) {
				console.log(err);
			});
		}
	}
})();