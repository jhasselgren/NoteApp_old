/**
 * Created by jhas on 2014-09-28.
 */
angular.module('noteApp.filters', [])
    .filter('jhHtml', function($sce){
       return function(text){
           return $sce.trustAsHtml(text);
       };
    });