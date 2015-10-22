/**
 * Created by linoor on 10/21/15.
 */

var app = angular.module('blog', ['ngRoute']);

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/:username/entries', {
        templateUrl: 'partials/entries.html',
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

var blogController = function($scope, $http, $routeParams) {

    $scope.getAllBlogs = function () {
        $scope.username = $routeParams.username;
        $http.get('/users/' + $scope.username + '/articles').success(function(response) {
            $scope.entries = response;

            // filter the blog entries
            $scope.entryId = $routeParams.entryId;
            console.log($scope.entryId);
            $scope.entries_filtered = $scope.entries.filter(function(entry) {
                return entry['id'] == $scope.entryId || typeof $scope.entryId === 'undefined'
            });
        }).error(function() {
            var errorMessage = "Could not display all entries.";
            $scope.error = errorMessage;
        });
    };
    $scope.getAllBlogs();
};
