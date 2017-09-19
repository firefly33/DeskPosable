(function(angular) {
    'use strict';
    angular.module('app').config(function(ngToastProvider) {
        ngToastProvider.configure({
            animation : 'slide',
            timeout : 5000,
            dismissButton : true,
            maxNumber : 2
        });
    });
})(angular);