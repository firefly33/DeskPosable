app.controller('homeController', function($scope) {

});
app.controller('modificationPlanController',function($scope,$http){
    var data = bureaux;
    console.log(data);
    $scope.saveMap = function() {
        $http
            .post("desks/save", data).then(
            function (response) {
                swal("Enregistrement", "Sauvegarde réussie!", "success");
            },
            function (data) {
                swal("Echec", "Impossible de contacter le serveur distant, veuillez réessayer ultérieurement.", "error");
            }
        );
    }
    $scope.addBureau = function (event) {
        var x = event.offsetX;
        var y = event.offsetY;
        if(typeCreation == 1){
            idBureau++;
            swal({
                    title: "Confirmation",
                    text: "Etes vous sûr de vouloir placer un bureau à cet emplacement ?",
                    type: "warning",
                    showCancelButton: true,
                    cancelButtonText: "Non, c'est une erreur !",
                    confirmButtonColor: "#ff4500",
                    confirmButtonText: "Oui",
                    closeOnConfirm: false
                },
                function(){
                    bureaux[idBureau] = new Bureau(idBureau,"bureau"+idBureau,x,y);
                    console.log(bureaux);
                    createViewBureau(idBureau,x,y);
                    swal("Ajouté !", "Le bureau a bien été ajouté à votre plan.", "success");
                });
        }
    }
});

app.controller('connexionController', function($scope,$http) {

    $scope.connexion = function () {
        var email = $scope.email;
        var password = $scope.password;
        if(email == ""){
            swal("Veuillez spécifier une adresse mail.").setDefaults({ confirmButtonColor: '#ff4500' });
            return;
        }
        if(password == ""){
            swal("Veuillez spécifier un mot de passe de connexion.").setDefaults({ confirmButtonColor: '#ff4500' });
            return;
        }
        var data = {
            email : email,
            password : password
        };
        console.log(data);
        $http
            .post("/auth", data)
            .then(
                function (response) {
                    document.location.href="#/modification-plan";
                },
                function (data) {
                    swal("Accès refusé.").setDefaults({ confirmButtonColor: '#ff4500' });;
                }
            );
    }
});

app.controller('buildingController', function($scope, $http, $routeParams) {

    var all = function () {
        $http({
            method: 'GET',
            url: '/buildings'
        }).then(function (data) {
            $scope.buildings = data.data;

            /*$scope.buildings.forEach(function(building) {
                console.log(building);

                var toto = building.maps.lengh;
            });

            $scope.count = data.data.length;*/
        }, function (error) {
            alert("ça passe pas (GET_ALL)");
        });
    }

    var getOne = function ($routeParams) {
        var id = $routeParams.id;
        $http({
            method: 'GET',
            url: '/buildings/'+id
        }).then(function (data) {
            $scope.building = data.data;
            $scope.maps = $scope.building.maps;
        }, function (error) {
            alert("ça passe pas (GET_BY_ID)");
        });
    }

    //if ($routeParams == "")
    //    all();
    //else
        getOne($routeParams);

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
            $scope.person = data.data;
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
            url: '/maps/{id}'
        }).then(function (data) {
            $scope.map = data.data;
        }, function (error) {
            alert("ça passe pas du tout (map)");
        });
});
