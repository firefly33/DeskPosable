app.controller('homeController', function($scope) {

});

app.controller('connexionController', function($scope) {

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
