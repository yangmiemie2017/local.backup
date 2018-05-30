(function () {
    'use strict';
    define(['app'], function (app) {
        app.controller('registCtrl', registCtrl);

        registCtrl.$inject = ['$state', '$window','$http', 'logger'];

        function registCtrl($state,  $window, $http, logger) {
            var vm = this;
            vm.formData = {
            	userName: '',
                password: '',
                email:'',
                gender:'',
                signature:''
            };        	
        	vm.userRegister = function() {
                $http.defaults.headers.common['Content-Type'] = 'application/json';

                $http({
                    method: 'POST',
                    url: '/regist',
                    data: vm.formData
                }).then(
        				function(response) {
        					logger.info("Register successfully!");
//                            $window.sessionStorage.setItem('userDetail', resp.data.userDetail);
//                            $window.sessionStorage.setItem('TOKEN',resp.data.userDetail.token);         
        					$state.go("login");
        				},
        				function(response) {
        					logger.error(response.data,response.status);

        				});
        	};
        }
    })
})();

