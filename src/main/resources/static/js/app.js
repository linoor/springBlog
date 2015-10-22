/**
 * Created by linoor on 10/21/15.
 */

var app = angular.module('blog', ['ngRoute']);

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/:username/entries', {
        templateUrl: 'partials/entries.html',
        controller: blogController
    })
    .when('/:username/entries/new', {
        templateUrl: "partials/new_entry.html",
        controller: blogController
    })
    .when('/:username/entries/:entryId', {
        templateUrl: "/partials/entries.html",
        controller: blogController
    })
    .otherwise({
        redirectTo: "/"
    });
}]);

var blogController = function($scope, $http, $routeParams, $location) {
    $scope.username = $routeParams.username;

    $scope.getAllEntries = function () {
        $http.get('/users/' + $scope.username + '/entries').success(function(response) {
            $scope.entries = response;

            // filter the blog entries
            $scope.entryId = $routeParams.entryId;
            $scope.entries_filtered = $scope.entries.filter(function(entry) {
                return entry['id'] == $scope.entryId || typeof $scope.entryId === 'undefined'
            });
        }).error(function() {
            var errorMessage = "Could not display all entries.";
            $scope.error = errorMessage;
        });
    };

    $scope.addEntry = function() {
        $http.post('/users/' + $scope.username + '/entries', {
            'title': $scope.title,
            'body': $scope.body
        }).success(function (data, status, headers, config) {
           // redirect to the new entry
           $scope.title = '';
           $scope.body = '';
           var location = headers()['location'];
           $location.path(location.substr(location.indexOf($scope.username)));
        })
    }

    $scope.getAllEntries();
};
