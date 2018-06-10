<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class GymLocation extends Model
{
    //
    protected $fillable = ['name', 'opening_time', 'closing_time', 'lat', 'long'];

    protected $table = 'gym_locations_92879';

    public function members() {
        return $this->hasMany('App\User');
    }
    
    public function instructors() {
        return $this->hasMany('App\Instructor');
    }

    public function workouts() {
        return $this->hasMany('App\Workout');
    }
}