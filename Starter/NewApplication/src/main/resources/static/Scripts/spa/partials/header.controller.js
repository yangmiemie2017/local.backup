(function () {
    'use strict';
    angular.module('menu', []);
    
    angular.module('menu')
    .controller('headerCtrl', headerCtrl);

		headerCtrl.$inject = ['$http', '$state', '$rootScope', 'logger', '$uibModal','$window'];

        function headerCtrl($http, $state, $rootScope, logger, $uibModal, $window) {
            var vm = this;
            vm.tabs = [];
            vm.activeTab = false;
            vm.activeTabSaved = false;            
            vm.showHeader = false;
            
            vm.enter = function (clickingTab) {
                var searchArray = ['t1', "t2", 't3'];
                if (clickingTab.route) {
                    if (searchArray.indexOf(clickingTab.route) >= 0) {
                        $state.go(clickingTab.route, { trigger: "menu" });
                    } else if (clickingTab.route === 't4') {
                        $state.go("t4");
                    } else if (clickingTab.route === 't5') {
                        $state.go("t5");
                    } else if (!clickingTab.subNav) {
                        $state.go(clickingTab.route);
                    } else if (clickingTab.route == clickingTab.subNav[0].route) {
                        $state.go(clickingTab.route);
                    }
                }
            };


            vm.subTabActive = function (tab) {
                return $state.current.name === tab.route;
            };

            vm.myResolver = function (defaultResolver, state, isCurrent) {
                if (isCurrent) {
                    return state.data.settings.displayName || state.name;
                }

                return defaultResolver(state);
            }

            var isConfirmed = false;
            $rootScope.$on('$stateChangeStart',
                function (event, toState, toParams, fromState, fromParams) {
            	if (['login', 'regist','help','logout','noAccess'].indexOf(toState.name)<0) {
  	              vm.showHeader = true;
  		          }
  		          else {
  		              vm.showHeader = false;
  		          } 
            	var token = $window.sessionStorage.getItem("TOKEN");
            	if (['login', 'regist'].indexOf(toState.name)<0 &&token==null) {
                        event.preventDefault();
                        $state.go('login');
                        return;
                    }
                if (!isConfirmed && fromParams.isDirty && fromState.name != toState.name && fromState.warning == true) {
	                event.preventDefault();
	                var msg = "You have modified data on this page. Do you wish to leave without saving changes?";
	                var modal = $uibModal.open({
	                    animation: true,
	                    templateUrl: 'modal-leave',
	                    backdrop: 'static',
	                    controller: ['$uibModalInstance', 'message', LeaveModalCtrl],
	                    controllerAs: 'vm',
	                    resolve: {
	                        message: function () { return msg; }
	                    }
	                });
	
	                modal.result.then(function () {
	                    isConfirmed = true;
	                    $state.go(toState, toParams);
	                }, function () {
	                    return;
	                });
                }
                        
                loadMenu();                        
                });

            $(document).on("keydown", function (e) {
                if (e.which === 8 && !$(e.target).is("input, textarea") && angular.element(".modal-open").length > 0) {
                    e.preventDefault();
                }
            });

            $rootScope.$on('$stateChangeSuccess',
                function (event, toState, toParams, fromState, fromParams) {
                    //to clear all the active tag
                    sessionStorage.setItem("routeStateTo", toState.name);
                    sessionStorage.setItem("routeStateFrom", fromState.name);
                    for (var i = 0; i < vm.tabs.length; i++) {
                        vm.tabs[i].activeTab = false;
                    }

                    vm.checkForIndex = function () {
                        var index = 0;
                        angular.forEach(vm.tabs, function (tab) {
                            if (tab.subNav) {
                                for (var i = 0; i < tab.subNav.length; i++) {
                                    if (tab.subNav[i].route === toState.name) {
                                        tab.newRouteResult = tab.subNav[i].route;
                                    }
                                }
                            }

                            if (angular.equals(toState.name, tab.route) || angular.equals(toState.name, tab.newRouteResult)) {
                                event.preventDefault();
                                //vm.activeTab = index;
                                //start a new active tag
                                vm.tabs[index].activeTab = true;
                                return;
                            }
                            index++;
                            tab.newRouteResult = null;
                        });
                    };
                    vm.checkForIndex();

                    isConfirmed = false;

//                    if (toState.name != "help" && toState.name != "login" && toState.name != "logout" && toState.name != "noAccess") {
//                        vm.showHeader = true;
//                    }
//                    else {
//                        vm.showHeader = false;
//                    }

                    document.getElementsByTagName('body')[0].scrollTop = 0;
                });

//            $rootScope.$on('$stateChangeStart', function () {               	
//                loadMenu();
//            });

            // load menu data
            function loadMenu() {        	
            	if(!vm.showHeader){
            		return;
            	}
	    		var token = $window.sessionStorage.getItem('TOKEN');
	    		if(token == null) {
	    			return;
	    		}
	    		else{
	    			$http.defaults.headers.common['Authorization'] = 'Token ' + token;
	    		}

	    		$http.defaults.headers.common['Content-Type'] = 'application/json';
           	
	            $http({
	                method: 'get',
	                url: '/menu'
	            }).then(function (resp) {
                    vm.tabs = resp.data;

                    //the function of mouse over and show subtab
                    vm.enterSubMenu = function (tab) {
                        for (var i = 0; i < vm.tabs.length; i++) {
                            if (vm.tabs[i].subNav) {
                                if (tab.title === vm.tabs[i].title) {
                                    vm.tabs[i].needShowSub = true;
                                }
                            }
                        }
                    }

                    //the function of mouse out and hide subtab
                    vm.leaveSubMenu = function () {
                        for (var i = 0; i < vm.tabs.length; i++) {
                            if (vm.tabs[i].needShowSub) {
                                vm.tabs[i].needShowSub = false;
                            }
                        }
                    }
                },
                function (error) {
                    logger.error(error.data.message);

                });
            }
            loadMenu();
        }

})();        