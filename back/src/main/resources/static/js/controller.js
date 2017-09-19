app.controller('homeController', function ($scope) {

});
app.controller('modificationPlanController', function ($scope, $http, $routeParams) {
    $scope.idPlan = $routeParams.id;
    $http({
        method: 'GET',
        url: '/maps/' + $scope.idPlan
    }).then(function (data) {
        $scope.map = data.data;
    }, function (error) {
        swal("Une erreur est survenue, veuillez réessayer plus tard.", "error");
    });
    $scope.saveMap = function () {
        $http
            .post("desks/save/" + $scope.idPlan, bureaux)
            .then(
                function (data) {
                    bureaux = data.data;
                    swal("Sauvegarde réussie !", "Le plan est enregistré sur le serveur.", "success").setDefaults({confirmButtonColor: '#ff4500'});
                },
                function (data) {
                    swal("Impossible de contacter le serveur distant. Veuillez réesayer plus tard.").setDefaults({confirmButtonColor: '#ff4500'});
                }
            );

    };
    $scope.addBureau = function (event) {
        var x = event.offsetX;
        var y = event.offsetY;
        if (typeCreation == 1) {
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
                function () {
                    bureaux[idBureau] = new Bureau(null, "bureau" + idBureau, x, y);

                    idBureau++;
                    createViewBureau(idBureau, x, y);
                    swal("Ajouté !", "Le bureau a bien été ajouté à votre plan.", "success");
                });
        }
    }
    $scope.ajoutEmploye = function () {
        $http({
            method: 'GET',
            url: '/persons'
        }).then(function (data) {
            afficherlistePersonne(data.data);
        }, function (error) {
            swal("Une erreur est survenue, veuillez réessayer plus tard.", "error");
        });
    }
});

app.controller('connexionController', function ($scope, $http) {

    $scope.connexion = function () {
        var email = $scope.email;
        var password = $scope.password;
        if (email == "") {
            swal("Veuillez spécifier une adresse mail.").setDefaults({confirmButtonColor: '#ff4500'});
            return;
        }
        if (password == "") {
            swal("Veuillez spécifier un mot de passe de connexion.").setDefaults({confirmButtonColor: '#ff4500'});
            return;
        }
        var data = {
            email: email,
            password: password
        };
        console.log(data);
        $http
            .post("/auth", data)
            .then(
                function (response) {
                    document.location.href = "#/modification-plan/1";
                },
                function (data) {
                    swal("Accès refusé.").setDefaults({confirmButtonColor: '#ff4500'});
                    ;
                }
            );
    }
});

app.controller('buildingController', function ($scope, $http, $routeParams) {

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
    };

    var getOne = function ($routeParams) {
        var id = $routeParams.id;
        $http({
            method: 'GET',
            url: '/buildings/' + id
        }).then(function (data) {
            $scope.building = data.data;
            $scope.maps = $scope.building.maps;
        }, function (error) {
            alert("ça passe pas (GET_BY_ID)");
        });
    };

    //if ($routeParams == "")
    //    all();
    //else
    getOne($routeParams);

});

app.controller('personController', function ($scope, $http) {
    $http({
        method: 'GET',
        url: '/persons'
    }).then(function (data) {
        // On stock dans persons la liste des personnes que nous renvoi l'api
        $scope.persons = data.data;
    }, function (error) {
        alert("ça passe pas");
    });

    /*
    Méthode servant à définir quel employé est sélectionné pour modification
     */
    $scope.personChoice = function (person) {
        $scope.personToModify = person;
    };

    /*
    Méthode utilisé pour modifier l'utilisateur
     */
    $scope.updatePerson = function (personToModify) {
        $http.put("/persons/"+personToModify.id, personToModify)
            .then(
                function (response) {
                    alert("c'est okkkkkkkkkkkkk");

                },
                function (data) {
                    alert("oups c'est pas okkkkkkkkkkkk");
                }
            );
    }


});

app.controller('updatePersonController', function ($scope, $http, $routeParams) {
    var idPerson = $routeParams.id;

    $http({
        method: 'GET',
        url: '/persons/' + idPerson
    }).then(function (data) {
        // On stock dans person la personne que nous renvoi l'api
        $scope.person = data.data;
    }, function (error) {
        alert("ça passe pas user solo");
    });

});
app.controller('mapController', function ($scope, $http) {

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
