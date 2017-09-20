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
                }
            );
    }
});
app.controller('buildingsController', function ($scope, $http, ngToast) {

    function getAllBuildings() {
        $http({
            method: 'GET',
            url: '/buildings'
        }).then(function (data) {
            $scope.buildings = data.data;
        }, function (error) {
            ngToast.danger("Une erreur est survenue lors de la récupération des batiments");
        });
    }
    getAllBuildings();

    $scope.buildingChoice = function (building) {
        $scope.buildingToModify = building;
    };

    $scope.updateBuilding = function (buildingToModify) {
        $http.put("/buildings/" + buildingToModify.id, buildingToModify)
            .then(
                function (data) {
                    ngToast.success("Modification réussie !");
                },
                function (error) {
                    ngToast.danger("Une erreur est suvenue lors de la modification.");
                }
            );
    };

    $scope.deleteBuilding = function (buildingToDelete) {
      $http.delete("/buildings/" + buildingToDelete.id)
          .then(
              function (data) {
                  ngToast.success("Suppression réussie !");
                  getAllBuildings();
              },
              function (error) {
                  ngToast.danger("Impossible de supprimer le batiment, des plans liés à celui-ci existent toujours.");
              }
          );
    };

    $scope.addBuilding = function (buildingToAdd) {
        $http.post("/buildings/", buildingToAdd)
            .then(
                function (data) {
                    ngToast.success("Batiment créé avec succès !");
                    $scope.addMode = false;
                    $scope.buildingToAdd = null;
                    getAllBuildings();
                },
                function (error) {
                    ngToast.danger("Une erreur est survenue lors de la création du batiment.")
                    $scope.addMode = false;
                }
            );
    };
});
app.controller('buildingController', function ($scope, $http, $routeParams) {
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
});

app.controller('personController', function ($scope, $http, ngToast) {

    function getAllEmployes() {
        $http({
            method: 'GET',
            url: '/persons'
        }).then(function (data) {
            // On stock dans persons la liste des personnes que nous renvoi l'api
            $scope.persons = data.data;
        }, function (error) {
            ngToast.danger("Une erreur est survenue lors de la récupération des employés.");
        });
    }
    getAllEmployes();

    /*
    Méthode servant à définir quel employé est sélectionné pour modification
     */
    $scope.personChoice = function (person) {
        $scope.personToModify = person;
    };

    /*
    Méthode utilisé pour modifier l'utilisateur
     */
    $scope.updatePerson = function  (personToModify) {
        $http.put("/persons/" + personToModify.id, personToModify)
            .then(
                function (data) {
                    ngToast.success("Modification réussie !");
                },
                function (error) {
                    ngToast.danger("Une erreur est survenue lors de la modification.");
                }
            );
    };

    /*
    Méthode permettant de supprimer l'employé correspondant
     */
    $scope.deletePerson = function (personToDelete) {
        $http.delete("/persons/" + personToDelete.id)
            .then(
                function (data) {
                    ngToast.success("Suppression réussie !");
                    getAllEmployes();
                },
                function (error) {
                    ngToast.danger("Une erreur est survenue lors de la suppression.");
                }
            );

    };

    $scope.addPerson = function (personToAdd) {
        $http.post("/persons/", personToAdd)
            .then(
                function (data) {
                    ngToast.success("Employé créé avec succès !");
                    $scope.addMode = false;
                    $scope.personToAdd = null;
                    getAllEmployes();
                },
                function (error) {
                    ngToast.danger("Une erreur est survenue lors de la création de l'employé.")
                    $scope.addMode = false;
                }
            );
    };

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

app.controller('mapController', function ($scope, $http, $routeParams) {

    $http({
        method: 'GET',
        url: '/maps/{id}'
    }).then(function (data) {
        $scope.map = data.data;
    }, function (error) {
        alert("ça passe pas du tout (map)");
    });
});

app.controller('createMapController', function ($scope, $http) {
    $http({
        method: 'POST',
        url: '/buildings',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        transformRequest: function (obj) {
            var str = [];
            for (var p in obj)
                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
            return str.join("&");
        },
        data: {username: $scope.userName, password: $scope.password}
    }).success(function () {
    });
});
