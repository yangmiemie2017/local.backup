(function () {
    'use strict';

    define(['app', 'webApi/webapi'], function (app) {
        app.controller('helpCtrl', helpCtrl);

        helpCtrl.$inject = ['webapi', '$state', '$timeout', 'logger', '$stateParams', '$scope'];

        function helpCtrl(webapi, $state, $timeout, logger, $stateParams, $scope) {
            var vm = this;
            vm.page = "SelectSubNode";

            vm.GetUrl = function (name) {
                return "Scripts/spa/help/docs/" + name + '.html';
            }

            vm.openContent = function (node) {
                var name = node.template;
                if (name == null || name == "")
                    name = "NoDetails";

                vm.page = name;
                vm.deactive(vm.data);
                node.active = true;
            }

            vm.collapseAll = function () {
                $scope.$broadcast('angular-ui-tree:collapse-all');
            };

            vm.expandAll = function () {
                $scope.$broadcast('angular-ui-tree:expand-all');
            };

            vm.deactive = function (data) {
                if (data != null && data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        data[i].active = false;
                        if (data[i].nodes != null && data[i].nodes.length > 0)
                            vm.deactive(data[i].nodes);
                    }
                }
            };


            $timeout(function () {
                vm.collapseAll();
                vm.deactive(vm.data);
            }, 0);
        }
    })
})();