﻿(function () {
    // http://milestone.topics.it/2014/03/angularjs-ui-router-and-breadcrumbs.html
    'use strict';
    angular.module('strive')
        .directive('breadcrumbs', ['$log', '$parse', '$interpolate', function ($log, $parse) {
            return {
                restrict: 'EA',
                replace: false,
                scope: {
                    itemDisplayNameResolver: '&'
                },
                templateUrl: 'Scripts/spa/directives/breadcrumbsDirective.html',
                controller: ['$scope', '$state', '$stateParams', function ($scope, $state, $stateParams) {

                    var defaultResolver = function (state) {

                        var displayName = state.data.settings.displayName || state.name;

                        return displayName;
                    };

                    var isCurrent = function (state) {
                        return $state.$current.name === state.name;
                    };

                    var setNavigationState = function () {
                        $scope.$navigationState = {
                            currentState: $state.$current,
                            params: $stateParams,
                            getDisplayName: function (state) {

                                if ($scope.hasCustomResolver) {
                                    return $scope.itemDisplayNameResolver({
                                        defaultResolver: defaultResolver,
                                        state: state,
                                        isCurrent: isCurrent(state)
                                    });
                                }
                                else {
                                    return defaultResolver(state);
                                }
                            },
                            isCurrent: function (state) {

                                return isCurrent(state);
                            }
                        }
                    };

                    $scope.$on('$stateChangeSuccess', function () {
                        setNavigationState();
                    });

                    setNavigationState();
                }],
                link: function (scope, element, attrs, controller) {
                    scope.hasCustomResolver = angular.isDefined(attrs['itemDisplayNameResolver']);
                }
            };
        }]);

})();