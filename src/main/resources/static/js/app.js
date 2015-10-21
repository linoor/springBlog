/**
 * Created by linoor on 10/21/15.
 */

var app = angular.module('blog', ['ngRoute']);

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'partials/main.html'
    })
    .otherwise({
        redirectTo: "/"
    });
}]);