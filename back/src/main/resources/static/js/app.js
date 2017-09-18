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
            templateUrl: '/views/person/person.html',
            controller: 'personController'
        })
        .when('/personnes/:id',{
            templateUrl: '/views/person/updatePerson.html',
            controller: 'updatePersonController'
        })
        .when('/maps', {
            templateUrl: '/views/maps.html',
            controller: 'mapController'
        })
        .when('/map',{
            templateUrl: '/views/map.html',
            controller: 'mapController'
        })
        .when('/buildings',  {
            templateUrl: '/views/building.html',
            controller: 'buildingController'
        })
        .otherwise(
            { redirectTo: '/home'}
        );
    //$locationProvider.html5Mode(true);
});

