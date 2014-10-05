/**
 * Created by jhas on 2014-09-27.
 */
module.exports = function(config) {
    config.set({

        preprocessors: {
            '**/*.html': 'ng-html2js',
            'app/views/**/*.js': ['jshint'],
            'app/app_filters/*.js': ['jshint'],
            'app/app_components/**/*.js' : ['jshint']
        },

        files: [
            'app/bower_components/jquery/dist/jquery.min.js',
            'app/bower_components/angular/angular.js',
            'app/bower_components/angular-mocks/angular-mocks.js',
            'app/bower_components/angular-route/angular-route.js',
            'app/bower_components/angular-sanitize/angular-sanitize.min.js',
            'app/bower_components/textAngular/dist/textAngular.min.js',
            'app/bower_components/angular-strap/dist/angular-strap.min.js',
            'app/bower_components/angular-strap/dist/angular-strap.tpl.min.js',
            'app/views/**/*.js',
            'app/app_filters/*.js',
            'app/app_components/**/*.js',
            'app/**/*.html'
        ],

        exclude: [
            'app/**/components/**/*.html'
        ],

        ngHtml2JsPreprocessor: {
            // strip app from the file path
            stripPrefix: 'app/',

            prependPrefix: '/'
            //moduleName: 'noteApp.templates'
        },

        plugins: ['karma-*', 'karma-ng-html2js-preprocessor'],

        autoWatch : true,

        frameworks: ['jasmine'],

        browsers : ['Chrome'],

        colors: true,

        logLevel: config.LOG_INFO,

        singleRun: false,

        reporters: ['dots', 'progress']


    });
};