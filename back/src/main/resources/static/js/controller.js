app.controller('homeController', function($scope) {

});

app.controller('connexionController', function($scope) {

});

app.controller('buildingController', function($scope) {

    $http({
        method: 'GET',
        url: '/buildings'
    }).then(function (data) {
        $scope.map = data.data;
    }, function (error) {
        alert("ça passe pas du tout (building)");
    });

    $http({
        method: 'GET',
        url: '/buildings/{id}'
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

app.controller('updatePersonController', function($scope, $http, $routeParams) {
        var idPerson = $routeParams.id;

        $http({
            method: 'GET',
            url: '/persons/'+idPerson
        }).then(function (data) {
            // On stock dans person la personne que nous renvoi l'api
            $scope.maperson = data.data;
            console.log($scope.maperson);
        }, function (error) {
            alert("ça passe pas user solo");
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
            url: '/map'
        }).then(function (data) {
            $scope.map = data.data;
        }, function (error) {
            alert("ça passe pas du tout (map)");
        });
});
