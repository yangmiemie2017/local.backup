(function () {
    'use strict';
    angular.module('strive')
           .factory("authService", authService);

    authService.$inject = ['$http', '$window'];
    function authService($http, $window) {
        var authService = {};

        authService.login = function (formData) {
            $http.defaults.headers.common['Content-Type'] = 'application/json';

            return $http({
                method: 'POST',
                url: '/login',
                data: formData
            })
        };

        authService.isAuthenticated = function () {
            return $window.sessionStorage.getItem('TOKEN')!=null ||$window.sessionStorage.getItem('TOKEN')!='';
        };
        return authService;
    }
})();

