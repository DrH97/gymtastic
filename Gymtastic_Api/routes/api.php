<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

// Route::middleware('auth:api')->get('/user', function (Request $request) {
//     return $request->user();
// });

// Route::namespace('Api\Auth')->middleware('api')->prefix('/v1/users')-> group(function() {
    
//     // Route::post('/login', function(Request $request) {
//     //     return 'true';
//     // });
//     Route::post('/logout', 'LoginController@logout');

//     Route::post('/register', 'RegisterController@register');
// });

Route::namespace('Api\V1')->prefix('v1')->group(function() {
    Route::get('/', function() {
        return view('welcome');
    });

    Route::apiResource('gyms', 'GymLocationController')->except(['store', 'destroy']);
    Route::prefix('gyms')->group(function() {
        Route::get('/{gym}/instructors', 'GymLocationController@gymInstructors');
        Route::get('/{gym}/members', 'GymLocationController@gymMembers');
    });

    Route::apiResource('instructors', 'InstructorController')->except(['store', 'destroy']);
    Route::prefix('instructors')->group(function() {
        Route::get('/{instructor}/gym', 'InstructorController@instructorGymLocation');
    });

    Route::get('users', function() {
        return 'Unauthorized';
    });
    Route::prefix('users')->group(function() {
        Route::post('/login', 'UserController@login');
        Route::post('/logout', 'UserController@logout');

        Route::post('/register', 'UserController@register');

        Route::get('/{user}/gyms', 'UserController@memberGymLocation');
        Route::get('/{user}/workouts', 'UserController@memberWorkouts');
    });
    Route::resource('users', 'UserController')->except(['index', 'store', 'destroy']);
});