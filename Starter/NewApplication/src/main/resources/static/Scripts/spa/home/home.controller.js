(function () {
    'use strict';

    define(['app','services/webapi'], function (app) {
        app.controller('homeCtrl', homeCtrl);

        homeCtrl.$inject = ['webapi','logger'];

        function homeCtrl(webapi,logger) {
            var vm = this;

            vm.getData = function () {               
                webapi.post("BookService1", "getBooks", {test:"testtest"}).then(
            			function(response) {
            				vm.books = response.data.table1;
            				//vm.temp=JSON.stringify(response.data);
            				
            			},
            			function(response) {
            				logger.error(response.data,response.status);
            			});	                
            }
        }
    })
})();