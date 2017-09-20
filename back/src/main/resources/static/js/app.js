var app = angular.module('app', ['ngRoute','ngResource', 'ngToast', 'ngSanitize']);

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
        .when('/personnes/:id',{
            templateUrl: '/views/person/updatePerson.html',
            controller: 'updatePersonController'
        })
        .when('/plan/:id',{
            templateUrl: '/views/map.html',
            controller: 'mapController'
        })
        .when('/plans',{
            templateUrl: '/views/maps.html',
            controller: 'mapsController'
        })
        .when('/batiments',  {
            templateUrl: '/views/buildings.html',
            controller: 'buildingsController'
        })
        .when('/batiment/:id',  {
            templateUrl: '/views/building.html',
            controller: 'buildingController'
        })
        .when('/modification-plan/:id',{
            templateUrl: '/views/modificationPlan.html',
            controller: 'modificationPlanController'
        })
        .when('/plan/nouveau/:id', {
            templateUrl: '/views/nouveauPlan.html',
            controller: 'nouveauPlanController'
        })
        .otherwise(
            { redirectTo: '/accueil'}
        );
});

