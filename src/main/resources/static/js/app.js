/**
 * Created by linoor on 10/21/15.
 */

var app = angular.module('blog', ['ngRoute']);

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/:username', {
        templateUrl: 'partials/main.html',
        controller: blogController
    })
    .otherwise({
        redirectTo: "/"
    });
}]);

var blogController = function($scope, $http, $routeParams) {

    $scope.getAllBlogs = function () {
        console.log("")
        $http.get('/users/' + $routeParams.username + '/articles').success(function(response) {
            $scope.articles = response;
        }).error(function() {
            var errorMessage = "Could not display all articles.";
            $scope.error = errorMessage;
        });
    }
    $scope.getAllBlogs();
}