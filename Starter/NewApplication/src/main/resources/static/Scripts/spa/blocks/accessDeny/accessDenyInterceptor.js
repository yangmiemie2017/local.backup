(function () {
    'use strict';

    angular.module('strive').factory('accessDenyInjector', ['$q', '$location', function ($q, $location) {

        return {

            'responseError': function (rejection) {

                if (rejection.status == 403) {
                    $location.path('/no-access');
                }
    
                return $q.reject(rejection);
            }
        };

    }]);

    angular.module('strive').config(['$httpProvider', function ($httpProvider) {
       $httpProvider.interceptors.push('accessDenyInjector');
    }]);

}());