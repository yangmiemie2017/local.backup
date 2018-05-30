(function () {
    'use strict';

    angular
        .module('strive')
        .factory("controllerCacheService", controllerCacheService);

    
    controllerCacheService.$inject = ['$state', '$rootScope','$timeout'];
    function controllerCacheService($state, $rootScope, $timeout) {

        return {
            vm:this,
            savePageData: function (key, data) {
                $rootScope.$pageCache[key] = data;
               
                },
                getPageData: function (key,data)
                {
                    for(var temp in $rootScope.$pageCache[key]){  
                        data[temp] = $rootScope.$pageCache[key][temp];  
                    }  
                },
                setCacheData: function (key,data)
                {
                    

                    if ($rootScope.$pageCache == null) {
                        $rootScope.$pageCache = {};
                    }
                   
                    if ($rootScope.$pageCache[key] == null) {
                        this.savePageData(key, data);   
                        return false;
                    }
                    else
                    {
                        this.getPageData(key, data);
                        return true;
                    }
                   
                },
                removeCacheData: function (key)
                {
                    $rootScope.$pageCache[key]=null;
                }
        }


    }


})();