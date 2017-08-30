var app = angular.module('app', ['ngRoute','ngResource']);
app.config(function($routeProvider, $locationProvider){
    $routeProvider
        .when('/users',{
            templateUrl: '/views/users.html',
            controller: 'usersController'
        })
        .when('/home',{
            templateUrl: '/views/home.html',
            controller: 'homeController'
        })
        .when('/connexion',{
            templateUrl: '/views/connexion.html',
            controller: 'connexionController'
        })
        .when('/personnes',{
            templateUrl: '/views/person.html',
            controller: 'personController'
        })
        .otherwise(
            { redirectTo: '/home'}
        );
    //$locationProvider.html5Mode(true);
});

