app.controller('homeController', function ($scope) {
});

app.controller('modificationPlanController', function ($scope, $http, $routeParams,$compile,$timeout) {
    $scope.idPlan = $routeParams.id;
    $scope.outputContainer = "";
    $http({
        method: 'GET',
        url: '/maps/' + $scope.idPlan
    }).then(function (data) {
        $scope.map = data.data;
    }, function (error) {
        swal("Une erreur est survenue, veuillez réessayer plus tard.", "error");
    });
    $scope.saveMap = function () {
        removeStyleOfAllSelectedOffice();
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
        removeStyleOfAllSelectedOffice();
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

                    var heightOfOfficeImg = 100;
                    var widthOfOfficeImg = 200;

                    var heightOfMap = 578;

                    var leftPositionRelative = -((idBureau-1)*widthOfOfficeImg)+x-(widthOfOfficeImg/2);
                    var topPositionRelative = -heightOfMap+y-(heightOfOfficeImg/2);
                    var imgBureau= '<img data-id="'+idBureau+'" ng-click="setBureau(this)" src="../images/bureau.png" class="bureau-style" id="bureau-'+idBureau+'" style="opacity: 0.9;position: relative;top :'+topPositionRelative+'px;left :'+leftPositionRelative+'px;"/>';
                    var content = $compile(imgBureau)($scope);
                    $timeout(function(){
                        $scope.outputContainer += content.innerHTML;
                    });
                    $(".bureaux-container").html($(".bureaux-container").html()+imgBureau);
                    swal("Ajouté !", "Le bureau a bien été ajouté à votre plan.", "success");
                });
        }
    };
    $scope.setBureau = function(el){
        $scope.btnOk = true;
       // var myEl = angular.element( document.querySelector( '#div1' ) );
        //myEl.addClass('alpha');
        //removeStyleOfAllSelectedOffice();
        /*$("#"+el.getAttribute("id")).addClass('selectedOffice');
        $(".selected-office").html(getInfosBureau(el.dataset.id));*/
    }

    $scope.deskChoice = function (desk) {
        $scope.deskToModify = desk;
    };

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

        $http
            .post("/auth", data)
            .then(
                function (response) {
                    $scope.user = response.data;
                    console.log($scope.user);

                    var d = new Date($scope.user.expirationDate);
                    console.log(d);
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
app.controller('buildingController', function($scope, $http, $routeParams) {
    $scope.idBatiment = $routeParams.id;
    $http({
        method: 'GET',
        url: '/buildings/'+$scope.idBatiment
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

app.controller('mapsController', function ($scope, $http) {
    $http({
        method: 'GET',
        url: '/buildings'
    }).then(function (data) {
        $scope.buildings = data.data;
    }, function (error) {
        swal("Une erreur est survenue, Veuillez réessayer plus tard.","error").setDefaults({confirmButtonColor: '#ff4500'});
    });
});

app.controller('createMapController', function ($scope, $http,$routeParams) {
    $http({
        method: 'GET',
        url: '/buildings/'+$scope.idBatiment,
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        transformRequest: function(obj) {
            var str = [];
            for(var p in obj)
                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
            return str.join("&");
        },
        data: {username: $scope.userName, password: $scope.password}
    }).success(function () {});
});
app.directive('fileModel',function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
});

app.service('fileUpload',function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl,name,idBatiment){
        var fd = new FormData();
        fd.append('image', file);
        fd.append('name', name);
        fd.append('idBuilding',idBatiment);

        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(function (data) {
            document.location.href = "#/batiment/"+idBatiment;
        }, function (error) {
            swal("Le fichier téléchargé doit être une image de type jpeg ou png.", "error");
        });
    }
});
app.controller('nouveauPlanController', function ($scope, $http,$routeParams,fileUpload) {
    $scope.idBatiment = $routeParams.id;
    $http({
        method: 'GET',
        url: '/buildings/'+$routeParams.id
    }).then(function (data) {
        // On stock dans persons la liste des personnes que nous renvoi l'api
        $scope.building = data.data;
    }, function (error) {
        swal("Une erreur est survenue, veuillez réessayer plus tard.", "error");
    });
    $scope.addPlan = function(map){
        var file = $scope.imagePlan;

        console.log('file is ' );
        console.dir(file);

        var uploadUrl = "/maps";
        fileUpload.uploadFileToUrl(file, uploadUrl,map.name,$scope.idBatiment);
    };
});
