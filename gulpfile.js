var gulp = require("gulp"),                 // Include gulp
  del = require("del"),                     // Allow deleting the current working directory and files/folders outside it
  concat = require("gulp-concat"),          // Concatenate the passed file
  vsource = require("vinyl-source-stream"), // Creates a through stream which takes text as input, and emits a single vinyl file instance for streams down the pipeline to consume.

  less = require("gulp-less"),              // Compile the less files
  jshint = require("gulp-jshint"),          // Detect errors and potential problems in your JavaScript code.
  uglify = require("gulp-uglify"),          // gulp-uglify emits an 'error' event if it is unable to minify a specific file. Wherever popssible, the PluginError object will contain the following properties:
  gulpify = require("gulpify"),             // Alternative for browserify

  browserify = require("browserify"),       // Browserify will recursively analyze all the require() calls in your app in order to build a bundle you can serve up to the browser in a single <script> tag.
  connect = require("gulp-connect"),        // Gulp plugin to run a webserver (with LiveReload)
  pkg = require("./package.json");          // Include the dependencies

var Config = {
  srcdir: "UI",
  distdir: "public",
  assets: [
    "/lib/",
    "/css/",
    "/fonts/",
    "/images/",
    "/dummy-data/",
    "/views/"
  ]
};




/* Final build --------------------------------------------------------------------------------- */

gulp.task("default", function() {
  console.log("------------------------------------------------------");
  console.log("Project build file. Available Targets:\r\n");
  console.log([
    "default        Prints this help",
    "clean          Cleans the dist directory",
    "lessc          Compile less files",
    "build          Builds the app in dist directory",
    "serve/server   Build and start the server"
  ].join("\r\n"));
  console.log("------------------------------------------------------");
});

/* Clean --------------------------------------------------------------------------------------- */

gulp.task("clean", function(cb) {
  del([Config.distdir + "/**"], cb);
});

/* Less Compilation ---------------------------------------------------------------------------- */

gulp.task("lessc", function() {
  return gulp.src(Config.srcdir + "/app.less")
    .pipe(less())
    .pipe(gulp.dest(Config.distdir + "/css/"));
});

/* Tests --------------------------------------------------------------------------------------- */


/* Application Bundle -------------------------------------------------------------------------- */

gulp.task('build-scripts', function() {
  return  gulp.src(['./' + Config.srcdir + '/directives/*.js', './' + Config.srcdir + '/js/**.js', './' + Config.srcdir + '/js/**/*.js', './' + Config.srcdir + '/app.js'])
    .pipe(concat('app.js'))
    .pipe(uglify())
    .pipe(gulp.dest('./'+ Config.distdir +'/js/'));
});

gulp.task('assets', function() {
  Config.assets.forEach(function(ass) {
    console.log("assets : ", ass);
    return gulp.src(Config.srcdir + ass + "**/*.*")
      .pipe(gulp.dest(Config.distdir + ass));
  });
  //
});

gulp.task('index', function() {
  return gulp.src([Config.srcdir + "/*.{html, js, css, png, jpeg, jpg, gif, json}"])
    .pipe(gulp.dest(Config.distdir));
})

gulp.task('directive-views', function() {
  console.log("Entered in directive-views");
  // return gulp.src('./' + Config.srcdir + '/directives/html/*.*')
  //   .pipe(gulp.dest('./' + Config.distdir + '/views/'));
})


/* Final build --------------------------------------------------------------------------------- */

gulp.task("build", ["clean", "lessc", "build-scripts", "assets", "index", "directive-views"], function() {
  // copy other assets

  // return gulp.src('./' + Config.srcdir + '/directives/html/*.*')
  //   .pipe(gulp.dest('./' + Config.distdir + '/views/'));

});

/* Local server -------------------------------------------------------------------------------- */
gulp.task("server", function() {
  connect.server({
    root: "dist",
    post: 8080
  });
});

gulp.task("serve", ["server"]);
