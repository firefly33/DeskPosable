app.controller('homeController', function ($scope) {
});

app.controller('modificationPlanController', function ($scope, $http, $routeParams, $compile, $timeout, ngToast) {
    $scope.idPlan = $routeParams.id;
    $scope.outputContainer = "";
    $scope.bureaux = [];
    function getCurrentMap() {
        $http({
            method: 'GET',
            url: '/maps/' + $scope.idPlan
        }).then(function (data) {
            $scope.map = data.data;
            $scope.cmpBureaux = $scope.map.desks.length;
        }, function (error) {
            swal("Une erreur est survenue, veuillez réessayer plus tard.", "error");
        });
    }

    getCurrentMap();

    function getAllPersons() {
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

    getAllPersons();

    $scope.saveMap = function () {
        removeStyleOfAllSelectedOffice();
        $http
            .post("desks/save/" + $scope.idPlan, $scope.bureaux)
            .then(
                function (data) {
                    $scope.bureaux = data.data;
                    getCurrentMap();
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
                    $scope.bureaux.push(new Bureau(null, "bureau", x, y));

                    var heightOfOfficeImg = 100;
                    var widthOfOfficeImg = 200;

                    var heightOfMap = 750;

                    var leftPositionRelative = -(($scope.cmpBureaux) * widthOfOfficeImg) + x - (widthOfOfficeImg / 2);
                    var topPositionRelative = -heightOfMap + y - (heightOfOfficeImg / 2);
                    var imgBureau = '<a href="#updateDesk" ng-click="deskChoice(desk)" class="modal-trigger" style="position: relative; top: ' + topPositionRelative + 'px; left: ' + leftPositionRelative + 'px;" onclick="event.preventDefault();">' +
                        '           <img data-id="' + $scope.cmpBureaux + '" ng-click="setBureau(this)" src="../images/bureau.png" class="bureau-style" id="bureau-' + $scope.cmpBureaux + '" style="opacity: 0.9;"/>' +
                        '           </a>';
                    var content = $compile(imgBureau)($scope);
                    $scope.cmpBureaux++;
                    $timeout(function () {
                        $scope.outputContainer += content.innerHTML;
                    });
                    $(".bureaux-container").html($(".bureaux-container").html() + imgBureau);
                    swal("Ajouté !", "Le bureau a bien été ajouté à votre plan.", "success");
                });
        }
    };
    $scope.setBureau = function (el) {
        $scope.btnOk = true;
        // var myEl = angular.element( document.querySelector( '#div1' ) );
        //myEl.addClass('alpha');
        //removeStyleOfAllSelectedOffice();
        /*$("#"+el.getAttribute("id")).addClass('selectedOffice');
        $(".selected-office").html(getInfosBureau(el.dataset.id));*/
    };

    function getItemsByDesk(desk) {
        $http({
            method: 'GET',
            url: '/items/byDesk/' + desk.id
        }).then(function (data) {
            $scope.items = data.data;
        }, function (error) {
            ngToast.danger("Une erreur est survenue lors de la récupération des items du bureau.");
        });
    }

    function getPersonByDesk(desk) {
        $http({
            method: 'GET',
            url: '/persons/byDesk/' + desk.id
        }).then(function (data) {
            $scope.person = data.data;
        }, function (error) {
            ngToast.danger("Une erreur est survenue lors de la récupération de l'employé.");
        });
    }

    function affectPersonToDesk(person, desk) {
        person.desk = desk;

        $http.put("/persons/" + person.id, person)
            .then(
                function (data) {
                },
                function (error) {
                    ngToast.danger("Une erreur est survenue lors de l'attribution du bureau à un collaborateur.");
                }
            );
    }

    $scope.deskChoice = function (desk) {
        $scope.deskToModify = desk;
        getItemsByDesk(desk);
        getPersonByDesk(desk);
    };

    $scope.updateDesk = function (deskToModify) {
        $http.put("/desks/" + deskToModify.id, deskToModify)
            .then(
                function (data) {
                    ngToast.success("Modification réussie !");
                },
                function (error) {
                    ngToast.danger("Une erreur est survenue lors de la modification.");
                }
            );

        // Contournement pour enlever l'ancien propriétaire du bureau
        var personToDelete = null;
        $http({
            method: 'GET',
            url: '/persons/byDesk/' + deskToModify.id
        }).then(function (data) {
            personToDelete = data.data;
            personToDelete.desk = null;
            $http.put("/persons/" + personToDelete.id, personToDelete)
                .then(
                    function (data) {
                        affectPersonToDesk($scope.person, deskToModify);
                    },
                    function (error) {
                        ngToast.danger("Une erreur est survenue lors de la désafectation.");
                    }
                );
        }, function (error) {
            ngToast.danger("Une erreur est survenue lors de la récupération de l'employé.");
        });

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
    };

    $scope.addItem = function (itemToAdd) {

        itemToAdd.desk = $scope.deskToModify;
        if ($scope.person != null) {
            itemToAdd.person = $scope.person;
        }

        $http.post("/items/", itemToAdd)
            .then(
                function (data) {
                    ngToast.success("Item ajouté avec succès !");
                    $scope.addMode = false;
                    $scope.personToAdd = null;
                    getItemsByDesk($scope.deskToModify);
                },
                function (error) {
                    ngToast.danger("Une erreur est survenue lors de la création de l'item.");
                    $scope.addMode = false;
                }
            );
    };

    $scope.deleteItem = function (itemToDelete) {
        $http.delete("/items/" + itemToDelete.id)
            .then(
                function (data) {
                    ngToast.success("Suppression réussie !");
                    getItemsByDesk($scope.deskToModify);
                },
                function (error) {
                    ngToast.danger("Une erreur est survenue lors de la suppression.");
                }
            );
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
app.controller('buildingController', function ($scope, $http, $routeParams) {
    $scope.idBatiment = $routeParams.id;
    $http({
        method: 'GET',
        url: '/buildings/' + $scope.idBatiment
    }).then(function (data) {
        $scope.building = data.data;
        $scope.maps = $scope.building.maps;
    }, function (error) {
        alert("ça passe pas (GET_BY_ID)");
    });
});

app.controller('desksController', function ($scope, $http, ngToast) {

    function getAllDesks() {
        $http({
            method: 'GET',
            url: '/desks'
        }).then(function (data) {
            $scope.desks = data.data;
        }, function (error) {
            ngToast.danger("Une erreur est survenue lors de la récupération des bureaux.");
        });
    }

    getAllDesks();

    $scope.deskChoice = function (desk) {
        $scope.deskToModify = desk;
    };

    $scope.updatedesk = function (deskToModify) {
        $http.put("/desks/" + deskToModify.id, deskToModify)
            .then(
                function (data) {
                    ngToast.success("Modification réussie !");
                },
                function (error) {
                    ngToast.danger("Une erreur est suvenue lors de la modification.");
                }
            );
    };

    $scope.deleteDesk = function (deskToDelete) {

        // LA ON MET LA DELETION EN CASACADE

        var idPersonne = $routeParams.id;
        $http({
            method: 'GET',
            url: '/persons/' + idPersonne
        }).then(function (data) {
            var personne = data.data;
        }, function (error) {
            alert("ça passe pas (GET_BY_ID)");
        });

        $http.delete("/desks/" + deskToDelete.id)
            .then(
                function (data) {
                    ngToast.success("Suppression réussie !");
                    getAllDesks();
                },
                function (error) {
                    ngToast.danger("Impossible de supprimer le batiment, des plans liés à celui-ci existent toujours.");
                }
            );
    };

    $scope.adddesk = function (deskToAdd) {
        $http.post("/desks/", deskToAdd)
            .then(
                function (data) {
                    ngToast.success("Batiment créé avec succès !");
                    $scope.addMode = false;
                    $scope.deskToAdd = null;
                    getAllDesks();
                },
                function (error) {
                    ngToast.danger("Une erreur est survenue lors de la création du batiment.")
                    $scope.addMode = false;
                }
            );
    };
});

app.controller('desksPerMapController', function($scope, $http, $routeParams) {
    $scope.idMap = $routeParams.id;
    $http({
        method: 'GET',
        url: '/maps/'+$scope.idMap
    }).then(function (data) {
        $scope.map = data.data;
    }, function (error) {
        swal("Une erreur est survenue, veuillez réessayer plus tard.", "error");
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
    $scope.updatePerson = function (personToModify) {
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

app.controller('mapsController', function ($scope, $http, fileUpload, ngToast) {

    function  getAllBuildings() {
        $http({
            method: 'GET',
            url: '/buildings'
        }).then(function (data) {
            $scope.buildings = data.data;
        }, function (error) {
            ngToast.danger("Une erreur est survenue lors de la récupération des bâtiments.");
            // swal("Une erreur est survenue, Veuillez réessayer plus tard.", "error").setDefaults({confirmButtonColor: '#ff4500'});
        });
    }

    getAllBuildings();

    $scope.createMap = function (mapToAdd) {
        var file = $scope.imagePlan;

        var uploadUrl = "/maps";
        fileUpload.uploadFileToUrl(file, uploadUrl, mapToAdd.name, mapToAdd.building.id);
    }
});

app.controller('createMapController', function ($scope, $http, $routeParams) {
    $http({
        method: 'GET',
        url: '/buildings/' + $scope.idBatiment,
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
app.directive('fileModel', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
});

app.service('fileUpload', function ($http) {
    this.uploadFileToUrl = function (file, uploadUrl, name, idBatiment) {
        var fd = new FormData();
        fd.append('image', file);
        fd.append('name', name);
        fd.append('idBuilding', idBatiment);

        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(function (data) {
            $http({
                method: 'GET',
                url: '/buildings'
            }).then(function (data) {
                $scope.buildings = data.data;
            }, function (error) {
                console.log("Une erreur est survenue lors de la récupération des bâtiments.");
               // ngToast.danger("Une erreur est survenue lors de la récupération des bâtiments.");
            });
        }, function (error) {
            console.log("Le fichier téléchargé doit être une image de type jpeg ou png.");
           // ngToast("Le fichier téléchargé doit être une image de type jpeg ou png.");
        });
    }
});

app.controller('nouveauPlanController', function ($scope, $http, $routeParams, fileUpload) {
    $scope.idBatiment = $routeParams.id;
    $http({
        method: 'GET',
        url: '/buildings/' + $routeParams.id
    }).then(function (data) {
        // On stock dans persons la liste des personnes que nous renvoi l'api
        $scope.building = data.data;
    }, function (error) {
        swal("Une erreur est survenue, veuillez réessayer plus tard.", "error");
    });
    $scope.addPlan = function (map) {
        var file = $scope.imagePlan;

        console.log('file is ');
        console.dir(file);

        var uploadUrl = "/maps";
        fileUpload.uploadFileToUrl(file, uploadUrl, map.name, $scope.idBatiment);
    };
});
