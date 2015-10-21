/**
 * Created by linoor on 10/21/15.
 */

var app = angular.module('blog', ['ngRoute']);

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/:username', {
        templateUrl: 'partials/main.html',
        controller: blogController
    })
    .when('/:username/:articleId', {
        templateUrl: "/partials/main.html",
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
            $scope.articleId = $routeParams.articleId;
            console.log($scope.articleId);
            $scope.entries_filtered = $scope.entries.filter(function(entry) {
                return entry['id'] == $scope.articleId || typeof $scope.articleId === 'undefined'
            });
        }).error(function() {
            var errorMessage = "Could not display all entries.";
            $scope.error = errorMessage;
        });
    };
    $scope.getAllBlogs();
};
