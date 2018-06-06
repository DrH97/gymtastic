<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class GymLocation extends Model
{
    //

    protected $table = 'gym_locations_92879';

    public function members() {
        return $this->hasMany('App\User', 'workout_loc_id');
    }

    public function instructors() {
        return $this->hasMany('App\Instructor');
    }
}