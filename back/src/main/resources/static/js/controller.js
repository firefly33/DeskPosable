app.controller('homeController', function($scope) {

});

app.controller('connexionController', function($scope) {

});

app.controller('buildingController', function($scope, $http, $routeParams) {
    var id = $routeParams.id;
    $http({
        method: 'GET',
        url: '/buildings/'+id
    }).then(function (data) {
        // On stock dans persons la liste des personnes que nous renvoi l'api
        $scope.building = data.data;
    }, function (error) {
        alert("ça passe pas");
    });
});

app.controller('personController', function($scope, $http) {
    $http({
        method: 'GET',
        url: '/persons'
    }).then(function (data) {
        // On stock dans persons la liste des personnes que nous renvoi l'api
        $scope.persons = data.data;
    }, function (error) {
        alert("ça passe pas");
    });

});

app.controller('mapController', function($scope, $http) {

        $http({
            method: 'GET',
            url: '/maps'
        }).then(function (data) {
            // On stock dans maps la liste des maps que nous renvoi l'api
            $scope.maps = data.data;
        }, function (error) {
            alert("ça passe pas (maps)");
        });

        $http({
            method: 'GET',
            url: '/maps/{id}'
        }).then(function (data) {
            $scope.map = data.data;
        }, function (error) {
            alert("ça passe pas du tout (map)");
        });


});
