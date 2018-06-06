<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Workout extends Model
{
    //

    protected $table = 'workouts_92879';

    public function user() {
        return $this->belongsTo('App\User');
    }
}
