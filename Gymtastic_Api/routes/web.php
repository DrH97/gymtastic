<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Auth::routes();

Route::get('/', 'HomeController@index')->name('home');

Route::middleware('auth')->group(function() {

    Route::resource('gyms', 'GymLocationController');
    Route::prefix('gyms')->group(function() {
        Route::get('/{gym}/instructors', 'GymLocationController@gymInstructors');
        Route::get('/{gym}/members', 'GymLocationController@gymMembers');
    });

    Route::resource('workouts', 'WorkoutController');
    Route::prefix('workouts')->group(function() {
        Route::get('/{workout}/gym', 'WorkoutController@workoutGym');
        Route::get('/{workout}/member', 'WorkoutController@workoutMember');
    });

    Route::resource('instructors', 'InstructorController');
    Route::prefix('instructors')->group(function() {
        Route::get('/{instructor}/gyms', 'InstructorController@instructorGymLocation');
    });

    Route::resource('users', 'UserController');
    Route::prefix('users')->group(function() {
        Route::get('/{user}/gyms', 'UserController@memberGymLocation');
        Route::get('/{user}/workouts', 'UserController@memberWorkouts');
    });
});