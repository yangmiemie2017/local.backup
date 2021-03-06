﻿(function () {
    'use strict';
    define(['app'], function (app) {
        app.controller('logoutCtrl', logoutCtrl);

        logoutCtrl.$inject = ['$http', '$state', '$window','logger'];

        function logoutCtrl($http, $state, $window,logger) {
            var vm = this;

            vm.logout = function () {
	    		var token = $window.sessionStorage.getItem('TOKEN');
	    		if(token != null) {
	    			$http.defaults.headers.common['Authorization'] = 'Token ' + token;   
		    		$http.defaults.headers.common['Content-Type'] = 'application/json';            	
	        		$http({
	        			method : 'get',
	        			url : '/logoff'
	        		}).then(
	        				function(response) {       					
	        					if ('200' == response.status) {
	        						$window.sessionStorage.removeItem('TOKEN');
	        					} else {
	
	        					}
	        				},
	        				function(error) {
        						$window.sessionStorage.removeItem('TOKEN');	        					
	        					logger.error(error.message);
	        				});
	    		}
            };

            vm.logout();
        }
    })
})();