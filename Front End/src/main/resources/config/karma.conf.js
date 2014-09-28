/**
 * Created by jhas on 2014-09-27.
 */
module.exports = function(config) {
    config.set({
        basePath: '../app',

        files: [
            'bower_components/angular/angular.js',
            'bower_components/angular-mocks/angular-mocks.js',
            'views/**/*.js',
            'app_components/**/*.js',
            'views/**/*.test.js',
            'app_components/**/*.tpl.html',
            'views/**/*.tpl.html'
        ],

        preprocessors: {
            'app/app_components/**/*.html': 'html2js'
        },

        ngHtml2JsPreprocessor: {
            // strip app from the file path
            stripPrefix: 'app/'
        },

        autoWatch : true,

        frameworks: ['jasmine'],

        browsers : ['Firefox'],

        colors: true,

        logLevel: config.LOG_DEBUG,

        singleRun: false,

        reporters: ['progress', 'html'],

        htmlReporter: {
            outputDir: 'karma_html',
            templatePath: 'node_modules/karma-html-reporter/jasmine_template.html'
        }


    });
};