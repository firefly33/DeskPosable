var app = angular.module('app', ['ngRoute','ngResource']);
app.config(function($routeProvider, $locationProvider){

    $locationProvider.hashPrefix('');
    $routeProvider
        .when('/accueil',{
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
<<<<<<< HEAD
        .when('/plans', {
=======
        .when('/personnes/:id',{
            templateUrl: '/views/person/updatePerson.html',
            controller: 'updatePersonController'
        })
        .when('/maps', {
>>>>>>> dev
            templateUrl: '/views/maps.html',
            controller: 'mapController'
        })
        .when('/plan/:id',{
            templateUrl: '/views/map.html',
            controller: 'mapController'
        })
        .when('/batiments',  {
            templateUrl: '/views/building.html',
            controller: 'buildingController'
        })
        .when('/batiment/:id',  {
            templateUrl: '/views/building.html',
            controller: 'buildingController'
        })
        .when('/modification-plan',{
            templateUrl: '/views/modificationPlan.html',
            controller: 'modificationPlanController'
        })
        .otherwise(
            { redirectTo: '/accueil'}
        );
});

