/// <reference path="../../angular.js" />
(function () {
    'use strict';
    define(['app', 'services/authService'], function (app) {
        app.controller('loginCtrl', loginCtrl);

        loginCtrl.$inject = ['$state', '$window', 'authService', 'logger'];

        function loginCtrl($state,  $window, authService, logger) {
            var vm = this;
            vm.formData = {
            	loginName: 'test',
                password: 'test'
            };
            vm.userLogin = function () {
                //logger.info('login', vm.formData);
                authService.login(vm.formData).then(function (resp) {
                    $window.sessionStorage.setItem('userDetail', resp.data.userDetail);
                    $window.sessionStorage.setItem('TOKEN',resp.data.userDetail.token);                    
    				$state.go("home");                    
                }, function () {
                	$window.sessionStorage.removeItem('TOKEN');
                    logger.error("Login failed!");
                });
            };
            
            vm.userLogin();//auto login for debug.
        }
    })
})();