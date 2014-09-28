/**
 * Created by jhas on 2014-09-27.
 */
module.exports = function(grunt){

    grunt.initConfig({
        karma: {
            unit: {
                configFile: 'config/karma.conf.js'
            }
        },
        watch: {
            karma: {
                files: [
                    'app/*.js',
                    'app/views/**/*.js',
                    'app/app_components/**/*.js'
                ],
                tasks: ['karma:unit:run']
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-karma');

    grunt.registerTask('devmode', ['karma:unit', 'watch']);

};
