(function() {
    'use strict';

    angular
        .module('blocks.logger')
        .factory('logger', logger);

    logger.$inject = ['$log', 'toastr'];

    function logger($log, toastr) {
        var service = {
            showToasts: true,

            error   : error,
            info    : info,
            success : success,
            warning : warning,
            clear: clear,
            remove:remove,
            logs: [],
            // straight to console; bypass toastr
            log     : $log.log
        };

        return service;
        /////////////////////

        //var logs = [];

        function error(message, data, title) {
            clear(this.logs.shift());
            var $toast = toastr.error(message, title);
            this.logs.push($toast);
           // $log.error('Error: ' + message, data);
        }

        function info(message, data, title) {
            clear(this.logs.shift());
            var $toast = toastr.info(message, title);
            this.logs.push($toast);
            $log.info('Info: ' + message, data);
        }

        function success(message, data, title) {
            clear(this.logs.shift());
            var $toast = toastr.success(message, title);
            this.logs.push($toast);
            $log.info('Success: ' + message, data);
        }

        function warning(message, data, title) {
            clear(this.logs.shift());
            var $toast = toastr.warning(message, title);
            this.logs.push($toast);
            $log.warn('Warning: ' + message, data);
        }

        function remove() {
            toastr.clear();
        }

        function clear(last) {
            if (last) {
                toastr.clear(last);
            }
            
        }
    }
}());
